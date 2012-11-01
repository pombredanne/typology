package de.typology.lexerParser;

import java.io.File;
import java.io.IOException;

import de.typology.utils.Config;

public class WikipediaMain {

	/**
	 * @author Martin Koerner
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		WikipediaTokenizer tokenizer = new WikipediaTokenizer(
				Config.get().wikiXmlPath);
		WikipediaRecognizer recognizer = new WikipediaRecognizer(tokenizer);
		WikipediaParser parser = new WikipediaParser(recognizer,
				Config.get().parsedWikiOutputPath);
		System.out.println("start parsing");
		parser.parse();
		System.out.println("parsing done");
		System.out.println("start cleanup");
		WikipediaNormalizer wn = new WikipediaNormalizer(
				Config.get().parsedWikiOutputPath,
				Config.get().normalizedWikiOutputPath);
		wn.normalize();
		System.out.println("cleanup done");
		System.out.println("generate indicator file");
		File done = new File(Config.get().normalizedWikiOutputPath + "IsDone");
		done.createNewFile();
		System.out.println("done");
	}

}