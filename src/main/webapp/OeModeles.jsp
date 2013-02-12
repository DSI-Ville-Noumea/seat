<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
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
<TITLE>OeModeles.jsp</TITLE>
</HEAD>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">
<jsp:useBean class="nc.mairie.seat.process.OeModeles" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%if ("".equals(process.getVAL_ST_TITRE_ACTION())){ %>

			<BR></SPAN>Sélectionnez la marque :
			<BR>
			<table class="sigp2" align="center">
				<tr>
					<td>
						<SELECT name="<%= process.getNOM_LB_MARQUE() %>" class="sigp2-liste"  onchange='executeBouton("<%=process.getNOM_PB_OKMARQUES()%>")'" style="text-transform: uppercase">
							<%= process.forComboHTML(process.getVAL_LB_MARQUE(),process.getVAL_LB_MARQUE_SELECT()) %>
						</SELECT>
					</td>
					<td><INPUT type="submit" value="OK" name="<%= process.getNOM_PB_OKMARQUES() %>"  style="visibility : hidden;"></td>
				</tr>
			</table>
			<BR>
	<%if (! "".equals(process.getVAL_ST_ACTION_OK()) ) {%><BR>
			<FIELDSET><LEGEND class="sigp2Fieldset" align="left"><%= process.getVAL_ST_ACTION_OK() %></LEGEND><BR>
			<TABLE border="0" class="sigp2">
				<TR align="center">
					<TD>
						<TABLE class="sigp2" border="1" cellspacing="1">
							<tr>
								<TD class="sigp2-titre-liste">Libellé&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Version&nbsp;Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Carburant&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
								
							</tr>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD>
						<SELECT size="10" name="<%= process.getNOM_LB_MODELE() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_MODELE(),process.getVAL_LB_MODELE_SELECT()) %>
				</SELECT>
					</TD>
				</TR>
			</TABLE><BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%=process.getNOM_PB_AJOUTER() %>" value="Ajouter" class="sigp2-Bouton-100"></TD>
<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE></FIELDSET>
	<%} %>
<%} else {%><BR>
	<FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center"><%= process.getVAL_ST_TITRE_ACTION() %></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<TABLE border="1" class="sigp2">
								<tr>
									<td>Désignation</td>
									<td>Version</td>
									<td>Type d'équipement</td>
									<td>Capacité du réservoir</td>
									<td>Puissance fiscal</td>
									<td>Nb pneu avant</td>
									<td>Nb pneu arrière</td>
									<td>Nb essieux</td>
									<td>Pneu</td>
									<td>Carburant</td>
									<td>Compteur</td>
								</tr>
								<tr>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DESIGNATION() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_VERSION() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPEEQUIP() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_RESERVOIR() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PUISSANCE() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NBPNEUAV() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NBPNEUAR() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NBESSIEUX() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_PNEU() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_CARBU() %></td>
									<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMPTEUR() %></td>
								</tr>

							</TABLE><TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table></FIELDSET>
<%}%>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
