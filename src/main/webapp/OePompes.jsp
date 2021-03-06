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
<TITLE>OePompes.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePompes" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" align="center" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;" width="681"><FORM name="formu" method="POST"><FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD height="26" width="126">
					<TABLE border="0" class="sigp2">
						<TR align="center">
							<TD width="122">La liste des pompes</TD>
						</TR>
					<TR align="center">
						<TD width="122">
						<TABLE border="1">
							<TBODY>
								<TR>
									<TD class="sigp2-titre-liste">Libellé          </TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
				</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="125" width="126">
						<SELECT size="5" name="<%= process.getNOM_LB_POMPES() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_POMPES(),process.getVAL_LB_POMPES_SELECT()) %>
				</SELECT><INPUT type="submit" name="<%//= process.getNOM_PB_OK()%>" value="OK" style="visibility : hidden;"></TD>
				</TR>
			</TABLE>
<BR>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_AJOUTER()%>" value="Ajouter" class="sigp2-Bouton-100"></TD>
<%if (process.getVide()>0){ %>
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
							<TD align="left">Pompes</TD>
							<TD class="sigp2-saisie" style="text-transform: uppercase">
							<INPUT type="text" size="30"
							name="<%= process.getNOM_EF_LIBELLE() %>" class="sigp2-saisie"
							value="<%= process.getVAL_EF_LIBELLE()%>" maxlength="30"
							style="text-transform: uppercase"></TD>
						</TR>
					<TR align="center">
						<TD align="left">Commentaire</TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><INPUT
							type="text" size="30"
							name="<%= process.getNOM_EF_COMMENTAIRE() %>"
							class="sigp2-saisie"
							value="<%= process.getVAL_EF_COMMENTAIRE()%>" maxlength="30"
							style="text-transform: uppercase"></TD>
					</TR>
					<TR align="center">
						<TD colspan="2"><%}else{ %></TD></TR>
					<TR align="center">
						<TD align="left">Pompes</TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_LIBELLE() %></TD>
					</TR>
					<TR align="center">
						<TD align="left">Commentaire</TD>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMMENTAIRE() %></TD>
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
<%} %>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
