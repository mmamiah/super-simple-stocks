package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
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
 * System Test for SuperSimpleStockManager.calculateStockPrice(stock)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerCalculateStockPriceSTest {

	@Autowired
	private SuperSimpleStockManager stockManager;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void shouldThrowNPEWhenRecordingInvalidTrade(){
		// Assert
		exception.expect(NullPointerException.class);

		// Act
		stockManager.recordTrade(null, BuySellIndicator.NONE, BigDecimal.ZERO);

	}

	@Test
	public void shouldThrowNPEWhenCalculatingPriceForNullStock(){
		// Assert
		exception.expect(NullPointerException.class);

		// Arrange
		stockManager.recordTrade(new Stock(), BuySellIndicator.NONE, BigDecimal.ZERO);

		// Act
		BigDecimal result = stockManager.calculateStockPrice(null);

	}

	@Test
	public void shouldReturnZeroWhenStockHistoryIsEmpty(){
		// Arrange
		Stock stock = new Stock();

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stock);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0d));
	}

	@Test
	public void shouldReturnZeroWhenEmptyStock(){
		// Arrange
		Stock stock = new Stock();
		stockManager.recordTrade(stock, BuySellIndicator.BUY, BigDecimal.ZERO);

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stock);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0d));
	}

	@Test
	public void shouldReturnZeroWhenStockNotInHistory(){
		// Arrange
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		Stock stockTEA = new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(100));
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265));
		stockManager.recordTrade(stockTEA, BuySellIndicator.BUY, BigDecimal.valueOf(0.658));

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stockGIN);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(0d));
	}

	@Test
	public void shouldCalculateStockPriceWhenSingleTrade(){
		// Arrange
		Stock stockGIN = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		Stock stockTEA = new Stock("TEA", StockType.COMMON, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.valueOf(100));
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265));
		stockManager.recordTrade(stockTEA, BuySellIndicator.BUY, BigDecimal.valueOf(0.658));
		
		Stock stockGin2 = new Stock("GIN", StockType.PREFERRED, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stockManager.recordTrade(stockGin2, BuySellIndicator.BUY, BigDecimal.valueOf(4.352));

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stockGin2);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(1.265));
	}

}
