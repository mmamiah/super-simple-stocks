package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.model.Stock;
import com.stocks.enums.StockSymbol;
import com.stocks.simpleStock.impl.GlobalBeverageCorporationImpl;
import com.stocks.simpleStock.impl.SuperSimpleStockManager;
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

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;

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
		BigDecimal tickerPrice = null;
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

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
		Stock stockALE = globalBeverageCorp.findStock(StockSymbol.ALE);

		// Act
		BigDecimal result = stockManager.calculatePeRatio(BigDecimal.valueOf(7.58), stockALE);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.33));
	}

	@Test
	public void shouldCalculatePeRatioWhenStockIsJOE(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(1.62);
		Stock stockJOE = globalBeverageCorp.findStock(StockSymbol.JOE);

		// Act
		BigDecimal result = stockManager.calculatePeRatio(tickerPrice, stockJOE);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.125));
	}

}
