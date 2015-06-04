<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>A.T.D. Schermontwerp</title>
<link href="bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="custom.css" rel="stylesheet">
</head>
<body>
<div style=" width: 800px; background-color: lightblue; padding-top: 0px;">
<div class="form1">
		 <p><h4>Financi&euml;n:</h4></p>
				<fieldset id="klantenportaal">
					<div><label>Omzet:</label>
					<input type="text" name="omzet" id = "omzet" size="30"/></div>
					<div><label>BTW:</label>
					<input type="text" name="btw" id = "btw" size="30"/></div>
					<div><label>Aantal facturen:</label>
					<input type="text" name="facturen" id = "facturen" size="30" /></div>
					<br>
					<div><label>Facturen op totaalprijs:</label>
					<input type="text" name="totaalprijs" id = "totaalprijs" size="30" /></div>
					<!-- hier moet een grafiek staan -->
					
					<br>
					
					<div><label for="date">Kies van datum:</label>
					<input type="date" name="date" id="date" value=""  />	
					<link rel="stylesheet" href="jquery.ui.datepicker.mobile.css" /> 
					<script src="jQuery.ui.datepicker.js"></script>
					<script src="jquery.ui.datepicker.mobile.js"></script>
					<script>
					//reset type=date inputs to text
					$( document ).bind( "mobileinit", function(){
					$.mobile.page.prototype.options.degradeInputs.date = true;
					});	
					</script></div>
					
					
					<div><label for="date">Kies tot datum:</label>
					<input type="date" name="date" id="date" value=""  />	
					<link rel="stylesheet" href="jquery.ui.datepicker.mobile.css" /> 
					<script src="jQuery.ui.datepicker.js"></script>
					<script src="jquery.ui.datepicker.mobile.js"></script>
					<script>
					//reset type=date inputs to text
					$( document ).bind( "mobileinit", function(){
					$.mobile.page.prototype.options.degradeInputs.date = true;
					});	
					</script></div>
				</fieldset>
				</div>
				</body>
<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>

</html>		
