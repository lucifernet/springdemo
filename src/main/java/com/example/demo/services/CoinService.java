package com.example.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Coin;
import com.example.demo.repository.CoinRepository;
import com.example.demo.utils.HttpUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CoinService {

    public final static String URL_COIN_DESK = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Autowired
    private CoinRepository coinRepository;

    public List<Coin> syncCoinDesk() throws IOException {
        var result = fetchCoinDesk();
        var coins = (HashMap<String, Object>) result.get("bpi");
        coinRepository.deleteAll();

        var list = new ArrayList<Coin>();
        var mapper = new ObjectMapper();
        for (var key : coins.keySet()) {
            var value = coins.get(key);
            var coin = mapper.convertValue(value, Coin.class);
            coin.setLast_modified_at(System.currentTimeMillis());
            list.add(coinRepository.save(coin));
        }
        return list;
    }

    public HashMap<String, Object> fetchCoinDesk() throws IOException {
        return HttpUtility.doGetMap(URL_COIN_DESK);
    }

    public List<Coin> listCoins() {
        return this.coinRepository.findAll();
    }

    public Coin getCoinByCode(String code) {
        return this.coinRepository.findByCode(code);
    }

    public Coin setCoin(Coin coin) {
        var coinOpt = this.coinRepository.findById(coin.getId());
        if (coinOpt.isPresent()) {
            coin.setLast_modified_at(System.currentTimeMillis());
            return this.coinRepository.save(coin);
        } else
            return null;
    }

    public void removeCoin(Integer id) {
        this.coinRepository.deleteById(id);
    }

    public Coin insertCoin(Coin coin) {
        coin.setLast_modified_at(System.currentTimeMillis());
        coin.setId(null);
        return this.coinRepository.save(coin);
    }
}
