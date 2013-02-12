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
<TITLE>OeDeclaration_VisualisationEquip.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeDeclaration_VisualisationEquip" id="process" scope="session"></jsp:useBean>
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
			<TR>
				<TD class="sigp2-titre"></TD>
				<TD></TD>
				<TD></TD>
				<TD class="sigp2-titre">Petit Materiel</TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECH_PM() %>"></TD>
			</TR>
		</TABLE><TABLE border="3" bordercolor="#669999" cellpadding="2"
			cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos
					concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N°Inventaire</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINV()%></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>N°immatriculation</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOIMMAT()%></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()+" "+process.getVAL_ST_MODELE()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD>Type</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3">
				<TABLE border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Date       OT         Déclarant        
							            </TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
					</tr>
					<tr>
						<td colspan="3">
				<SELECT size="10" name="<%= process.getNOM_LB_DECLARATIONS() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" onclick='executeBouton("<%= process.getNOM_PB_DETAILS()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_DECLARATIONS(),process.getVAL_LB_DECLARATIONS_SELECT()) %>

				</SELECT></td>
		</tr>
		 <tr>
			<td colspan="3" align="center" height="10"></td>
		</tr>
			<TR>
				<TD align="left" colspan="3">Anomalies signalées<INPUT type="submit"
					value="Voir les anomalies signalées"
					name="<%=process.getNOM_PB_DETAILS() %>"
					style="visibility : hidden;"></TD>
			</TR>
			<tr>
				<td colspan="3" align="left" class="sigp2-saisie"
					style="text-transform: uppercase; font-weight: bold"><%=process.getVAL_ST_ANOMALIES() %>
				</td>
			</tr> 
		</table><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
