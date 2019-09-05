package com.jerry.dupstat.service;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.PerTime;
import com.jerry.dupstat.domain.Summary;
import javafx.util.Pair;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface SummaryService {
    Summary doSummary(Login login);
    List<PerTime> query(Date start, Date end, Summary summary);
}
