package com.doobs.invest.parser;

import com.doobs.invest.InvestException;
import com.doobs.invest.InvestQuoteBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Iterator;

/**
 * Created by mduby on 7/17/18.
 */
public class IexTradingJsonParser {
    // instance variables

    public InvestQuoteBean parseJson(JSONObject jsonObject) {
        // local variables
        InvestQuoteBean bean = new InvestQuoteBean();

        // return
        return bean;

    }

    public String getStockYearlyDividend(String symbol) throws InvestException {
        // local variables
        String yearlyDividend = null;
        JsonObject jsonObject = null;
        String url = this.getStockStatsUrl(symbol);
        JsonElement jsonElement = null;

        // get the json object
        jsonObject = this.getStockInformationJson(url);

        // get the quote
        if (jsonObject != null) {
            jsonElement = jsonObject.get("dividendRate");
            yearlyDividend = jsonElement.getAsString();

        } else {
            throw new InvestException("Got null yearly dividend for symbol: " + symbol);
        }

        // return
        return yearlyDividend;
    }

    public String getStockQuote(String symbol) throws InvestException {
        // local variables
        String quote = null;
        JsonObject jsonObject = null;
        String url = this.getStockQuoteUrl(symbol);
        JsonElement jsonElement = null;

        // get the json object
        jsonObject = this.getStockInformationJson(url);

        // get the quote
        if (jsonObject != null) {
            jsonElement = jsonObject.get("latestPrice");
            quote = jsonElement.getAsString();

        } else {
            throw new InvestException("Got null price for symbol: " + symbol);
        }

        // return
        return quote;
    }

    public String getStockQuoteUrl(String symbol) {
        return "https://api.iextrading.com/1.0/stock/" + symbol + "/quote";
    }

    public String getStockStatsUrl(String symbol) {
        return "https://api.iextrading.com/1.0/stock/" + symbol + "/stats";
    }

    /**
     * get the stock quote
     *
     * @return
     */
    public JsonObject getStockInformationJson(String url) {
        // instance variables
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        Iterator<String> keyIterator = null;
        UriComponentsBuilder builder = null;
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();

        // get the url builder
        builder = UriComponentsBuilder.fromHttpUrl(url);

        // get URI
        URI uri = builder.build().encode().toUri();

        // get the rest template
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = null;
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // make the call
        try {
//            response = restTemplate.exchange(
//                    uri,
//                    HttpMethod.GET,
//                    entity,
//                    String.class);

            response = restTemplate.getForEntity(uri, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseString = response.getBody();
//                jsonReader = JSONObject.createReader(new StringReader(responseString));
                jsonObject = jsonParser.parse(responseString).getAsJsonObject();

            } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            }

        } catch (HttpClientErrorException exception) {
        }

        // return
        return jsonObject;
    }
}
