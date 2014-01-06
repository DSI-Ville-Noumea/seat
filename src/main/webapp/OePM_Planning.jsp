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
document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OePlanning.jsp</TITLE>
</HEAD>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">
<jsp:useBean class="nc.mairie.seat.process.OePM_Planning" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"></SPAN><FIELDSET><LEGEND class="sigp2Fieldset" align="left">Planning</LEGEND>
<FIELDSET>
			<TABLE class="sigp2" border="0">
				<tr>
					<td>Affichage des entretiens</td>
					<td><INPUT type="radio" 
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_ENCOURS(),process.getVAL_RG_AFFICHAGE()) %> onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></td>
					<td>En cours</td>
					<td width="10"></td>					
					<td><INPUT type="radio"
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_TOUS(),process.getVAL_RG_AFFICHAGE()) %>
					onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></td>
					<td>Tous</td>
				<TD width="10"></TD>
				<TD><INPUT type="radio"
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_EN_RETARD(),process.getVAL_RG_AFFICHAGE()) %>
					onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
				<TD>En retard</TD>
				<TD><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_TRI() %>"
					style="visibility : hidden;"></TD>
			</tr>
</table>
<table class="sigp2" border="0">
			<TR>
				<TD>Planning jusqu'au : </TD>
				<TD colspan="3"><INPUT type="text" size="11" name="<%=process.getNOM_EF_DATEFINPLANNING() %>" value="<%=process.getVAL_EF_DATEFINPLANNING() %>" class="sigp2-saisie" style="text-transform: uppercase"></td>
				<td>
					<IMG
					src="images/calendrier.gif"
					onclick="return showCalendar('<%=process.getNOM_EF_DATEFINPLANNING()%>', 'dd/mm/y');"></TD>
				<TD colspan="2"><INPUT type="submit" value="     OK     "
					name="<%=process.getNOM_PB_OK_PLANNING() %>" class="sigp2-Bouton-100"></TD>
				<TD></TD>
				<TD><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK() %>" style="visibility : hidden;"></TD>
			</TR>
		</TABLE>
</FIELDSET>
		<TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left">
				<TABLE border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">
							<TABLE class="sigp2" border="0">
								<TR>
									<TD width="150">
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_SERIE(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">N°Série</TD>
										</TR>
									</TABLE>
									</TD>
									<TD width="200">
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_ENTRETIENS(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">Entretiens</TD>
										</TR>
									</TABLE>
									</TD>
									<TD width="79">
									<TABLE border="0" class="sigp2" class="sigp2-titre-liste">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_DATEPREVU(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">Prévu</TD>
										</TR>
									</TABLE>
									</TD>
									<!-- <td height="29" width="110">
									Type
								</TD> -->
									<TD align="left"
										class="sigp2-titre-liste" width="65">N°Fiche</TD>
									<TD width="100" class="sigp2-titre-liste">Commentaire

									</TD>
								</TR>
							</TABLE></TD>
						</TR>
					</TBODY>
				</TABLE>
				
					</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="8"
					name="<%= process.getNOM_LB_PLANNING() %>"
					ondblclick='executeBouton("<%= process.getNOM_PB_AJOUTER()%>")'  class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<!-- onchange='executeBouton("<%//= process.getNOM_PB_DETAILS()%>")' onchange='executeBouton("<%//= process.getNOM_PB_EFFACE_COMMENTAIRE()%>")'-->

					<%= process.forComboHTML(process.getVAL_LB_PLANNING(),process.getLB_PLANNING_Couleurs(),process.getLB_PLANNING_FCouleurs(),process.getVAL_LB_PLANNING_SELECT()) %>

				</SELECT></TD>
				</TR>
				<tr>
					<td class="sigp2">Commentaire : <INPUT type="submit"
					value="Voir le commentaire " name="<%=process.getNOM_PB_DETAILS() %>"><INPUT
					style="visibility : hidden;" type="submit" value="Efface le commentaire " 
					name="<%=process.getNOM_PB_EFFACE_COMMENTAIRE() %>"><br><a class="sigp2-liste"><%= process.getVAL_ST_COMMENTAIRE() %></a></td>
				</tr>
<%if (!process.isVide()){ %>
				<tr>
					<td align="center"><INPUT type="submit" value="Ajouter" name="<%=process.getNOM_PB_AJOUTER() %>" class="sigp2-Bouton-100"></td>
				</tr>
			</TABLE></FIELDSET>
			<fieldset><LEGEND class="sigp2Fieldset" align="left">Les entretiens à réaliser</LEGEND>
			<TABLE class="sigp2" border="0">
				<tr>
					<td colspan="2">
				<TABLE border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°Série      Entretiens                     Prévu     </TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
				</tr>
				<tr>
					<td valign="top" colspan="2"><SELECT size="8" name="<%= process.getNOM_LB_ENTRETIENS_A_FAIRE() %>"
					ondblclick='executeBouton("<%= process.getNOM_PB_ENLEVER()%>")'
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_ENTRETIENS_A_FAIRE(),process.getVAL_LB_ENTRETIENS_A_FAIRE_SELECT()) %>
				</SELECT></td>
				</tr>
<%if (process.isAfaire()){ %>
				<tr>
					<td>
						<table align="center">
							<tr>
								<td align="left" width="151"><INPUT type="submit" value="Enlever" name="<%=process.getNOM_PB_ENLEVER() %>" class="sigp2-Bouton-100"></td>
								<td><INPUT type="submit" value="Valider" name="<%=process.getNOM_PB_VALIDER() %>" class="sigp2-Bouton-100"></td>
							</tr>
						</table>
					</td>
				</tr>
<%} %>
<%} %>
			</TABLE>
			</fieldset>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
