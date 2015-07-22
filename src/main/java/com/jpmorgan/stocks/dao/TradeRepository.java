package com.jpmorgan.stocks.dao;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jpmorgan.stocks.model.Trade;

/**
 * @author BADR
 *
 */
public interface TradeRepository extends CrudRepository<Trade, Long> {

	/**
	 * @param timeBegin
	 *            The begining of the interval to fetch trades
	 * @param timeEnd
	 *            The End of the interval to fetch trades
	 * @param l
	 *            Stock technical identifier
	 * @return Set of trades recorded for the past duration for a stock
	 */
	@Query("select t from Trade t " + "where t.timestamp between ?1 and ?2 and t.stock.id = ?3")
	Set<Trade> findTradesByTimestampBetween(Date timeBegin, Date timeEnd, long l);

}
