package com.jerry.dupstat.domain;

import java.util.List;
import java.util.Map;

public class Summary {
    private User user;
    private Map<Integer, List<String>> result;
    private String startDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer,List<String>> getResult() {
        return result;
    }

    public void setResult(Map<Integer,List<String>> result) {
        this.result = result;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
