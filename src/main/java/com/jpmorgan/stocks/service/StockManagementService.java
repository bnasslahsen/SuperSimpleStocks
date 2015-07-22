package com.jpmorgan.stocks.service;

import java.math.BigDecimal;

import com.jpmorgan.stocks.model.Trade;
import com.jpmorgan.stocks.service.dto.TradeDTO;

/**
 * @author BADR
 *
 */
public interface StockManagementService {

	/**
	 * @param stockSymbol
	 *            Identifies the stock symbol
	 * @return Calculated dividend yield
	 */
	BigDecimal calcultateDividendYield(String stockSymbol);

	/**
	 * @param stockSymbol
	 *            Identifies the stock symbol
	 * @return Calculated P/E Ratio
	 */
	BigDecimal calculatePERatio(String stockSymbol);

	/**
	 * @param tradeInfo
	 *            Represents a trade with timestamp, quantity of shares, buy or
	 *            sell indicator and price
	 * @return Returns the created trade
	 */
	Trade createTrade(TradeDTO tradeInfo);

	/**
	 * @param history
	 *            Represents the depth of trades to fetch in minutes
	 * @param stockSymbol
	 *            Identifies the stock symbol
	 * @return Calculated Stock Price based on trades recorded in past history
	 *         minutes
	 */
	BigDecimal calculateStockPrice(int history, String stockSymbol);

	/**
	 * @return Calculated GBCE All Share Index, using the geometric mean of
	 *         prices for all stocks
	 */
	BigDecimal calculateGBCEindex();

}
