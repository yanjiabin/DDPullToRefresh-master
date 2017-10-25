package com.yanjiabin.pulltorefresh.utils;

import com.yanjiabin.pulltorefresh.adapter.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnsunrun on 2017-09-08.
 */

public class TestData {
    public  static List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            data.add("测试" + i);
        }
        return data;
    }

    public  static List<MultipleItem> createMultiData() {
        List<MultipleItem> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if (i %10 ==0){
                data.add(new MultipleItem(MultipleItem.IMG,""));
            } else {
                data.add(new MultipleItem(MultipleItem.TEXT,"andeseng"));
            }


        }
        return data;
    }
}
