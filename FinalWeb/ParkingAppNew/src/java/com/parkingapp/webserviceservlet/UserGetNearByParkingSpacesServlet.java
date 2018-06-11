/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.parkingapp.manager.QueryManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserGetNearByParkingSpacesServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            QueryManager queryManager = new QueryManager();
            queryManager.autoCancelBooking();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jsono = (JSONObject) new JSONParser().parse(data);
            //JSONObject jsono = (JSONObject) jSONObject.get("nameValuePairs");

            String srcLat = (String) jsono.get("lat");
            String srcLongi = (String) jsono.get("longi");
            JSONArray finalArray = new JSONArray();
            JSONArray jsonArray = queryManager.getAllParkingSpaces();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsono1 = (JSONObject) jsonArray.get(i);
                double dist = CheckDistanceMatrix.getDistance(srcLat, srcLongi, (String) jsono1.get("lat"), (String) jsono1.get("longi"), 2000);
                if (dist > 0) {
                    jsono1.put("distance", dist);
                    finalArray.add(jsono1);
                }
            }
            // System.out.println(finalArray.toJSONString());
            out.write(finalArray.toJSONString());

        } catch (ParseException ex) {
            Logger.getLogger(UserGetNearByParkingSpacesServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
