package com.stocks.stockFormulaService.impl;

import java.math.BigDecimal;
import com.stocks.core.Stock;
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
			result = result.divide(tickerPrice, 9, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
		}
		return result;
	}
	
}
