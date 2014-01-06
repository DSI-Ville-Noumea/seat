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
<TITLE>OeEquipement_Visualisation.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeEquipement_Visualisation" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
						<TABLE border="0" class="sigp2">
							<tr>
								<td class="sigp2-titre">Equipement </td>
								<td></td>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_RECHERCHE_EQUIP() %>"
					value="<%=process.getVAL_EF_RECHERCHE_EQUIP() %>" class="sigp2-saisie" size="10"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_RECHERCHE_EQUIP() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<td><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"></td>
							</tr>
						</TABLE>

					</FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" width="100%" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center" width="100%">
								<tr>
						<td bgcolor="#669999" class="sigp2-titre">Détails d'un
						équipement</td>
					</tr>
								<TR>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2">
											<tr>
								<td colspan="7" height="10"></td>
							</tr>
							<TR>
								<TD><B>N° inventaire</B></TD>
								<TD width="5"></TD>
								<TD class="sigp2-saisiemajuscule"><%=process.getVAL_ST_NOINVENT() %></TD>
								<TD width="15"></TD>
								<TD><B>N° immatriculation</B></TD>
								<TD width="5"></TD>
								<TD class="sigp2-saisiemajuscule"><%=process.getVAL_ST_NOIMMAT() %></TD>
							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NOMEQUIP() %></TD>
								<TD></TD>
								<TD>
								Prix d'achat</TD>
								<TD></TD>
								<TD class="sigp2-saisie">
								<%=process.getVAL_ST_PRIX() %></TD>
							</TR>
							<TR>
								<TD>Carburant</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_CARBU() %></TD>
								<TD></TD>
								<TD>Type de pneu</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_PNEU() %></TD>
							</TR>
											<TR>
												<TD>Puissance fiscale</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_PUISSANCE() %></TD>
												<TD></TD>
												<TD>Type de compteur</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_COMPTEUR() %></TD>
							</TR>
							<TR>
								<TD>Capacité du reservoir</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_RESERVOIR() %></TD>
								<TD></TD>
								<TD>Durée de garantie</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_GARANTIE() %></TD>
							</TR>
							<TR>
								<TD>Agent responsable</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_AGENT_RESPONSABLE() %></TD>
								<TD></TD>
								<TD>Réserve</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_RESERVE() %></TD>
							</TR>
						</TABLE>
								</FIELDSET><br>
								<FIELDSET>
									<table border="0" class="sigp2">
										<tr>
											<td align="center">Plan d'entretien : </td>
								<TD align="center" width="10"></TD>
								<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_PE() %></td>
											<td width="40"></td>
											<td>Version : </td>
								<TD width="10"></TD>
								<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_VERSION() %></td>
										</tr>
									</table>
								</FIELDSET><br>
<FIELDSET>
									<table border="0" class="sigp2">
										<tr>
											<td align="center">Service</td>
								<TD align="center" width="10"></TD>
								<td class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_SERVICE() %></td>
											<td width="40"></td>
										</tr>
									</table>
								</FIELDSET><br>
								<FIELDSET>
									<table border="0" class="sigp2">
										<tr>
								<td align="left">Date de mise en circulation</td>
								<TD align="left" width="10"></TD>
								<td class="sigp2-saisie"><%=process.getVAL_ST_DATEMISEENCIRCULATION() %></td>
										</tr>
							<TR>
								<TD align="left">Date de mise hors circulation</TD>
								<TD align="left" width="10"></TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_DATEHORSCIRCUIT() %></TD>
							</TR>
							<tr>
								<td align="left">Date de vente ou réforme</td>
								<TD align="left" width="10"></TD>
								<td class="sigp2-saisie"><%=process.getVAL_ST_DATEVENTEREFORME() %></td>
										</tr>
						</table>
								</FIELDSET>
									</TD>
								</TR>
							</TABLE>		
			</td>
		</tr>
	</table>
			
		<BR>
		<%if(process.isDebranche){ %>
		<INPUT type="submit" value="Retour"
			name="<%=process.getNOM_PB_RETOUR() %>" class="sigp2-Bouton-100"><BR>
		<%} %><br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
