package com.jpmorgan.stocks.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.stocks.dao.StockRepository;
import com.jpmorgan.stocks.dao.TradeRepository;
import com.jpmorgan.stocks.model.Stock;
import com.jpmorgan.stocks.model.StockType;
import com.jpmorgan.stocks.model.Trade;
import com.jpmorgan.stocks.model.TradeType;
import com.jpmorgan.stocks.service.StockManagementService;
import com.jpmorgan.stocks.service.dto.TradeDTO;

/**
 * @author BADR
 *
 */
@Service
public class StockManagementServiceImpl implements StockManagementService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TradeRepository tradeRepository;

	@Override
	public BigDecimal calcultateDividendYield(String stockSymbol) {
		Stock stock = stockRepository.findByStockSymbol(stockSymbol);
		if (stock == null) {
			throw new IllegalArgumentException("Stock argument can not be null");
		}
		if (stock.getTickerPrice() == null) {
			throw new IllegalArgumentException("Stock ticker price can not be null");
		}
		if (BigDecimal.ZERO.equals(stock.getTickerPrice())) {
			throw new IllegalArgumentException("Stock ticker price can not be equal to 0");
		}
		BigDecimal result = null;
		if (StockType.COMMON.equals(stock.getStockType())) {
			result = stock.getLastDividend().divide(stock.getTickerPrice(), 2, RoundingMode.HALF_EVEN);
		} else if (StockType.PREFERED.equals(stock.getStockType())) {
			result = stock.getFixedDividend().multiply(stock.getParValue()).divide(stock.getTickerPrice(), 2,
					RoundingMode.HALF_EVEN);
		}
		return result;
	}

	@Override
	public BigDecimal calculatePERatio(String stockSymbol) {
		Stock stock = stockRepository.findByStockSymbol(stockSymbol);
		if (stock == null) {
			throw new IllegalArgumentException("Stock argument can not be null");
		}
		if (stock.getLastDividend() == null) {
			throw new IllegalArgumentException("Stock last dividend can not be null");
		}
		if (BigDecimal.ZERO.equals(stock.getLastDividend())) {
			throw new IllegalArgumentException("Stock last dividend can not be equal to 0");
		}
		return stock.getTickerPrice().divide(stock.getLastDividend(), 2, RoundingMode.HALF_EVEN);
	}

	@Override
	public Trade createTrade(TradeDTO tradeInfo) {
		String stockSymbol = tradeInfo.getStockSymbol();
		Stock stock = stockRepository.findByStockSymbol(stockSymbol);
		if (stock == null)
			throw new IllegalArgumentException("No stock found for symbol " + stockSymbol);
		TradeType tradeType = TradeType.valueOf(tradeInfo.getTradeIndicator());
		Trade trade = new Trade(tradeInfo.getTimestamp(), stock, tradeInfo.getQuantity(), tradeType,
				tradeInfo.getPrice());
		return tradeRepository.save(trade);
	}

	@Override
	public BigDecimal calculateStockPrice(int history, String stockSymbol) {
		Stock stock = stockRepository.findByStockSymbol(stockSymbol);
		if (stock == null)
			throw new IllegalArgumentException("No stock found for symbol " + stockSymbol);
		// Get trades of the specified history (15 mins)
		Date dateEnd = new Date();
		long t = dateEnd.getTime();
		final long historyMillis = history * 60 * 1000;// millisecs
		Date dateBegin = new Date(t - historyMillis);
		// Get the stock information from Database
		Set<Trade> trades = tradeRepository.findTradesByTimestampBetween(dateBegin, dateEnd, stock.getId());
		if (trades.size() == 0)
			throw new IllegalArgumentException(
					"There was no Trade for " + stockSymbol + " in the last " + history + " minutes");
		BigDecimal totalPrice = BigDecimal.ZERO;
		BigDecimal sumQuantities = BigDecimal.ZERO;
		for (Trade trade : trades) {
			int quantity = trade.getQuantity();
			BigDecimal price = trade.getPrice();
			totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(quantity)));
			sumQuantities = sumQuantities.add(BigDecimal.valueOf(quantity));
		}
		return totalPrice.divide(sumQuantities, 2, RoundingMode.HALF_EVEN);
	}

	@Override
	public BigDecimal calculateGBCEindex() {
		// Get all the stocks prices
		List<BigDecimal> prices = (List<BigDecimal>) stockRepository.getStocksPrices();
		BigDecimal globalPrice = BigDecimal.ONE;
		for (BigDecimal price : prices) {
			globalPrice = globalPrice.multiply(price);
		}
		double result = Math.pow(globalPrice.doubleValue(), 1.0 / prices.size());
		return new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}
