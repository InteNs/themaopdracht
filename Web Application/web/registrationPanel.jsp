<%--JSP imports--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--HTML--%>
<!DOCTYPE html>
<html>
<head>
    <%--CSS imports--%>
    <%--<link rel="stylesheet" href="components/registration/registrationPanel.css"/>--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="jQuery.ui.datepicker.js"></script>
    <script src="jquery.ui.datepicker.mobile.js"></script>
    <script>
        //reset type=date inputs to text
        $( document ).bind( "mobileinit", function(){
            $.mobile.page.prototype.options.degradeInputs.date = true;
        });
    </script>
</head>
<body>
<%--Main container--%>
<!-- Registration form -->
<form action="register" method="post">
    <%--Item containers--%>
    <div>
        <h2>Registreren</h2>
    </div>

    <%--Email--%>
    <div>
        <input type="email" name="email" id="email" placeholder="Vul emailadres in"/>
    </div>
    <div>
        <input type="text" name="emailrepeat" id="emailrepeat" placeholder="Herhaal email"/>
    </div>
    <%--Password--%>
    <div>
        <input type="password" name="password" id="password" placeholder="Vul wachtwoord in"/>
    </div>
    <div >
        <input type="password" name="passwordrepeat" id="passwordrepeat" placeholder="Herhaal wachtwoord"/>
    </div>
    <%--Realname--%>
    <div>
        <input type="text" name="realname" id="realname" placeholder="Vul naam in"/>
    </div>
    <%--DateofBirth--%>
    <div><label for="date">Geboortedatum:</label>
        <input type="date" name="date" id="date" value="" />
        <link rel="stylesheet" href="jquery.ui.datepicker.mobile.css" />

    </div>
    <%--Address--%>
    <div>
        <input type="text" name="address" id="address" placeholder="Vul adres in"/>
    </div>
    <%--postal--%>
    <div>
        <input type="text" name="postal" id="postal" placeholder="Vul postcode in"/>
    </div>
    <%--phoneNumber--%>
    <div>
        <input type="text" name="phonenumber" id="phonenumber" placeholder="Vul telefoonnummer in"/>
    </div>
    <%--Buttons--%>
    <div>
        <input type="submit" value="Registreren"/>
    </div>
    <div>
        <input type="button" value="Loginscherm" onclick="location.href='index.jsp'"/>
    </div>
    <%-- ^^ Place new form-items here ^^ --%>
</form>
</body>
</html>