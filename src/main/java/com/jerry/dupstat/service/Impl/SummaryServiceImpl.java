package com.jerry.dupstat.service.Impl;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.PerTime;
import com.jerry.dupstat.domain.Summary;
import com.jerry.dupstat.service.SummaryService;
import com.jerry.dupstat.util.CourseStat;
import com.jerry.dupstat.util.DateUtil;
import com.jerry.dupstat.util.SysConstant;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SummaryServiceImpl implements SummaryService {

    public Summary doSummary(Login login){

        String sessionId_1=login.getSessionId_1();
        String sessionId_2=login.getSessionId_2();
        List<String> participants=login.getParticipants();

        List<Document> docs=new ArrayList<>();
        for(String participant:participants){
            try{
                docs.add(Jsoup.connect(SysConstant.ProductionSchedule+participant)
                        .cookie("ASP.NET_SessionId",sessionId_1)
                        .cookie("UserTokeID",sessionId_2)
                        .timeout(SysConstant.Wait_Time)
                        .post());
            }catch (Exception e){
                docs.add(null);
            }
        }

        Summary summary=new Summary();
        Map<Integer,List<String>> res=new HashMap<>();
        for(int i=0;i<1596;i++){
            res.put(i,new ArrayList<>());
        }

        CourseStat courseStat=new CourseStat();
        boolean flag=false;
        for(Document doc:docs){
            courseStat.setDocument(doc);
            String name=courseStat.getName();

            if(!flag){
                summary.setStartDate(courseStat.getStart());
                flag=true;
            }
            List<Integer> people=courseStat.doStatic();
            for(int crs:people){
                List<String> tmp=res.get(crs);
                if(name!=null&&tmp!=null) {
                    tmp.add(name);
                }
                res.remove(crs);
                res.put(crs,tmp);
            }
        }
        summary.setResult(res);
        return summary;
    }

    public List<PerTime> query(Date start, Date end, Summary summary){
        List<PerTime> ans = new ArrayList<>();
        Map<Integer,List<String>> res=summary.getResult();
        Date first=new Date();
        try {
            first=new SimpleDateFormat("yyyy/MM/dd").parse(summary.getStartDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        long left=(start.getTime()-first.getTime()+1000000)/(3600*24*1000)*12;
        long right=((end.getTime()-first.getTime()+1000000)/(3600*24*1000)+1)*12-1;
        for(Map.Entry<Integer,List<String>> entry:res.entrySet()){
            if(entry.getKey()>=(int) left&&entry.getKey()<=right){
                if(entry.getValue().size()>0) {
                    Pair<Date,Date> curTime=DateUtil.getTime(summary.getStartDate(),entry.getKey());
                    PerTime perTime=new PerTime();
                    perTime.setStartTime(curTime.getKey());
                    perTime.setEndTime(curTime.getValue());
                    perTime.setNameList(entry.getValue());
                    ans.add(perTime);
                }
            }
        }
        return ans;
    }
}
