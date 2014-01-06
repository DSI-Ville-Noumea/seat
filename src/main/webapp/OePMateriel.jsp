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
<TITLE>OeEquipement.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePMateriel" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><%if ("".equals(process.getVAL_ST_TITRE_ACTION())){ %>
			<!-- <TABLE class="sigp2" border="1">
				<TR>
					<TD>--><FIELDSET>
						<TABLE class="sigp2" border="0">
							<tr>
								<td>Affichage des équipements</td>
								<td>
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="radio" 
								<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_ACTIFS(),process.getVAL_RG_AFFICHAGE()) %> onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD>Actifs</TD>
										</TR>
									</TABLE></td>
								<td> </td>
								<td>
							<TABLE border="0" class="sigp2">
								<TR>
									<TD><INPUT type="radio"
										<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_INACTIFS(),process.getVAL_RG_AFFICHAGE()) %>
										onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
									<TD>Inactifs</TD>
								</TR>
							</TABLE>
							</td>
								<td> </td>
								<td>
							<TABLE border="0" class="sigp2">
								<TR>
									<TD><INPUT type="radio"
										<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_TOUS(),process.getVAL_RG_AFFICHAGE()) %>
										onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
									<TD>Tous</TD>
								</TR>
							</TABLE>
							</td>
							</tr>
						</TABLE></FIELDSET>
		
		<!-- </TD>
				</TR>
			</TABLE> -->
					
			<INPUT type="submit" value="OK"
			name="<%=process.getNOM_PB_OK_TRI() %>" style="visibility : hidden;">
		
		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND>
		<TABLE border="0" class="sigp2" width="100%">
				<TR align="center">
					<TD align="left" height="35">

				<TABLE border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">
							<TABLE class="sigp2" border="0">
								<TR>
									<TD height="29" width="60">
									<TABLE border="0" class="sigp2" cellspacing="0" cellpadding="0">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_PMINV(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste" width="39">Inv</TD>
										</TR>
									</TABLE>
									</TD>
									<TD height="29" width="118">
									<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_PMSERIE(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">N°&nbsp;Série&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>
									</TABLE>
									</TD>
									<TD height="29" width="65">
									<TABLE border="0" class="sigp2" class="sigp2-titre-liste"
										cellpadding="0" cellspacing="0">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_MARQUE(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">Marque&nbsp;&nbsp;&nbsp;</TD>
										</TR>
									</TABLE>
									</TD>
									<TD height="29" width="135">
									<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_MODELE(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">Modèle&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>
									</TABLE>
									</TD>
									<TD width="120">
									<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
										<TR>
											<TD><INPUT type="radio"
												<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_TYPE(),process.getVAL_RG_TRI()) %>
												onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
											<TD class="sigp2-titre-liste">Type</TD>
										</TR>
									</TABLE>
									</TD>
									<TD width="100" class="sigp2-titre-liste"></TD>
									<!-- <TD height="29" width="87">Service d'affectation</TD>-->
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="15"
					name="<%= process.getNOM_LB_PMATERIEL() %>"
					class="sigp2-liste"
					onchange='executeBouton("<%=process.getNOM_PB_OK()%>")'
					style="text-transform: uppercase; width: 100%">
					<!-- <OPTION value="1">      1236|1526                |             renault|             clio RS|   VL|08/06/2005</OPTION> -->
					<%= process.forComboHTML(process.getVAL_LB_PMATERIEL(),process.getLB_PMAT_Couleurs(),process.getLB_PMAT_FCouleurs(),process.getVAL_LB_PMATERIEL_SELECT()) %>
				</SELECT>
					<INPUT type="submit" value="OK" name="<%=process.getNOM_PB_OK() %>" style="visibility : hidden;"></TD>
				</TR>
			</TABLE><TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%=process.getNOM_PB_AJOUTER() %>" value="Ajouter" class="sigp2-Bouton-100"></TD>
<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE></FIELDSET>
<%} else {%><BR>
	<FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center" class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
								<TABLE border="1" class="sigp2">
									<tr>
										<td>N°inventaire</td>
										<td>N°de série</td>
										<td>Marque</td>
										<td>Modèle</td>
										<td>Type</td>
										<td>Date de mise en service</td>
						<TD>Fournisseur</TD>
					</tr>
									<tr>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PMINV() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PMSERIE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_MARQUE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_MODELE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DMES() %></td>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_FRE() %></TD>
					</tr>
								</TABLE>
				<TABLE border="0" class="sigp2" align="center">
					<TR>
						<TD>Date de mise hors circulation</TD>
						<TD width="5"></TD>
						<TD><INPUT type="text"
							name="<%=process.getNOM_EF_DMHS() %>"
							value="<%=process.getVAL_EF_DMHS() %>"
							class="sigp2-saisie"></TD>
						<TD><IMG src="images/calendrier.gif"
							onclick="return showCalendar('<%=process.getNOM_EF_DMHS()%>', 'dd/mm/y');"></TD>
					</TR>
				</TABLE>
				<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="140"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
				</table></FIELDSET>
	<%}  %>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
