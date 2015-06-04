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
		 <p><h4>Onderhoudsgegevens:</h4></p>
				<fieldset id="onderhoudsgegevens">
					<div><label for="date">Datum van:</label>
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
					<div><label>Monteur:</label>
					<select name="filter">
						<option value="Geen" selected="selected">Geen</option>
						<option value="jaap">Jaap a €15,00</option>
						<option value="hans">Hans a €12,50</option>
						<option value="sjaak">Sjaak a €10,00</option>
						<option value="piet">Piet a €15,00</option>
					</select></div>
					<div><label>Aantal onderdelen:</label>
					<input type="text" name="aantalOnderdelen" id = "aantalOnderdelen" size="30"/></div>
					<div><label>Kenteken:</label>
					<input type="text" name="kenteken" id = "kenteken" size="30"/></div>					
				</fieldset>
				<button type="submit" >Opslaan</button>
			    <button type="submit" >Annuleren</button><br></div>
				



</body>

<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>

</html>		