package com.jerry.dupstat.util;

import com.jerry.dupstat.domain.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args){
        User user=new User();
        List<String> part=new ArrayList<String>(){{
                add("201713160915");
                add("201713160914");
                add("201701050321");
                add("201813160108");
                add("201713160220");
                add("201613161301");
            }};
        user.setUserName("201613161301");
        user.setPassWord("231004199803280713");
        user.setParticipants(part);
        Summary summary=new Summary();
        summary.setUser(user);
        summary.doSummary();

        Date start=new Date();
        Date end=new Date();
        try{
            start=new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/09");
            end=new SimpleDateFormat("yyyy/MM/dd").parse("2019/10/25");
        }catch (Exception e){
            e.printStackTrace();
        }
        summary.query(start,end);
    }
}
