package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;
import com.stocks.model.Stock;
import com.stocks.simpleStock.impl.GlobalBeverageCorporationImpl;
import com.stocks.simpleStock.impl.SuperSimpleStockManager;
import org.junit.Before;
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

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private int quantityOfShare;
	
	@Before
	public void init(){
		quantityOfShare = 37;
	}

	@Test
	public void shouldThrowIAEWhenRecordingInvalidTrade(){
		// Assert
		exception.expect(IllegalArgumentException.class);

		// Act
		stockManager.recordTrade(null, BuySellIndicator.NONE, BigDecimal.ZERO, quantityOfShare);

	}

	@Test
	public void shouldThrowIAEWhenCalculatingPriceForNullStock(){
		// Assert
		exception.expect(IllegalArgumentException.class);

		// Arrange
		stockManager.recordTrade(null, BuySellIndicator.NONE, BigDecimal.ZERO, quantityOfShare);

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
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenEmptyStock(){
		// Arrange
		stockManager.recordTrade(StockSymbol.NONE, BuySellIndicator.BUY, BigDecimal.ZERO, quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateStockPrice(new Stock());

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldReturnZeroWhenStockNotInHistory(){
		// Arrange
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);
		stockManager.recordTrade(StockSymbol.TEA, BuySellIndicator.BUY, BigDecimal.valueOf(0.658), quantityOfShare);

		Stock stockPOP = globalBeverageCorp.findStock(StockSymbol.POP);
		
		// Act
		BigDecimal result = stockManager.calculateStockPrice(stockPOP);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldCalculateStockPriceWhenSingleTrade(){
		// Arrange
		Stock stockGin2 = new Stock(StockSymbol.GIN, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(4.352), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stockGin2);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(2.808));
	}

	@Test
	public void shouldCalculateStockPriceWhenMultipleTrade(){
		// Arrange
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);
		stockManager.recordTrade(StockSymbol.TEA, BuySellIndicator.BUY, BigDecimal.valueOf(0.658), quantityOfShare);

		Stock stockGin2 = new Stock(StockSymbol.GIN, BigDecimal.valueOf(8), BigDecimal.valueOf(0.02), BigDecimal.valueOf(100));
		stockManager.recordTrade(StockSymbol.GIN, BuySellIndicator.BUY, BigDecimal.valueOf(4.352), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateStockPrice(stockGin2);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(2.808));
	}

}
