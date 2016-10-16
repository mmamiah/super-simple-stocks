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
 * Test for PeRatioImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class PeRatioTest {

	@Autowired
	@Qualifier("peRatioImpl")
	private StockFormulaService peRatioService;

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;

	@Test
	public void shouldConfirmFormulaName() {
		// Assert
		assertThat(peRatioService.getFormulaName(), is("P/E Ratio"));
	}

	@Test
	public void shouldConfirmStockType() {
		// Assert
		assertThat(peRatioService.getStockType(), is(StockType.NONE));
	}

	@Test
	public void shouldReturnZeroWhenPriceIsNullAndSymbolIsNull() {
		// Arrange
		BigDecimal tickerPrice = null;

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, null);

		// Assert
		assertThat(result, is(BigDecimal.ZERO));

	}

	@Test
	public void shouldReturnZeroWhenPriceIsZeroAndSymbolIsNull() {
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ZERO;

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, null);

		// Assert
		assertThat(result, is(BigDecimal.ZERO));

	}

	@Test
	public void shouldReturnZeroWhenPriceIsNullAndEmptyStock() {
		// Arrange
		BigDecimal tickerPrice = null;

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));

	}

	@Test
	public void shouldReturnZeroWhenPriceIsDefinedAndSymbolIsNone() {
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, new Stock());

		// Assert
		assertThat(result, is(BigDecimal.ZERO));

	}

	@Test
	public void shouldReturnZeroWhenPriceIsDefinedAndSymbolWithoutFixedDividend() {
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockTEA = globalBeverageCorp.findStock(StockSymbol.TEA);

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, stockTEA);

		// Assert
		assertThat(result, is(BigDecimal.ZERO));

	}

	@Test
	public void shouldReturnRatioWhenPriceIsDefinedAndSymbolWithFixedDividend() {
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);

		// Act
		BigDecimal result = peRatioService.computeValue(tickerPrice, stockGIN);

		// Assert
		assertThat(result.doubleValue(), is(0.125));

	}
	
}
