package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.core.Stock;
import com.stocks.enums.StockSymbol;
import com.stocks.simpleStock.impl.GlobalBeverageCorporationImpl;
import com.stocks.simpleStock.impl.SuperSimpleStockManager;
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

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;
	
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
		BigDecimal tickerPrice = null;
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

		// Act
		BigDecimal result = stockManager.calculateDividendYield(tickerPrice, stockGIN);

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
	public void shouldCalculateDividendYieldWhenCommonStock(){
		// Arrange
		Stock stockALE = globalBeverageCorp.findStock(StockSymbol.ALE);

		// Act
		BigDecimal result = stockManager.calculateDividendYield(BigDecimal.valueOf(1.56), stockALE);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(14.744));
	}

	@Test
	public void shouldCalculateDividendYieldWhenPreferredStock(){
		// Arrange
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

		// Act
		BigDecimal result = stockManager.calculateDividendYield(BigDecimal.valueOf(3.25), stockGIN);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0.615));
	}

}
