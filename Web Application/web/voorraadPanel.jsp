<%--JSP imports--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--HTML--%>
<!DOCTYPE html>
<html>
<head>
  <%--CSS imports--%>
  <%--<link rel="stylesheet" href="/voorraadPanel.css"/>--%>
</head>
<body>
<%--Main container--%>
<!-- Voorraad form -->
<form action="voorraad" method="post">
  <%--Item containers--%>
  <div>
    <h2>Product</h2>
  </div>

  <%--Naam--%>
  <div>
    <input type="text" name="naam" id="naam" placeholder="Vul naam van het product in"/>
  </div>
  <%--Aantal--%>
  <div>
    <input type="text" name="aantal" id="aantal" placeholder="Vul aantal in"/>
  </div>
  <%--minAantal--%>
  <div>
    <input type="text" name="minAantal" id="minAantal" placeholder="Vul minimale aantal in"/>
  </div>
  <%--Prijs--%>
    <div>
      <input type="text" name="prijs" id="prijs" placeholder="Vul prijs in"/>
    </div>
    <%--Inkoopprijs--%>
    <div>
      <input type="text" name="inkoopPrijs" id="inkoopPrijs" placeholder="Vul inkoopprijs in"/>
    </div>
    <%--leverancier--%>
    <div>
      <input type="text" name="leverancier" id="leverancier" placeholder="Vul leverancier in"/>
    </div>

    <%--Adres--%>
    <div>
      <input type="text" name="adres" id="adres" placeholder="Vul adres in"/>
    </div>
    <%--Postcode--%>
    <div>
      <input type="text" name="postcode" id="postcode" placeholder="Vul postcode in"/>
    </div>
    <%--Plaats--%>
    <div>
      <input type="text" name="plaats" id="plaats" placeholder="Vul plaats in"/>
    </div>
  <%--Buttons--%>
  <div>
    <input type="submit" value="Nieuw"/>
  </div>
  <div>
    <input type="button" value="aanpassen" onclick="location.href='index.jsp'"/>
  </div>
    <div>
      <input type="button" value="verwijderen" onclick="location.href='index.jsp'"/>
    </div>
  <%-- ^^ Place new form-items here ^^ --%>
</form>
</body>
</html>