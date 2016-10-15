package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import com.stocks.core.Stock;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockType;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * System Test for SuperSimpleStockManager.calculateGeometricMean()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerGeometricMeanSTest {

	@Autowired
	private SuperSimpleStockManager stockManager;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldThrowNPEWhenStockIsNullAndIndicatorIsNone(){
		// Assert
		exception.expect(NullPointerException.class);
		
		// Arrange
		stockManager.recordTrade(null, BuySellIndicator.NONE, BigDecimal.ONE);

		// Act
		BigDecimal result = stockManager.calculateGeometricMean();

	}

	@Test
	public void shouldReturnZeroWhenEmptyStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		stockManager.recordTrade(new Stock(), BuySellIndicator.NONE, tickerPrice);

		// Act
		BigDecimal result = stockManager.calculateGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0d));
	}

	@Test
	public void shouldCalculateGeoMeanWhenSingleTrade(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(1.265);
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265));

		// Act
		BigDecimal result = stockManager.calculateGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(1.265));
	}

	@Test
	public void shouldCalculateGeoMeanWhenMultipleTrade(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265));
		stockManager.recordTrade(stockGIN, BuySellIndicator.SELL, BigDecimal.valueOf(1.863));

		// Act
		BigDecimal result = stockManager.calculateGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		NumberFormat formatter = new DecimalFormat("#0.00000");
		assertThat(formatter.format(result.doubleValue()), is("1,53515"));
	}

}
