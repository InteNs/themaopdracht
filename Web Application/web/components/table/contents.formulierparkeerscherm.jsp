<!DOCTYPE html>
<html lang="en">
<head>
<link rel="import" href="contents.html">
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
		 <button href="www.google.com">Nieuw</button>
		 <button type="submit" >Aanpassen</button>
		 <button type="submit" >Verwijderen</button>
		 <p><h4>Parkeergegevens:</h4></p>
				<fieldset id="parkeergegevens">
					<!-- Datum werkt nu om te selecteren -->
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
					
					<div><label for="date">Datum tot:</label>
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
					
					
					
					<div><label>Kenteken:</label>
					<input type="text" name="kenteken" id = "kenteken" size="30" /></div>
				<div><label>Parkeerplaats:</label>
					<select name="filter">
						<option value="1" selected="selected">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
						<option value="18">18</option>
						<option value="19">19</option>
						<option value="20">20</option>
					</select></div>
					
	
				</fieldset>
				<button type="submit" >Opslaan</button>
			    <button type="submit" >Annuleren</button><br></div>





</body>

<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>

</html>	