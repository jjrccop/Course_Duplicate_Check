package com.jerry.dupstat.service;

import com.jerry.dupstat.domain.Login;
import com.jerry.dupstat.domain.User;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoginService {
    Boolean Login(User user);
}
