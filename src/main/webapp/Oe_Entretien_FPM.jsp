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
<TITLE>OeEntretiens_OT.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeEntretiens_OT" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION() %> d'un entretien pour la fiche d'entretien du petit matériel <%=process.getVAL_ST_NOOT() %><BR></SPAN>
		<TABLE border="3" bordercolor="#669999" cellpadding="2"
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
								<TD class="sigp2-saisie"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD width="5"></TD>
								<TD><B>N° de série</B></TD>
								<TD class="sigp2-saisie"><%= process.getVAL_ST_NOIMMAT()%></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_MARQUE()%> <%=process.getVAL_ST_MODELE() %></TD>
								<TD class="sigp2-saisie"></TD>
								<TD>Type</TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<INPUT
			type="submit" value="OK" name="<%=process.getNOM_PB_OK_ENT() %>"
			style="visibility : hidden;"><BR>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Entretiens</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left" height="35">
<%if(!process.suppresion){ %>
				<TABLE class="sigp2">
					<TR>
						<TD>Entretien :</TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><SELECT
							size="1" name="<%=process.getNOM_LB_ENTRETIENS() %>"
							class="sigp2-liste" style="text-transform: uppercase"
							onchange="executeBouton('<%=process.getNOM_PB_OK_ENT() %>')">
							<%= process.forComboHTML(process.getVAL_LB_ENTRETIENS(),process.getVAL_LB_ENTRETIENS_SELECT()) %>
						</SELECT></TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
						<TD>Type d'entretien</TD>
						<TD><SELECT size="1" name="<%=process.getNOM_LB_TENT() %>"
							class="sigp2-liste" style="text-transform: uppercase"
							onchange='executeBouton("<%= process.getNOM_PB_OK_TENT()%>")'>
							<%= process.forComboHTML(process.getVAL_LB_TENT(),process.getVAL_LB_TENT_SELECT()) %>
						</SELECT></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD>Durée</TD>
						<TD><INPUT type="text" size="20"
							name="<%=process.getNOM_EF_DUREE() %>"
							value="<%=process.getVAL_EF_DUREE() %>" class="sigp2-saisie"
							style="text-transform: uppercase"></TD>
						<TD></TD>
						<TD>Sinistre</TD>
						<TD><INPUT type="submit" name="<%=process.getNOM_PB_OK_TENT() %>"
							style="visibility : hidden;" value="ok"><INPUT type="checkbox"
							<%= process.forCheckBoxHTML(process.getNOM_CK_SINISTRE() , process.getVAL_CK_SINISTRE()) %>></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD>Prévu le</TD>
						<TD>
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="text" size="8"
									name="<%=process.getNOM_EF_DPREVU() %>"
									value="<%=process.getVAL_EF_DPREVU() %>" class="sigp2-saisie"
									style="text-transform: uppercase"></TD>
											<TD><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DPREVU()%>', 'dd/mm/y');"></TD>
										</TR>
									</TABLE></TD>
						<TD></TD>
						<TD>Réalisé le </TD>
						<TD>
									<TABLE border="0" class="sigp2">
										<TR>
											<TD><INPUT type="text" size="8"
									name="<%=process.getNOM_EF_DREAL() %>"
									value="<%=process.getVAL_EF_DREAL() %>" class="sigp2-saisie"
									style="text-transform: uppercase"></TD>
											<TD><IMG src="images/calendrier.gif"
									onclick="return showCalendar('<%=process.getNOM_EF_DREAL()%>', 'dd/mm/y');"></TD>
										</TR>
									</TABLE></TD>
						<TD></TD>
					</TR>
					<TR>
						<TD>Commentaire</TD>
						<TD colspan="4"><TEXTAREA rows="4" cols="50"
							name="<%=process.getNOM_EF_COMMENTAIRE() %>" class="sigp2-saisie"
							style="text-transform: uppercase"><%=process.getVAL_EF_COMMENTAIRE() %></TEXTAREA></TD>
						<TD></TD>
					</TR>
				</TABLE>
<%}else{ %>
				<table class="sigp2" border = "1">
					<tr>
						<td>Entretien</td>
						<td>Prévu </td>
						<td>Réalisé</td>
						<td>Durée</td>
					</tr>
					<tr>
						<td style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_ENTRETIEN() %></td>
						<td style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_DPREVU() %> </td>
						<td style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_DREAL() %></td>
						<td style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_DUREE() %></td>
					</tr>
				</table>
<%} %>
				</TD>
				</TR>
				<TR>
					<TD align="left">
				</TR>
				<tr>
					<td align="center">
<table>
	<tr>
	<td width="151"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>"
							value="Valider" class="sigp2-Bouton-100"></td>
<td><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>"
							value="Annuler" class="sigp2-Bouton-100"></td>
</tr>
</table></td>
				</tr>
			</TABLE>
				
			</FIELDSET>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
