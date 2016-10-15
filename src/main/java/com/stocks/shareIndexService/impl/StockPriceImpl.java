package com.stocks.shareIndexService.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import com.stocks.core.Trade;
import com.stocks.shareIndexService.ShareIndexService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * Stock Price Implementation
 */
@Service
public class StockPriceImpl implements ShareIndexService {

	@Override
	public BigDecimal compute(List<Trade> trades) {
		BigDecimal stockPriceQuantitySum = BigDecimal.ZERO;
		double sumOfQuantities = 0;

		if(CollectionUtils.isNotEmpty(trades)){
			for(Trade t: trades){
				BigDecimal quantity = BigDecimal.valueOf(t.getQuantityOfShare());
				stockPriceQuantitySum = stockPriceQuantitySum.add(t.getPrice().multiply(quantity));

				sumOfQuantities += t.getQuantityOfShare();
			}
		}

		BigDecimal result = BigDecimal.ZERO;
		if(sumOfQuantities != 0){
			BigDecimal bgQty = BigDecimal.valueOf(sumOfQuantities);
			result = stockPriceQuantitySum.divide(bgQty, 9, RoundingMode.HALF_UP).stripTrailingZeros();
		}
		
		return result;
	}
	
}
