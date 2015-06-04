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
		 <button type="submit" >Betalen</button>
		 <p><h4>Facturengegevens:</h4></p>
				<fieldset id="facturengegevens">
					
					<div><label for="date">Datum:</label>
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
					
					<div><label>Prijs:</label>
					<input type="text" name="prijs" id = "prijs" size="30"/></div>
					<div><label>Klant:</label>
					<select name="filter">
						<option value="Geen" selected="selected">Geen</option>
						<option value="klant1">Taoufik Abou</option>
						<option value="klant2">Oussama Aalili</option>
						<option value="klant3">Jorrit Meulenbeld</option>
						<option value="klant4">Mark Havekes</option>
					</select></div>
					<div><label>Is betaalt:</label>
					<input type="checkbox" name="size" value="betaalt" /></div>
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
				
