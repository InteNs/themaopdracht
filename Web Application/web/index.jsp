<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>A.T.D. Terminal</title>

  <!-- Bootstrap -->
  <link href="bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
      <![endif]-->
    </head>
    <body>
     <div class="container" style="margin-top:50px;">
       <div class="jumbotron">
        <h1 style="text-align:center; color:#0099CC; margin-bottom:20px;"></h1>

        <div class="row">
          <div class="col-md-4"></div>
          <div class="col-md-4" style="background-color:#FFF; border: 1px solid #0099CC; border-opacity:0.2; border-radius: 8px; padding:15px 15px 15px 15px;">
           <!-- Inloggen -->
           <p>Aanmelden:</p>

           <form>
            <div class="form-group">
              <label for="exampleInputEmail1">Email</label>
              <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Vul email in">
            </div>
            <div class="form-group">
              <label for="exampleInputPassword1">Wachtwoord</label>
              <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Vul wachtwoord in">
            </div>
            <button type="submit" class="btn pull-right" style="width:120px; background-color:#0099CC; color:#FFFFFF;">Aanmelden</button>
            
              <label>
                <input type="checkbox"> <span style="font-weight: 200;">Blijf aangemeld</span>
              </label>
            
            </form>
          
            <span>&nbsp;</span>
          <!-- Registreren -->
          <p>Registreren:</p>

           <form>
            <div class="form-group">
              <label for="exampleInputEmail1">Email</label>
              <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Vul email in">
            </div>
            <button type="button" class="btn pull-right"  data-toggle="modal" data-target="#myModal" style="width:120px; background-color:#0099CC; color:#FFFFFF;">Registreren</button>
            </form>
          
        </div>
      </div></div>

      <div class="col-md-4"></div>
    </div></div>

    <!-- Model Registration part 1 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content" style="border: 1px solid #0099CC; border-radius: 8px;">
          <div class="modal-header" style="background-color: #E2E2E2; border-radius: 8px 8px 0px 0px;">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Registratie deel 1</h4>
          </div>
          <div class="modal-body">

            <form style="padding: 10px 10px 10px 10px;">

              <div class="form-group">
                <label for="exampleInputPassword1">Emailadres</label>
                <input type="email" class="form-control" id="exampleInputPassword1" placeholder="Vul email in">
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">Herhaal emailadres</label>
                <input type="email" name="emailreg" class="form-control" id="exampleInputPassword1" placeholder="Herhaal email">
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">Wachtwoord</label>
                <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Vul wachtwoord in">
              </div>
              <div class="form-group">
                <label for="exampleInputPassword1">Herhaal wachtwoord</label>
                <input type="password" name="passwordreg" class="form-control" id="exampleInputPassword1" placeholder="Herhaal wachtwoord">
              </div>

            </form>
          </div>
          <div class="modal-footer" style="background-color: #E2E2E2; border-radius: 0px 0px 8px 8px;">
            <button type="button" class="btn btn-default" data-dismiss="modal">Annuleren</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#myModal2" >Stap 2</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- Modal Registration part 2 -->
    <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content" style="border: 1px solid #0099CC; border-radius: 8px;">
          <div class="modal-header" style="background-color: #E2E2E2; border-radius: 8px 8px 0px 0px;">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Registratie deel 2</h4>
          </div>
          <div class="modal-body">

            <form style="padding: 10px 10px 10px 10px;">

              <div class="form-group">
                <label for="exampleInputPassword1">Gebruikersnaam</label>
                <input type="email" name="username" class="form-control" id="exampleInputPassword1" placeholder="Vul gebruikersnaam in">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Voornaam</label>
                <input type="text" name="name" class="form-control" id="exampleInputEmail1" placeholder="Vul voornaam in">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Achternaam</label>
                <input type="text" name="lastname" class="form-control" id="exampleInputEmail1" placeholder="Vul achternaam in">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Adres</label>
                <input type="text" name="address" class="form-control" id="exampleInputEmail1" placeholder="Vul adres in">
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Land</label>
                <input type="text" name="country" class="form-control" id="exampleInputEmail1" placeholder="Vul land in">
              </div>

            </form>
          </div>
          <div class="modal-footer" style="background-color: #E2E2E2; border-radius: 0px 0px 8px 8px;">
            <button type="button" class="btn btn-default" data-dismiss="modal" data-toggle="modal" data-target="#myModal">Vorige</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#myModal3">Registreren</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- Modal Registration part 2 -->
    <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content" style="border: 1px solid #0099CC; border-radius: 8px;">
          <div class="modal-header" style="background-color: #E2E2E2; border-radius: 8px 8px 0px 0px;">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">Registratie voltooid</h4>
          </div>
          <div class="modal-body">
            <p>Bedankt voor uw registratie.</p>
          </div>
          <div class="modal-footer" style="background-color: #E2E2E2; border-radius: 0px 0px 8px 8px;">
            <button type="button" class="btn btn-primary" data-dismiss="modal">Terug naar aanmelden</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
  </body>
  </html>