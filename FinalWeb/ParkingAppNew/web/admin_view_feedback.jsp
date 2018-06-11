<%@page import="com.parkingapp.bean.FeedbackBean"%>
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
                <th>PId</th>
                <th>User Id</th>
                <th>Feedback</th>
                <th>Options</th>
            </tr>
        </thead>
        <tbody>
            <%
                Object object = request.getAttribute("temp");
                if (object != null && object instanceof ArrayList) {
                    ArrayList<FeedbackBean> list = (ArrayList<FeedbackBean>) object;
                    for (int i = 0; i < list.size(); i++) {
                        FeedbackBean bean = list.get(i);
            %>
            <tr>
<!--                <td><%= i + 1%></td>-->
                <td><%= bean.getPaid()%></td>
                <td><%= bean.getUserid()%></td>
                <td><%= bean.getFeedback()%></td>                
                <td><a href="AdminBlockAreaAdminServlet?id=<%= bean.getPaid()%>"> Block /</a><a href="AdminUnBlockAreaAdminServlet?id=<%= bean.getPaid()%>"> UnBlock </a></td>
            </tr>

            <%              }
                }%>
        </tbody>
    </table>
</div>
<%@include file="admin_footer.jsp" %>