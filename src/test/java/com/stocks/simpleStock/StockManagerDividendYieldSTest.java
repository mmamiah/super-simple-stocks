package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.core.Stock;
import com.stocks.enums.StockType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * System Test for SuperSimpleStockManager.calculateDividendYield()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerDividendYieldSTest {
	
	@Autowired
	private SuperSimpleStockManager stockManager;
	
	@Test
	public void shouldReturnZeroWhenEmptyStock(){
		// Arrange
		Stock stock = new Stock();
		BigDecimal tickerPrice = BigDecimal.ONE;
		
		// Act
		BigDecimal result = stockManager.calculateDividendYield(tickerPrice, stock);
		
		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenStockSymbolAndTickerPricesIsNull(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stock = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));

		// Act
		BigDecimal result = stockManager.calculateDividendYield(tickerPrice, stock);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenStockSymbolIsNullAndTickerPricesIsEmpty(){
		// Act
		BigDecimal result = stockManager.calculateDividendYield(BigDecimal.ONE, new Stock());

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldCalculateCommonDividendYieldWhenSingleTick(){
		// Arrange
		Stock stock = new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), BigDecimal.ONE, BigDecimal.valueOf(60));

		// Act
		BigDecimal result = stockManager.calculateDividendYield(BigDecimal.valueOf(1.56), stock);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(14.743589744));
	}

	@Test
	public void shouldCalculatePreferredDividendYieldWhenSingleTick(){
		// Arrange
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));


		// Act
		BigDecimal result = stockManager.calculateDividendYield(BigDecimal.valueOf(3.25), stockGIN);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.615384615));
	}

}
