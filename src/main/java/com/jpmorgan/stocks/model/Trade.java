package com.jpmorgan.stocks.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author BADR
 *
 */
@Entity
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@ManyToOne
	@JoinColumn(name = "stock_id", referencedColumnName = "id")
	private Stock stock;

	private int quantity;

	private TradeType tradeIndicator;
	private BigDecimal price;

	public Trade() {
		super();
	}

	public Trade(long id, Date timestamp, Stock stock, int quantity, TradeType tradeIndicator, BigDecimal price) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.stock = stock;
		this.quantity = quantity;
		this.tradeIndicator = tradeIndicator;
		this.price = price;
	}

	public Trade(Date timestamp, Stock stock, int quantity, TradeType tradeIndicator, BigDecimal price) {
		super();
		this.timestamp = timestamp;
		this.stock = stock;
		this.quantity = quantity;
		this.tradeIndicator = tradeIndicator;
		this.price = price;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public TradeType getTradeIndicator() {
		return tradeIndicator;
	}

	public void setTradeIndicator(TradeType tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((tradeIndicator == null) ? 0 : tradeIndicator.hashCode());
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
		Trade other = (Trade) obj;
		if (id != other.id)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (price.compareTo(other.price) != 0)
			return false;
		if (quantity != other.quantity)
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (tradeIndicator != other.tradeIndicator)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Trade [id=");
		builder.append(id);
		builder.append(", timestamp=");
		builder.append(timestamp);
		builder.append(", stock=");
		builder.append(stock);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", tradeIndicator=");
		builder.append(tradeIndicator);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}

}
