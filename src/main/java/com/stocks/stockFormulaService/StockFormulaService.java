package com.stocks.stockFormulaService;

import java.math.BigDecimal;
import com.stocks.model.Stock;
import com.stocks.enums.StockType;

/**
 * Service to calculate Dividend Yield
 */
public interface StockFormulaService {
	
	public String getFormulaName();
	
	public StockType getStockType();
	
	public BigDecimal computeValue(BigDecimal tickerPrice, Stock stock);
	
}
