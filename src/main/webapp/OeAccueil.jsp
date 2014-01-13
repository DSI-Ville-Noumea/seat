<%@page contentType="text/html;charset=UTF-8"%>
<!-- Sample JSP file --> <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
<TITLE>Put Your Title Here</TITLE>
<SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
document.formu.elements[nom].focus();
}

</SCRIPT>

</HEAD>
<%
	if (!nc.mairie.seat.servlet.ServletSeat.controlerHabilitation(request)) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
		response.setHeader("WWW-Authenticate","BASIC realm=\"Habilitation HTTP pour la Mairie\"");
		javax.servlet.ServletContext sc= getServletContext();
		javax.servlet.RequestDispatcher rd = sc.getRequestDispatcher("/ConnectionInsulte.jsp");
		rd.forward(request,response);
	}
%>
<BODY bgcolor="#FFFFFF" background="images/fond.jpg" class="sigp2-BODY">

<jsp:useBean class="nc.mairie.seat.process.OeAccueil" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;">
  <TBODY align="center">

    <%@include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
      <FORM name="formu" method="POST"><BR>
      <BR>
      MENU
    
<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Module à tester</LEGEND><BR>


<INPUT type="submit" value="Marques"
				name="<%=process.getNOM_PB_MARQUES() %>"><BR>
			<INPUT type="submit"
				value="Type d'Equipement" name="<%=process.getNOM_PB_TYPEEQUIP() %>"><BR>
			<INPUT type="submit" value="Modèles"
				name="<%= process.getNOM_PB_MODELES() %>">
			<BR>
			<INPUT type="submit" value="Carburant"
				name="<%=process.getNOM_PB_CARBURANT() %>"><BR>
			<INPUT type="submit"
				value="Pneus" name="<%= process.getNOM_PB_PNEU()%>"><BR>
			<INPUT type="submit" value="Types de compteur"
				name="<%=process.getNOM_PB_COMPTEUR() %>">
			<BR>
			<INPUT type="submit" value="Equipement" name="<%=process.getNOM_PB_EQUIPEMENT() %>"><BR>
			<INPUT
				type="submit" name="<%= process.getNOM_PB_BPC() %>" value="BPC"><BR>
			<INPUT type="submit" name="<%= process.getNOM_PB_MODEPRISE() %>"
				value="Mode de prise de carburant"><BR>
			</FIELDSET>
        <INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"><BR>
      
		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Module validé</LEGEND><BR>

		</FIELDSET>
<BR>
	<INPUT type="submit" value="Test Marque" name="<%=process.getNOM_PB_GO() %>"><br><br><BR>
	 <INPUT type="submit" value="Entretien" name="<%=process.getNOM_PB_ENTRETIEN() %>"><BR>
			<INPUT	type="submit" value="Pièces" name="<%=process.getNOM_PB_PIECES() %>"><BR>
			<BR>
			<INPUT type="submit" value="Type" name="<%=process.getNOM_PB_TINTERVALLE() %>"><BR><BR></FORM>
      </TD>
    </TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>