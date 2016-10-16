package com.stocks.simpleStock;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.Map;
import com.stocks.enums.BuySellIndicator;
import com.stocks.model.Stock;
import com.stocks.enums.StockSymbol;
import com.stocks.model.Trade;

/**
 * This is the interface for the Stock Exchange family
 */
public interface StockExchange {

	public Map<StockSymbol, Stock> getStocks();

	public Stock findStock(StockSymbol symbol);
	
	public void recordTrade(StockSymbol symbol, BigDecimal price, BuySellIndicator indicator, int quantityOfTrade);
	
	public Deque<Trade> findTrades(StockSymbol symbol);
	
}
