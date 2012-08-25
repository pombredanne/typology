package de.typology.lexerParser;

public enum WikipediaToken {
	OBJECT, STRING, OTHER, WS, LINESEPARATOR, URI, EOF, FULLSTOP, COMMA, SEMICOLON, COLON, EQUALITYSIGN, ASTERISK, HYPHEN, EXCLAMATIONMARK, QUESTIONMARK, QUOTATIONMARK, VERTICALBAR, SLASH,
	//
	BRACKET, CLOSEDBRACKET, BRACKETS, CURLYBRACKET, CLOSEDCURLYBRACKET, CURLYBRACKETS, SQUAREDBRACKET, CLOSEDSQUAREDBRACKET,
	//
	LESSTHAN, GREATERTHAN,
	//
	EHH, // EHH = !--
	HH, // HH = --
	//
	ELEMENT, CLOSEDELEMENT,
	//
	LINK, LABELEDLINK,
	//
	PAGE, TITLE, TEXT, REF,
	//
	CLOSEDPAGE, CLOSEDTITLE, CLOSEDTEXT, CLOSEDREF,
	//
	//
	DISAMBIGUATION, TOC,
	//
	AUDIO // {Audio|...|...}
}
