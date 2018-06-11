package com.parkingapp.webserviceservlet;

import com.parkingapp.bean.AreaAdminRegisterBean;
import com.parkingapp.manager.QueryManager;
import com.parkingapp.time.TM;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet(name = "AreaAdminParkingInServlet", urlPatterns = {"/AreaAdminParkingInServlet"})
public class AreaAdminParkingInServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            System.out.println("inweb");
            String msg = "";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String data = bufferedReader.readLine();
            JSONObject jsono = null;
            try {
                jsono = (JSONObject) new JSONParser().parse(data);
                String username = (String) jsono.get("user_name");
                String pAdmin = (String) jsono.get("p_admin");
                String type = (String) jsono.get("vehicle_type");
                //checking already booked or not
                QueryManager queryManager = new QueryManager();
                JSONObject todaysBooking = queryManager.getTodaysBookingDetailsbyUserId(username, type);
                String time = (String) todaysBooking.get("time");
                System.out.println(time);
                if (time == null) {
                    time = TM.getDate(TM.DD_MM_YYYY_HH_MM_SS);
                }
                long diffDate = TM.diffDate(TM.getDate(TM.DD_MM_YYYY_HH_MM_SS), time, TM.DD_MM_YYYY_HH_MM_SS);
                if (diffDate > 0) {
                    //before
                    time = TM.getDate(TM.DD_MM_YYYY_HH_MM_SS);
                }

                //-------------------------------
                //booking code
                int insertUser = queryManager.parkingAllotSlot(username, pAdmin, time, type);
                if (insertUser == 1) {
                    msg = "success";
                } else if (insertUser == -1) {
                    msg = "you have already checked in!";
                } else {
                    msg = "failed";
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
