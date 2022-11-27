package com.capitankelps.web.stabledifussion;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class QueryExecutor {

    public String text2Img(String query){

        String imagePath = "/Users/sergiosainz/Downloads/bluewhale.jpg";
        File file = new File(imagePath);
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] encoded = Base64.getEncoder().encode(fileContent);
            return new String(encoded);
        } catch (IOException e) {
            return "";
        }
    }
}
