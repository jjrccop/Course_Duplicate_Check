package com.jerry.dupstat.domain;

import java.util.List;

public class Login {
    private String sessionId_1;
    private String sessionId_2;
    private List<String> participants;

    public String getSessionId_1() {
        return sessionId_1;
    }

    public void setSessionId_1(String sessionId_1) {
        this.sessionId_1 = sessionId_1;
    }

    public String getSessionId_2() {
        return sessionId_2;
    }

    public void setSessionId_2(String sessionId_2) {
        this.sessionId_2 = sessionId_2;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}
