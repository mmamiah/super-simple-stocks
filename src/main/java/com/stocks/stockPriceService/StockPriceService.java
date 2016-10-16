package com.stocks.stockPriceService;

import java.math.BigDecimal;
import java.util.List;
import com.stocks.core.Trade;

/**
 * Interface for Stock price Service
 */
public interface StockPriceService {
	
	public BigDecimal compute(List<Trade> trades);
	
}
