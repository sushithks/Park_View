<%@page import="com.parkingapp.bean.BookingBean"%>
<%@page import="com.parkingapp.bean.UserBean"%>
<%@page import="com.parkingapp.bean.AreaAdminRegisterBean"%>
<%@page import="java.util.ArrayList"%>
<%@include file="admin_header.jsp" %>
<style>
    .banner-dott{
        height: 200px;
    }
</style>
<div class="container" style="min-height: 400px;">

    <table class="table">
        <thead>
            <tr>
                <th>PId</th>
                <th>Booking Time</th>
                <th>For Time</th>
                <th>Sloat Type</th>
                <th>Status</th>
<!--                <th>Balance</th>               -->
<!--                <th>Options</th>-->
            </tr>
        </thead>
        <tbody>
            <%
                Object object = request.getAttribute("temp");
                if (object != null && object instanceof ArrayList) {
                    ArrayList<BookingBean> list = (ArrayList<BookingBean>) object;
                    for (int i = 0; i < list.size(); i++) {
                        BookingBean bean = list.get(i);
            %>
            <tr>
<!--                <td><%= i + 1%></td>-->
                <td><%= bean.getPa_id()%></td>
                <td><%= bean.getBooking_time()%></td>
                <td><%= bean.getFor_time()%></td>                
                <td><%= bean.getSlot_type()%></td>
                <td><%= bean.getStatus_of_parking_area_admin()%></td>

<!--                <td><a href=""> Approve </a></td>-->
            </tr>

            <%              }
                }%>
        </tbody>
    </table>
</div>
<%@include file="admin_footer.jsp" %>