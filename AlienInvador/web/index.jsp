<%-- 
    Document   : index
    Created on : Mar 20, 2016, 6:48:44 PM
    Author     : Tanika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alien Invader Login</title>
    </head>

    <body>
        <h1>Alien Invader Login</h1>
        <br />
        <br />
        <form id ="frmLogin" action ="loginValidator" method ="post">
            <lable for="txtUserName">User name: </lable>&nbsp;<input type ="text" id="txtUserName" name ="txtUserName" value =""</input>
            <br />
            <br />
            <lable for="txtPassword">Password: </lable>&nbsp;<input type ="password" id="txtPassword" name ="txtPassword" value =""</input>
            <br />
            <br />
            <input type ="submit" id="btnSubmit" name ="btnSubmit" value ="Login"</input>
            <br />
            <br />
            <a href="register.jsp">Register</a
        </form>
    </body>
</html>
