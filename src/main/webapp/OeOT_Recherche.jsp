<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

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
<TITLE>OeOT_Recherche.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeOT_Recherche" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Sélection d'un OT<BR></SPAN>
			<FIELDSET><br>
				<TABLE border="0" class="sigp2">
							<tr>
								<td>N°inventaire ou N°immatriculation </td>
								<td><INPUT type="text" size="20" name="<%=process.getNOM_EF_DESIGNATION() %>" value="<%=process.getVAL_EF_DESIGNATION() %>" class="sigp2-saisie"></td>
								<td><INPUT type="submit" value="Rechercher" name="<%=process.getNOM_PB_OK() %>" class="sigp2-Bouton-100"></td>
							</tr>
						</TABLE>
			<INPUT type="submit" value="OK" name="<%=process.getNOM_PB_OK_EQUIPEMENT() %>" style="visibility : hidden;"><br></FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Equipements</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
						<TABLE class="sigp2" border="1">
							<tr>
								<td height="29" class="sigp2-titre-liste">Inventaire Immatriculation Marque          Modèle          Type  En service   </TD>
								<!-- <TD height="29" width="87">Service d'affectation</TD>-->
					</tr>
						</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="5" name="<%= process.getNOM_LB_EQUIPEMENT() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%"
					onchange='executeBouton("<%= process.getNOM_PB_OK_EQUIPEMENT()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_EQUIPEMENT(),process.getVAL_LB_EQUIPEMENT_SELECT()) %>
				</SELECT>
				</TR>
			</TABLE>
				
			</FIELDSET>
<FIELDSET><LEGEND class="sigp2Fieldset" align="left">OT</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
				<TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°OT       </TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="5" name="<%= process.getNOM_LB_OT() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_VALIDER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_OT(),process.getVAL_LB_OT_SELECT()) %>
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
