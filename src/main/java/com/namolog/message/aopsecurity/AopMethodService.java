package com.namolog.message.aopsecurity;

import org.springframework.stereotype.Service;

// for Method Security test
@Service
public class AopMethodService {

    public void methodSecured() {
        System.out.println("methodSecured");
    }
}
