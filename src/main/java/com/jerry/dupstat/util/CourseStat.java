package com.jerry.dupstat.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourseStat {
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getName(){
        Document doc= getDocument();
        Elements info=doc.select("table[class=tab3]").select("td[class=title1]");
        String tmpName=info.text().split("\\s+")[1];
        String name=tmpName.substring(3,tmpName.length());
        return name;
    }
    public String getStart(){
        String strDate="";
        Document doc= getDocument();
        Elements info=doc.select("table[class=tab3]").select("td[class=title nob]");

        Pattern pattern=Pattern.compile("[0-9]{4}");
        Matcher matcher=pattern.matcher(info.text());
        if(matcher.find()) {
            strDate=(matcher.group(0));
        }

        info=doc.select("table[class=tab1]").select("td[class=td1]");
        String tmpDate=info.get(7).text().split("\\s+")[1];
        pattern=Pattern.compile("[0-9]{2}/[0-9]{2}");
        matcher=pattern.matcher(tmpDate);
        if(matcher.find()){
            strDate+=('/'+matcher.group(0));
        }
        return  strDate;
    }

    public List<Integer> doStatic(){
        Document doc= getDocument();
        String startDate=getStart();
        List<Integer> ans=new ArrayList<>(1596);

        Elements trs=doc.select("table[class=tab1]").select("tr");
        String[] extraStr={"","教研","不排课","中秋节","全国计算机等级考试","国庆节","艺考","全国英语四六级","研究生入学考试","元旦节","考试周"};

        int cnt=0;
        for(int i=0;i<trs.size();i++){
            Elements tds=trs.get(i).select("td[class=fontcss]");
            for(int j=0;j<tds.size();j++){
                String texts[]=tds.get(j).text().split("\\s+");
                String strLen=tds.get(j).attr("colspan");
                int len=1;
                if(strLen!="") {
                    len=Integer.parseInt(strLen);
                    cnt+=len;
                }
                else cnt++;
                if(texts.length==2) {
                    for(int q=cnt-len;q<cnt;q++) ans.add(q);
                }
                else{
                    boolean flag=false;
                    for (String s : extraStr) {
                        if (texts[0].equals(s)) {
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) {
                        for(int q=cnt-len;q<cnt;q++) ans.add(q);
                    }
                }
            }
        }
        return ans;
    }
}
