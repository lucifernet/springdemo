// package com.example.demo.utils;

// import java.io.IOException;
// import java.util.HashMap;

// import com.example.demo.models.Coin;
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;

// public class CoindeskFetcher {

//     public final static String URL_COIN_DESK = "https://api.coindesk.com/v1/bpi/currentprice.json";

//     public void fetchCoinDesk() {

//         String resultString = null;
//         try {
//             resultString = HttpUtility.doGet(URL_COIN_DESK);
//         } catch (IOException e) {
//             e.printStackTrace();
//             return;
//         }

//         var mapper = new ObjectMapper();
//         var typeRef = new TypeReference<HashMap<String, Object>>() {
//         };

//         HashMap<String, Object> coins = new HashMap<String, Object>();
//         try {
//             var o = mapper.readValue(resultString, typeRef);            
//             coins = (HashMap<String, Object>) o.get("bpi");
//             System.out.println("Got " + o.get("disclaimer"));
//         } catch (IOException e) { 
//             e.printStackTrace();
//             return;
//         }

//         for(var key : coins.keySet()) {
//             var value = coins.get(key);
//             var coin = mapper.convertValue(value, Coin.class);

//             System.out.println("coin:" + coin.getCode());
//         }
//     }
// }
