package com.doobs.invest.parser;

import com.doobs.invest.InvestException;
import com.doobs.invest.bean.stock.AlphaAdvantageStockBean;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mduby on 1/3/20.
 */
public class AlpahAdvantageJsonParser {
    // constants
    private final String dailyRestURL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED";
    private final String weeklyRestURL = "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY_ADJUSTED";
    private final String symbolParameter = "&symbol=";
    private final String apiKeyParameter = "&apikey=91T1OX104JJU251S";

    // json keys
    private final String dailyJsonKey = "Time Series (Daily)";
    private final String weeklyJsonKey = "Weekly Adjusted Time Series";
    private final String openPriceJsonKey = "1. open";
    private final String highPriceJsonKey = "2. high";
    private final String lowPriceJsonKey = "3. low";
    private final String closePriceJsonKey = "4. close";
    private final String adjustedClosePriceJsonKey = "5. adjusted close";
    private final String volumeJsonKey = "6. volume";
    private final String dividendAmountJsonKey = "7. dividend amount";

    /**
     * get the weekly stock information from REST call
     *
     * @param symbol
     * @return
     * @throws InvestException
     */
    public List<AlphaAdvantageStockBean> getWeeklyStockQuoteList(String symbol) throws InvestException {
        return this.getStockQuoteList(symbol, false);
    }

    /**
     * get the stock information from REST call
     *
     * @param symbol
     * @return
     * @throws InvestException
     */
    public List<AlphaAdvantageStockBean> getDailyStockQuoteList(String symbol) throws InvestException {
        return this.getStockQuoteList(symbol, true);
    }

    /**
     * get the stock daily information from REST call
     *
     * @param symbol
     * @return
     * @throws InvestException
     */
    public List<AlphaAdvantageStockBean> getStockQuoteList(String symbol, boolean isDaily) throws InvestException {
        // local variables
        String quote = null;
        JsonObject jsonObject = null;
        JsonElement jsonElement = null;
        List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = null;
        String errorKey = "Error Message";

        // get the json object
        jsonObject = this.getStockInformationJson(symbol, isDaily);

        // throw error if got API error
        if (jsonObject.has(errorKey)) {
            String error = jsonObject.get(errorKey).toString();
            throw new InvestException("Got API error for symbol: " + symbol + " with message: " + error);
        }

        // get the quote
        if (jsonObject != null) {
            alphaAdvantageStockBeanList = this.parseBeanListFromJson(jsonObject, symbol, isDaily);

        } else {
            throw new InvestException("Got null price for symbol: " + symbol);
        }

        // return
        return alphaAdvantageStockBeanList;
    }

    /**
     * filters the weekly prices to only be for previous weeks (avoid middle of the week prices)
     *
     * @param alphaAdvantageStockBeanList
     * @return
     * @throws InvestException
     */
    public List<AlphaAdvantageStockBean> returnOnlyPreviousWeeklyPrices(List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList) throws InvestException {
        // local variables
        List<AlphaAdvantageStockBean> resultList = new ArrayList<AlphaAdvantageStockBean>();
        Calendar calendar = Calendar.getInstance();
        Date today = new Date();

        // get todays week and year
        calendar.setTime(today);
        int todayWeekId = calendar.get(Calendar.WEEK_OF_YEAR);
        int yearId = calendar.get(Calendar.YEAR);

//        // loop through the list and make sure all dates are fridays
//        for (AlphaAdvantageStockBean bean : alphaAdvantageStockBeanList) {
//            calendar.setTime(bean.getDate());
//            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//
//            if (dayOfWeek == Calendar.FRIDAY) {
//                resultList.add(bean);
//
//            } else {
//                // skip
//            }
//        }

        // loop through the list and make sure all dates are fridays
        for (AlphaAdvantageStockBean bean : alphaAdvantageStockBeanList) {
            calendar.setTime(bean.getDate());

            if (calendar.get(Calendar.YEAR) == yearId && calendar.get(Calendar.WEEK_OF_YEAR) == todayWeekId) {
                // skip

            } else {
                resultList.add(bean);
            }
        }

        // return
        return resultList;
    }

    /**
     * parse a stock price entry
     *
     * @param jsonElement
     * @param symbol
     * @param dateKey
     * @return
     * @throws InvestException
     */
    public AlphaAdvantageStockBean parseBeanFromJson(JsonElement jsonElement, String symbol, String dateKey) throws InvestException {
        // local variables
        AlphaAdvantageStockBean alphaAdvantageStockBean = new AlphaAdvantageStockBean();
        JsonObject json = jsonElement.getAsJsonObject();

        // set
        alphaAdvantageStockBean.setSymbol(symbol);
        alphaAdvantageStockBean.setDateString(dateKey);
        alphaAdvantageStockBean.setOpenPrice(json.get(openPriceJsonKey).getAsFloat());
        alphaAdvantageStockBean.setHighPrice(json.get(highPriceJsonKey).getAsFloat());
        alphaAdvantageStockBean.setLowPrice(json.get(lowPriceJsonKey).getAsFloat());
        alphaAdvantageStockBean.setClosePrice(json.get(closePriceJsonKey).getAsFloat());
        alphaAdvantageStockBean.setAdjustedClosePrice(json.get(adjustedClosePriceJsonKey).getAsFloat());
        alphaAdvantageStockBean.setVolume(json.get(volumeJsonKey).getAsLong());
        alphaAdvantageStockBean.setDividendAmount(json.get(dividendAmountJsonKey).getAsFloat());

        // return
        return alphaAdvantageStockBean;
    }

    /**
     * parse the json into a list of stock prices
     *
     * @param json
     * @param symbol
     * @return
     * @throws InvestException
     */
    public List<AlphaAdvantageStockBean> parseBeanListFromJson(JsonObject json, String symbol, boolean isDaily) throws InvestException {
        // local variables
        JsonObject jsonObject = null;
        List<AlphaAdvantageStockBean> alphaAdvantageStockBeanList = new ArrayList<AlphaAdvantageStockBean>();

        // get the json array
        if (isDaily) {
            jsonObject = json.getAsJsonObject(dailyJsonKey);

        } else {
            jsonObject = json.getAsJsonObject(weeklyJsonKey);
        }

        // loop through the stock price entries
        for (Map.Entry<String, JsonElement> jsonEntry : jsonObject.entrySet()) {
            AlphaAdvantageStockBean bean = this.parseBeanFromJson(jsonEntry.getValue(), symbol, jsonEntry.getKey());
            alphaAdvantageStockBeanList.add(bean);
        }

        // return
        return alphaAdvantageStockBeanList;
    }

    /**
     * make rest call for stock information
     *
     * @param symbol
     * @return
     */
    public JsonObject getStockInformationJson(String symbol, boolean isDaily) throws InvestException {
        // instance variables
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        StringBuffer stringBuffer = new StringBuffer();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        Iterator<String> keyIterator = null;
        UriComponentsBuilder builder = null;
        JsonObject jsonObject = null;
        JsonParser jsonParser = new JsonParser();

        // build the url
        if (isDaily) {
            stringBuffer.append(dailyRestURL);

        } else {
            stringBuffer.append(weeklyRestURL);
        }
        stringBuffer.append(symbolParameter);
        stringBuffer.append(symbol);
        stringBuffer.append(apiKeyParameter);

        // get the url builder
        builder = UriComponentsBuilder.fromHttpUrl(stringBuffer.toString());

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
                throw new InvestException("Got unauthorized response");
            }

        } catch (HttpClientErrorException exception) {
            throw new InvestException("Got http exception: " + exception.getMessage());
        }

        // return
        return jsonObject;
    }

}
