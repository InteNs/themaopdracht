<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" type="text/css" href="base.css"/>
    <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>A.T.D. Terminal</title>

<!-- Bootstrap -->
<link href="bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="registration.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
</head>
<body>
	<div class="container" style="margin-top: 50px;">
				<div class="jumotron"
					style="background-color: rgba(238,238,238,0.8); width: 300px; padding: 15px; margin: 20vh auto auto;">
					<!-- Inloggen -->
					<h4 class="modal-title">Aanmelden:</h4>

					<form action="LoginServlet.do" method="post">
						<div class="form-group">
							<label for="exampleInputEmail1">Email</label> <input type="text"
								name="email" class="form-control" id="exampleInputEmail1"
								placeholder="Vul email in">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Wachtwoord</label> <input
								type="password" name='password' class="form-control"
								id="exampleInputPassword1" placeholder="Vul wachtwoord in">
						</div>
						<div>
							<label> <input type="checkbox"> <span
								style="font-weight: 200;">Blijf aangemeld</span>
							</label>
						</div>
						<div class="btn-toolbar" role="toolbar" aria-label="label">
							<button type="submit" class="btn">Aanmelden</button>
                            <button type="button" onclick="location.href='Registration.jsp'" class="btn">Registreren</button>
						</div>
					</form>
					<%
				Object succes = request.getAttribute("succes");
				if (succes != null) {
					out.println(succes);
				}			 
			%>
					<span>&nbsp;</span>
				</div>
		</div>
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</body>
</html>