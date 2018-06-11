/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.parkingapp.bean.LoginBean;
import com.parkingapp.manager.QueryManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "LoginServlet_1", urlPatterns = {"/LoginServlet_1"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("in web");
            String msg = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jSONObject = (JSONObject) new JSONParser().parse(data);
            JSONObject jsono = (JSONObject) jSONObject.get("nameValuePairs");
            String userName = (String) jsono.get("username");
            String password = (String) jsono.get("password");
            String fcm = (String) jsono.get("fcm");
            LoginBean bean = new LoginBean();
            bean.setUsername(userName);
            bean.setPassword(password);
            QueryManager qm = new QueryManager();
            String checkLogin = qm.checkLogin(bean);
            if (checkLogin.equals("PARKING AREA ADMIN")) {
                msg = "padmin";
            } else if (checkLogin.equals("PARK EM USER")) {
                msg = "user";
                qm.updateFCM(userName,password,fcm);
                
            } else {
                msg = "failed";
            }
            JSONObject jb = new JSONObject();
            jb.put("userType", msg);
            System.out.println(jb.toJSONString());
            out.write(jb.toJSONString());
        } catch (ParseException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
