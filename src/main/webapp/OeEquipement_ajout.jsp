<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<TITLE>OeEquipement_ajout.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeEquipement_ajout" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre">Création<BR></SPAN>
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
										<TABLE class="sigp2" width="100%">
											<tr>
								<td colspan="5" height="10"></td>
							</tr>
											<TR>
												<TD width="108">N° inventaire</TD>
												<TD width="105" style="text-transform: uppercase"><INPUT type="text" size="4" name="<%=process.getNOM_EF_NOINVENTAIRE() %>" value="<%=process.getVAL_EF_NOINVENTAIRE() %>" class="sigp2-saisiemajuscule" maxlength="4"></TD>
												<TD width="27"></TD>
												<TD width="113">N°  immatriculation</TD>
								<TD width="150" style="text-transform: uppercase"><INPUT type="text" name="<%=process.getNOM_EF_NOIMMAT() %>"
									value="<%=process.getVAL_EF_NOIMMAT() %>"
									class="sigp2-saisiemajuscule" maxlength="8"></TD>
							</TR>
							</table>
					<fieldset>
						<table class="sigp2">
							<tr>
								<TD></TD>
								<td align="center">Marque</td>
								<td></td>
								<td align="center">Modèle</td>
								<td></td>
								<td align="center">Type</td>
							</tr>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD style="text-transform: uppercase">
									<SELECT name="<%=process.getNOM_LB_MARQUE() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%= process.getNOM_PB_OK_MARQUE()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_MARQUE(),process.getVAL_LB_MARQUE_SELECT()) %>
								</SELECT></TD>
								<TD><INPUT type="submit" value="OK"
									name="<%=process.getNOM_PB_OK_MARQUE() %>" style="visibility : hidden;"></TD>
								<TD style="text-transform: uppercase">
								<%if (! "".equals(process.getVAL_ST_MARQUE()) ){ %>
										<SELECT name="<%=process.getNOM_LB_MODELE() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%= process.getNOM_PB_OK_MODELE()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_MODELE(),process.getVAL_LB_MODELE_SELECT()) %>
								</SELECT>
								<%}%>
								</TD>
								<TD>
									
								</TD>
								<TD class="sigp2-saisie" valign="top"><%if (! "".equals(process.getVAL_ST_MARQUE()) ){ %>
								<INPUT type="submit" value="OK"
									name="<%=process.getNOM_PB_OK_MODELE() %>"
									style="visibility : hidden;"> <%} %> <%= process.getVAL_ST_TYPE_EQUIP() %></TD>
							</TR>
						</table>
						</fieldset>
						<table class="sigp2">
							<TR>
												<TD>Carburant</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_CARBU() %></TD>
												<TD width="20"></TD>
												<TD>Type de pneu</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_PNEU() %></TD>
							</TR>
											<TR>
												<TD>Puissance fiscale</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_PUISSANCE() %></TD>
												<TD width="27"></TD>
												<TD>Type de compteur</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_COMPTEUR() %></TD>
							</TR>
							<TR>
								<TD>Capacité du reservoir</TD>
								<TD></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_RESERVOIR() %></TD>
								<TD width="27"></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
							</TR>
							<TR>
												<TD>Prix d'achat</TD>
								<TD></TD>
								<TD><INPUT type="text" name="<%=process.getNOM_EF_PRIX() %>"
									value="<%=process.getVAL_EF_PRIX() %>" class="sigp2-saisie" size="10"></TD>
												<TD width="27"></TD>
												<td>Durée de garantie</td>
								<TD></TD>
								<td><INPUT type="text" size="3"
									name="<%=process.getNOM_EF_GARANTIE() %>"
									value="<%=process.getVAL_EF_GARANTIE() %>" class="sigp2-saisie"></td>
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
											<td align="center">Date de mise en circulation</td>
								<td valign="top"><INPUT type="text" size="10"
									name="<%=process.getNOM_EF_DATEMISEENCIRCULATION() %>"
									value="<%=process.getVAL_EF_DATEMISEENCIRCULATION() %>"
									class="sigp2-saisie"></td>
								<td><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DATEMISEENCIRCULATION()%>', 'dd/mm/y');"></td>
											<td width="50"></td>
											<td>Réserve :</td>
											<td><INPUT type="checkbox" <%= process.forCheckBoxHTML(process.getNOM_CK_RESERVE() , process.getVAL_CK_RESERVE()) %> ></td>
										</tr>
										<!-- <tr>
											<td align="center" width="166">Date de réception du matériel</td>
											<td width="146"><INPUT type="text" size="10"
									name=""
									value="" class="sigp2-saisie"
									style="height: 100%; width: 100%"></td>
											<td width="32"><IMG src="images/calendrier.gif" onclick="return showCalendar('<%=process.getNOM_EF_DATERECEPTION()%>', 'dd/mm/y');"></td>
											<td width="82"></td>
											<td></td>
										</tr> -->
									</table>
								</FIELDSET>
									</TD>
								</TR>
							</TABLE>		
			<br>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%if (!process.isManqueParam()) {%>
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%} %>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table>
				<br>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
