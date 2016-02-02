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
<TITLE>OeBPC_VisualisationEquip.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeBPC_VisualisationComplete" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET style="borderColor='blue'"><LEGEND class="sigp2Fieldset" align="left">Critères de recherche</LEGEND>
		<%if(process.isImprimable()) {%>
		<TABLE border="0" align="right">
			<TR>
				<TD><INPUT type="image" name="<%= process.getNOM_PB_IMPRIMER() %>"
					src="images/print.gif"></TD>
			</TR>
		</TABLE>
		<%} %>
		<TABLE border="0" class="sigp2">
						<TR>
							<TD class="sigp2-titre">Service</TD>
							<TD><INPUT type="text"
					name="<%=process.getNOM_EF_RECH_SERVICE() %>"
					value="<%=process.getVAL_EF_RECH_SERVICE() %>"
					class="sigp2-saisie" size="10" onblur="<%= process.getNOM_PB_RECH_SERVICE() %>" onkeypress="if (event.keyCode == 13) return setfocus('<%=process.getFocus() %>')"></TD>
							<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_SERVICE() %>"></TD>
							<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_RECH_SERVICE() %>" value="Rechercher"
					class="sigp2-Bouton-100" style="visibility : hidden;"></TD>

						</TR>
						<TR>
							<TD class="sigp2-titre">Période   du</TD>
							<TD>
				<TABLE border="0" class="sigp2">
					<TR>
						<TD><INPUT type="text" size="8"
							name="<%=process.getNOM_EF_DDEB() %>"
							value="<%=process.getVAL_EF_DDEB() %>" class="sigp2-saisie" onkeypress="if (event.keyCode == 13) return setfocus('<%=process.getFocus() %>')"></TD>
						<TD><IMG src="images/calendrier.gif"
							onclick="return showCalendar('<%=process.getNOM_EF_DDEB()%>', 'dd/mm/y');"></TD>
					</TR>
				</TABLE>
				</TD>
							<TD align="center" class="sigp2-titre">au </TD>
							<TD><TABLE border="0" class="sigp2">
					<TR>
						<TD><INPUT type="text" size="8"
							name="<%=process.getNOM_EF_DFIN() %>"
							value="<%=process.getVAL_EF_DFIN() %>" class="sigp2-saisie" onkeypress="if (event.keyCode == 13) return setfocus('<%=process.getFocus() %>')"></TD>
						<TD><IMG src="images/calendrier.gif"
							onclick="return showCalendar('<%=process.getNOM_EF_DFIN()%>', 'dd/mm/y');"></TD>
					</TR>
				</TABLE>
				</TD>

						</TR>
						<TR>
							<TD><SPAN class="sigp2-titre">Equipement</SPAN></TD>
							<TD><INPUT type="text"
					name="<%=process.getNOM_EF_RECHE_EQUIP() %>"
					value="<%=process.getVAL_EF_RECHE_EQUIP() %>"
					class="sigp2-saisie" size="10" onblur="<%= process.getNOM_PB_RECHE_EQUIP() %>" onkeypress="if (event.keyCode == 13) return setfocus('<%=process.getFocus() %>')"></TD>
							<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_EQUIP() %>"></TD>
							<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_RECHE_EQUIP() %>" value="Rechercher"
					class="sigp2-Bouton-100" style="visibility : hidden;"><INPUT
					type="submit" name="<%= process.getNOM_PB_DETAILS() %>"
					value="Détails" class="sigp2-Bouton-100"
					style="visibility : hidden;"></TD>

						</TR>
			<TR>
				<TD colspan="4" align="center"><INPUT type="submit"
					name="<%= process.getNOM_PB_VALIDER() %>"
					value="Valider" class="sigp2-Bouton-100"></TD>
			</TR>
		</TABLE>
						
		</FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3">
				<TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°BPC     Immat      Date          Compteur   Essence   Gasoil       </TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
					</tr>
					<tr>
						<td colspan="3"><SELECT size="14"
					name="<%= process.getNOM_LB_BPCINFOS() %>" class="sigp2-liste"
					style="text-transform: uppercase; width: 100%"
					onchange='executeBouton("<%= process.getNOM_PB_DETAILS()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_BPCINFOS(),process.getVAL_LB_BPCINFOS_SELECT()) %>

				</SELECT></td>
		</tr>
		 <tr>
			<td colspan="3" align="left"><B><SPAN class="sigp2-titre-liste">Totaux</SPAN></B></td>
		</tr>
			<TR>
				<TD align="left" colspan="3"><SELECT size="2"
					name="<%= process.getNOM_LB_TOTAUX() %>" class="sigp2-liste"
					style="text-transform: uppercase; width: 100%"
					onchange='executeBouton("<%= process.getNOM_PB_DETAILS()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_TOTAUX(),process.getVAL_LB_TOTAUX_SELECT()) %>

				</SELECT></TD>
			</TR>
			<TR>
				<TD align="left" colspan="3"><SPAN class="sigp2-titre-liste"><B>Service
				concerné</B></SPAN></TD>
			</TR>
			<tr>
			<td colspan="3" class="sigp2-saisie"><b><%=process.getVAL_ST_SERVICE() %></b></td>
		</tr> 
		</table><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
<%=process.afficheScript() %>
