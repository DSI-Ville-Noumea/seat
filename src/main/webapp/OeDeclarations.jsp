<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
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
document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeOT.jsp</TITLE>
</HEAD>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY"><jsp:useBean class="nc.mairie.seat.process.OeDeclarations" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND>
<%if (null==process.getVAL_LB_DECLARATIONS_SELECT()||process.getVAL_ST_TITRE_ACTION().equals("")){ %><INPUT
			type="submit" value="OK" name="<%=process.getNOM_PB_TRI() %>"
			style="visibility : hidden;"><TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left"><TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Date       Immat      Déclarant                             OT        Service  </TD>
						</TR>
					</TBODY>
				</TABLE>

</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="15" name="<%= process.getNOM_LB_DECLARATIONS() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_DECLARATIONS(),process.getVAL_LB_DECLARATIONS_SELECT()) %>
				</SELECT>
					</TD>
				</TR>
			</TABLE>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<!-- <TD width="151"></TD> -->
					<TD width="151"><INPUT type="submit"
					name="<%= process.getNOM_PB_AJOUTER() %>" value="Ajouter"
					class="sigp2-Bouton-100"></TD>
				<TD width="151"><%if (process.isVide){ %><INPUT type="submit"
					name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier"
					class="sigp2-Bouton-100"></TD>
				<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
				<%} %>
				</TR>
			</TABLE>
			<br>
	</FIELDSET>
<%}else{%><BR>
	<FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center" class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3" align="center">
								<TABLE border="1" class="sigp2">
									<tr>
										<td>Date</td>
										<td>Déclarant </td>
										<td>Anomalies sigalées</td>
					</tr>
									<tr>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DECLARANT() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_ANOMALIES() %></td>
					</tr>
								</TABLE>
				
							<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
				</table>
				<br>
	</FIELDSET>
	<%} %>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
