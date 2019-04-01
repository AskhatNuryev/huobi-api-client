package com.huobi.api.client;

import com.huobi.api.client.model.Ticker;
import com.huobi.api.client.model.TickersDetail;
import io.restassured.http.ContentType;
import lombok.Data;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@Data
public class ApiClient {
    private static final int CONN_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 5;
    private static final int WRITE_TIMEOUT = 5;

    private static final String accessKeyId="008c2357-ee87bb08-52a80cff-2f281";
    private static final String accessKeySecret="4ec8f774-aecd2a13-df04273b-35b42";
    public static final String MARKET_TICKERS = "/market/tickers";
    private static String baseUri = "https://api.huobi.pro";

    public static void main(String[] argv){
        getDataUntilLimit(10);


    }

    private static void getDataUntilLimit(Integer max){
        int i=0;
        while(i<max){
            Long start = System.nanoTime();
            System.out.println("Cycle: "+i+" Before ");
            getActualData();
            Long end = System.nanoTime();
            float time = 1 + (end-start)/1000000;
            System.out.println("Cycle: "+i+" After "+ time);
            i++;
        }

    }

    private static void getActualData() {
        Map<String,String> param = new HashMap<>();
        new ApiSignature().createSignature(accessKeyId,accessKeySecret,"GET", "api.huobi.pro", MARKET_TICKERS, param) ;
        TickersDetail details =
        given()
//                .log().all()
                .baseUri(baseUri)
                .basePath("/")
                .queryParams(param)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .expect()
                .statusCode(HttpURLConnection.HTTP_OK)
//                .log().all()
                .when()
                .get(MARKET_TICKERS)
                .body().as(TickersDetail.class);
//        System.out.println(details.getStatus());
//
//        System.out.println(details.getData().size());
        List<Ticker> importantTickers = details.getData().stream().filter(tic->tic.getVol()>20000).collect(Collectors.toList());
        System.out.println(importantTickers.size());
    }

}
