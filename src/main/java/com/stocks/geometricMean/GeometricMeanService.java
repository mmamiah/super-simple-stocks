package com.stocks.geometricMean;

import java.math.BigDecimal;
import java.util.List;
import com.stocks.model.Stock;

/**
 * Interface for GeometricMean
 */
public interface GeometricMeanService {

	public BigDecimal compute(List<Stock> stocks);
	
}
