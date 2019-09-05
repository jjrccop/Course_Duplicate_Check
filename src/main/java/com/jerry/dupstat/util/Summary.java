package com.jerry.dupstat.util;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.PerTime;
import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.*;

public class Summary {
    public static List<PerTime> query(Date start, Date end, Login login) {

        //sessionID: ASP.NET_SessionId
        String sessionId_1 = login.getSessionId_1();

        //sessionID: UserTokeID
        String sessionId_2 = login.getSessionId_2();

        //获取参与者学号
        List<String> participants = login.getParticipants();

        //该学期开始日期
        String startDate = "";
        Date first = new Date();

        //使用Document列表获取HTML
        List<Document> docs = new ArrayList<>();

        //获取Document分析结果
        Map<Integer, List<String>> res = new HashMap<>();

        //获取最终分析结果
        List<PerTime> ans = new ArrayList<>();

        /*
         * 获取所有参与者课程表内容
         * @params: Document
         */
        for (String participant : participants) {
            try {
                docs.add(Jsoup.connect(SysConstant.ProductionSchedule + participant)
                        .cookie("ASP.NET_SessionId", sessionId_1)
                        .cookie("UserTokeID", sessionId_2)
                        .timeout(SysConstant.Wait_Time)
                        .post());
            } catch (Exception e) {
                docs.add(null);
            }
        }

        /*
         * 结果列表初始化
         * @params: Map<Integer,List<String>>
         */
        for (int i = 0; i < 1596; i++) {
            res.put(i, new ArrayList<>());
        }

        /*
         * 分析每个参与者的HTML课程表
         * @params: Map<Integer,List<String>>
         */
        boolean flag = false;
        for (Document doc : docs) {
            String name = CourseStat.getName(doc);

            if (!flag) {
                startDate = CourseStat.getStart(doc);
                flag = true;
            }
            List<Integer> people = CourseStat.doStatic(doc);
            for (int crs : people) {
                List<String> tmp = res.get(crs);
                if (tmp != null) {
                    tmp.add(name);
                }
                res.remove(crs);
                res.put(crs, tmp);
            }
        }

        /*
         * 获取分析结果，存入Domain PerTime列表中
         * @param: List<PerTime>
         */
        try {
            first = new SimpleDateFormat("yyyy/MM/dd").parse(startDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(start.getTime()<first.getTime()){
            start=first;
        }
        if(end.getTime()<first.getTime()){
            end=first;
        }
        long left = (start.getTime() - first.getTime() + 1000000) / (3600 * 24 * 1000) * 12;
        long right = ((end.getTime() - first.getTime() + 1000000) / (3600 * 24 * 1000) + 1) * 12 - 1;
        for (Map.Entry<Integer, List<String>> entry : res.entrySet()) {
            if (entry.getKey() >= (int) left && entry.getKey() <= right) {
                if (entry.getValue().size() > 0) {
                    Pair<Date, Date> curTime = DateUtil.getTime(startDate, entry.getKey());
                    PerTime perTime = new PerTime();
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
