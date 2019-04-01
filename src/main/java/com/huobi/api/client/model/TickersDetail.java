package com.huobi.api.client.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TickersDetail {
    String status;
    Long ts;
    List<Ticker> data = new ArrayList<>();

}
