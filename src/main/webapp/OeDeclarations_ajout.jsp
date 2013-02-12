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

</SCRIPT>
<TITLE>OeBPC_ajout.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeDeclarations_ajout" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION() %><BR>
		<SPAN class="sigp2-titre"> <BR>
		</SPAN>
		</SPAN>
		<FIELDSET>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement</TD>
				<TD></TD>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_RECHERCHE_EQUIP() %>"
					value="<%=process.getVAL_EF_RECHERCHE_EQUIP() %>" class="sigp2-saisie"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_RECHERCHE_EQUIP() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17"
					name="<%=process.getNOM_PB_RECHERCHER() %>"></TD>
			</TR>
			<!-- TR>
				<TD class="sigp2-titre"></TD>
				<TD></TD>
				<TD></TD>
				<TD class="sigp2-titre">Petit matériel</TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECH_PM() %>"></TD>
			</TR-->
			<TR>
				<TD class="sigp2-titre">Petit matériel</TD>
				<TD></TD>
				<TD><INPUT type="text" name="<%=process.getNOM_EF_RECHERCHE_PM() %>"
					value="<%=process.getVAL_EF_RECHERCHE_PM() %>" class="sigp2-saisie"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_RECHERCHE_PM() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17"
					name="<%=process.getNOM_PB_RECH_PM() %>"></TD>
			</TR>
		</TABLE>
		</FIELDSET><TABLE border="3" bordercolor="#669999" cellpadding="2"
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
								<TD><B>N° d'inventaire</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOINV() %></TD>
								<TD width="10"></TD>
								<TD><B>N° d'immatriculation</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_NOIMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()+" "+process.getVAL_ST_MODELE()%></TD>
								<TD width="10"></TD>
								<TD>Type</TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
							<TR>
								<TD height="15"></TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD width="10"></TD>
								<TD></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
							</TR>
							<TR>
								<TD>Service</TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"
									colspan="5"><%=process.getVAL_ST_SERVICE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE><!-- <FIELDSET> -->
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							<table border="3" bordercolor="#669999" cellpadding="2" cellspacing="2" align="center">
								<tr>
									<td colspan="3" bgcolor="#669999" class="sigp2-titre">Détail d'une déclaration</td>
								</tr>
								<TR>
									<TD width="10"></TD>
									<TD><!--Détails du BPC--><FIELDSET>
										<TABLE class="sigp2">
											<tr>
								<td colspan="2" height="10"></td>
							</tr>
											<TR>
												<TD>Date</TD>
												<TD valign="middle">
								<TABLE border="0" class="sigp2">
									<TR>
										<TD><INPUT type="text" size="8"
											name="<%=process.getNOM_EF_DATE() %>"
											value="<%=process.getVAL_EF_DATE() %>" class="sigp2-saisie"></TD>
										<TD><IMG src="images/calendrier.gif"
											onclick="return showCalendar('<%=process.getNOM_EF_DATE()%>', 'dd/mm/y');"></TD>
									</TR>
								</TABLE>
								</TD>
							</TR>
							<TR>
								<TD colspan="2">Anomalies signalées</TD></TR>
							<TR>
								<TD colspan="2"><TEXTAREA rows="4" cols="50"
									name="<%=process.getNOM_EF_COMMENTAIRE() %>"
									class="sigp2-saisie"
									style="text-transform: uppercase; width: 100%"><%=process.getVAL_EF_COMMENTAIRE() %></TEXTAREA></TD></TR>
							<TR>
												<TD colspan="2">Le déclarant</TD></TR>
											<TR>
												<TD colspan="2"><SELECT
									name="<%= process.getNOM_LB_SERVICE() %>"
									class="sigp2-liste"
									style="text-transform: uppercase;" onchange='executeBouton("<%= process.getNOM_PB_OK_SCE()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_SERVICE(),process.getVAL_LB_SERVICE_SELECT()) %>
								</SELECT><INPUT type="submit" value="OK"
									name="<%=process.getNOM_PB_OK_SCE() %>"
									style="visibility : hidden;"></TD></TR>
							<TR>
								<TD colspan="2"><SELECT name="<%= process.getNOM_LB_AGENT() %>"
									class="sigp2-liste" style="text-transform: uppercase;">
									<%= process.forComboHTML(process.getVAL_LB_AGENT(),process.getVAL_LB_AGENT_SELECT()) %>
								</SELECT></TD></TR>
						</TABLE>
								</FIELDSET><br>
									</TD>
									<TD width="10"></TD>
								</TR>
							</TABLE><TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">

						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>

						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
			</td>
		</tr>
	</table><!-- </FIELDSET> -->
	

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
