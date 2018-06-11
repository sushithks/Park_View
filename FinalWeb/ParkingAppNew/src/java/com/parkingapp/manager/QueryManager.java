package com.parkingapp.manager;

import com.parkingapp.bean.AreaAdminRegisterBean;
import com.parkingapp.bean.BookingBean;
import com.parkingapp.bean.LoginBean;
import com.parkingapp.bean.UserBean;
import com.parkingapp.bean.FeedbackBean;
import com.parkingapp.db.DbConnection;
import com.parkingapp.time.TM;
import static com.parkingapp.time.TM.diffDate;
import static com.parkingapp.time.TM.getDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class QueryManager {

    Connection connection = null;

    public QueryManager() {
        connection = new DbConnection().getConnection();
    }

    public String checkLogin(LoginBean bean) {
        String userType = "";
        try {
            String sql = "SELECT user_type FROM login_tbl WHERE uname = ? AND password = ? AND status ='A'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bean.getUsername());
            ps.setString(2, bean.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userType = rs.getString("user_type");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userType;
    }

    public ArrayList<AreaAdminRegisterBean> viewRequest() {
        ArrayList<AreaAdminRegisterBean> list = new ArrayList<AreaAdminRegisterBean>();
        try {
            String sql = "SELECT * FROM area_admin_register_tbl INNER JOIN login_tbl ON login_tbl.uname = area_admin_register_tbl.username where login_tbl.status='p' AND login_tbl.user_type = 'PARKING AREA ADMIN '";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AreaAdminRegisterBean bean = new AreaAdminRegisterBean();
                bean.setEmail_id(rs.getString("email_id"));
                bean.setFour_wheeler_after_1hr(rs.getString("four_wheeler_after_1hr"));
                bean.setFour_wheeler_for_1hr(rs.getString("four_wheeler_for_1hr"));
                bean.setNumber_for_four_wheeler(rs.getString("number_for_four_wheeler"));
                bean.setNumber_for_two_wheeler(rs.getString("number_for_two_wheeler"));
                bean.setParking_name(rs.getString("parking_name"));
                bean.setPhone_number(rs.getString("phone_number"));
                bean.setTwo_wheeler_after_1hr(rs.getString("two_wheeler_after_1hr"));
                bean.setTwo_wheeler_for_1hr(rs.getString("two_wheeler_for_1hr"));
                bean.setUsername(rs.getString("username"));
                list.add(bean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public int aproveAreaReq(String username) {
        int executeUpdate = 0;
        try {
            String query = "UPDATE login_tbl SET status = 'A' WHERE uname = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            executeUpdate = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeUpdate;
    }

    public int rejectAreaReq(String username) {
        int executeUpdate = 0;
        try {
            String query = "DELETE FROM area_admin_register_tbl  WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            executeUpdate = ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return executeUpdate;
    }

    public ArrayList<UserBean> viewUser() {
        ArrayList<UserBean> list = new ArrayList<UserBean>();
        try {
            String sql = "SELECT * FROM user_register_tbl";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserBean bean = new UserBean();
                bean.setEmail(rs.getString("email_id"));
                bean.setBalance(rs.getString("balance"));
                bean.setPhone(rs.getString("phone_number"));
                bean.setUsername(rs.getString("username"));
                bean.setName(rs.getString("name"));
                list.add(bean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<BookingBean> viewBooking() {
        ArrayList<BookingBean> list = new ArrayList<BookingBean>();
        try {
            String sql = "SELECT * FROM booking_tbl";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookingBean bean = new BookingBean();
                bean.setBooking_time(rs.getString("booking_time"));
                bean.setFor_time(rs.getString("for_time"));
                bean.setPa_id(rs.getString("pa_id"));
                bean.setSlot_type(rs.getString("slot_type"));
                bean.setStatus_of_parking_area_admin(rs.getString("booking_status"));
                bean.setUser_id(rs.getString("user_id"));
                list.add(bean);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<FeedbackBean> viewFeedback() {
        ArrayList<FeedbackBean> list = new ArrayList<FeedbackBean>();
        try {
            String sql = "SELECT * FROM parking_tbl";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FeedbackBean bean = new FeedbackBean();
                bean.setFeedback(rs.getString("feed_back"));
                bean.setPaid(rs.getString("p_id"));
                bean.setUserid(rs.getString("user_id"));
                list.add(bean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean insertUser(UserBean info) {
        boolean flag = false;
        try {
            String query = "SELECT * FROM login_tbl WHERE uname = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            prepareStatement.setString(1, info.getUsername());
            ResultSet rs2 = prepareStatement.executeQuery();
            if (rs2.next()) {
                flag = false;
            } else {
                String sql = "INSERT INTO user_register_tbl(name,username,email_id,"
                        + "phone_number,balance,qrcode_path)VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, info.getName());
                ps.setString(2, info.getUsername());
//            ps.setString(3, info.getPassword());
                ps.setString(3, info.getEmail());
                ps.setString(4, info.getPhone());
                ps.setString(5, info.getBalance());
                ps.setString(6, info.getQrCode());
                int auto = ps.executeUpdate();
                if (auto > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    rs.next();
                    auto = rs.getInt(1);
                }
                if (auto > 0) {
                    String sql1 = "INSERT INTO login_tbl(uname,password,user_type,status)VALUES(?,?,?,?)";
                    PreparedStatement ps1 = connection.prepareStatement(sql1);
                    // ps1.setInt(1, auto);
                    ps1.setString(1, info.getUsername());
                    ps1.setString(2, info.getPassword());
                    ps1.setString(3, info.getUserType());
                    ps1.setString(4, "A");
                    if (ps1.executeUpdate() > 0) {
                        flag = true;
                    }
                }
                if (auto > 0) {
                    String sqlWallet = "INSERT INTO wallet_tbl(user_id,balance)VALUES(?,0)";
                    PreparedStatement ps1 = connection.prepareStatement(sqlWallet);
                    ps1.setString(1, info.getUsername());
                    if (ps1.executeUpdate() > 0) {
                        flag = true;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean insertAreaAdmin(AreaAdminRegisterBean info) {
        boolean flag = false;
        try {
            String sql = "INSERT INTO area_admin_register_tbl(parking_name,username,"
                    + "password,email_id,phone_number,lat,longitude,number_for_two_wheeler,"
                    + "two_wheeler_for_1hr,two_wheeler_after_1hr,number_for_four_wheeler,"
                    + "four_wheeler_for_1hr,four_wheeler_after_1hr)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, info.getParking_name());
            ps.setString(2, info.getUsername());
            ps.setString(3, info.getPassword());
            ps.setString(4, info.getEmail_id());
            ps.setString(5, info.getPhone_number());
            ps.setString(6, info.getLati());
            ps.setString(7, info.getLongi());
            ps.setString(8, info.getNumber_for_two_wheeler());
            ps.setString(9, info.getTwo_wheeler_for_1hr());
            ps.setString(10, info.getTwo_wheeler_after_1hr());
            ps.setString(11, info.getNumber_for_four_wheeler());
            ps.setString(12, info.getFour_wheeler_for_1hr());
            ps.setString(13, info.getFour_wheeler_after_1hr());
            if (ps.executeUpdate() > 0) {
                String sql1 = "INSERT INTO login_tbl(uname,password,user_type,status)VALUES(?,?,?,?)";
                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ps1.setString(1, info.getUsername());
                ps1.setString(2, info.getPassword());
                ps1.setString(3, info.getUserType());
                ps1.setString(4, "P");
                if (ps1.executeUpdate() > 0) {
                    flag = true;
                    String sqlWallet = "INSERT INTO wallet_tbl(user_id,balance)VALUES(?,0)";
                    PreparedStatement ps2 = connection.prepareStatement(sqlWallet);
                    ps2.setString(1, info.getUsername());
                    if (ps2.executeUpdate() > 0) {
                        flag = true;
                    }

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateUser(UserBean info) {
        boolean flag = false;
        try {
            String query = "UPDATE login_tbl SET  password = ? WHERE uname = ?";
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setString(1, info.getPassword());
            ps1.setString(2, info.getUsername());
            ps1.executeUpdate();
            String sql = "UPDATE user_register_tbl SET name = ?, "
                    + "email_id = ?,phone_number = ? WHERE username = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, info.getName());
//            ps.setString(2, info.getPassword());
            ps.setString(2, info.getEmail());
            ps.setString(3, info.getPhone());
            ps.setString(4, info.getUsername());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateAreaAdmin(AreaAdminRegisterBean info) {
        boolean flag = false;
        try {
            String sql = "UPDATE area_admin_register_tbl SET parking_name=?,password=?,email_id=?,"
                    + "phone_number=?,lat=?,longitude=?,number_for_two_wheeler=?,two_wheeler_for_1hr=?,"
                    + "two_wheeler_after_1hr=?,number_for_four_wheeler=?,four_wheeler_for_1hr=?,"
                    + "four_wheeler_after_1hr=? WHERE username = ? ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, info.getParking_name());
            ps.setString(2, info.getPassword());
            ps.setString(3, info.getEmail_id());
            ps.setString(4, info.getPhone_number());
            ps.setString(5, info.getLati());
            ps.setString(6, info.getLongi());
            ps.setString(7, info.getNumber_for_two_wheeler());
            ps.setString(8, info.getTwo_wheeler_for_1hr());
            ps.setString(9, info.getTwo_wheeler_after_1hr());
            ps.setString(10, info.getNumber_for_four_wheeler());
            ps.setString(11, info.getFour_wheeler_for_1hr());
            ps.setString(12, info.getFour_wheeler_after_1hr());
            ps.setString(13, info.getUsername());
            if (ps.executeUpdate() > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public String getDetails(String username) {
        String path = "";
        try {
            String query = "SELECT qrcode_path FROM user_register_tbl WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                path = rs.getString("qrcode_path");
//                File file = new File(rs.getString("bio_data"));
//                if (file.exists()) {
//                    FileInputStream fis = new FileInputStream(file);
//                    XWPFDocument document = new XWPFDocument(fis);
//                    XWPFWordExtractor docExtractor = new XWPFWordExtractor(document);
                //bean.setBiodata(docExtractor.getText());
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public JSONObject viewUser(String userName) {
//        ArrayList<UserBean> list = new ArrayList<UserBean>();
        JSONObject jSONObject = new JSONObject();
        try {
            String sql = "SELECT * FROM user_register_tbl WHERE username = ?";
            String query = "SELECT password FROM login_tbl WHERE uname = ?";
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setString(1, userName);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                jSONObject.put("app_user_password", rs1.getString("password"));
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                UserBean bean = new UserBean();
//                bean.setEmail(rs.getString("email_id"));
//                bean.setBalance(rs.getString("balance"));
//                bean.setPhone(rs.getString("phone_number"));
//                bean.setUsername(rs.getString("username"));
//                bean.setName(rs.getString("name"));
//                bean.setName(rs.getString("password"));
//                list.add(bean);

                jSONObject.put("app_user_name", rs.getString("name"));
                jSONObject.put("app_user_username", rs.getString("username"));
                jSONObject.put("app_user_email", rs.getString("email_id"));
                jSONObject.put("app_user_phoneNumber", rs.getString("phone_number"));
                jSONObject.put("status", "valid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jSONObject;
    }

    public JSONObject viewParkingAreaAdmin(String userName) {
        JSONObject jSONObject = new JSONObject();
        try {
            String sql = "SELECT * FROM area_admin_register_tbl WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jSONObject.put("area_admin_email", rs.getString("email_id"));
                jSONObject.put("fourWheelerPriceAfter1Hr", rs.getString("four_wheeler_after_1hr"));
                jSONObject.put("fourWheelerPriceFor1Hr", rs.getString("four_wheeler_for_1hr"));
                jSONObject.put("fourWheelerNoOfSlots", rs.getString("number_for_four_wheeler"));
                jSONObject.put("twoWheelerNoOfSlots", rs.getString("number_for_two_wheeler"));
                jSONObject.put("area_admin_parking_name", rs.getString("parking_name"));
                jSONObject.put("area_admin_phoneNumber", rs.getString("phone_number"));
                jSONObject.put("twoWheelerPriceAfter1Hr", rs.getString("two_wheeler_after_1hr"));
                jSONObject.put("twoWheelerPriceFor1Hr", rs.getString("two_wheeler_for_1hr"));
                jSONObject.put("area_admin_username", rs.getString("username"));
                jSONObject.put("area_admin_password", rs.getString("password"));
                jSONObject.put("lat", rs.getString("lat"));
                jSONObject.put("longi", rs.getString("longitude"));
                jSONObject.put("status", "valid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jSONObject;
    }

    public JSONArray getAllParkingSpaces() {
        JSONArray jSONArray = new JSONArray();
        try {
            String query = "Select * from area_admin_register_tbl";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject jobj = new JSONObject();
                jobj.put("lat", rs.getString("lat"));
                jobj.put("longi", rs.getString("longitude"));

                int totalTwoWheelerArea = Integer.parseInt(rs.getString("number_for_two_wheeler"));
                int totalFourWheelerArea = Integer.parseInt(rs.getString("number_for_four_wheeler"));
                JSONObject filledCounts = getFilledCount(rs.getString("id"));
                int filledFourWheeler = Integer.parseInt((String) filledCounts.get("number_for_four_wheeler"));
                int filledTwoWheeler = Integer.parseInt((String) filledCounts.get("number_for_two_wheeler"));
                int r = (totalTwoWheelerArea - filledTwoWheeler) < 0 ? 0 : (totalTwoWheelerArea - filledTwoWheeler);
                jobj.put("motor_bikes_slots", r);
                r = (totalFourWheelerArea - filledFourWheeler) < 0 ? 0 : (totalFourWheelerArea - filledFourWheeler);
                jobj.put("car_slots", r);

                jobj.put("minimum_charge_bike", rs.getString("two_wheeler_for_1hr"));
                jobj.put("minimum_charge_car", rs.getString("four_wheeler_for_1hr"));
                jobj.put("bike_after_one_hr", rs.getString("two_wheeler_after_1hr"));
                jobj.put("car_after_one_hr", rs.getString("four_wheeler_after_1hr"));
                jobj.put("id", rs.getString("id"));
                jSONArray.add(jobj);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jSONArray;
    }

    public JSONObject getFilledCount(String id) {
        JSONObject jobj = new JSONObject();
        try {
            String query = "SELECT  (select COUNT(*) from parking_tbl WHERE p_id = ? AND slot_type='car') +"
                    + " (select COUNT(*) from booking_tbl WHERE pa_id = ? AND slot_type='car' AND booking_status!='pending') fill_count from dual";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, id);
            ResultSet rs = ps.executeQuery();

            jobj.put("number_for_four_wheeler", "0");
            jobj.put("number_for_two_wheeler", "0");

            if (rs.next()) {
                jobj.put("number_for_four_wheeler", rs.getString("fill_count"));
            }
            rs.close();
            ps.close();
            query = "SELECT  (select COUNT(*) from parking_tbl WHERE p_id = ? AND slot_type='bike') +"
                    + " (select COUNT(*) from booking_tbl WHERE pa_id = ? AND slot_type='bike' AND booking_status!='pending') fill_count from dual";
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("number_for_two_wheeler", rs.getString("fill_count"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public boolean userAccount(String userName, double amount, String cardName, String expDate, String cvv, String cardNo) {
        boolean flag = false;
        try {
            String sql1 = "SELECT id,balance FROM bank_tbl WHERE card_name = ?"
                    + " AND cvv_no = ? AND exp_date = ? AND card_no = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql1);
            prepareStatement.setString(1, cardName);
            prepareStatement.setString(2, cvv);
            prepareStatement.setString(3, expDate);
            prepareStatement.setString(4, cardNo);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                double bal = Double.parseDouble(rs.getString("balance"));
                if (bal >= amount) {
                    String sql = "UPDATE wallet_tbl SET balance = balance + " + amount + " WHERE user_id = ?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    // ps.setDouble(1, amount);
                    ps.setString(1, userName);
                    if (ps.executeUpdate() > 0) {
                        String query = "UPDATE bank_tbl SET balance = balance - " + amount + " WHERE id = ?";
                        PreparedStatement ps1 = connection.prepareStatement(query);
                        ps1.setString(1, id);
                        if (ps1.executeUpdate() > 0) {
                            flag = true;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public int calculateAmount(String timeIn, String timeOut) {
        int amount = 0;
        int numOfHour = 0;
        int numOfMinute = 0;
        String[] timeInArr = timeIn.split(":");
        String[] timeOutArr = timeOut.split(":");
        if (timeInArr != null && timeOutArr.length >= 2 || timeOutArr != null && timeOutArr.length >= 2) {
            int inHour = Integer.parseInt(timeInArr[0]);
            int inMinute = Integer.parseInt(timeInArr[1]);
            int outHour = Integer.parseInt(timeOutArr[0]);
            int outMinute = Integer.parseInt(timeOutArr[1]);
            if (inHour > outHour) {
                numOfHour = inHour - outHour;
            } else {
                numOfHour = outHour - inHour;
            }
            if (numOfHour > 0) {
                if (inMinute == outMinute) {
                    if (numOfHour == 1) {
                        amount = 100;
                    } else if (numOfHour > 1) {
                        amount = 100 + (50 * numOfHour - 1);
                    }
                } else if (inMinute < outMinute) {
                    if (numOfHour == 1) {
                        amount = 100 + 50;
                    } else if (numOfHour > 1) {
                        amount = 100 + 50 + (50 * numOfHour - 1);
                    }
                }
            } else {
                amount = 0;
            }
        }
        return amount;
    }

    public JSONObject getNumOfSlot(String userName) {
        JSONObject jobj = new JSONObject();
        try {
            String sql = "SELECT number_for_two_wheeler,number_for_four_wheeler FROM"
                    + " area_admin_register_tbl WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jobj.put("number_for_two_wheeler", rs.getString("number_for_two_wheeler"));
                jobj.put("number_for_four_wheeler", rs.getString("number_for_four_wheeler"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public JSONObject getAvailableNumOfSlot(String pAdmin, String vehicleType) {
        JSONObject jobj = new JSONObject();
        try {
            String sql = "SELECT  ((SELECT ? from area_admin_register_tbl "
                    + "where username = ?) - (select COUNT(*) FROM parking_tbl where check_out_time = 'NA')) as avi from dual ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, vehicleType);
            ps.setString(2, pAdmin);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int avi = rs.getInt("avi");
                avi = avi > 0 ? avi : 0;
                jobj.put("available", avi);
//              jobj.put("number_for_four_wheeler", rs.getString("number_for_four_wheeler"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public int parkingAllotSlot(String userName, String pAdmin, String timeIn, String vehicleType) {
        int flag = 0;
        String check = "Select * from parking_tbl where user_id = ? AND check_in_time !='NA' AND check_out_time='NA'";
        try {
            String userId = getUserId(userName);
            PreparedStatement psCheck = connection.prepareStatement(check);
            psCheck.setString(1, userId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                flag = -1;
            } else {
                //---------------------------------------- 
                String sql = "INSERT INTO parking_tbl(user_id,p_id,slot_type,check_in_time)VALUES(?,?,?,?) ";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, userId);
                ps.setString(2, getAdminUserId(pAdmin));
                ps.setString(3, vehicleType);
                ps.setString(4, timeIn);
                int executeUpdate = ps.executeUpdate();
                if (executeUpdate > 0) {
                    flag = 1;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean parkingOutSlot(String userName, String pAdmin, String timeOut, String vehicleType, String cash) {
        boolean flag = false;
        String sql = "UPDATE  parking_tbl SET check_out_time = ?, amount = ? WHERE user_id = ? AND p_id = ? AND slot_type = ? AND check_out_time ='NA'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, timeOut);
            ps.setString(2, cash);
            ps.setString(3, getUserId(userName));
            ps.setString(4, getAdminUserId(pAdmin));
            ps.setString(5, vehicleType);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public String getAdminUserId(String userName) {
        String id = "0";
        try {
            String sql = "SELECT id FROM"
                    + " area_admin_register_tbl WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("id");

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getUserId(String userName) {
        String id = "0";
        try {
            String sql = "SELECT user_id FROM"
                    + " user_register_tbl WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("user_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getUserIdByBookID(String bookId) {
        String id = "0";
        try {
            String sql = "SELECT user_id FROM"
                    + " booking_tbl WHERE booking_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("user_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getUserName(String userId) {
        String id = "0";
        try {
            String sql = "SELECT username FROM"
                    + " user_register_tbl WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String getAdminUserName(String userId) {
        String id = "0";
        try {
            String sql = "SELECT username FROM"
                    + " area_admin_register_tbl WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public String[] getCheckInTime(String username, String pAdmin, String type) {
        String checkInTime$id[] = new String[2];
        try {
            String query = "Select check_in_time,id from parking_tbl  WHERE user_id = ? AND p_id = ? AND slot_type = ? AND check_in_time!='NA'";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getUserId(username));
            ps.setString(2, getAdminUserId(pAdmin));
            ps.setString(3, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                checkInTime$id[0] = rs.getString("check_in_time");
                checkInTime$id[1] = rs.getString("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkInTime$id;
    }

    public JSONObject getParkingSlotPrice(String pAdmin, String type) {
        JSONObject jsono = new JSONObject();
        try {
            String query = "Select * from area_admin_register_tbl  WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pAdmin);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jsono.put("two_wheeler_for_1hr", rs.getString("two_wheeler_for_1hr"));
                jsono.put("two_wheeler_after_1hr", rs.getString("two_wheeler_after_1hr"));
                jsono.put("four_wheeler_for_1hr", rs.getString("four_wheeler_for_1hr"));
                jsono.put("four_wheeler_after_1hr", rs.getString("four_wheeler_after_1hr"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsono;
    }

    public boolean updatePaymentMode(String id, String mode) {
        boolean flag = false;
        String sql = "UPDATE parking_tbl SET cash_mode = ? WHERE id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mode);
            ps.setString(2, id);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateUserBalance(String id, String amount) {
        boolean flag = false;
        String sql = "update wallet_tbl set balance = balance - ? WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, amount);
            ps.setString(2, id);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
            String[] users = {id};
            new FCMManager().pushFCMNotification(id, users, "Booking canceled \n, amount is" + amount + " deducted");
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateAdminBalance(String id, String amount) {
        boolean flag = false;
        String sql = "update wallet_tbl set balance = balance + ? WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, amount);
            ps.setString(2, id);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public JSONObject getParkListById(String id) {
        /*hh*/
        JSONObject jobj = new JSONObject();
        try {
            String query = "Select * from parking_tbl where id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("username", getUserName(rs.getString("user_id")));
                jobj.put("admin_username", getAdminUserName(rs.getString("p_id")));
                jobj.put("p_id", rs.getString("p_id"));
                jobj.put("amount", rs.getString("amount"));
                jobj.put("check_in_time", rs.getString("check_in_time"));
                jobj.put("check_out_time", rs.getString("check_out_time"));
                jobj.put("slot_type", rs.getString("slot_type"));
                jobj.put("booking_id", rs.getString("booking_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }
//users list

    public JSONArray getParkHistory(String username) {
        JSONArray arr = new JSONArray();
        try {
            String query = "Select * from parking_tbl where user_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getUserId(username));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JSONObject jobj = new JSONObject();
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("p_id", rs.getString("p_id"));
                jobj.put("cash", rs.getString("amount"));
                jobj.put("check_in_time", rs.getString("check_in_time"));
                jobj.put("check_out_time", rs.getString("check_out_time"));
                jobj.put("slot_type", rs.getString("slot_type"));
                jobj.put("booking_id", rs.getString("booking_id"));
                jobj.put("payment_mode", rs.getString("cash_mode"));
                //==========================================================
                JSONObject slotDetails = getSlotDetailsById(rs.getString("p_id"));
                jobj.put("parking_name", slotDetails.get("parking_name"));
                jobj.put("lat", slotDetails.get("lat"));
                jobj.put("longi", slotDetails.get("log"));
                arr.add(jobj);

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public JSONArray getAdminParkHistory(String username) {
        JSONArray arr = new JSONArray();
        try {
            String query = "Select * from parking_tbl where p_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getAdminUserId(username));
            ResultSet rs = ps.executeQuery();
            JSONObject slotDetails = getSlotDetails(username);
            while (rs.next()) {
                JSONObject jobj = new JSONObject();
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("p_id", rs.getString("p_id"));
                jobj.put("cash", rs.getString("amount"));
                jobj.put("check_in_time", rs.getString("check_in_time"));
                jobj.put("check_out_time", rs.getString("check_out_time"));
                jobj.put("vehicle_type", rs.getString("slot_type"));
                jobj.put("booking_id", rs.getString("booking_id"));
                jobj.put("payment_mode", rs.getString("cash_mode"));
                jobj.put("feed_back", rs.getString("feed_back"));
                //================================================
                jobj.put("parking_name", slotDetails.get("parking_name"));
                jobj.put("lat", slotDetails.get("lat"));
                jobj.put("log", slotDetails.get("log"));

                arr.add(jobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public JSONArray getBookingDetails(String username) {
        JSONArray arr = new JSONArray();
        try {

            String query = "Select * from booking_tbl where user_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getUserId(username));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject jobj = new JSONObject();
                jobj.put("id", rs.getString("booking_id"));
                jobj.put("p_id", rs.getString("pa_id"));
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("booking_time", rs.getString("booking_time"));
                jobj.put("time", rs.getString("for_time"));
                jobj.put("type", rs.getString("slot_type"));
                jobj.put("status", rs.getString("booking_status"));
                jobj.put("price", rs.getString("price"));
                jobj.put("slot", rs.getString("token"));
                //================================================
                JSONObject latLog = getLatLog(rs.getString("pa_id"));
                jobj.put("parking_name", latLog.get("parking_name"));
                jobj.put("lat", latLog.get("lat"));
                jobj.put("longi", latLog.get("log"));

                arr.add(jobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public JSONObject getUserBalnceDetails(String username) {
        JSONObject jobj = new JSONObject();
        try {

            String query = "Select * from wallet_tbl where user_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("id", rs.getString("id"));
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("balance", rs.getString("balance"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public JSONObject getTodaysBookingDetailsbyUserId(String username, String slotType) {
        JSONObject jobj = new JSONObject();
        try {

            String query = "Select * from booking_tbl where user_id = ? AND "
                    + "booking_status ='pending' AND slot_type = ? "
                    + "order by booking_id desc LIMIT 1";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getUserId(username));
            ps.setString(2, slotType);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                jobj.put("id", rs.getString("booking_id"));
                jobj.put("p_id", rs.getString("pa_id"));
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("booking_time", rs.getString("booking_time"));
                jobj.put("time", rs.getString("for_time"));
                jobj.put("type", rs.getString("slot_type"));
                jobj.put("status", rs.getString("booking_status"));
                //================================================
                JSONObject latLog = getLatLog(rs.getString("pa_id"));
                jobj.put("parking_name", latLog.get("parking_name"));
                jobj.put("lat", latLog.get("lat"));
                jobj.put("longi", latLog.get("log"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public JSONArray getBookingDetailsbyAdminId(String username) {
        JSONArray arr = new JSONArray();
        try {

            String query = "Select * from booking_tbl where pa_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, getUserId(username));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                JSONObject jobj = new JSONObject();
                jobj.put("id", rs.getString("booking_id"));
                jobj.put("p_id", rs.getString("pa_id"));
                jobj.put("user_id", rs.getString("user_id"));
                jobj.put("booking_time", rs.getString("booking_time"));
                jobj.put("time", rs.getString("for_time"));
                jobj.put("type", rs.getString("slot_type"));
                jobj.put("status", rs.getString("booking_status"));
                //================================================
                JSONObject latLog = getLatLog(rs.getString("pa_id"));
                jobj.put("parking_name", latLog.get("parking_name"));
                jobj.put("lat", latLog.get("lat"));
                jobj.put("longi", latLog.get("log"));

                arr.add(jobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public JSONObject getLatLog(String areaId) {
        JSONObject jobj = new JSONObject();
        try {
            String query = "Select id,parking_name, lat,longitude from area_admin_register_tbl where id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, areaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("parking_name", rs.getString("parking_name"));
                jobj.put("p_id", rs.getString("id"));
                jobj.put("lat", rs.getString("lat"));
                jobj.put("log", rs.getString("longitude"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public JSONObject getSlotDetails(String username) {
        JSONObject jobj = new JSONObject();
        try {
            String query = "Select * from area_admin_register_tbl where username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("parking_name", rs.getString("parking_name"));
                jobj.put("p_id", rs.getString("id"));
                jobj.put("lat", rs.getString("lat"));
                jobj.put("log", rs.getString("longitude"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public JSONObject getSlotDetailsById(String id) {
        JSONObject jobj = new JSONObject();
        try {
            String query = "Select * from area_admin_register_tbl where id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jobj.put("parking_name", rs.getString("parking_name"));
                jobj.put("p_id", rs.getString("id"));
                jobj.put("lat", rs.getString("lat"));
                jobj.put("log", rs.getString("longitude"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobj;
    }

    public int checkUserBalance(String id, String amount) {
        int flag = 0;
        String sql = "select balance from wallet_tbl WHERE user_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            double amnt = Double.parseDouble(amount);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getDouble("balance") - amnt <= 0) {
                    flag = -1;
                }
            } else {
                flag = -2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateFCM(String userName, String password, String fcm) {
        boolean flag = false;
        String sql = "update login_tbl set fcm = ? WHERE uname = ? AND password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fcm);
            ps.setString(2, userName);
            ps.setString(3, password);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public String getFCM(String userName) {
        String flag = "NO";
        String sql = "Select fcm  from login_tbl  WHERE uname = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                flag = rs.getString("fcm");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean updateFeedBack(String id, String feedBack) {
        boolean flag = false;
        String sql = "update parking_tbl set feed_back = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, feedBack);
            ps.setString(2, id);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public String bookSlot(BookingBean bean) {
        String flag = "failed to book";
        try {
            String query = "SELECT * FROM parking_tbl WHERE p_id = ? AND cash_mode = ? AND slot_type =?";
            PreparedStatement ps1 = connection.prepareStatement(query);
            ps1.setString(1, bean.getPa_id());
            ps1.setString(2, "NA");
            ps1.setString(3, bean.getSlot_type());
            ResultSet rs = ps1.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            String query1 = "SELECT * FROM booking_tbl WHERE pa_id = ? AND booking_status = ? AND slot_type =?";
            PreparedStatement ps2 = connection.prepareStatement(query1);
            ps2.setString(1, bean.getPa_id());
            ps2.setString(2, "pending");
            ps2.setString(3, bean.getSlot_type());
            ResultSet rs1 = ps2.executeQuery();
            while (rs1.next()) {
                count++;
            }
            String query2 = "SELECT * FROM area_admin_register_tbl WHERE id = ? ";
            PreparedStatement ps3 = connection.prepareStatement(query2);
            ps3.setString(1, bean.getPa_id());
            ResultSet rs2 = ps3.executeQuery();
            int total = 0;
            if (rs2.next()) {
                if (bean.getSlot_type().equalsIgnoreCase("car")) {
                    total = Integer.parseInt(rs2.getString("number_for_four_wheeler"));
                } else if (bean.getSlot_type().equalsIgnoreCase("bike")) {
                    total = Integer.parseInt(rs2.getString("number_for_two_wheeler"));
                }
            }
            if (count < total) {
                String sql = "INSERT INTO booking_tbl(pa_id,user_id,"
                        + "booking_time,for_time,slot_type,token)VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, bean.getPa_id());
                ps.setString(2, bean.getUser_id());
                ps.setString(3, bean.getBooking_time());
                ps.setString(4, bean.getFor_time());
                ps.setString(5, bean.getSlot_type());
                ps.setString(6, (count + 1) + "");
                if (ps.executeUpdate() > 0) {
                    count = count + 1;
                    flag = count + "";
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean cancelBooking(String bookingId) {
        System.out.println(bookingId);
        boolean flag = false;
        String select = "select booking_id, pa_id, slot_type, for_time "
                + "from booking_tbl where booking_status='pending' AND booking_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(select);
            ps.setString(1, bookingId);
            ResultSet srs = ps.executeQuery();
            while (srs.next()) {
                JSONObject parkingSlotPrice
                        = getParkingSlotPrice(getAdminUserName(srs.getString("pa_id")), srs.getString("slot_type"));
                String price = "0";
                if (srs.getString("slot_type").equalsIgnoreCase("car")) {
                    price = (String) parkingSlotPrice.get("four_wheeler_for_1hr");
                } else {
                    price = (String) parkingSlotPrice.get("two_wheeler_for_1hr");
                }
                long diffDate = diffDate(srs.getString("for_time"), getDate(TM.DD_MM_YYYY_HH_MM_SS_A), TM.DD_MM_YYYY_HH_MM_SS_A);
                long after = (diffDate / TM.MINUTE);
                if (after <= -1) {

                } else {
                    price = String.valueOf(Double.parseDouble(price) / 2);
                }
                String sql = "update booking_tbl set booking_status = 'cancel', price=? WHERE booking_id = ?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, price);
                ps.setString(2, srs.getString("booking_id"));
                int executeUpdate = ps.executeUpdate();
                if (executeUpdate > 0) {
                    flag = true;
                    updateUserBalance(getUserName(getUserIdByBookID(srs.getString("booking_id"))), price);
                    updateAdminBalance(getAdminUserName(srs.getString("pa_id")), price);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean autoCancelBooking() {
        boolean flag = false;
        String select = "select booking_id, pa_id, slot_type, for_time from booking_tbl where booking_status='pending'";
        try {
            PreparedStatement ps = connection.prepareStatement(select);
            ResultSet srs = ps.executeQuery();
            while (srs.next()) {
                JSONObject parkingSlotPrice
                        = getParkingSlotPrice(getAdminUserName(srs.getString("pa_id")), srs.getString("slot_type"));
                String price = "0";
                if (srs.getString("slot_type").equalsIgnoreCase("car")) {
                    price = (String) parkingSlotPrice.get("four_wheeler_for_1hr");
                } else {
                    price = (String) parkingSlotPrice.get("two_wheeler_for_1hr");
                }
                long diffDate = diffDate(srs.getString("for_time"), getDate(TM.DD_MM_YYYY_HH_MM_SS_A), TM.DD_MM_YYYY_HH_MM_SS_A);
                long after = (diffDate / TM.HOUR);
                if (after <= -1) {
                    String sql = "update booking_tbl set booking_status = 'cancel' WHERE booking_id = ?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, srs.getString("booking_id"));
                    int executeUpdate = ps.executeUpdate();
                    if (executeUpdate > 0) {
                        flag = true;
                        updateUserBalance(getUserName(getUserIdByBookID(srs.getString("booking_id"))), price);
                        updateAdminBalance(getAdminUserName(srs.getString("pa_id")), price);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public static void main(String[] args) {
        new QueryManager().autoCancelBooking();
    }

    public boolean blockAreaAdmin(String paId) {
        boolean flag = false;
        String sql = "update login_tbl set status = 'P' WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, paId);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

    public boolean unBlockAreaAdmin(String paId) {
        boolean flag = false;
        String sql = "update login_tbl set status = 'A' WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, paId);
            int executeUpdate = ps.executeUpdate();
            if (executeUpdate > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }

}
