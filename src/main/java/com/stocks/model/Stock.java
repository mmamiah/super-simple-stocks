package com.stocks.model;

import java.math.BigDecimal;
import com.stocks.enums.StockSymbol;

/**
 * This class represent a Stock
 */
public class Stock {
	
	private StockSymbol symbol;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal parValue;

	public Stock(){
		symbol = StockSymbol.NONE;
		lastDividend = BigDecimal.ZERO;
		fixedDividend = BigDecimal.ZERO;
		parValue = BigDecimal.ZERO;
	}
	
	public Stock(StockSymbol symbol, BigDecimal lastDividend, BigDecimal fixedDividend, BigDecimal parValue){
		this.symbol = symbol;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	public StockSymbol getSymbol() {
		return symbol;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

}
