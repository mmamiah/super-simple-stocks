package com.stocks.simpleStock.impl;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Map;
import com.google.common.collect.Maps;
import com.stocks.core.Stock;
import com.stocks.enums.StockSymbol;
import com.stocks.simpleStock.StockExchange;
import org.springframework.stereotype.Component;

/**
 * Global Beverage Corp Implementation for StockExchange
 */
@Component
public class GlobalBeverageCorporationImpl implements StockExchange {

	private Map<StockSymbol, Stock> stocks;

	@PostConstruct
	private void initStocks(){
		stocks = Maps.newHashMap();
		
		Stock stockTEA = new Stock(StockSymbol.TEA, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(100));
		stocks.put(stockTEA.getSymbol(), stockTEA);
		
		Stock stockPOP = new Stock(StockSymbol.POP, BigDecimal.valueOf(8), BigDecimal.ONE, BigDecimal.valueOf(100));
		stocks.put(stockPOP.getSymbol(), stockPOP);
		
		Stock stockALE = new Stock(StockSymbol.ALE, BigDecimal.valueOf(23), BigDecimal.ONE, BigDecimal.valueOf(60));
		stocks.put(stockALE.getSymbol(), stockALE);
		
		Stock stockGIN = new Stock(StockSymbol.GIN, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stocks.put(stockGIN.getSymbol(), stockGIN);
		
		Stock stockJOE = new Stock(StockSymbol.JOE, BigDecimal.valueOf(13), BigDecimal.ONE, BigDecimal.valueOf(250));
		stocks.put(stockJOE.getSymbol(), stockJOE);
	}

	@Override
	public Map<StockSymbol, Stock> getStocks() {
		return stocks;
	}

	@Override
	public Stock findStock(StockSymbol symbol) {
		return stocks.get(symbol);
	}
}
