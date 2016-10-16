package com.stocks.stockFormulaService.impl;

import java.math.BigDecimal;
import com.stocks.common.StockConstants;
import com.stocks.model.Stock;
import com.stocks.enums.StockType;
import com.stocks.stockFormulaService.StockFormulaService;
import org.springframework.stereotype.Service;

/**
 * Calculate the Dividend Yield Preferred
 */
@Service
public class DividendYieldPreferredImpl implements StockFormulaService {

	@Override
	public String getFormulaName() {
		return "Dividend Yield";
	}

	@Override
	public StockType getStockType() {
		return StockType.PREFERRED;
	}

	@Override
	public BigDecimal computeValue(BigDecimal tickerPrice, Stock stock) {
		BigDecimal result = BigDecimal.ZERO;
		if(tickerPrice != null && BigDecimal.ZERO.compareTo(tickerPrice) != 0){
			result = stock.getFixedDividend().multiply(stock.getParValue());
		}

		if(! BigDecimal.ZERO.equals(result)){
			result = result
					.divide(tickerPrice, StockConstants.DECIMALS, StockConstants.ROUNDING_MODE)
					.stripTrailingZeros();
		}
		return result;
	}
	
}
