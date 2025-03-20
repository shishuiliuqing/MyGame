package com.hjc.CardAdventure.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

//冒险事件及其对应文本
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdventureElite {
    //场景地址
    private String address;
    //场景对应精英
    private ArrayList<String> elite;
}
