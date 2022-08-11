package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Coin;
import com.example.demo.services.CoinService;

@RestController
@RequestMapping(value = "api/coin", produces = "application/json;charset=UTF-8")
public class CoinController {
    @Autowired
    private CoinService coinService;

    @GetMapping(value = "/all")
    public List<Coin> listCoins() {
        return coinService.listCoins();
    }

    @GetMapping(value = "/{code}")
    public Coin getCoin(@PathVariable(value="code") String code) {
        return coinService.getCoinByCode(code);        
    }

    @PostMapping(value = "/")
    public Coin setCoin(@RequestBody Coin coin) {               
        return coinService.insertCoin(coin);
    }

    @PutMapping(value = "/{id}")
    public Coin setCoin(@PathVariable(value="id") Integer id, @RequestBody Coin coin) {
        coin.setId(id);        
        return coinService.setCoin(coin);
    }

    @DeleteMapping(value = "/{id}")
    public void removeCoin(@PathVariable(value="id") Integer id) {       
        coinService.removeCoin(id);
    }
}
