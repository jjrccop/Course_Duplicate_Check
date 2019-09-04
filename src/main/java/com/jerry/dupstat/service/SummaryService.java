package com.jerry.dupstat.service;

import java.util.Date;

public interface SummaryService {
    void doSummary();
    void query(Date start, Date end);
}
