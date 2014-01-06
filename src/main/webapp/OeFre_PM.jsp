<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
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
<TITLE>OeFre_PM.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OeFre_PM" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" align="center" class="sigp2" width="580">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;"><FORM name="formu" method="POST"><FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD height="26" width="126">
					<TABLE border="0" class="sigp2">
						<TR align="center">
							<TD>
						<TABLE class="sigp2" border="1" cellspacing="1">
							<TR>
								<TD class="sigp2-titre-liste">Nom&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Contact&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>

							</TR>
						</TABLE></TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD>
						<SELECT size="5" name="<%= process.getNOM_LB_FOURNISSEURS() %>"
					class="sigp2-liste" onclick='executeBouton("<%= process.getNOM_PB_OK()%>")' style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_FOURNISSEURS(),process.getVAL_LB_FOURNISSEURS_SELECT()) %>
				</SELECT><INPUT type="submit" name="<%= process.getNOM_PB_OK()%>" value="OK" style="visibility : hidden;"></TD>
				</TR>
			<TR>
				<TD>Observations : </TD>
			</TR>
<tr>
				<td colspan="3" align="left" class="sigp2-saisie"
					style="text-transform: uppercase; font-weight: bold"><%=process.getVAL_ST_OBSERVATIONS() %>
				</td>
			</tr>
		</TABLE>
<BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_AJOUTER()%>" value="Ajouter" class="sigp2-Bouton-100"></TD>
<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE><br>
</FIELDSET><br>
<%if (! "".equals(process.getVAL_ST_TITRE_ACTION()) ) {%>
<FIELDSET>
<BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<tr>
					<td class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %></td>
				</tr>
				<tr>
					<td height="10"><%if ("".equals(process.getVAL_ST_LIBELLE())){ %></td>
				</tr>
				<TR>
					<TD height="26">
					<TABLE border="0" class="sigp2">
						<TR align="center">
							<TD align="left">Nom :</TD>
							<TD class="sigp2-saisie" style="text-transform: uppercase; font-variant: normal">
								<INPUT type="text" size="30" name="<%= process.getNOM_EF_LIBELLE() %>"
								class="sigp2-saisie" value="<%= process.getVAL_EF_LIBELLE()%>" maxlength="30" style="text-transform: uppercase; font-variant: normal" onselect="return func_1(this, event);">
							</TD>
						</TR>
					<TR align="center">
						<TD align="left">Observation</TD>
						<TD class="sigp2-saisie"
							style="text-transform: uppercase; font-variant: normal">
<TEXTAREA rows="4" cols="50"
									name="<%=process.getNOM_EF_OBSERVATIONS() %>"
									class="sigp2-saisie"
									style="text-transform: uppercase; width: 100%;font-variant: normal" onselect="return func_1(this, event);">
<%=process.getVAL_EF_OBSERVATIONS() %>
</TEXTAREA>
</TD>
					</TR>
					<TR align="center">
						<TD align="left">contact</TD>
						<TD class="sigp2-saisie"
							style="text-transform: uppercase; font-variant: normal"><INPUT
							type="text" size="30" name="<%= process.getNOM_EF_CONTACT() %>"
							class="sigp2-saisie" value="<%= process.getVAL_EF_CONTACT()%>"
							maxlength="30"
							style="text-transform: uppercase; font-variant: normal"
							onselect="return func_1(this, event);"></TD>
					</TR>
					<TR align="center">
						<TD colspan="2"><%}else{ %></TD></TR>
					<TR align="center">
						<TD align="left">Nom</TD>
						<TD class="sigp2-saisie"
							style="text-transform: uppercase; font-variant: normal" align="left"><%=process.getVAL_ST_LIBELLE() %></TD>
					</TR>
					<TR align="center">
						<TD align="left">Observations</TD>
						<TD class="sigp2-saisie"
							style="text-transform: uppercase; font-variant: normal" align="left">
							<%=process.getVAL_ST_OBSERVATIONS() %></TD>
					</TR>
					<TR align="center">
						<TD align="left">Contact</TD>
						<TD class="sigp2-saisie"
							style="text-transform: uppercase; font-variant: normal" align="left"><%=process.getVAL_ST_CONTACT() %></TD>
					</TR>
					<TR align="center">
						<TD colspan="2"><%} %></TD></TR>
				</TABLE>
					</TD>
				</TR>
			</TABLE>
			<BR>
			<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2">
				<TR align="center">
					<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
					<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
				</TR>
			</TABLE><br></FIELDSET>
<%}%>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
