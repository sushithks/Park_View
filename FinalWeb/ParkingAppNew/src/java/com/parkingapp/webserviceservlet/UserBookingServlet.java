/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parkingapp.webserviceservlet;

import com.parkingapp.bean.BookingBean;
import com.parkingapp.manager.QueryManager;
import com.parkingapp.time.TM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet(name = "UserBookingServlet", urlPatterns = {"/UserBookingServlet"})
public class UserBookingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String msg = "failed";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jsono = (JSONObject) new JSONParser().parse(data);
            QueryManager qm = new QueryManager();
            BookingBean bean = new BookingBean();
            String userId = qm.getUserId((String) jsono.get("user"));
            int balance = qm.checkUserBalance((String) jsono.get("user"), "50");
            if (balance == -1) {
                msg = "insufficient balance";
            } else if (balance == -2) {
                msg = "you have no account here";
            } else {
                bean.setUser_id(userId);
                bean.setPa_id((String) jsono.get("id"));
                bean.setBooking_time(TM.getDate(TM.DD_MM_YYYY_HH_MM_SS_A));
                bean.setSlot_type((String) jsono.get("type"));
                SimpleDateFormat format = new SimpleDateFormat(TM.DD_MM_YYYY_HH_MM_SS_A);
                final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                final Date dateObj = sdf.parse((String) jsono.get("time"));
                String forTime = TM.getDate(TM.DD_MM_YYYY) + " " + new SimpleDateFormat("K:mm:ss a").format(dateObj);
                bean.setFor_time(forTime);
                jsono.clear();
                msg = qm.bookSlot(bean);
            }
            jsono.put("status", msg);
            out.write(jsono.toJSONString());

        } catch (org.json.simple.parser.ParseException ex) {
            Logger.getLogger(UserGetNearByParkingSpacesServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserBookingServlet.class.getName()).log(Level.SEVERE, null, ex);
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
