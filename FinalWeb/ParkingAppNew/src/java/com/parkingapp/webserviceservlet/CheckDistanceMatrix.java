package com.parkingapp.webserviceservlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CheckDistanceMatrix {

    public static double getDistance(String lat1, String longi1, String lat2, String longi2, double r) {
        boolean status = false;
        double distance1 = 0.0;
        try {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + lat1 + "," + longi1 + "&destinations=" + lat2 + "%2C" + longi2 + "&key=AIzaSyA_gRm06-tj6Md5XKTx6lekvxmgYsGNn1M";

            URL url1 = new URL(url);
            HttpURLConnection uRLConnection = (HttpURLConnection) url1.openConnection();
            uRLConnection.setDoInput(true);
            uRLConnection.setDoOutput(true);
            uRLConnection.connect();
            BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = bufferdReader.readLine()) != null) {
                response.append(inputLine + "\n");
            }
            JSONObject jSONObject = (JSONObject) new JSONParser().parse(response.toString());
            JSONArray rowsJsonArray = (JSONArray) jSONObject.get("rows");
            JSONObject jSONObject1 = (JSONObject) rowsJsonArray.get(0);
            JSONArray elementsJSONArray = (JSONArray) jSONObject1.get("elements");
            JSONObject json = (JSONObject) elementsJSONArray.get(0);
            JSONObject distJsonObject = (JSONObject) json.get("distance");
            System.out.println("disti " + distJsonObject);
            if (distJsonObject != null) {
                String distance = (String) distJsonObject.get("text");
                String dist = "0.0";
                if (distance != null && distance.contains(" ")) {
                    dist = distance.substring(0, distance.indexOf(" "));
                }
                System.out.println("dist=" + dist);
                double parseDouble = Double.parseDouble(dist);
                if (parseDouble <= (r)) {
                    distance1 = parseDouble;
                    status = true;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(CheckDistanceMatrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(CheckDistanceMatrix.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(CheckDistanceMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        return distance1;

    }

    public static void main(String[] args) {
        System.out.println(getDistance("10.095921", "76.339874", "10.014338", "76.350861", 30));
    }
}
