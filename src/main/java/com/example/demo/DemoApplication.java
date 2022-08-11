package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.example.demo.services.CoinService;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private CoinService coinService;

	@EventListener(ApplicationReadyEvent.class)
	public void fetchCoinDesk() {
		try {
			this.coinService.syncCoinDesk();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
