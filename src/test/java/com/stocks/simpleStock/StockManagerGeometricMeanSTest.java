package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;
import com.stocks.simpleStock.impl.SuperSimpleStockManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * System Test for SuperSimpleStockManager.calculateBgceGeometricMean()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerGeometricMeanSTest {

	@Autowired
	private SuperSimpleStockManager stockManager;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private int quantityOfShare;
	
	@Before
	public void init(){
		quantityOfShare = 100;
	}

	@Test
	public void shouldReturnGeoMeanWhenEmptyStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		stockManager.recordTrade(StockSymbol.NONE, BuySellIndicator.NONE, tickerPrice, quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

	@Test
	public void shouldCalculateGeoMeanWhenSingleStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(1.265);
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

	@Test
	public void shouldCalculateGeoMeanWhenMultipleStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.SELL, BigDecimal.valueOf(1.863), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

}
