package com.doobs.invest

class MarketCurrentsService {

    static transactional = true

    def serviceMethod() {

    }
	
	List<MarketCurrentsNewsCommand> parseMarketCurrents() {
		List<String> newsList = new ArrayList<String>()
		@Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2' )
		def tagsoupParser = new org.ccil.cowan.tagsoup.Parser()
		def slurper = new XmlSlurper(tagsoupParser)		
		def htmlParser = slurper.parse("http://seekingalpha.com/currents/all/today")
		
		htmlParser.'**'.findAll{ it.@id == 'enter_new_mc'}.each {
//			log.info "news: " + it?.toString()
			
			newsList.add(it?.toString())
		}
		
		return newsList
	}
}
