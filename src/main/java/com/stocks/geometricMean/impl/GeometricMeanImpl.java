package com.stocks.geometricMean.impl;

import java.math.BigDecimal;
import java.util.List;
import com.stocks.common.StockConstants;
import com.stocks.model.Stock;
import com.stocks.geometricMean.GeometricMeanService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * GeometricMean Implementation
 */
@Service
public class GeometricMeanImpl implements GeometricMeanService {

	@Override
	public BigDecimal compute(List<Stock> stocks) {

		if(CollectionUtils.isEmpty(stocks)){
			return BigDecimal.ZERO;
		}

		BigDecimal intermediatePrices = BigDecimal.ONE;
		for(Stock t: stocks){
			intermediatePrices = intermediatePrices.multiply(t.getParValue());
		}
		
		double pow = 1.0 / stocks.size();
		double result = Math.pow(intermediatePrices.doubleValue(), pow);
		
		return BigDecimal.valueOf(result)
				.setScale(StockConstants.DECIMALS, StockConstants.ROUNDING_MODE)
				.stripTrailingZeros();
	}
	
}
