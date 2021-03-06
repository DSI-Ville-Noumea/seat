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
<script language="JavaScript1.2" src="js/masks.js"></script>
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


// Mask JavaScript API (v0.3) - dswitzer [chez] pengoworks [point] com - iubito [chez] asp-php [point] net
function init(nom)
{
   // Création du masque montant en euro
   oHeure = new Mask("##:##", "heure");
   
   // Associer le oEuroMask au champ
   oHeure.attach(document.formu.elements[nom]);
}

</SCRIPT>
<TITLE>OePeBase_ajout.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePeBase_ajout" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION() %><BR>
		</SPAN><BR>
		
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant le modèle</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<Td>Marque</TD>
								<TD width="10"></TD>
								<TD>Modèle</TD>
								<TD width="10"></TD>							
								<TD>Type</TD>
							</TR>
							<TR>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()%></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_MODELE() %></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TEQUIP() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE><BR>
		<!-- <FIELDSET> -->
				<TABLE border="0" class="sigp2">
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center">
								<tr>
									<td colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un Plan d'Entretien</td>
								</tr>
								<TR>
									<TD width="10"></TD>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2">
											<tr>
								<td colspan="5" height="10"></td>
							</tr>
							<TR>
								<TD colspan="2" align="right">Entretien : </TD>
								<TD colspan="3" class="sigp2-saisie" style="text-transform: uppercase">
								<%if ((null==process.getIsModif())||("".equals(process.getIsModif()))){ %>
								<SELECT size="1"
									name="<%=process.getNOM_LB_ENTRETIEN() %>" class="sigp2-liste" style="text-transform: uppercase">
									<%= process.forComboHTML(process.getVAL_LB_ENTRETIEN(),process.getVAL_LB_ENTRETIEN_SELECT()) %>
								</SELECT>
								<%}else{ %>
								<%=process.getVAL_ST_ENTRETIEN() %>
								<%}%>
								</TD>
							</TR>
							<TR>
												<TD>Durée</TD>
												<TD><INPUT type="text" size="8" name="<%=process.getNOM_EF_DUREE() %>" value="<%=process.getVAL_EF_DUREE() %>" class="sigp2-saisie" style="text-transform: uppercase"></TD>
												<TD>heure(s)</TD>
												<TD></TD>
								<TD width="126"></TD>
							</TR>
							<TR>
								<TD>Intervalle</TD>
								<TD><INPUT type="text" size="5"
									name="<%=process.getNOM_EF_INTERVALLE() %>"
									value="<%=process.getVAL_EF_INTERVALLE() %>" class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD>Type d'intervalle</TD>
								<TD><SELECT size="1" name="<%=process.getNOM_LB_TINTER() %>"
									class="sigp2-liste" style="text-transform: uppercase"><%= process.forComboHTML(process.getVAL_LB_TINTER(),process.getVAL_LB_TINTER_SELECT()) %>
								</SELECT></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Commentaire</TD>
								<td colspan="4"><TEXTAREA rows="4" cols="50" name="<%=process.getNOM_EF_COMMENTAIRE() %>" class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_EF_COMMENTAIRE() %></TEXTAREA></td>
							</TR>
<%if ((null!=process.getIsModif())||(!"".equals(process.getIsModif()))){%>
							<TR>
								<td colspan="5">
									<TABLE class="sigp2">
										<tr>
										<td><INPUT type="checkbox"
											<%= process.forCheckBoxHTML(process.getNOM_CK_DESACTIV() , process.getVAL_CK_DESACTIV()) %> class="sigp2-saisie"></td>
										<td>Enlever l'entretien du Plan d'entretien original</td>
										<td width="5"></td>
										<td><INPUT type="text" size="20" name="<%=process.getNOM_EF_DATEDESACTIV() %>" value="<%=process.getVAL_EF_DATEDESACTIV() %>" class="sigp2-saisie"></td>
										<td><IMG src="images/calendrier.gif" onclick="return showCalendar('<%=process.getNOM_EF_DATEDESACTIV()%>', 'dd/mm/y');"></td>
										</tr>
									</TABLE>
								</td>	
							</TR>
<%} %>
						</TABLE>
								</FIELDSET>
						<br>
									</TD>
									<TD width="10"></TD>
								</TR>
							</TABLE>		
			<br>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%if (!process.isManqueParam()){ %>
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%} %>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
				<br>
	<!-- </FIELDSET> -->
	

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
