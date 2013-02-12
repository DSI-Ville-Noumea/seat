<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
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
<TITLE>OePieces.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePieces" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" align="center" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;" width="681"><FORM name="formu" method="POST"><FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD height="26" width="200">
					<TABLE border="0" class="sigp2">
						<TR align="center">
							<TD>
						<TABLE class="sigp2" border="1" cellspacing="1">
							<TR>
								<TD width="200" class="sigp2-titre-liste">Libellé&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prix&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>

							</TR>
						</TABLE></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="200">
						<SELECT size="10" name="<%= process.getNOM_LB_PIECES() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_PIECES(),process.getVAL_LB_PIECES_SELECT()) %>
				</SELECT><INPUT type="submit" name="<%= process.getNOM_PB_OK()%>" value="OK" style="visibility : hidden;"></TD>
				</TR>
			</TABLE>
<BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_AJOUTER()%>" value="Ajouter" class="sigp2-Bouton-100"></TD>
<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE><br>
</FIELDSET><br>
<%if (! "".equals(process.getVAL_ST_TITRE_ACTION()) ) {%>
<FIELDSET>
<BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<tr>
					<td class="sigp2-titre" align="center"><%= process.getVAL_ST_TITRE_ACTION() %></td>
				</tr>
				<tr>
					<td height="10"></td>
				</tr>
				<TR>
					<TD height="26">
					<TABLE border="0" class="sigp2">
						<TR align="center">
							<TD>Type de pièces:</TD>
							<TD class="sigp2-saisie" style="text-transform: uppercase">
								<%if ("".equals(process.getVAL_ST_DESIGNATION())){ %>
								<INPUT type="text" name="<%= process.getNOM_EF_PIECES() %>"
								class="sigp2-saisie" value="<%= process.getVAL_EF_PIECES()%>" maxlength="30" size="30" style="text-transform: uppercase">
								<%}else{ %>
									<%=process.getVAL_ST_DESIGNATION() %>
								<%} %>
							</TD>
						</TR>
					<TR align="center">
						<TD>Prix unitaire</TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><%if ("".equals(process.getVAL_ST_DESIGNATION())){ %>
								<INPUT type="text"
							name="<%= process.getNOM_EF_PU() %>"
							class="sigp2-saisie"
							value="<%= process.getVAL_EF_PU()%>" maxlength="30"
							size="30" style="text-transform: uppercase">
								<%}else{ %>
									<%=process.getVAL_ST_PU() %>
								<%} %></TD>
					</TR>
				</TABLE>
					</TD>
				</TR>
			</TABLE>
			<BR>
			<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2">
				<TR align="center">
					<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
					<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
				</TR>
			</TABLE><br></FIELDSET>
<%} else {%><BR>
<%if(process.isDebranche){ %>
<FIELDSET>
	<INPUT type="submit" name="<%= process.getNOM_PB_RETOUR() %>" value="Retour" class="sigp2-Bouton-100">
</FIELDSET>
<%} %>
		<!-- 
<FIELDSET class="sigp2Fieldset"><INPUT type="submit" class="sigp2-Bouton-100" value="Annuler" name=""></FIELDSET>
 	-->
<%}%>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
