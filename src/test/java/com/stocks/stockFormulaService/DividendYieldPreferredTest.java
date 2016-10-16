package com.stocks.stockFormulaService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.core.Stock;
import com.stocks.enums.StockSymbol;
import com.stocks.enums.StockType;
import com.stocks.simpleStock.impl.GlobalBeverageCorporationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test Class for DividendYieldPreferredImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class DividendYieldPreferredTest {
	
	@Autowired
	@Qualifier("dividendYieldPreferredImpl")
	private StockFormulaService dividendYieldPreferredService;

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;

	@Test
	public void shouldConfirmFormulaName(){
		assertThat(dividendYieldPreferredService.getFormulaName(), is("Dividend Yield"));
	}
	
	@Test
	public void shouldConfirmStockTypePreferred(){
		assertThat(dividendYieldPreferredService.getStockType(), is(StockType.PREFERRED));
	}

	@Test
	public void shouldReturnZeroWhenTickerPriceIsNull(){
		// Arrange
		BigDecimal tickerPrice = null;

		// Act 
		BigDecimal result = dividendYieldPreferredService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenTickerPriceIsZero(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ZERO;

		// Act 
		BigDecimal result = dividendYieldPreferredService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeWhenTickerPriceGreaterThanZeroAndNoFixedDividend(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);
		Stock stockPOP = globalBeverageCorp.findStock(StockSymbol.POP);

		// Act 
		BigDecimal result = dividendYieldPreferredService.computeValue(tickerPrice, stockPOP);

		// Assert
		assertThat(result.doubleValue(), is(0.469));
	}

	@Test
	public void shouldComputeWhenTickerPriceGreaterThanZeroAndFixedDividend(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

		// Act 
		BigDecimal result = dividendYieldPreferredService.computeValue(tickerPrice, stockGIN);

		// Assert
		assertThat(result.doubleValue(), is(0.009));
	}
	
}
