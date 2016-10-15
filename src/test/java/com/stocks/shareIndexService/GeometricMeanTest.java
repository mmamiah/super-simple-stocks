package com.stocks.shareIndexService;

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
 * Test class for GeometricMeanImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class GeometricMeanTest {
	
	@Autowired
	@Qualifier("geometricMeanImpl")
	private ShareIndexService geometricMeanService;

	@Test
	public void shouldComputeGeoMeanWhenTradeListIsNull(){
		// Arrange
		List<Trade> trades = null;

		// Act
		BigDecimal result = geometricMeanService.compute(trades);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}
	
	@Test
	public void shouldComputeGeoMeanWhenEmptyTradeList(){
		// Arrange
		List<Trade> trades = Lists.newArrayList();
		
		// Act
		BigDecimal result = geometricMeanService.compute(trades);
		
		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeGeoMeanWhenUniqueTrade(){
		// Arrange
		List<Trade> trades = Lists.newArrayList();
		BigDecimal tradePrice = BigDecimal.valueOf(23.123);
		Trade trade = new Trade(tradePrice, 1);
		trades.add(trade);

		// Act
		BigDecimal result = geometricMeanService.compute(trades);

		// Assert
		assertThat(result, is(tradePrice));
	}

	@Test
	public void shouldComputeGeoMeanWhenTwoTrades(){
		// Arrange
		BigDecimal tradePrice = BigDecimal.valueOf(23.23);
		List<Trade> trades = Lists.newArrayList();
		for(int i=0; i<2; i++){
			Trade trade = new Trade(tradePrice, trades.size() + 1);
			trades.add(trade);
		}

		// Act
		BigDecimal result = geometricMeanService.compute(trades);

		// Assert
		assertThat(result, is(tradePrice));
	}

	@Test
	public void shouldComputeGeoMeanWhenOverTwoTrades(){
		// Arrange
		double basePrice = 23.32;
		List<Trade> trades = Lists.newArrayList();
		for(int i=0; i<3; i++){
			double price = basePrice * (i + 1); // Sort of random price
			Trade trade = new Trade(BigDecimal.valueOf(price), trades.size() + 1);
			trades.add(trade);
		}

		// Act
		BigDecimal result = geometricMeanService.compute(trades);

		// Assert
		assertThat(result.doubleValue() > basePrice, is(true));
	}
 	
}
