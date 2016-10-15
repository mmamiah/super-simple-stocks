package com.stocks.simpleStock;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.stocks.core.Stock;
import com.stocks.core.Trade;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockType;
import com.stocks.shareIndexService.ShareIndexService;
import com.stocks.stockFormulaService.StockFormulaService;
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
	@Qualifier("geometricMeanImpl")
	private ShareIndexService geometricMean;

	@Autowired
	@Qualifier("stockPriceImpl")
	private ShareIndexService stockPriceService;
	
	/** Map<StockSymbol, Deque<Trade>> */
	private Map<String, Deque<Trade>> tradeHistory = Maps.newHashMap();
//	tradeHistory = Lists.newLinkedList();

	private Map<StockType, StockFormulaService> stockFormulas = Maps.newHashMap();
	
	@Autowired
	private void init(List<StockFormulaService> formulas){
		for(StockFormulaService formula: formulas){
			stockFormulas.put(formula.getStockType(), formula);
		}
	}
	
	/* Calculate The Dividend Yield for a given Stock */
	public BigDecimal calculateDividendYield(BigDecimal tickerPrice, Stock stock){
		StockFormulaService formula = stockFormulas.get(stock.getType());
		return formula.computeValue(tickerPrice, stock);
	}

	/* Calculate The P/E Ratio for a given Stock */
	public BigDecimal calculatePeRatio(BigDecimal tickerPrice, Stock stock){
		return peRatioService.computeValue(tickerPrice, stock);
	}

	/* Record a Trade for a given StockSymbol */
	public void recordTrade(Stock stock, BuySellIndicator indicator, BigDecimal tickerPrice){
		Deque<Trade> trades = tradeHistory.get(stock.getSymbol());
		int quantityOfShare = 0;
		if(CollectionUtils.isNotEmpty(trades)){
			quantityOfShare = trades.size();
		}
		else if (trades == null){
			trades = Lists.newLinkedList();
			quantityOfShare = 1;
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

		List<Trade> max15minAgedTrades = Lists.newArrayList();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -15); // Subtract 15 min in actual time
		for(Trade trade: trades){
			if(isValidTradeAge(trade, cal.getTime())){
				max15minAgedTrades.add(trade);
			}
		}
		
		return stockPriceService.compute(max15minAgedTrades);
		
	}

	/* Calculate the Geometric Mean on based Stock */
	public BigDecimal calculateGeometricMean(){
		List<Trade> foundTrades = Lists.newArrayList();
		for(String symbol: tradeHistory.keySet()){
			Deque<Trade> trades = tradeHistory.get(symbol);
			if(CollectionUtils.isNotEmpty(trades)){
				foundTrades.addAll(trades);
			}
		}
		
		return geometricMean.compute(foundTrades);
	}

	private static boolean isValidTradeAge(Trade trade, Date deadLine) {
		boolean isAfter = trade.getTimeStamp().after(deadLine);
		boolean isEqual = trade.getTimeStamp().equals(deadLine);
		return isAfter || isEqual;
	}

}
