package com.jgw.supercodeplatform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Console {
    public static void main(String[] args)
    {

        List<String> list=new ArrayList<String>();
        list.add("yyyy年MM月dd日");
        list.add("Y/MM/d");
        list.add("YMMd");
        list.add("Y.MM.d");


        Date date = new Date();

        for(String fmt:list){
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            String strDate=sdf.format(date);
            System.out.println(strDate);
        }



    }
}
