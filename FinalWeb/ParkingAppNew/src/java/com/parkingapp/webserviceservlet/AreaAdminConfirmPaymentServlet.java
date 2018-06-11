/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.parkingapp.manager.FCMManager;
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

@WebServlet(name = "AreaAdminConfirmPaymentServlet", urlPatterns = {"/AreaAdminConfirmPaymentServlet"})
public class AreaAdminConfirmPaymentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("inweb");
            String msg = "failed";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jsono = null;
            try {
                jsono = (JSONObject) new JSONParser().parse(data);
                String id = (String) jsono.get("id");
                String mode = (String) jsono.get("payment_mode");
                QueryManager qm = new QueryManager();
                int status = 0;
                if (mode != null && mode.equalsIgnoreCase("online")) {
                    JSONObject parks = qm.getParkListById(id);
                    //check balace
                    status = qm.checkUserBalance((String) parks.get("username"), (String) parks.get("amount"));
                    if (status == -1) {
                        msg = "insufficient balance";
                    } else if (status == -2) {
                        msg = "you have no account here";
                    } else {
                        qm.updateUserBalance((String) parks.get("username"), (String) parks.get("amount"));
                        qm.updateAdminBalance((String) parks.get("admin_username"), (String) parks.get("amount"));
                        boolean insertUser = qm.updatePaymentMode(id, mode);
                        if (insertUser == true) {
                            msg = "success";
                            FCMManager fcm = new FCMManager();
                            String[] users = {(String)parks.get("username")};
                            fcm.pushFCMNotification(id,users, "Payment completed \n, amount is"+(String) parks.get("amount") +" deducted");
                        } else {
                            msg = "failed";
                        }
                    }
                } else {
                    boolean insertUser = qm.updatePaymentMode(id, mode);
                    msg = insertUser == true ? "success" : "failed";
                }

                jsono.clear();
                jsono.put("response", msg);
            } catch (ParseException ex) {
                msg = "error";
                Logger.getLogger(AreaAdminEditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.out.println("Message :" + msg);
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
