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
<TITLE>OePeBase.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePeBase" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST">
		<FIELDSET>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Modèle</TD>
				<TD></TD>
				<TD><INPUT size="1" type="text" class="sigp2-saisie" maxlength="1" name="ZoneTampon" style="display : none;">
				<INPUT type="text" name="<%=process.getNOM_EF_RECHERCHE() %>"
					value="<%=process.getVAL_EF_RECHERCHE() %>" class="sigp2-saisie"
					size="20"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_RECHERCHE() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHEMODELE() %>"></TD>
			</TR>
		</TABLE>
		</FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><BR>
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos concernant le modèle</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD>Marque </TD>
								<TD width="10"></TD>
								<TD>Modèle</TD>
								<TD width="10"></TD>
								<TD>Type</TD>
								<TD width="10"></TD>
								<TD>Version</TD>
							</TR>
							<TR>
								<TD class="sigp2-saisie" style="text-transform: uppercase"> <%= process.getVAL_ST_MARQUE()%></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_MODELE() %></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TEQUIP() %></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_VERSION() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<%if ( "".equals(process.getVAL_ST_TITRE_ACTION()) ) { %><BR>
		<TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left"><TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Entretien                      Durée  Intervalle Inactifs Date     </TD>
						</TR>
					</TBODY>
				</TABLE>

</TD>
				</TR>
				<TR>
					<TD align="left"><SELECT size="12" name="<%= process.getNOM_LB_PE() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">

					<%= process.forComboHTML(process.getVAL_LB_PE(),process.getLB_PE_Couleurs(),process.getVAL_LB_PE_SELECT()) %>

				</SELECT>
				</TR>
			</TABLE>
	<%if (!"".equals(process.getVAL_ST_MODELE())){ %>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit"
					name="<%=process.getNOM_PB_AJOUTER() %>" value="Ajouter"
					class="sigp2-Bouton-100"></TD>
					<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE>
	<%} %>
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
										<td>Entretien</td>
										<td>Durée </td>
										<td>Intervalle</td>
										<td>Type d'intervalle</td>
										<TD>Commentaire</TD>
									</tr>
													<tr>
														<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_ENTRETIEN() %></td>
														<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DUREE() %></td>
														<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_INTERVALLE() %></td>
														<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TINT() %></td>
										<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMMENTAIRE() %></TD>
									</tr>
								</TABLE>
								<table class="sigp2">
									<tr>
										<td>Enlever l'entretien du Plan d'entretien de base :</td>
										<td><INPUT type="checkbox"<%= process.forCheckBoxHTML(process.getNOM_CK_DESACTIV() , process.getVAL_CK_DESACTIV()) %> ></td>
										<td width="5"></td>
										<td><INPUT type="text" size="20" name="<%=process.getNOM_EF_DATEDESACTIV() %>" value="<%=process.getVAL_EF_DATEDESACTIV() %>" class="sigp2-saisie"></td>
										<td><IMG src="images/calendrier.gif" onclick="return showCalendar('<%=process.getNOM_EF_DATEDESACTIV()%>', 'dd/mm/y');"></td>
									</tr>
								</table>
							<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
				</table><br>
	</FIELDSET>
	<%} %>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
