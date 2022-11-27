package com.capitankelps.web.controller;

import com.capitankelps.web.model.QueryResponse;
import com.capitankelps.web.stabledifussion.QueryExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageRestController {

    @GetMapping("/sbbackend")
    QueryResponse getQuery(@RequestParam(name="query", required=false, defaultValue="") String query) {
        QueryResponse qr = new QueryResponse();
        qr.image = "";
        QueryExecutor qe = new QueryExecutor();
        if(!query.isEmpty()) qr.image = qe.txt2Img(query);
        return qr;
    }

}
