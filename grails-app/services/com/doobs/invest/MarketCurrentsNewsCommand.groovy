package com.doobs.invest

class MarketCurrentsNewsCommand {
	String id
	List<Symbol> symbolList = new ArrayList<Symbol>()
	News newsItem
	
	public String toString() {
		StringBuffer buffer = new StringBuffer()
		buffer.append("id: " + id)
		if (symbolList?.size() > 0) {
			buffer.append("symbols: ")
			symbolList.each {Symbol symbol ->
				buffer.append(symbol?.symbol + "\t")
			}
		}
		buffer.append("news: " + newsItem?.text)
	}

}
