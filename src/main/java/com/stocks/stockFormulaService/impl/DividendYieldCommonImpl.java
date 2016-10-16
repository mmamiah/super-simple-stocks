package com.stocks.stockFormulaService.impl;

import java.math.BigDecimal;
import com.stocks.common.StockConstants;
import com.stocks.model.Stock;
import com.stocks.enums.StockType;
import com.stocks.stockFormulaService.StockFormulaService;
import org.springframework.stereotype.Service;

/**
 * Calculate the Dividend Yield Common
 */
@Service
public class DividendYieldCommonImpl implements StockFormulaService {

	@Override
	public String getFormulaName() {
		return "Dividend Yield";
	}

	@Override
	public StockType getStockType() {
		return StockType.COMMON;
	}

	@Override
	public BigDecimal computeValue(BigDecimal tickerPrice, Stock stock) {
		BigDecimal result = BigDecimal.ZERO;
		if(tickerPrice != null && BigDecimal.ZERO.compareTo(tickerPrice) != 0){
			result = stock.getLastDividend()
					.divide(tickerPrice, StockConstants.DECIMALS, StockConstants.ROUNDING_MODE)
					.stripTrailingZeros();
		}

		return result;
	}
	
}
