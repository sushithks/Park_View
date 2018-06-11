<%
    String sta = request.getParameter("status");
    if (sta != null && sta.equals("false")) {

%>

<script>
    alert("Invalid Username or Password");

</script>
<%}%>
<form name=save action="LoginServlet" method=post >

    <div class='form-group'>
        <label>Username</label>
        <input type='text' name='uname' req='text' minm='1' maxm='20' class='form-control'/>
    </div>
    <div class='form-group'>
        <label>Password</label>
        <input type='password' name='password' req='text' minm='1' maxm='8' class='form-control'/>
    </div>

    <input type='reset' class='btn' value='Clear'/>
    <input type='submit' class='btn' value='Login'/>
</form>
<script src='js/validation2.2.js'></script>
