package com.jpmorgan.stocks.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author BADR
 *
 */
@Entity
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String stockSymbol;
	private StockType stockType;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal tickerPrice;
	private BigDecimal parValue;

	public Stock(String stockSymbol, StockType stockType, BigDecimal lastDividend, BigDecimal fixedDividend,
			BigDecimal tickerPrice, BigDecimal parValue) {
		super();
		this.stockSymbol = stockSymbol;
		this.stockType = stockType;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.tickerPrice = tickerPrice;
		this.parValue = parValue;
	}

	public Stock() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getStockType() {
		return stockType;
	}

	public void setStockType(StockType stockType) {
		this.stockType = stockType;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(BigDecimal tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fixedDividend == null) ? 0 : fixedDividend.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastDividend == null) ? 0 : lastDividend.hashCode());
		result = prime * result + ((parValue == null) ? 0 : parValue.hashCode());
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((stockType == null) ? 0 : stockType.hashCode());
		result = prime * result + ((tickerPrice == null) ? 0 : tickerPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (fixedDividend == null) {
			if (other.fixedDividend != null)
				return false;
		} else if (fixedDividend.compareTo(other.fixedDividend) != 0)
			return false;
		if (id != other.id)
			return false;
		if (lastDividend == null) {
			if (other.lastDividend != null)
				return false;
		} else if (lastDividend.compareTo(other.lastDividend) != 0)
			return false;
		if (parValue == null) {
			if (other.parValue != null)
				return false;
		} else if (parValue.compareTo(other.parValue) != 0)
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (stockSymbol.compareTo(other.stockSymbol) != 0)
			return false;
		if (stockType != other.stockType)
			return false;
		if (tickerPrice == null) {
			if (other.tickerPrice != null)
				return false;
		} else if (tickerPrice.compareTo(other.tickerPrice) != 0)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stock [id=");
		builder.append(id);
		builder.append(", stockSymbol=");
		builder.append(stockSymbol);
		builder.append(", stockType=");
		builder.append(stockType);
		builder.append(", lastDividend=");
		builder.append(lastDividend);
		builder.append(", fixedDividend=");
		builder.append(fixedDividend);
		builder.append(", tickerPrice=");
		builder.append(tickerPrice);
		builder.append(", parValue=");
		builder.append(parValue);
		builder.append("]");
		return builder.toString();
	}

}
