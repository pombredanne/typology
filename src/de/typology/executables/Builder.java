package de.typology.executables;

import de.typology.smoother.Absolute_Aggregator;
import de.typology.smoother._absoluteSplitter;
import de.typology.smoother._absolute_Aggregator;
import de.typology.splitter.DataSetSplitter;
import de.typology.splitter.GLMSplitter;
import de.typology.splitter.IndexBuilder;
import de.typology.splitter.NGramSplitter;
import de.typology.splitter.NGramSplitterWithCount;
import de.typology.splitter.TypoSplitter;
import de.typology.splitter.TypoSplitterWithCount;
import de.typology.stats.StatsBuilder;
import de.typology.utils.Config;
import de.typology.utils.IOHelper;

public class Builder {

	public void build(String outputPath) {

		String indexFileName = "index.txt";
		String statsFileName = "stats.txt";
		String trainingFileName = "training.txt";
		String learningFileName = "learning.txt";
		String testingFileName = "testing.txt";

		if (Config.get().splitData) {
			// index and stats are empty since they don't exist at the moment
			DataSetSplitter dss = new DataSetSplitter(outputPath, "", "",
					"normalized.txt");
			dss.split(trainingFileName, learningFileName, testingFileName,
					Config.get().modelLength);
		}
		// TODO: add stats

		if (Config.get().buildIndex) {
			IndexBuilder ib = new IndexBuilder();
			ib.buildIndex(outputPath + trainingFileName, outputPath
					+ indexFileName, outputPath + statsFileName);
		}

		if (Config.get().buildNGrams) {
			NGramSplitter ngs = new NGramSplitter(outputPath, indexFileName,
					statsFileName, trainingFileName);
			ngs.split(Config.get().modelLength);
		}
		if (Config.get().buildTypoEdges) {
			TypoSplitter ts = new TypoSplitter(outputPath, indexFileName,
					statsFileName, trainingFileName);
			ts.split(Config.get().modelLength);
		}
		if (Config.get().buildGLM) {
			GLMSplitter glms = new GLMSplitter(outputPath, indexFileName,
					statsFileName, trainingFileName);
			glms.splitGLMForKneserNey(Config.get().modelLength);
		}
		if (Config.get().build_absoluteGLM) {
			_absoluteSplitter _absoluteSplitter = new _absoluteSplitter(
					outputPath, "absolute", "_absolute-unaggregated",
					"index.txt", "stats.txt", "training.txt", false);
			try {
				_absoluteSplitter.split(Config.get().modelLength);
			} catch (Exception e) {
				int mb = 1024 * 1024;
				// Getting the runtime reference from system
				Runtime runtime = Runtime.getRuntime();

				IOHelper.log("##### Heap utilization statistics [MB] #####");

				// Print used memory
				IOHelper.log("Used Memory:\t"
						+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

				// Print free memory
				IOHelper.log("Free Memory:\t" + runtime.freeMemory() / mb);

				// Print total available memory
				IOHelper.log("Total Memory:\t" + runtime.totalMemory() / mb);

				// Print Maximum available memory
				IOHelper.log("Max Memory:\t" + runtime.maxMemory() / mb);
			}
		}

		if (Config.get().aggregateAbsolute_) {
			Absolute_Aggregator absolute_Aggregator = new Absolute_Aggregator(
					outputPath, "absolute", "absolute_");
			absolute_Aggregator.aggregate(Config.get().modelLength);
			_absolute_Aggregator _absolute_Aggregator = new _absolute_Aggregator(
					outputPath, "_absolute", "_absolute_");
			_absolute_Aggregator.aggregate(Config.get().modelLength);
		}
	}

	public void buildFromNGrams(String outputPath) {
		String indexFileName = "index.txt";
		String statsFileName = "stats.txt";
		String trainingFileName;
		NGramSplitterWithCount ngs;
		// no splitting into training/learning/testing since there are only
		// ngrams

		if (Config.get().buildNGrams) {
			StatsBuilder sb = new StatsBuilder();
			sb.buildStats(outputPath + "1gram-normalized.txt", outputPath
					+ "stats.txt");
		}
		// no index building; use index from another file instead

		if (Config.get().buildNGrams) {
			for (int i = 1; i <= Config.get().modelLength; i++) {
				trainingFileName = i + "gram-normalized.txt";
				ngs = new NGramSplitterWithCount(outputPath, indexFileName,
						statsFileName, trainingFileName);
				ngs.split(i);
			}
		}
		if (Config.get().buildTypoEdges) {
			for (int i = 1; i <= Config.get().modelLength; i++) {
				trainingFileName = i + "gram-normalized.txt";
				TypoSplitterWithCount ts = new TypoSplitterWithCount(
						outputPath, indexFileName, statsFileName,
						trainingFileName);
				ts.split(i);
			}
		}
	}
}
