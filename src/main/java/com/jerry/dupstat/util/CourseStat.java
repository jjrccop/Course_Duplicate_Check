package com.jerry.dupstat.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CourseStat {
    static String getName(Document doc) {
        Elements info = doc.select("table[class=tab3]").select("td[class=title1]");
        String tmpName = info.text().split("\\s+")[1];
        String name = tmpName.substring(3, tmpName.length());
        return name;
    }

    static String getStart(Document doc) {
        String strDate = "";
        Elements info = doc.select("table[class=tab3]").select("td[class=title nob]");

        Pattern pattern = Pattern.compile("[0-9]{4}");
        Matcher matcher = pattern.matcher(info.text());
        if (matcher.find()) {
            strDate = (matcher.group(0));
        }

        info = doc.select("table[class=tab1]").select("td[class=td1]");
        String tmpDate = info.get(7).text().split("\\s+")[1];
        pattern = Pattern.compile("[0-9]{2}/[0-9]{2}");
        matcher = pattern.matcher(tmpDate);
        if (matcher.find()) {
            strDate += ('/' + matcher.group(0));
        }
        return strDate;
    }

    static List<Integer> doStatic(Document doc) {
        List<Integer> ans = new ArrayList<>(1596);

        Elements trs = doc.select("table[class=tab1]").select("tr");
        String[] extraStr = {"", "教研", "不排课", "中秋节", "全国计算机等级考试", "国庆节", "艺考", "全国英语四六级", "研究生入学考试", "元旦节", "考试周"};

        int cnt = 0;
        for (org.jsoup.nodes.Element tr : trs) {
            Elements tds = tr.select("td[class=fontcss]");
            for (org.jsoup.nodes.Element td : tds) {
                String[] texts = td.text().split("\\s+");
                String strLen = td.attr("colspan");
                int len = 1;
                if (!strLen.equals("")) {
                    len = Integer.parseInt(strLen);
                    cnt += len;
                } else cnt++;
                if (texts.length == 2) {
                    for (int q = cnt - len; q < cnt; q++) ans.add(q);
                } else {
                    boolean flag = false;
                    for (String s : extraStr) {
                        if (texts[0].equals(s)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        for (int q = cnt - len; q < cnt; q++) ans.add(q);
                    }
                }
            }
        }
        return ans;
    }
}
