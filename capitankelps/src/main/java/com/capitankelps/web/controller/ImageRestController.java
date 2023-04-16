package com.capitankelps.web.controller;

import com.capitankelps.web.model.QueryResponse;
import com.capitankelps.web.stabledifussion.QueryExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class ImageRestController {

    ReentrantLock lock = new ReentrantLock();

    @GetMapping("/sbbackend")
    QueryResponse getQuery(@RequestParam(name="query", required=false, defaultValue="") String query) {
        QueryResponse qr = new QueryResponse();
        qr.image = "";
        QueryExecutor qe = new QueryExecutor();
        if(!query.isEmpty()) {

            try {
                boolean isLockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

                if(isLockAcquired){
                    System.out.println("Lock locked  ");
                    try{
                        qr.image = qe.txt2Img(query);

                    }finally {
                        lock.unlock();
                        System.out.println("Lock unlocked");
                    }
                }else{
                    qr.image = "";
                    qr.busy = "true";
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return qr;
    }

}

