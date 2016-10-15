package com.stocks.core;

import java.math.BigDecimal;
import java.util.Date;
import com.stocks.enums.BuySellIndicator;

/**
 * This class represent a trade
 */
public class Trade {

	private Date timeStamp;
	private int quantityOfShare;
	private BuySellIndicator indicator;
	private BigDecimal price;

	public Trade(){
		setPrice(BigDecimal.ZERO);
		setQuantityOfShare(0);
		setTimeStamp(new Date());
	}

	public Trade(BigDecimal price, BuySellIndicator indicator, int quantityOfShare){
		setPrice(price);
		setIndicator(indicator);
		setQuantityOfShare(quantityOfShare);
		setTimeStamp(new Date());
	}

	public Trade(BigDecimal price, int quantityOfShare){
		setPrice(price);
		setIndicator(BuySellIndicator.NONE);
		setQuantityOfShare(quantityOfShare);
		setTimeStamp(new Date());
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
		return price;
	}

	public void setPrice(BigDecimal price) {
		if(price == null) throw new IllegalArgumentException("The price cannot be null.");
		if(price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("The price cannot negative.");
		this.price = price;
	}

}
