package com.stocks.stockFormulaService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.stocks.core.Stock;
import com.stocks.enums.StockType;
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
		Stock stock = new Stock("POP", StockType.COMMON, BigDecimal.valueOf(8), BigDecimal.ONE, BigDecimal.valueOf(100));

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, stock);

		// Assert
		NumberFormat formatter = new DecimalFormat("#0.00000");
		assertThat(formatter.format(result.doubleValue()), is("0,03753"));
	}

	@Test
	public void shouldComputeWhenTickerPriceGreaterThanZeroAndFixedDividend(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(213.15);
		Stock stock = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));

		// Act 
		BigDecimal result = dividendYieldCommonService.computeValue(tickerPrice, stock);

		// Assert
		NumberFormat formatter = new DecimalFormat("#0.00000");
		assertThat(formatter.format(result.doubleValue()), is("0,03753"));
	}
	
}
