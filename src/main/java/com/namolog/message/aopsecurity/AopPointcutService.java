package com.namolog.message.aopsecurity;

import org.springframework.stereotype.Service;

// for Method Security test - pointcut
@Service
public class AopPointcutService {

    public void pointcutSecured(){
        System.out.println("pointcutSecured");
    }

    public void notSecured(){
        System.out.println("notSecured");
    }
}
