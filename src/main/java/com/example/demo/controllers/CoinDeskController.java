package com.example.demo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Coin;
import com.example.demo.services.CoinService;

@RestController
@RequestMapping(value = "api/coindesk", produces = "application/json;charset=UTF-8")
public class CoinDeskController {
    @Autowired
    private CoinService coinService;

    @GetMapping(value = "/fetch")
    public Map<String, Object> fetchCoinDesk() throws IOException {
        return coinService.fetchCoinDesk();
    }   

    @GetMapping(value = "/sync")
    public List<Coin> syncCoinDesk() throws IOException {
        return coinService.syncCoinDesk();
    }    
}
