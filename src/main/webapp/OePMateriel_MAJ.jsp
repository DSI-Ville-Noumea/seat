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
<TITLE>OePMateriel_MAJ</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePMateriel_MAJ" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %><BR></SPAN>
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
						petit matériel</td>
					</tr>
								<TR>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2" width="100%">
											<tr>
								<td colspan="5" height="10"></td>
							</tr>
											<TR>
												<TD width="95">N° inventaire</TD>
												<TD width="100" style="text-transform: uppercase"><INPUT type="text" size="5" name="<%=process.getNOM_EF_PMINV() %>" value="<%=process.getVAL_EF_PMINV() %>" class="sigp2-saisiemajuscule"></TD>
												<TD width="15"></TD>
												<TD width="110">N°  de série</TD>
								<TD width="150" style="text-transform: uppercase"><INPUT type="text" name="<%=process.getNOM_EF_PMSERIE() %>"
									value="<%=process.getVAL_EF_PMSERIE() %>"
									class="sigp2-saisiemajuscule"></TD>
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
								<TD width="95">Nom d'équipement</TD>
								<TD style="text-transform: uppercase">
									<SELECT name="<%=process.getNOM_LB_MARQUE() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%= process.getNOM_PB_MARQUE()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_MARQUE(),process.getVAL_LB_MARQUE_SELECT()) %>
								</SELECT></TD>
								<TD><INPUT type="submit" value="OK"
									name="<%=process.getNOM_PB_MARQUE() %>" style="visibility : hidden;"></TD>
								<TD style="text-transform: uppercase">
								<%if (! "".equals(process.getVAL_ST_MARQUE()) ){ %>
										<SELECT name="<%=process.getNOM_LB_MODELE() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%= process.getNOM_PB_MODELE()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_MODELE(),process.getVAL_LB_MODELE_SELECT()) %>
								</SELECT>
								<%}%>
								</TD>
								<TD>
									
								</TD>
								<TD class="sigp2-saisie" valign="top"><%if (! "".equals(process.getVAL_ST_MARQUE()) ){ %>
								<INPUT type="submit" value="OK"
									name="<%=process.getNOM_PB_MODELE() %>"
									style="visibility : hidden;"> <%} %> <%= process.getVAL_ST_TYPE_EQUIP() %></TD>
							</TR>
						</table>
						</fieldset>
						<table class="sigp2">
							<TR>
												<TD width="85">Fournisseur</TD>
								<TD width="10"></TD>
								<TD colspan="5"><SELECT name="<%=process.getNOM_LB_FOURNISSEUR() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%= process.getNOM_PB_FOURNISSEUR()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_FOURNISSEUR(),process.getVAL_LB_FOURNISSEUR_SELECT()) %>
								</SELECT></TD>
							</TR>
							<TR>
								<TD>Date d'achat</TD>
								<TD></TD>
								<TD>
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><INPUT type="text" size="10"
									name="<%=process.getNOM_EF_DACHAT() %>"
									value="<%=process.getVAL_EF_DACHAT() %>" class="sigp2-saisie"></td>
											<td width="15"></td>
											<td><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DACHAT()%>', 'dd/mm/y');"></td>
									</tr>
									</table>
								</TD>
								<TD width="15"></TD>
								<TD>Prix d'achat</TD>
								<TD></TD>
								<TD><INPUT type="text" name="<%=process.getNOM_EF_PRIX() %>"
									value="<%=process.getVAL_EF_PRIX() %>" class="sigp2-saisie"
									size="10"></TD>
							</TR>
							<TR>
								<TD></TD>
								<TD></TD>
								<TD></TD>
								<TD width="15"></TD>
								<TD>Durée de garantie (en jours)</TD>
								<TD></TD>
								<TD><INPUT type="text" size="3"
									name="<%=process.getNOM_EF_DGARANTIE() %>"
									value="<%=process.getVAL_EF_DGARANTIE() %>"
									class="sigp2-saisie"></TD>
							</TR>
						</TABLE>
								</FIELDSET><br>
								<FIELDSET>
									
						<TABLE border="0" class="sigp2">
							<TR>
								<TD align="left" width="95">Date de mise en circulation</TD>
								<TD><INPUT type="text" size="10"
									name="<%=process.getNOM_EF_DMES() %>"
									value="<%=process.getVAL_EF_DMES() %>"
									class="sigp2-saisie"></TD>
								<TD><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DMES()%>', 'dd/mm/y');"></TD>
								<TD width="40"></TD>
								<TD>Réserve :</TD>
								<TD><INPUT type="checkbox"
									<%= process.forCheckBoxHTML(process.getNOM_CK_RESERVE() , process.getVAL_CK_RESERVE()) %>></TD>
							</TR>
<%if (process.isModif()){ %>
							<TR>
								<TD align="left" width="95">Date de mise hors circulation</TD>
								<TD><INPUT type="text" size="10"
									name="<%=process.getNOM_EF_DMHS() %>"
									value="<%=process.getVAL_EF_DMHS() %>"
									class="sigp2-saisie"></TD>
								<TD><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DMHS()%>', 'dd/mm/y');"></TD>
								<TD></TD>
								<TD></TD>
								<TD></TD>
							</TR>
<%} %>
						</TABLE>
						</FIELDSET>
									</TD>
								</TR>
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
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
