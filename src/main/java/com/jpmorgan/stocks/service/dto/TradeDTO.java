package com.jpmorgan.stocks.service.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author BADR
 *
 */
public class TradeDTO {

	private Date timestamp;
	private String stockSymbol;
	private String tradeIndicator;
	private int quantity;
	private BigDecimal price;

	public Date getTimestamp() {
		return timestamp;
	}

	public TradeDTO(Date timestamp, String stockSymbol, int quantity, String tradeIndicator, BigDecimal price) {
		super();
		this.timestamp = timestamp;
		this.stockSymbol = stockSymbol;
		this.quantity = quantity;
		this.tradeIndicator = tradeIndicator;
		this.price = price;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTradeIndicator() {
		return tradeIndicator;
	}

	public void setTradeIndicator(String tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
