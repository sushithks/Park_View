package com.parkingapp.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

public class FCMManager {
    
    static String[] userList = null;
    Connection connection = null;
    static String user = null;
    static String msg = "";
    static boolean send = false;
    HttpURLConnection conn;
    String authKey = "AAAAtWe5hHw:APA91bE1AetOg7HwB68PF6uvBGcCEAR08U_DllL1sBcdY-1HrFP2NtpKEf-ngdpNuA1soT2kK5tnFol2o1Cc5QE_BG4UNYViR2O11tKflFSobjLrqonI54ub7HFJKlMQTIFnM6cXhzwF";   // You FCM AUTH key
    String FMCurl = "https://fcm.googleapis.com/fcm/send";
    
    public void pushFCMNotification(String id , String[] userList, String message) {
        PreparedStatement preparedStatement = null;
        QueryManager qm = new QueryManager();
        
        try {
            for (String username : userList) {
                String fcm = qm.getFCM(username);
                System.out.println("FCM :" + fcm);
                if (!fcm.equals("NO")) {
                    URL url = new URL(FMCurl);
                    conn = (HttpURLConnection) url.openConnection();
                    
                    conn.setUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setConnectTimeout(10000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Authorization", "key=" + authKey);
                    conn.setRequestProperty("Content-Type", "application/json");
                    
                    JSONObject json = new JSONObject();
                    json.put("to", fcm.trim());
                    JSONObject info = new JSONObject();
                    info.put("title", id);   // Notification title
                    info.put("body", "Parking App Notification:" + message); // Notification body
                    info.put("click_action", "OPEN_ACTIVITY_1");
                    json.put("notification", info);
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("message", "Parking App Notification:" + message);
                    json.put("data", dataObj);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(json.toString());
                    wr.flush();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    System.out.println(bufferedReader.readLine());
                    wr.close();
                    bufferedReader.close();
                } else {
                    System.out.println("invalid gcm ");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FCMManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void forwardNotification(String message, String fcmId) {
        try {
            URL url = new URL(FMCurl);
            conn = (HttpURLConnection) url.openConnection();
            
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + authKey);
            conn.setRequestProperty("Content-Type", "application/json");
            
            JSONObject json = new JSONObject();
            json.put("to", fcmId.trim());
            JSONObject info = new JSONObject();
            info.put("title", "Mon Forwarded Notification");   // Notification title
            info.put("body", message); // Notification body
            info.put("click_action", "OPEN_ACTIVITY_1");
            json.put("notification", info);
            JSONObject dataObj = new JSONObject();
            dataObj.put("message", message);
            json.put("data", dataObj);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println(bufferedReader.readLine());
            wr.close();
            bufferedReader.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(FCMManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FCMManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
