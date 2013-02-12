<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>SEAT V2 - Suivi des Equipements de l'ATelier Municipal</TITLE>
<SCRIPT language="javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
</HEAD>
<%
	if (!nc.mairie.seat.servlet.ServletSeat.controlerHabilitation(request)) {
		response.setStatus(response.SC_UNAUTHORIZED); 
		response.setHeader("WWW-Authenticate","BASIC realm=\"Habilitation HTTP pour la Mairie\"");
		javax.servlet.ServletContext sc= getServletContext();
		javax.servlet.RequestDispatcher rd = sc.getRequestDispatcher("/ConnectionInsulte.jsp");
		rd.forward(request,response);
	}
%>

<frameset rows="*" cols="170,*" frameborder="0" border="0" framespacing="0">
<!--     <FRAME src="file:///C:/Documents and Settings/boulu72/Mes documents/Studio 3.5 Projects/TestLuc/PrincipalIndex.jsp" name="Index" frameborder="no" scrolling="NO" noresize marginwidth="0" marginheight="0"> -->
  <FRAMESET rows="91,*">
    <FRAME src="SeatLogo.jsp" name="Logo" scrolling="NO" noresize marginwidth="0" marginheight="0">
    <FRAME src="SeatIndex.jsp" name="Index" frameborder="no" noresize marginwidth="0" marginheight="0">
  </FRAMESET>
  <frameset rows="38,*" cols="*" framespacing="0" frameborder="NO" border="0" >
    <FRAME src="SeatTitre.jsp" name="Titre" scrolling="NO" noresize marginwidth="0" marginheight="2">
    <FRAME src="SeatMain.jsp" name="Main" marginwidth="0" marginheight="0">
  </frameset>
  <NOFRAMES>
  <BODY>
  <P>L'affichage de cette page requiert un navigateur prenant en charge les
  cadres.</P>
  </BODY>
  </NOFRAMES>
</FRAMESET>
</HTML>