<%@page import="com.parkingapp.bean.AreaAdminRegisterBean"%>
<%@page import="java.util.ArrayList"%>
<%@include file="admin_header.jsp" %>
<style>
    .banner-dott{
        height: 200px;
    }
</style>
<div class="container" style="min-height: 400px;">
    <%
        String status = request.getParameter("status");
        if (status != null && !status.equals("") && status.equals("false")) {
    %>
    <label> Failed...</label>
    <%
    } else if (status != null && !status.equals("") && status.equals("true")) {
    %>
    <label> Successful...</label>
    <% }
    %>
    <table class="table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Parking name</th>
                <th>Username</th>
                <th>Email id</th>
                <th>Phone number</th>
                <th>Number for two wheeler</th>
                <th>Two wheeler for 1hr</th>
                <th>Two wheeler after 1hr</th>
                <th>Number for four wheeler</th>
                <th>Four wheeler for 1hr</th>
                <th>Four wheeler after 1hr</th>
                <th>Options</th>
            </tr>
        </thead>
        <tbody>
            <%
                Object object = request.getAttribute("temp");
                if (object != null && object instanceof ArrayList) {
                    ArrayList<AreaAdminRegisterBean> list = (ArrayList<AreaAdminRegisterBean>) object;
                    for (int i = 0; i < list.size(); i++) {
                        AreaAdminRegisterBean bean = list.get(i);
            %>
            <tr>
                <td><%= i + 1%></td>
                <td><%= bean.getParking_name()%></td>
                <td><%= bean.getUsername()%></td>
                <td><%= bean.getEmail_id()%></td>
                <td><%= bean.getPhone_number()%></td>
                <td><%= bean.getNumber_for_two_wheeler()%></td>
                <td><%= bean.getTwo_wheeler_for_1hr()%></td>
                <td><%= bean.getTwo_wheeler_after_1hr()%></td>
                <td><%= bean.getNumber_for_four_wheeler()%></td>
                <td><%= bean.getFour_wheeler_for_1hr()%></td>
                <td><%= bean.getFour_wheeler_after_1hr()%></td>
                <td><a href="AdminApproveAreaRequest?user=<%= bean.getUsername()%>"> Approve / </a>
                <a href="AdminRejectAreaRequest?user=<%= bean.getUsername()%>"> Reject </a>
                </td>
            </tr>

            <%              }
                }%>
        </tbody>
    </table>
</div>
<%@include file="admin_footer.jsp" %>