package com.stocks.shareIndexService.impl;

import java.math.BigDecimal;
import java.util.List;
import com.stocks.core.Trade;
import com.stocks.shareIndexService.ShareIndexService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * GeometricMean Implementation
 */
@Service
public class GeometricMeanImpl implements ShareIndexService {

	@Override
	public BigDecimal compute(List<Trade> trades) {

		if(CollectionUtils.isEmpty(trades)){
			return BigDecimal.ZERO;
		}

		BigDecimal intermediatePrices = BigDecimal.ONE;
		for(Trade t: trades){
			intermediatePrices = intermediatePrices.multiply(t.getPrice());
		}
		
		double pow = 1.0 / trades.size();
		double result = Math.pow(intermediatePrices.doubleValue(), pow);
		
		return BigDecimal.valueOf(result);
	}
	
}
