package com.capitankelps.web.stabledifussion;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Base64;

public class QueryExecutor {

    String SCRIPT_PATH = "/home/sergio/Projects/ssainz/webapps/capitankelps/src/main/resources/scripts/txt2img.sh ";
    //String SCRIPT_PATH = "scripts/txt2img.sh ";
    public String txt2imgAndGetPath(String query){

        String cmd = SCRIPT_PATH + " '"+query+"'";
        String outPath = "";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
            var process = processBuilder.start();
            var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            process.getOutputStream();

            String lastLine = "";
            String line = "";
            while((line = reader.readLine()) != null){
                lastLine = line;
            }
            outPath = lastLine;
            process.waitFor();
        }catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!outPath.isEmpty() && outPath.contains(":")){
            outPath = outPath.split(":")[1];
        }
        return outPath;
    }

    public String txt2Img(String query){

        String imagePath = txt2imgAndGetPath(query);

        if(imagePath.isEmpty()){
           return imagePath;
        }

        File file = new File(imagePath);
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            byte[] encoded = Base64.getEncoder().encode(fileContent);
            return new String(encoded);
        } catch (IOException e) {
            return "";
        }
    }

    public static void main(String[] args){

        QueryExecutor qe = new QueryExecutor();
        String out = qe.txt2Img("stuffed animal at the bottom of the sea");
        System.out.println(out);

    }

}
