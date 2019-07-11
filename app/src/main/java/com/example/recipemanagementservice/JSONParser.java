package com.example.recipemanagementservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mustafatozluoglu on 10.07.2019
 */
public class JSONParser {
    // http://recipemanagementservice495.herokuapp.com/rest.php?list

    public JSONParser() {

    }

    public String makeServiceCall(String requestUrl) throws IOException {
        String response = null;

        URL url = new URL(requestUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream in = new BufferedInputStream(connection.getInputStream());
        response = convertStreamToString(in);


        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String satir = "";

        try {
            while ((satir = reader.readLine())!= null){
                sb.append(satir).append("\n");
            }
        } catch (IOException e) {

        } finally {
            try{
                is.close();
            }catch (IOException e){

            }
        }

        return sb.toString();
    }

}