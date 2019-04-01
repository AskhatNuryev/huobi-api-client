package com.huobi.api.client.model;

import lombok.Data;

@Data
public class Ticker {
    private String symbol;
    private Double open;
    private Double close;
    private Double low;
    private Double high;
    private Double amount;
    private Integer count;
    private Double vol;
}
