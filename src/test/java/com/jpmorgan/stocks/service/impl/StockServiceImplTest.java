package com.jpmorgan.stocks.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jpmorgan.stocks.TestConfiguration;
import com.jpmorgan.stocks.constants.StockCodes;
import com.jpmorgan.stocks.dao.StockRepository;
import com.jpmorgan.stocks.dao.TradeRepository;
import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.Trade;
import com.jpmorgan.stocks.model.TradeType;
import com.jpmorgan.stocks.service.StockManagementService;
import com.jpmorgan.stocks.service.dto.TradeDTO;

/**
 * @author BADR
 *
 */
public class StockServiceImplTest extends TestConfiguration {

	@Autowired
	private StockManagementService stockManagementService;

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private StockRepository stockRepository;

	@Test
	public void testCalcultateDividendYield() {
		// Test of common type Estock
		Stock stock = stockRepository.findByStockSymbol(StockCodes.POP);
		Assert.assertNotNull(stock);
		BigDecimal expectedResult = stock.getLastDividend().divide(stock.getTickerPrice(), 2, RoundingMode.HALF_EVEN);
		BigDecimal methodResult = stockManagementService.calcultateDividendYield(StockCodes.POP);
		logger.info("Dividend Yield for " + StockCodes.POP + " is " + methodResult.multiply(BigDecimal.valueOf(100))
				+ " %");
		Assert.assertEquals(expectedResult, methodResult);
		// Test of prefered type stock
		stock = stockRepository.findByStockSymbol(StockCodes.GIN);
		expectedResult = stock.getFixedDividend().multiply(stock.getParValue()).divide(stock.getTickerPrice(), 2,
				RoundingMode.HALF_EVEN);
		methodResult = stockManagementService.calcultateDividendYield(StockCodes.GIN);
		logger.info("Dividend Yield for " + StockCodes.GIN + " is " + methodResult.multiply(BigDecimal.valueOf(100))
				+ " %");
		Assert.assertEquals(expectedResult, methodResult);
	}

	@Test
	public void testCalculatePERatio() {
		Stock stock = stockRepository.findByStockSymbol(StockCodes.JOE);
		BigDecimal expectedResult = stock.getTickerPrice().divide(stock.getLastDividend(), 2, RoundingMode.HALF_EVEN);
		BigDecimal methodResult = stockManagementService.calculatePERatio(StockCodes.JOE);
		logger.info("P/E Ratio for " + StockCodes.JOE + " is " + methodResult);
		Assert.assertEquals(expectedResult, methodResult);
	}

	@Test
	public void testCreateTrade() {
		Trade trade = createTradeData(StockCodes.ALE, new BigDecimal(100), 5);
		logger.info("Created record: " + trade);
		// Check if the trade has been created: Get the record from the database
		Trade tradeCreated = tradeRepository.findOne(Long.valueOf(1));
		Assert.assertEquals(trade, tradeCreated);
		Assert.assertEquals(StockCodes.ALE, trade.getStock().getStockSymbol());
		Assert.assertEquals(TradeType.BUY, trade.getTradeIndicator());
	}

	@Test
	public void testCalculateStockPrice() {
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal sumQuntities = BigDecimal.ZERO;
		for (int i = 0; i < 22; i++) {
			BigDecimal price = BigDecimal.valueOf(100 + i);
			int quantity = 5 + i;
			BigDecimal quantityB = BigDecimal.valueOf(quantity);
			createTradeData(StockCodes.POP, price, quantity);
			totalPrice = totalPrice.add(price.multiply(quantityB));
			sumQuntities = sumQuntities.add(quantityB);
		}
		BigDecimal expectedStockPrice = totalPrice.divide(sumQuntities, 2, RoundingMode.HALF_EVEN);
		BigDecimal stockPrice = stockManagementService.calculateStockPrice(15, StockCodes.POP);
		logger.info("Stock Price for " + StockCodes.POP + " is " + stockPrice);
		Assert.assertEquals(expectedStockPrice, stockPrice);
	}

	@Test
	public void testCalculateGBCEindex() {
		// Get all stock prices
		List<BigDecimal> prices = stockRepository.getStocksPrices();
		Assert.assertTrue(prices.size() > 0);
		BigDecimal globalPrice = BigDecimal.ONE;
		for (BigDecimal price : prices) {
			globalPrice = globalPrice.multiply(price);
		}
		double result = Math.pow(globalPrice.doubleValue(), 1.0 / prices.size());
		BigDecimal expectedIndex = new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal gbceIndex = stockManagementService.calculateGBCEindex();
		logger.info("GBCE All Share Index  " + gbceIndex);
		Assert.assertEquals(expectedIndex, gbceIndex);
	}

	private Trade createTradeData(String stockSymbol, BigDecimal tradePrice, int quantity) {
		TradeDTO trade = new TradeDTO(new Date(), stockSymbol, quantity, TradeType.BUY.toString(), tradePrice);
		return stockManagementService.createTrade(trade);
	}
}
