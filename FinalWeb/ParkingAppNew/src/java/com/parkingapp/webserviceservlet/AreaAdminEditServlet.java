/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.parkingapp.bean.AreaAdminRegisterBean;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Staff
 */
public class AreaAdminEditServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("inweb");
            String msg = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jSONObject;
            try {
                jSONObject = (JSONObject) new JSONParser().parse(data);
                JSONObject jsono = (JSONObject) jSONObject.get("nameValuePairs");
                AreaAdminRegisterBean info = new AreaAdminRegisterBean();
                info.setUserType((String) jsono.get("user_type"));
                info.setEmail_id((String) jsono.get("area_admin_email"));
                info.setFour_wheeler_after_1hr((String) jsono.get("fourWheelerPriceAfetrOneHour"));
                info.setFour_wheeler_for_1hr((String) jsono.get("fourWheelerPriceForOneHour"));
                info.setLati((String) jsono.get("lat"));
                info.setLongi((String) jsono.get("longi"));
                info.setNumber_for_four_wheeler((String) jsono.get("fourWheelerSlots"));
                info.setNumber_for_two_wheeler((String) jsono.get("twoWheelerSlots"));
                info.setParking_name((String) jsono.get("name"));
                info.setPassword((String) jsono.get("area_admin_password"));
                info.setPhone_number((String) jsono.get("area_admin_number"));
                info.setTwo_wheeler_after_1hr((String) jsono.get("twoWheelerPriceAfterOneHour"));
                info.setTwo_wheeler_for_1hr((String) jsono.get("twoWheelerPriceForOneHour"));
                info.setUsername((String) jsono.get("area_admin_username"));
                boolean insertUser = new QueryManager().updateAreaAdmin(info);
                if (insertUser) {
                    msg = "success";
                } else {
                    msg = "failed";
                }
            } catch (ParseException ex) {
                msg = "error";
                Logger.getLogger(AreaAdminEditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.out.println("Message :" + msg);
                JSONObject jsono = new JSONObject();
                jsono.put("status", msg);
                out.write(jsono.toJSONString());
            }
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
