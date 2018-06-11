
package com.parkingapp.webserviceservlet;

import com.parkingapp.manager.FCMManager;
import com.parkingapp.manager.QueryManager;
import com.parkingapp.time.TM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
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

@WebServlet(name = "AreaAdminParkingOutServlet", urlPatterns = {"/AreaAdminParkingOutServlet"})
public class AreaAdminParkingOutServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        JSONObject responseObj = new JSONObject();
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
                String checkinTime[] = new QueryManager().getCheckInTime(username, pAdmin, type);
                long diff = new Date(TM.getDate(TM.DD_MM_YYYY_HH_MM_SS)).getTime() - new Date(checkinTime[0]).getTime();
                System.out.println(diff);
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000);
                double cash = 0.0;
//                int diffInDays = (int) ((dt2.getTime() - dt1.getTime()) / (1000 * 60 * 60 * 24));
                JSONObject jSONObject = new QueryManager().getParkingSlotPrice(pAdmin, type);
                if (type.equals("bike")) {
                    String minString = (String) jSONObject.get("two_wheeler_for_1hr");
                    double min = Double.parseDouble(minString);

                    String afterOneHrString = (String) jSONObject.get("two_wheeler_after_1hr");
                    double afterOneHr = Double.parseDouble(afterOneHrString);

                    cash = cash + min;
                    if (diffHours > 1) {
                        cash = cash + ((diffHours - 1) * afterOneHr);
                    }
                    if (diffMinutes > 5) {
                        cash = cash + afterOneHr;
                    }

                } else {
                    String minString = (String) jSONObject.get("four_wheeler_for_1hr");
                    double min = Double.parseDouble(minString);

                    String afterOneHrString = (String) jSONObject.get("four_wheeler_after_1hr");
                    double afterOneHr = Double.parseDouble(afterOneHrString);

                    cash = cash + min;
                    if (diffHours > 1) {
                        cash = cash + ((diffHours - 1) * afterOneHr);
                    }
                    if (diffMinutes > 5) {
                        cash = cash + afterOneHr;
                    }
                }
                boolean insertUser = new QueryManager().parkingOutSlot(username, pAdmin, TM.getDate(TM.DD_MM_YYYY_HH_MM_SS), type, String.valueOf(cash));
                if (insertUser) {
                    msg = "success";
                    responseObj.put("cash", cash);
                    responseObj.put("id", checkinTime[1]);
                    responseObj.put("total_time", diffHours + ":" + diffMinutes + ":" + diffSeconds);

                } else {
                    msg = "you have already checked out. please check in";
                }
                
                jsono.clear();
                responseObj.put("response", msg);
            } catch (ParseException ex) {
                msg = "error";
                Logger.getLogger(AreaAdminEditServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                System.out.println("Message :" + msg);
                out.write(responseObj.toJSONString());
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
