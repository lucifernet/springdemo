package com.example.demo.models;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table
public class Coin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String code;

    @Column
    private String symbol;

    @Column
    private String rate;

    @Column
    private String description;

    @Column
    private String rate_float;

    @Column
    private String zh_tw;

    @Column
    @JsonIgnore
    private Long last_modified_at;

    @JsonProperty("last_modified_at")
    public String getLast_update_at(){
        if(this.last_modified_at == null) return "";
        Date time = new Date(this.last_modified_at);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(time);
    }
}
