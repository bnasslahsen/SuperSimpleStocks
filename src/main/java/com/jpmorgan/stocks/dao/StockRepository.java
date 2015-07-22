package com.jpmorgan.stocks.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jpmorgan.stocks.model.Stock;

/**
 * @author BADR
 *
 */
public interface StockRepository extends CrudRepository<Stock, Long> {

	/**
	 * @param stockSymbol
	 *            Identifies the stock symbol
	 * @return Stock inforamtion
	 */
	Stock findByStockSymbol(String stockSymbol);

	/**
	 * @return List of all stocks prices
	 */
	@Query("select s.tickerPrice from Stock s")
	List<BigDecimal> getStocksPrices();

}
