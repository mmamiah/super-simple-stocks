package com.stocks.stockFormulaService.impl;

import java.math.BigDecimal;
import com.stocks.common.StockConstants;
import com.stocks.model.Stock;
import com.stocks.enums.StockType;
import com.stocks.stockFormulaService.StockFormulaService;
import org.springframework.stereotype.Service;

/**
 * Compute P/E Ratio
 */
@Service
public class PeRatioImpl implements StockFormulaService {
	
	@Override
	public String getFormulaName() {
		return "P/E Ratio";
	}

	@Override
	public StockType getStockType() {
		return StockType.NONE;
	}

	@Override
	public BigDecimal computeValue(BigDecimal tickerPrice, Stock stock) {
		boolean validTickerPrice = tickerPrice != null && BigDecimal.ZERO.compareTo(tickerPrice) != 0;
		boolean validStockSymbol = stock != null && !BigDecimal.ZERO.equals(stock.getLastDividend());
		if(!validTickerPrice || !validStockSymbol){
			return BigDecimal.ZERO;
		}
		return tickerPrice
				.divide(stock.getLastDividend(), StockConstants.DECIMALS, StockConstants.ROUNDING_MODE)
				.stripTrailingZeros();
	}
}
