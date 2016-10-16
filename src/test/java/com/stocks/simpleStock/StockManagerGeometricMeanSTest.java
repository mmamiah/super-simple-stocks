package com.stocks.simpleStock;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import com.stocks.core.Stock;
import com.stocks.enums.BuySellIndicator;
import com.stocks.enums.StockSymbol;
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
 * System Test for SuperSimpleStockManager.calculateBgceGeometricMean()
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/super-simple-stock-test-context.xml")
public class StockManagerGeometricMeanSTest {

	@Autowired
	private SuperSimpleStockManager stockManager;

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private int quantityOfShare;
	
	@Before
	public void init(){
		quantityOfShare = 100;
	}

	@Test
	public void shouldThrowNPEWhenStockIsNullAndIndicatorIsNone(){
		// Assert
		exception.expect(NullPointerException.class);
		
		// Arrange
		stockManager.recordTrade(null, BuySellIndicator.NONE, BigDecimal.ONE, quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

	}

	@Test
	public void shouldReturnGeoMeanWhenEmptyStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		stockManager.recordTrade(new Stock(), BuySellIndicator.NONE, tickerPrice, quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

	@Test
	public void shouldCalculateGeoMeanWhenSingleStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.valueOf(1.265);
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

	@Test
	public void shouldCalculateGeoMeanWhenMultipleStock(){
		// Arrange
		BigDecimal tickerPrice = BigDecimal.ONE;
		Stock stockGIN = globalBeverageCorp.findStock(StockSymbol.GIN);
		stockManager.recordTrade(stockGIN, BuySellIndicator.BUY, BigDecimal.valueOf(1.265), quantityOfShare);
		stockManager.recordTrade(stockGIN, BuySellIndicator.SELL, BigDecimal.valueOf(1.863), quantityOfShare);

		// Act
		BigDecimal result = stockManager.calculateBgceGeometricMean();

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result.doubleValue(), is(108.447));
	}

}
