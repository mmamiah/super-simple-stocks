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
 * System Test for SuperSimpleStockManager.calculatePeRatio()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerPeRatioSTest {

	@Autowired
	private SuperSimpleStockManager stockManager;

	@Test
	public void shouldReturnZeroWhenEmptyStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stock = new Stock();

		// Act
		BigDecimal result = stockManager.calculatePeRatio(tickerPrice, stock);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenStockSymbolAndTickerPricesIsNull(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));

		// Act
		BigDecimal result = stockManager.calculatePeRatio(tickerPrice, stockGIN);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenStockSymbolIsNullAndTickerPricesIsEmpty(){
		// Act
		BigDecimal result = stockManager.calculatePeRatio(BigDecimal.ONE, new Stock());

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldCalculatePeRatioWhenSingleTick(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockALE = new Stock("ALE", StockType.COMMON, BigDecimal.valueOf(23), BigDecimal.ONE, BigDecimal.valueOf(60));

		// Act
		BigDecimal result = stockManager.calculatePeRatio(BigDecimal.valueOf(7.58), stockALE);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.329565217));
	}

	@Test
	public void shouldCalculatePeRatioWhenStockIsJOE(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(1.62);
		Stock stockJOE = new Stock("JOE", StockType.COMMON, BigDecimal.valueOf(13), BigDecimal.ONE, BigDecimal.valueOf(250));

		// Act
		BigDecimal result = stockManager.calculatePeRatio(tickerPrice, stockJOE);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.070434783));
	}

}
