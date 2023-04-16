package com.capitankelps.web.controller;

import com.capitankelps.web.model.QueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Controller
public class ImageController {


    String endpoint;

    @Value("${backend.endpoint}")
    void setEndpoint(String endp){
        endpoint = endp;
    }

    @GetMapping("/sb")
    public String stableDiffusionQuery(@RequestParam(name="query", required=false, defaultValue="") String query, Model model) throws InterruptedException {
        model.addAttribute("has_query", !query.isEmpty());
        if(!query.isEmpty()){
            model.addAttribute("query", query);
            // add output of image
            QueryResponse qr = text2ImgFromBackend(query);
            if(qr.busy.equals("true")){
                model.addAttribute("is_busy", true);
                model.addAttribute("imagebase64", "");
            }else{
                model.addAttribute("imagebase64", qr.image);
            }


        }
        return "sb";
    }

    QueryResponse text2ImgFromBackend(String query){
        QueryResponse qr = new QueryResponse();
        if(StringUtils.hasLength(endpoint)){

            String uri = endpoint + "/sbbackend?query="+ URLEncoder.encode(query, StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();

            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet request = new HttpGet(uri);

                QueryResponse response = client.execute(request, httpResponse ->
                        mapper.readValue(httpResponse.getEntity().getContent(), QueryResponse.class));

                qr = response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return qr;
    }


}
