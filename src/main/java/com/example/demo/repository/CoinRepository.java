package com.example.demo.repository;

import com.example.demo.models.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, Integer> {

    public Coin findByCode(String code);
}
