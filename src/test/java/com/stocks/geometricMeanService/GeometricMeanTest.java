package com.stocks.geometricMeanService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import com.google.common.collect.Lists;
import com.stocks.core.Stock;
import com.stocks.core.Trade;
import com.stocks.enums.StockSymbol;
import com.stocks.geometricMean.GeometricMeanService;
import com.stocks.simpleStock.impl.GlobalBeverageCorporationImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test class for GeometricMeanImpl
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/super-simple-stock-test-context.xml")
public class GeometricMeanTest {
	
	@Autowired
	private GeometricMeanService geometricMeanService;

	@Autowired
	private GlobalBeverageCorporationImpl globalBeverageCorp;

	@Test
	public void shouldComputeGeoMeanWhenStockListIsNull(){
		// Arrange
		List<Stock> trades = null;

		// Act
		BigDecimal result = geometricMeanService.compute(trades);

		// Assert
		assertThat(result, not(nullValue()));
		assertThat(result, is(BigDecimal.ZERO));
	}
	
	@Test
	public void shouldComputeGeoMeanWhenEmptyStockList(){
		// Arrange
		List<Stock> trades = Lists.newArrayList();
		
		// Act
		BigDecimal result = geometricMeanService.compute(trades);
		
		// Assert
		assertThat(result, is(BigDecimal.ZERO));
	}

	@Test
	public void shouldComputeGeoMeanWhenUniqueStock(){
		// Arrange
		List<Stock> stocks = Lists.newArrayList();
		Stock stock = globalBeverageCorp.findStock(StockSymbol.ALE);
		stocks.add(stock);

		// Act
		BigDecimal result = geometricMeanService.compute(stocks);

		// Assert
		assertThat(result.doubleValue(), is(60d));
	}

	@Test
	public void shouldComputeGeoMeanWhenMultipleStock(){
		// Arrange
		List<Stock> stocks = Lists.newArrayList();
		Stock stock1 = globalBeverageCorp.findStock(StockSymbol.TEA);
		stocks.add(stock1);
		
		Stock stock2 = globalBeverageCorp.findStock(StockSymbol.POP);
		stocks.add(stock2);

		// Act
		BigDecimal result = geometricMeanService.compute(stocks);

		// Assert
		assertThat(result.doubleValue(), is(100d));
	}

}
