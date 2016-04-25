<%-- 
    Document   : registration
    Created on : Mar 20, 2016, 9:17:15 PM
    Author     : Tanika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alien Invasion Registration</title>
    </head>

    <body>
        <h1>Alien Invader Registration</h1>
        <br />
        <br />
        <form id ="frmRegistration" action ="registerValidator" method ="post">
            <lable for="txtFirstName">First name: </lable>&nbsp;<input type ="text" id="txtFirstName" name ="txtFirstName" value =""</input>
            <br />
            <br />
            <lable for="txtLastName">Last Name: </lable>&nbsp;<input type ="text" id="txtLastName" name ="txtLastName" value =""</input>
            <br />
            <br />
            <lable for="txtEmail">Email: </lable>&nbsp;<input type ="text" id="txtEmail" name ="txtEmail" value =""</input>
            <br />
            <br />
            <lable for="txtPassword">Password: </lable>&nbsp;<input type ="password" id="txtPassword" name ="txtPassword" value =""</input>
            <br />
            <br />
            <lable for="txtConfirmPassword">Confirm Password: </lable>&nbsp;<input type ="password" id="txtConfirmPassword" name ="txtConfirmPassword" value =""</input>
            <br />
            <br />
            <input type ="submit" id="btnSubmit" name ="btnSubmit" value ="Register"</input>
        </form>
    </body>
</html>
