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
		 <button type="submit" >Nieuw</button>
		 <button type="submit" >Aanpassen</button>
		 <button type="submit" >Verwijderen</button>
		 <p><h4>Klantengegevens:</h4></p>
				<fieldset id="klantenportaal">
					<div><label>Naam:</label>
					<input type="text" name="naam" id = "naam" size="30"/></div>
					<div><label>Adres:</label>
					<input type="text" name="adres" id = "adres" size="30"/></div>
					<div><label>Postcode:</label>
					<input type="text" name="postcode" id = "postcode" size="30" /></div>
					<div class = "rechts"><label>Plaats:</label>
					<input type="text" name="plaats" id = "plaats" size="30" /></div>
					
					
					<div><label for="date">Geboortedatum:</label>
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
					<br>
					
					
					<div><label>Email:</label>
					<input type="text" name="email" id = "email" size="20"/></div>
					<div><label>Telefoonnummer:</label>
					<input type="text" name="telefoonnummer" id = "telefoonnummer" size="10"/></div>
					<div><label>Klantnummer:</label>
					<input type="text" name="klantnummer" id = "klantnummer" size="10"/></div>
					<div><label>Blacklist</label>
					<input type="radio" name="size" value="J" />Ja<input type="radio" name="size" value="N" /> Nee</div>
				</fieldset>
	
				<button type="submit" >Opslaan</button>
			    <button type="submit" >Annuleren</button><br>
	</div>			
	</body>

<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>

</html>		