package com.jerry.dupstat.service.Impl;

import com.jerry.dupstat.domain.Summary;
import com.jerry.dupstat.service.SummaryService;
import com.jerry.dupstat.util.CourseStat;
import com.jerry.dupstat.util.DateUtil;
import com.jerry.dupstat.util.Login;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.*;

public class SummaryServiceImpl implements SummaryService {

    private Summary summary;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public void doSummary(){
        List<Document> docs= Login.Login(getSummary().getUser());
        Map<Integer,List<String>> res=new HashMap<>();
        for(int i=0;i<1596;i++){
            res.put(i,new ArrayList<>());
        }

        if(docs==null) return;
        CourseStat courseStat=new CourseStat();
        boolean flag=false;
        for(Document doc:docs){
            courseStat.setDocument(doc);
            String name=courseStat.getName();

            if(!flag){
                getSummary().setStartDate(courseStat.getStart());
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
        getSummary().setResult(res);
    }

    public void query(Date start, Date end){
        Map<Integer,List<String>> res=getSummary().getResult();
        Date first=new Date();
        try {
            first=new SimpleDateFormat("yyyy/MM/dd").parse(getSummary().getStartDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        long left=(start.getTime()-first.getTime()+1000000)/(3600*24*1000)*12;
        long right=((end.getTime()-first.getTime()+1000000)/(3600*24*1000)+1)*12-1;
        for(Map.Entry<Integer,List<String>> entry:res.entrySet()){
            if(entry.getKey()>=(int) left&&entry.getKey()<=right){
                if(entry.getValue().size()>0) {
                    DateUtil.getTime(getSummary().getStartDate(),entry.getKey());
                    System.out.println(entry.getValue().size());
                    for(String name:entry.getValue()){
                        System.out.print(name+" ");
                    }
                    System.out.println();
                }
            }
        }
    }
}
