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
<TITLE>OeAgentsATM.jsp</TITLE>
</HEAD>
<BODY background="images/fond.jpg" class="sigp2-BODY">
<jsp:useBean class="nc.mairie.seat.process.OeAgentsATM" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST">
<%if(!process.isModif()){ %>
				<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Agents de l'ATM</LEGEND>
					<TABLE border="0" class="sigp2">
							<TR align="center">
								<TD align="left" height="35">
							<TABLE width="100%" border="1">
								<TBODY>
									<TR>
										<TD class="sigp2-titre-liste">Nom                       Prénom               </TD>
									</TR>
								</TBODY>
							</TABLE>
							</TD>
							</TR>
							<TR>
								<TD align="left">
									<SELECT size="8"
								name="<%= process.getNOM_LB_AGENTS() %>"
								ondblclick='executeBouton("<%= process.getNOM_PB_AJOUTER()%>")'
								class="sigp2-liste" style="text-transform: uppercase; width: 100%">
								
								<%= process.forComboHTML(process.getVAL_LB_AGENTS(),process.getVAL_LB_AGENTS_SELECT()) %>
			
							</SELECT></TD>
							</TR>
<%if (!process.isVide()){ %>
							<tr>
								<td align="center"><INPUT type="submit" value="Ajouter" name="<%=process.getNOM_PB_AJOUTER() %>" class="sigp2-Bouton-100"></td>
							</tr>
						</TABLE></FIELDSET>
<%} %>
<%} %>
<%if(process.afficheSpe){ %>
		
		<TABLE border="0" class="sigp2" width="294">
			<tr>
				<TD colspan="2" class="sigp2-titre" align="center"><%=process.getVAL_ST_TITRE_ACTION() %></TD>
			</tr>
			<tr>
				<TD>Agent :</TD>
				<td style="text-transform: uppercase" class="sigp2-saisie"
					width="196"><%=process.getVAL_ST_AGENT() %></TD>
			</tr>
			<tr>
				<td>Spécialité :</TD>
				<TD width="196" style="text-transform: uppercase" class="sigp2-saisie"><%if(!process.isSuppression){ %> <SELECT
					name="<%=process.getNOM_LB_SPE() %>" class="sigp2-liste"
					style="height: 100%; text-transform: uppercase;">
					<%= process.forComboHTML(process.getVAL_LB_SPE(),process.getVAL_LB_SPE_SELECT()) %>
				</SELECT> <%}else {%> <%=process.getVAL_ST_SPE() %> <%} %>
				</td>
			</tr>
			<TR>
				<TD align="left" width="151"><INPUT type="submit" value="Valider"
					name="<%=process.getNOM_PB_OK() %>" class="sigp2-Bouton-100"></TD>
				<TD align="left"><INPUT type="submit" value="Annuler"
					name="<%=process.getNOM_PB_ANNULER() %>" class="sigp2-Bouton-100"></TD>
			</TR>
		</TABLE>
		<%} %>
<%if(null==process.getVAL_ST_TITRE_ACTION()||process.getVAL_ST_TITRE_ACTION().equals("")){ %>
<fieldset><LEGEND class="sigp2Fieldset" align="left">Agents
				mécaniciens</LEGEND>
				<TABLE class="sigp2" border="0">
					<tr>
						<td colspan="2">
						<TABLE width="100%" border="1">
							<TBODY>
								<TR>
									<TD class="sigp2-titre-liste">Nom                       Prénom               Specialité                          </TD>
								</TR>
							</TBODY>
						</TABLE>
						</td>
					</tr>
					<tr>
						<td valign="top" colspan="2"><SELECT size="7"
							name="<%= process.getNOM_LB_MECANICIEN() %>"
							class="sigp2-liste"
							style="text-transform: uppercase; width: 100%">
							<%= process.forComboHTML(process.getVAL_LB_MECANICIEN(),process.getVAL_LB_MECANICIEN_SELECT()) %>
						</SELECT></td>
					</tr>
					<%if (!process.isVideMeca){ %>
					<tr>
						<td align="left" width="151"><INPUT type="submit" value="Modifier"
							name="<%=process.getNOM_PB_MODIFIER() %>" class="sigp2-Bouton-100"></td>
						<td align="left" width="151"><INPUT type="submit" value="Enlever"
							name="<%=process.getNOM_PB_ENLEVER() %>" class="sigp2-Bouton-100"></td>
					</tr>
					<%} %>
				</TABLE>
				</fieldset>

<%} %>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
