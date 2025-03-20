package com.hjc.CardAdventure.pojo.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    //当前所有选择选项
    public static ArrayList<Option> options;
    //选项文本
    private String text;
    //选项效果
    private String[] effects;
}
