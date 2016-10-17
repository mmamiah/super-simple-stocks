package com.stocks.model;

import java.math.BigDecimal;
import java.util.Date;
import com.stocks.common.StockConstants;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;

/**
 * This class represent a trade
 */
public class Trade {

	private StockSymbol symbol;
	private Date timeStamp;
	private int quantityOfShare;
	private BuySellIndicator indicator;
	private BigDecimal price;

	public Trade(){
		this.symbol = StockSymbol.NONE;
		setPrice(BigDecimal.ZERO);
		setQuantityOfShare(0);
		setTimeStamp(new Date());
	}

	public Trade(StockSymbol symbol, BigDecimal price, BuySellIndicator indicator, int quantityOfShare){
		setSymbol(symbol);
		setPrice(price);
		setIndicator(indicator);
		setQuantityOfShare(quantityOfShare);
		setTimeStamp(new Date());
	}

	public Trade(StockSymbol symbol, BigDecimal price, int quantityOfShare){
		setSymbol(symbol);
		setPrice(price);
		setIndicator(BuySellIndicator.NONE);
		setQuantityOfShare(quantityOfShare);
		setTimeStamp(new Date());
	}

	public StockSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(StockSymbol symbol) {
		if(symbol == null) throw new IllegalArgumentException("StockSymbol cannot be Null");
		this.symbol = symbol;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getQuantityOfShare() {
		return quantityOfShare;
	}

	public void setQuantityOfShare(int quantityOfShare) {
		if(quantityOfShare < 0) throw new IllegalArgumentException("The quantity of share cannot negative.");
		this.quantityOfShare = quantityOfShare;
	}

	public BuySellIndicator getIndicator() {
		return indicator;
	}

	public void setIndicator(BuySellIndicator indicator) {
		this.indicator = indicator;
	}

	public BigDecimal getPrice() {
		return price.setScale(StockConstants.DECIMALS, StockConstants.ROUNDING_MODE);
	}

	public void setPrice(BigDecimal price) {
		if(price == null){
			throw new IllegalArgumentException("The price cannot be null.");
		}
		else if(price.compareTo(BigDecimal.ZERO) < 0){
			throw new IllegalArgumentException("The price cannot negative.");
		}
		this.price = price;
	}

}
