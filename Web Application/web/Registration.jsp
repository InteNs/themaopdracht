<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>A.T.D. Terminal</title>

    <!-- Bootstrap -->
    <link href="bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="registration.css" rel="stylesheet" type="text/css"/>
    <link href="base.css" rel="stylesheet" type="text/css"/>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<div class="container" style="margin-top: 50px;">
    <div class="jumotron"
         style="background-color: rgba(238,238,238,0.8); width: 500px; padding: 15px; margin: auto auto;">
        <!-- Inloggen -->
        <h4>Registreren:</h4>

        <form action="RegisterServlet.do" method="post">
                    <div class="form-group">
                        <label for="emailreg">Emailadres</label> <input type="text"
                                                                        name="emailreg"
                                                                        onblur='validateElement("email");'
                                                                        class="form-control" id="emailreg"
                                                                        placeholder="Vul email in">
                        <span id="emailRegErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="emailrep">Herhaal emailadres</label> <input
                            type="text" name="emailrep"
                            class="form-control" onblur='validateElement("email");'
                            id="emailrep" placeholder="Herhaal email">
                        <span id="emailRepErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="passwordreg">Wachtwoord</label> <input
                            type="password" name="passwordreg" onblur='compareInput("passwordreg", "passwordrep");'
                            class="form-control" id="passwordreg"
                            placeholder="Vul wachtwoord in">
                        <span id="passwordRegErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="passwordrep">Herhaal wachtwoord</label> <input
                            type="password" name="passwordrep" onblur='compareInput("passwordrep", "passwordreg");'
                            class="form-control" id="passwordrep"
                            placeholder="Herhaal wachtwoord">
                        <span id="passwordRepErr"></span>
                    </div>



                    <div class="form-group">
                        <label for="surname">Voornaam</label> <input type="text"
                                                                     name="firstname" class="form-control" id="surname"
                                                                     placeholder="Vul voornaam in">
                        <span id="surnameErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="lastname">Achternaam</label> <input type="text"
                                                                        name="lastname" class="form-control"
                                                                        id="lastname"
                                                                        placeholder="Vul achternaam in">
                        <span id="lastmnameErr"></span>
                    </div>
            <div class="form-group">
                <label for="birth">Land</label> <input type="text"
                                                       name="birth" class="form-control" id="birth"
                                                       placeholder="Vul geboortedatum in">
                <span id="birthErr"></span>
            </div>
                    <div class="form-group">
                        <label for="address">Adres</label> <input type="text"
                                                                  name="address" class="form-control" id="address"
                                                                  placeholder="Vul adres in">
                        <span id="addressErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="postal">Adres</label> <input type="text"
                                                                  name="postal" class="form-control" id="postal"
                                                                  placeholder="Vul postcode in">
                        <span id="postalErr"></span>
                    </div>
                    <div class="form-group">
                        <label for="phone">Adres</label> <input type="text"
                                                                  name="phone" class="form-control" id="phone"
                                                                  placeholder="Vul telefoonnummer in">
                        <span id="phoneErr"></span>
                    </div>
                    <div class="btn-toolbar" role="toolbar" aria-label="label">
                        <button type="button" onclick="location.href='index.jsp'" class="btn">Terug</button>
                        <button type="submit" class="btn">Registreren</button>
                    </div>
        </form>
        <%
            Object msg = request.getAttribute("succesReg");
            if (msg != null) {
                out.println(msg);
            }
        %>
        <span>&nbsp;</span>

    </div>
</div>

<div class="col-md-4"></div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<script src="registration.js"></script>
</body>
</html>