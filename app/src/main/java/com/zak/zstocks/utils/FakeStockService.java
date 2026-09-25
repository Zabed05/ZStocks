package com.zak.zstocks.utils;


import com.zak.zstocks.model.Stock;
import java.util.ArrayList;
import java.util.List;

public class FakeStockService {

    public static List<Stock> getStocks() {
        List<Stock> list = new ArrayList<>();

        list.add(new Stock("TCS", 3500));
        list.add(new Stock("Infosys", 1450));
        list.add(new Stock("Reliance", 2800));
        list.add(new Stock("HDFC Bank", 1600));
        list.add(new Stock("SBI", 1500));

        return list;
    }
}
