package com.stocks.simpleStock.impl;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Deque;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;
import com.stocks.model.Stock;
import com.stocks.model.Trade;
import com.stocks.simpleStock.StockExchange;
import org.springframework.stereotype.Component;

/**
 * Global Beverage Corp Implementation for StockExchange
 */
@Component
public class GlobalBeverageCorporationImpl implements StockExchange {

	private Map<StockSymbol, Stock> stocks;
	private Map<StockSymbol, Deque<Trade>> trades;

	@PostConstruct
	private void initStockExchange(){
		stocks = initStocks();
		trades = Maps.newHashMap();
	}

	@Override
	public Map<StockSymbol, Stock> getStocks() {
		return stocks;
	}

	@Override
	public Stock findStock(StockSymbol symbol) {
		return stocks.get(symbol);
	}

	@Override
	public void recordTrade(StockSymbol symbol, BigDecimal price, BuySellIndicator indicator, int quantityOfTrade) {
		Deque<Trade> symbolTrades = findTrades(symbol);
		if(symbolTrades == null){
			symbolTrades = Lists.newLinkedList();
			trades.put(symbol, symbolTrades);
		}
		Trade trade = new Trade(symbol, price, indicator, quantityOfTrade);
		symbolTrades.addLast(trade);
	}

	@Override
	public Deque<Trade> findTrades(StockSymbol symbol) {
		Deque<Trade> result = null;
		if(stocks.get(symbol) == null){
			result = Lists.newLinkedList();
		}
		else{
			result = trades.get(symbol);
		}
		
		return result;
	}

	private static Map<StockSymbol, Stock> initStocks() {
		Map<StockSymbol, Stock> result = Maps.newHashMap();

		Stock stockTEA = new Stock(StockSymbol.TEA, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(100));
		result.put(stockTEA.getSymbol(), stockTEA);

		Stock stockPOP = new Stock(StockSymbol.POP, BigDecimal.valueOf(8), BigDecimal.ONE, BigDecimal.valueOf(100));
		result.put(stockPOP.getSymbol(), stockPOP);

		Stock stockALE = new Stock(StockSymbol.ALE, BigDecimal.valueOf(23), BigDecimal.ONE, BigDecimal.valueOf(60));
		result.put(stockALE.getSymbol(), stockALE);

		Stock stockGIN = new Stock(StockSymbol.GIN, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		result.put(stockGIN.getSymbol(), stockGIN);

		Stock stockJOE = new Stock(StockSymbol.JOE, BigDecimal.valueOf(13), BigDecimal.ONE, BigDecimal.valueOf(250));
		result.put(stockJOE.getSymbol(), stockJOE);
		return result;
	}
	
}
