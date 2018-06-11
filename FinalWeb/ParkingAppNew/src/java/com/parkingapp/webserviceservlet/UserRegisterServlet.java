/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.google.zxing.WriterException;
import com.parkingapp.servlet.*;
import com.parkingapp.bean.UserBean;
import com.parkingapp.manager.QueryManager;
import com.parkingapp.qrcode.QRCode;
import java.io.BufferedReader;
import java.io.File;
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
public class UserRegisterServlet extends HttpServlet {

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
            System.out.println("inweb");
            String msg = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jSONObject;
            try {
                jSONObject = (JSONObject) new JSONParser().parse(data);
                JSONObject jsono = (JSONObject) jSONObject.get("nameValuePairs");
                UserBean info = new UserBean();
                info.setPhone((String) jsono.get("number"));
                info.setEmail((String) jsono.get("email"));
                info.setUsername((String) jsono.get("username"));
                info.setName((String) jsono.get("name"));
                info.setBalance((String) jsono.get("balance"));
                info.setPassword((String) jsono.get("password"));
                info.setUserType((String) jsono.get("userType"));
//QR Code
                String realPath = request.getServletContext().getRealPath("");
                File file = new File(realPath + File.separator + "QrCode" + File.separator + (String) jsono.get("username"));
                if (!file.exists()) {
                    file.mkdirs();
                }
                String filePath = QRCode.createQRCode((String) jsono.get("username"), file.getAbsolutePath() + File.separator);
                info.setQrCode("QrCode" + File.separator + (String) jsono.get("username") + File.separator + "QRCode.png");
//End QRCode
                boolean insertUser = new QueryManager().insertUser(info);
                if (insertUser) {
                    msg = "success";
                } else {
                    msg = "failed";
                }
            } catch (ParseException ex) {
                msg = "error";
                Logger.getLogger(UserRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriterException ex) {
                Logger.getLogger(UserRegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.out.println("Message :" + msg);
                out.write(msg);
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
