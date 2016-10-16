package com.stocks.simpleStock;

import java.util.Map;
import com.stocks.core.Stock;
import com.stocks.enums.StockSymbol;

/**
 * This is the interface for the Stock Exchange family
 */
public interface StockExchange {

	public Map<StockSymbol, Stock> getStocks();

	public Stock findStock(StockSymbol symbol);
}
