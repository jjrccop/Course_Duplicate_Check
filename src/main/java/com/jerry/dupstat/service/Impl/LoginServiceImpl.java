package com.jerry.dupstat.service.Impl;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.User;
import com.jerry.dupstat.service.LoginService;
import com.jerry.dupstat.util.MD5;
import com.jerry.dupstat.util.SysConstant;
import com.jerry.dupstat.util.UserContext;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    public Boolean Login(User user){
        MD5 md5=new MD5();
        String Action="Login";
        String sign=String.valueOf(new Date().getTime());
        String signedPwd=md5.Encript(user.getUserName()+sign+md5.Encript(user.getPassWord()));

        try{
            Connection.Response res= Jsoup.connect(SysConstant.LoginURL)
                    .data("Action",Action)
                    .data("userName",user.getUserName())
                    .data("sign",sign)
                    .data("pwd",signedPwd)
                    .header("User-Agent",SysConstant.UserAgent)
                    .execute();
            Document document=res.parse();
            if(res.cookie("ASP.NET_SessionId")!=null){
                Login login=new Login();
                login.setSessionId_1(res.cookie("ASP.NET_SessionId"));
                login.setSessionId_2(res.cookie("UserTokeID"));
                login.setParticipants(user.getParticipants());
//                loginMapper.insert(login);
                UserContext.putLogin(login);
            }
            return res.cookie("ASP.NET_SessionId") == null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
