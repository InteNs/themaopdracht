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
		 <p><h4>Voorraadgegevens:</h4></p>
				<fieldset id="voorraadgegevens">
					<div><label>Naam:</label>
					<input type="text" name="naam" id = "naam" size="30"/></div>
					<div><label>Aantal:</label>
					<input type="text" name="aantal" id = "aantal" size="30"/></div>
					<div><label>Min. Aantal:</label>
					<input type="text" name="minAantal" id = "minAantal" size="30" /></div>
					<div><label>Prijs:</label>
					<input type="text" name="prijs" id = "prijs" size="30" /></div>
					<div><label>Inkoopprijs:</label>
					<input type="text" name="inkoopprijs" id = "inkoopprijs" size="30" /></div>
					<div><label>Leverancier:</label>
					<select name="filter">
						<option value="Geen" selected="selected">Geen</option>
						<option value="CheapoBV">Cheapo BV</option>
						<option value="BandenBV">Banden BV</option>
						<option value="Peugot">Peugot</option>
						<option value="Mercedes">Mercedes</option>
						<option value="Shell">Shell</option>
					</select></div>
					<div><label>Adres:</label>
					<input type="text" name="adres" id = "adres" size="20"/></div>
					<div><label>Postcode:</label>
					<input type="text" name="postcode" id = "postcode" size="10"/></div>
					<div><label>Plaats:</label>
					<input type="text" name="plaats" id = "plaats" size="10"/></div>
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
	