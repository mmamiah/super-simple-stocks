package com.stocks.simpleStock.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stocks.core.Stock;
import com.stocks.core.Trade;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;
import com.stocks.enums.StockType;
import com.stocks.geometricMean.GeometricMeanService;
import com.stocks.simpleStock.StockExchange;
import com.stocks.stockFormulaService.StockFormulaService;
import com.stocks.stockPriceService.StockPriceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The Super Simple Stock Manager
 */
@Component
@Scope("prototype")
public class SuperSimpleStockManager {
	
	@Autowired
	@Qualifier("peRatioImpl")
	private StockFormulaService peRatioService;
	
	@Autowired
	private GeometricMeanService geometricMean;

	@Autowired
	private StockPriceService stockPriceService;
	
	@Autowired
	private StockExchange bgce;
	
	/** Map<StockSymbol, Deque<Trade>> */
	private Map<StockSymbol, Deque<Trade>> tradeHistory = Maps.newHashMap();

	private Map<StockType, StockFormulaService> stockFormulas = Maps.newHashMap();
	
	@Autowired
	private void init(List<StockFormulaService> formulas){
		for(StockFormulaService formula: formulas){
			stockFormulas.put(formula.getStockType(), formula);
		}
	}
	
	/* Calculate The Dividend Yield for a given Stock */
	public BigDecimal calculateDividendYield(BigDecimal tickerPrice, Stock stock){
		StockFormulaService formula = stockFormulas.get(stock.getSymbol().getType());
		return formula.computeValue(tickerPrice, stock);
	}

	/* Calculate The P/E Ratio for a given Stock */
	public BigDecimal calculatePeRatio(BigDecimal tickerPrice, Stock stock){
		return peRatioService.computeValue(tickerPrice, stock);
	}

	/* Record a Trade for a given StockSymbol */
	public void recordTrade(Stock stock, BuySellIndicator indicator, BigDecimal tickerPrice, int quantityOfShare){
		Deque<Trade> trades = tradeHistory.get(stock.getSymbol());
		if(trades == null){
			trades = Lists.newLinkedList();
			tradeHistory.put(stock.getSymbol(), trades);
		}
		Trade trade = new Trade(tickerPrice, indicator, quantityOfShare);
		trades.addLast(trade);
	}
	
	/* Calculate Stock Price based on trade recorded in pass 15 min */
	public BigDecimal calculateStockPrice(Stock stock){
		Deque<Trade> trades = tradeHistory.get(stock.getSymbol());
		if(CollectionUtils.isEmpty(trades)){
			return BigDecimal.ZERO;
		}

		List<Trade> max15minAgedTrades = retrieveMax15MinAgedTrade(trades);
		
		return stockPriceService.compute(max15minAgedTrades);
		
	}

	/* Calculate the Geometric Mean on based BGCE Stocks */
	public BigDecimal calculateBgceGeometricMean(){
		if(bgce.getStocks().isEmpty()){
			return BigDecimal.ZERO;
		}
		
		List<Stock> stocks = Lists.newArrayList(bgce.getStocks().values());
		return geometricMean.compute(stocks);
	}

	private static List<Trade> retrieveMax15MinAgedTrade(Deque<Trade> trades) {
		List<Trade> result = Lists.newArrayList();
		Date now = new Date();
		boolean is15MinAge = true;
		Iterator<Trade> tradeIterator = trades.descendingIterator();
		while(tradeIterator.hasNext() && is15MinAge){
			Trade trade = tradeIterator.next();
			is15MinAge = isMax15MinAgedTrade(trade, now);
			if(is15MinAge){
				result.add(trade);
			}
		}
		return result;
	}

	private static boolean isMax15MinAgedTrade(Trade trade, Date now) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -15); // Subtract 15 min in actual time
		Date deadLine = cal.getTime();
		
		boolean isAfter = trade.getTimeStamp().after(deadLine);
		boolean isEqual = trade.getTimeStamp().equals(deadLine);
		return isAfter || isEqual;
	}

}
