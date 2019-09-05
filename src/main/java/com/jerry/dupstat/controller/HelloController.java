package com.jerry.dupstat.controller;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.Summary;
import com.jerry.dupstat.domain.User;
import com.jerry.dupstat.service.Impl.LoginServiceImpl;
import com.jerry.dupstat.service.Impl.SummaryServiceImpl;
import com.jerry.dupstat.service.LoginService;
import com.jerry.dupstat.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HelloController {

    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private SummaryServiceImpl summaryService;

    @RequestMapping("/index")
    public String index(){
        return "login";
    }
    @RequestMapping("/login")
    public ModelAndView login(User user){
        ModelAndView modelAndView=new ModelAndView("query");
        if(loginService.Login(user)){
            modelAndView=new ModelAndView("login");
            modelAndView.addObject("ERROR","输错了啦!");
        }
        return modelAndView;
    }
    @RequestMapping("/query")
    public ModelAndView query(String part){
        ModelAndView modelAndView=new ModelAndView("query");
        System.out.println(part);
        Login curLogin= UserContext.getLogin();
        System.out.println(curLogin.getSessionId_2());
        List<String> curList=curLogin.getParticipants();
        if(part==null) return modelAndView;
        if(curList==null) curList=new ArrayList<>();
        curList.add(part);
        curLogin.setParticipants(curList);
        UserContext.putLogin(curLogin);
        modelAndView.addObject("participants",curList);
        return modelAndView;
    }
    @RequestMapping("/start")
    public ModelAndView start(@DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") Date end){
        ModelAndView modelAndView=new ModelAndView("result");
        Login login=UserContext.getLogin();
        Summary summary=new Summary();
        summary=summaryService.doSummary(login);
        try {
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.addObject("res",summaryService.query(start,end,summary));
        return modelAndView;
    }
}