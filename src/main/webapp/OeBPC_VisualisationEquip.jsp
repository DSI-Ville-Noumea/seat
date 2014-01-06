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
<TITLE>OeBPC_VisualisationEquip.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeBPC_VisualisationEquip" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET>
<%if(!process.isVide()) {%>
						<TABLE border="0" align="right">
							<tr>
				<TD><INPUT type="image"
					name="<%= process.getNOM_PB_IMPRIMER() %>"
					src="images/print.gif"></TD>
			</tr>
</TABLE>
<%} %>
<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement</TD>
				<TD></TD>
				<TD><INPUT type="text"
					name="<%=process.getNOM_EF_RECHERCHE_EQUIP() %>"
					value="<%=process.getVAL_EF_RECHERCHE_EQUIP() %>"
					class="sigp2-saisiemajuscule" size="10"></TD>
				<TD><INPUT type="submit"
					name="<%= process.getNOM_PB_RECHERCHE_EQUIP() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHER() %>"></TD>
				<TD class="sigp2-titre" width="35" align="right">PM</TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" alt="Sélection d'un petit matériel"
					name="<%=process.getNOM_PB_SEL_PM() %>"></TD>
				<TD></TD>
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
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>N°d'immat(ou série)</B></TD>
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
							<TR>
								<TD height="10" colspan="7"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD width="10"></TD>
								<TD colspan="5" class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_SERVICE()%></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td height="10" colspan="3">
				<TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°BPC         Date     Compteur  Quanti Km
							parcouru  Moyenne   </TD>
						</TR>
					</TBODY>
				</TABLE>
				</td>
					</tr>
					<tr>
						<td colspan="3">
				<SELECT size="18" name="<%= process.getNOM_LB_BPC() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_DETAILS()%>")'>

					<%= process.forComboHTML(process.getVAL_LB_BPC(),process.getVAL_LB_BPC_SELECT()) %>

				</SELECT></td>
		</tr>
		<tr>
			<td colspan="3" align="left" class="sigp2-titre-liste"><B>Totaux</B> </td>
		</tr> 
			<TR>
				<TD colspan="3">
				<SELECT size="2" name="<%= process.getNOM_LB_TOTAL() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">

					<%= process.forComboHTML(process.getVAL_LB_TOTAL(),process.getVAL_LB_TOTAL_SELECT()) %>

				</SELECT></TD>
			</TR>
			<TR>
				<TD colspan="3" align="center">
				<%if(!process.isListeVide()){ %>
					<INPUT type="submit"
					name="<%= process.getNOM_PB_DETAILS() %>"
					value="Détails" class="sigp2-Bouton-100">
				<%} %>
				</TD>
			</TR>
		</table><INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
<%=process.afficheScript() %>
