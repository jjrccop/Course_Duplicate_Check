package com.jerry.dupstat.util;

import com.jerry.dupstat.domain.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Login {
    public static List<Document> Login(User user){
        MD5 md5=new MD5();
        String Action="Login";
        String sign=String.valueOf(new Date().getTime());
        String signedPwd=md5.Encript(user.getUserName()+sign+md5.Encript(user.getPassWord()));

        try{
            Connection.Response res= Jsoup.connect(SysConstant.LoginURL)
                    .data("Action",Action)
                    .data("userName",user.getUserName())
                    .data("sign",sign)
                    .data("pwd",signedPwd).execute();
            res.header("User-Agent",SysConstant.UserAgent);
            Document document=res.parse();
            String sessionId_1=res.cookie("ASP.NET_SessionId");
            String sessionId_2=res.cookie("UserTokeID");


            return getDocuments(sessionId_1,sessionId_2,user.getParticipants());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static List<Document> getDocuments(String sessionId_1, String sessionId_2,List<String> participants){
        List<Document> res=new ArrayList<>();
        for(String participant:participants){
            try{
                res.add(Jsoup.connect(SysConstant.ProductionSchedule+participant)
                        .cookie("ASP.NET_SessionId",sessionId_1)
                        .cookie("UserTokeID",sessionId_2)
                        .timeout(SysConstant.Wait_Time)
                        .post());
            }catch (Exception e){
                res.add(null);
            }
        }
        return res;
    }
}
