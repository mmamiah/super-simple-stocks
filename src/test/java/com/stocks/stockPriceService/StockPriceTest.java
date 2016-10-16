package com.stocks.stockPriceService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import com.google.common.collect.Lists;
import com.stocks.core.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for StockPriceImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class StockPriceTest {

	@Autowired
	private StockPriceService stockPriceService;

	@Test
	public void shouldComputeStockPriceWhenTradeListIsNull(){
		// Arrange
		List<Trade> trades = null;

		// Act
		BigDecimal result = stockPriceService.compute(trades);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeStockPriceWhenEmptyTradeList(){
		// Arrange
		List<Trade> trades = Lists.newArrayList();

		// Act
		BigDecimal result = stockPriceService.compute(trades);

		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeStockPriceWhenUniqueTrade(){
		// Arrange
		List<Trade> trades = Lists.newArrayList();
		Trade trade = new Trade(BigDecimal.valueOf(23.123), 1);
		trades.add(trade);

		// Act
		BigDecimal result = stockPriceService.compute(trades);

		// Assert
		assertThat(result, is(trade.getPrice()));
	}

	@Test
	public void shouldComputeStockPriceWhenTwoTrades(){
		// Arrange
		BigDecimal tradePrice = BigDecimal.valueOf(84.64);
		List<Trade> trades = Lists.newArrayList();
		for(int i=0; i<2; i++){
			Trade trade = new Trade(tradePrice, trades.size() + 1);
			trades.add(trade);
		}

		// Act
		BigDecimal result = stockPriceService.compute(trades);

		// Assert
		assertThat(result, is(tradePrice));
	}

	@Test
	public void shouldComputeStockPriceWhenOverTwoTrades(){
		// Arrange
		double basePrice = 84.01;
		List<Trade> trades = Lists.newArrayList();
		for(int i=0; i<3; i++){
			double price = basePrice * (i + 1); // Sort of random price
			Trade trade = new Trade(BigDecimal.valueOf(price), trades.size() + 1);
			trades.add(trade);
		}

		// Act
		BigDecimal result = stockPriceService.compute(trades);

		// Assert
		assertThat(result.doubleValue() > basePrice, is(true));
	}
	
	
}
