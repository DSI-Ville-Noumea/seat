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
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OeModeles_ajout.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeModeles_ajout" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %><BR></SPAN>
	<FIELDSET>
				<TABLE border="0" class="sigp2">
					<tr>
						<td colspan="3" align="center"></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
							
		
							<table border="3" width="100%" bordercolor="#669999" cellpadding="2" cellspacing="1" align="center">
								<tr>
						<td bgcolor="#669999" class="sigp2-titre">Détails d'un
						modèle pour la marque <SPAN class="sigp2-titre"
							style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE() %></SPAN></td>
					</tr>
								<TR>
									<TD><!--Détails du BPC-->
										<TABLE class="sigp2">
											<TR>
												<TD colspan="3" align="right">Désignation du modèle :</TD>
												<TD colspan="2"><INPUT type="text" size="20"
										name="<%= process.getNOM_EF_DESIGNATION() %>"
										value="<%= process.getVAL_EF_DESIGNATION() %>"
										class="sigp2-saisie" style="text-transform: uppercase"> </TD>
							</TR>
											<tr>
												<td colspan="5" height="10"></td>
							</tr>
											<TR>
												<TD width="98">Version</TD>
												<TD style="text-transform: uppercase"><INPUT
									type="text" size="5" name="<%=process.getNOM_EF_VERSION() %>"
									value="<%=process.getVAL_EF_VERSION() %>" class="sigp2-saisie"
									style="height: 100%; text-transform: uppercase; "></TD>
												<TD width="20"></TD>
												<TD>  Type d'équipement</TD>
												<TD><SELECT name="<%=process.getNOM_LB_TYPEEQUIP() %>"
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%=process.getNOM_PB_OK_TYPEEQUIP()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_TYPEEQUIP(),process.getVAL_LB_TYPEEQUIP_SELECT()) %>
								</SELECT></TD>
							</TR>
											<TR>
												<TD width="98">Nombre pneu avant</TD>
												<TD><INPUT type="text" size="5"
									name="<%=process.getNOM_EF_NBPNEUAV() %>"
									value="<%=process.getVAL_EF_NBPNEUAV() %>" class="sigp2-saisie"
									style="height: 100%; "></TD>
												<TD></TD>
												<TD>Carburant</TD>
												<TD><SELECT class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									name="<%=process.getNOM_LB_CARBU() %>"
									onchange='executeBouton("<%=process.getNOM_PB_OK_CARBU()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_CARBU(),process.getVAL_LB_CARBU_SELECT()) %>
								</SELECT></TD>
							</TR>
											<TR>
												<TD width="98">Nombre de pneu arrière</TD>
												<TD style="text-transform: uppercase"><INPUT type="text"
									size="5" name="<%=process.getNOM_EF_NBPNEUAR() %>"
									value="<%=process.getVAL_EF_NBPNEUAR() %>" class="sigp2-saisie"></TD>
												<TD></TD>
												<TD>Nombre d'essieux</TD>
												<TD><INPUT name="<%= process.getNOM_EF_NBESSIEUX() %>" value="<%= process.getVAL_EF_NBESSIEUX() %>" type="text" size="5" class="sigp2-saisie"></TD>
							</TR>
											<TR>
												<TD width="98">Puissance fiscale</TD>
												<TD><INPUT type="text" size="5"
									name="<%= process.getNOM_EF_PUISSANCE() %>"
									value="<%= process.getVAL_EF_PUISSANCE() %>"
									class="sigp2-saisie"></TD>
												<TD></TD>
												<TD>Type de compteur</TD>
												<TD style="text-transform: uppercase"><SELECT
									class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									name="<%=process.getNOM_LB_COMPTEUR() %>"
									onchange='executeBouton("<%=process.getNOM_PB_OK_COMPTEUR()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_COMPTEUR(),process.getVAL_LB_COMPTEUR_SELECT()) %>
								</SELECT></TD>
							</TR>
							<TR>
								<TD width="98">Capacité du réservoir</TD>
								<TD><INPUT type="text" size="5"
									name="<%=process.getNOM_EF_RESERVOIR() %>"
									value="<%=process.getVAL_EF_RESERVOIR() %>"
									class="sigp2-saisie" maxlength="5"></TD>
								<TD></TD>
								<TD>Type de pneu</TD>
								<TD style="text-transform: uppercase"><SELECT
									name="<%=process.getNOM_LB_PNEU() %>" class="sigp2-liste"
									style="height: 100%; text-transform: uppercase; "
									onchange='executeBouton("<%=process.getNOM_PB_OK_PNEU()%>")'>
									<%= process.forComboHTML(process.getVAL_LB_PNEU(),process.getVAL_LB_PNEU_SELECT()) %>
								</SELECT></TD>
							</TR>
										</TABLE>
									</TD>
								</TR>
							</TABLE>		
			<INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_COMPTEUR() %>"
					style="visibility : hidden;"><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_PNEU() %>"
					style="visibility : hidden;"><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_CARBU() %>"
					style="visibility : hidden;"><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK_TYPEEQUIP() %>"
					style="visibility : hidden;"><br>

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
	</table></FIELDSET>
	

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
