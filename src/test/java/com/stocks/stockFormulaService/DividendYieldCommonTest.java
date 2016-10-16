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
 * Test class for DividendYieldCommonImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class DividendYieldCommonTest {
	
	
	@Autowired
	@Qualifier("dividendYieldCommonImpl")
	private StockFormulaService dividendYieldCommonService;

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;


	@Test
	public void shouldConfirmFormulaName(){
		assertThat(dividendYieldCommonService.getFormulaName(), is("Dividend Yield"));
	}

	@Test
	public void shouldConfirmStockTypeCommon(){
		assertThat(dividendYieldCommonService.getStockType(), is(StockType.COMMON));
	}

	@Test
	public void shouldReturnZeroWhenTickerPriceIsNull(){
		// Arrange
		BigDecimal tickerPrice = null;

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenTickerPriceIsZero(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ZERO;

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeWhenTickerPriceGreaterThanZeroAndNoFixedDividend(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);
		Stock stockALE = globalBeverageCorp.findStock(StockSymbol.ALE);

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, stockALE);

		// Assert
		assertThat(result.doubleValue(), is(0.108));
	}

	@Test
	public void shouldComputeWhenTickerPriceGreaterThanZeroAndFixedDividend(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, stockGIN);

		// Assert
		assertThat(result.doubleValue(), is(0.038));
	}
	
}
