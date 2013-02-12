<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" href="theme/calendrier-mairie.css" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone précise
function setfocus(nom)
{
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeModele_Recherche.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeModele_Recherche" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Sélection d'un modèle<BR></SPAN>
			<FIELDSET><br>
				<TABLE border="0" class="sigp2">
							<tr>
								<td>Désignation du modèle</td>
								<td><INPUT type="text" size="20" name="<%=process.getNOM_EF_RECHERCHE() %>" value="<%=process.getVAL_EF_RECHERCHE() %>" class="sigp2-saisie" style="text-transform: uppercase"></td>
								<td><INPUT type="submit" value="Rechercher" name="<%=process.getNOM_PB_RECHERCHE() %>" class="sigp2-Bouton-100"></td>
							</tr>
						</TABLE>
			<br></FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Modèles</LEGEND><BR>
		
		<TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
				<TABLE class="sigp2">
					<TR>
						<TD class="sigp2-titre-liste">Libellé&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version&nbsp;Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>

					</TR>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="10" name="<%= process.getNOM_LB_MODELE() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_VALIDER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_MODELE(),process.getVAL_LB_MODELE_SELECT()) %>
				</SELECT>
				</TR>
			</TABLE>
				<br>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="OK" class="sigp2-Bouton-100"></TD>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</FIELDSET>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
