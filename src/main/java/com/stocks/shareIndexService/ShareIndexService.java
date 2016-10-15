package com.stocks.shareIndexService;

import java.math.BigDecimal;
import java.util.List;
import com.stocks.core.Trade;

/**
 * Interface for Geamotric Mean Services
 */
public interface ShareIndexService {
	
	public BigDecimal compute(List<Trade> trades);
	
}
