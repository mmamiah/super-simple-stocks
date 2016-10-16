package com.stocks.enums;

/**
 * This class represent the Stock Symbol
 */
public enum StockSymbol {

	NONE ("NONE", StockType.NONE),
	TEA ("TEA", StockType.COMMON),
	POP ("POP", StockType.COMMON),
	ALE ("ALE", StockType.COMMON),
	GIN ("GIN", StockType.PREFERRED),
	JOE ("JOE", StockType.COMMON);
	
	private String code;
	private StockType type;
	
	StockSymbol(String code, StockType type){
		this.code = code;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public StockType getType() {
		return type;
	}
}
