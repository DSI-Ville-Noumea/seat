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
document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeModele_Visualisation.jsp</TITLE>
</HEAD>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">
<jsp:useBean class="nc.mairie.seat.process.OeModele_Visualisation" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
						<TABLE border="0" class="sigp2">
							<tr>
								<td class="sigp2-titre">Modèle </td>
								<td></td>
								<td><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"></td>
							</tr>
						</TABLE>

					</FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
				<TABLE border="3" width="100%" bordercolor="#669999" cellpadding="2"
					cellspacing="2" align="center" width="100%">
					<TR>
						<TD colspan="3" bgcolor="#669999" class="sigp2-titre">Détails d'un
						modèle</TD>
					</TR>
					<TR>
						<TD width="10"></TD>
						<TD align="center"><!--Détails du BPC-->
						<TABLE class="sigp2" width="100%">
							<TR>
								<TD colspan="3" align="right"><B>Désignation du modèle :</B></TD>
								<TD colspan="2" class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_DESIGNATION() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD colspan="5" height="10"></TD>
								<TD height="10"></TD>
							</TR>
							<TR>
								<TD>Marque</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_MARQUE() %></TD>
								<TD></TD>
								<TD><B>Version</B></TD>
								<TD class="sigp2-saisiemajuscule"
									style="text-transform: uppercase"><%=process.getVAL_ST_VERSION() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Carburant</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_CARBU() %></TD>
								<TD></TD>
								<TD>Capacité du réservoir</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_CAPACITE() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Nombre pneu avant</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_NBPNEUAV() %></TD>
								<TD></TD>
								<TD>Nombre de pneu arrière</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_NBPNEUAR() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Type de pneu</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PNEU() %></TD>
								<TD></TD>
								<TD>Nombre d'essieux</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_NBESSIEUX() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Puissance fiscale</TD>
								<TD class="sigp2-saisie"><%=process.getVAL_ST_PUISSANCE() %></TD>
								<TD></TD>
								<TD>Type de compteur</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMPTEUR() %></TD>
								<TD></TD>
							</TR>
							<TR>
								<TD>Type d'équipement</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TEQUIP() %></TD>
								<TD></TD>
								<TD></TD>
								<TD class="sigp2-saisie"></TD>
								<TD></TD>
							</TR>
						</TABLE>
						</TD>
						<TD width="10"></TD>
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
