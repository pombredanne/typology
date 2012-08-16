package de.typology.lexerParser;

public enum Token {
	OBJECT, STRING, OTHER, WS, LINESEPERATOR, URI, EOL, EOF, FULLSTOP, COMMA,
	//
	INFOBOX,
	//
	LINK, LABELEDLINK,
	//
	PAGE, CLOSEDPAGE,
	//
	TITLE, CLOSEDTITLE,
	//
	TEXT, CLOSEDTEXT
}