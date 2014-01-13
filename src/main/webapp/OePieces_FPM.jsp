<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" href="theme/calendrier-mairie.css" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
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
<TITLE>OePieces_FPM.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePieces_FPM" id="process" scope="session"></jsp:useBean>
<BODY background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">

<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%=process.getVAL_ST_TITRE_ACTION() %> d'une pièce pour l'OT <%=process.getVAL_ST_NOOT() %><BR>
		<BR></SPAN>
<%if(!process.isSuppresion() ){%>
			<FIELDSET>
			<TABLE border="0" class="sigp2">
				<tr>
					<td>Libelle de la pièce </td>
					<td><INPUT type="text" size="20" name="<%=process.getNOM_EF_LIBELLE() %>" value="<%=process.getVAL_EF_LIBELLE() %>" class="sigp2-saisie" style="text-transform: uppercase"></td>
<%if(!process.isModif()){ %>
					<td><INPUT type="submit" value="Rechercher" name="<%=process.getNOM_PB_OK_PIECES() %>" class="sigp2-Bouton-100"><INPUT
		type="submit" value="OK" name="<%=process.getNOM_PB_OK_PIECES() %>"
		style="visibility : hidden;"></td>
<%} %>
				</tr>
			</TABLE></FIELDSET><BR>
		<FIELDSET>
		
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
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD width="10"></TD>
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
							<TR>
								<TD height="15"></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie"></TD>
								<TD></TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"></TD>
							</TR>

							<TR>
								<TD>Service</TD>
								<TD></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"
									colspan="5"><%= process.getVAL_ST_SERVICE()%></TD>
							</TR>

						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</FIELDSET>
<br>
			<TABLE class="sigp2" border="0">
				<TR>
					<TD valign="top"><!-- Liste des OT en cours -->
<%if(!process.isModif()){ %>
		<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Pièces</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left">
				<TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Pièces               PU     </TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="5" name="<%= process.getNOM_LB_PIECES() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%"
					ondblclick='executeBouton("<%= process.getNOM_PB_AJOUTER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_PIECES(),process.getVAL_LB_PIECES_SELECT()) %>
				</SELECT>
				</TR>
				<tr>
					<td align="center"><INPUT type="submit" value="Création"
							name="<%=process.getNOM_PB_CREER_PIECES() %>" class="sigp2-Bouton-100"><INPUT
							type="submit" value="Ajouter"
							name="<%=process.getNOM_PB_AJOUTER() %>"
							class="sigp2-Bouton-100"></td>
				</tr>
			</TABLE>
			</FIELDSET>
				</TD>
				<TD valign="top"><!-- Liste des OT à valider -->

			<FIELDSET><LEGEND class="sigp2Fieldset" align="left">Pièces pour l'OT</LEGEND><TABLE border="0" class="sigp2">
				<TR align="center">
					<TD align="left">
				<TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">Pièces               PU      Qté   Sortie     </TD>
						</TR>
					</TBODY>
				</TABLE>
				</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="5" name="<%= process.getNOM_LB_PIECES_OT() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%" ondblclick='executeBouton("<%= process.getNOM_PB_ENLEVER()%>")'>
					<%= process.forComboHTML(process.getVAL_LB_PIECES_OT(),process.getVAL_LB_PIECES_OT_SELECT()) %>
				</SELECT>
				</TR>
				<TR>
					<TD align="center"><%if(process.getListePiecesFPM().size()>0){ %><INPUT
							type="submit" value="Enlever"
							name="<%=process.getNOM_PB_ENLEVER() %>" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE>
			</FIELDSET>
<%} %>
					</TD>
				</TR>
			</TABLE>

<%if(process.afficheQuantite){ %>
			<TABLE class="sigp2">
				<tr>
					<td>Quantité : </td>
					<td><INPUT type="text" size="20" style="text-transform: uppercase" class="sigp2-saisie" name="<%=process.getNOM_EF_QUANTITE() %>" value="<%=process.getVAL_EF_QUANTITE() %>"> </td>
					<td></td>
				<TD rowspan="2"><INPUT type="submit" value="OK"
					name="<%=process.getNOM_PB_OK() %>" class="sigp2-Bouton-100"></TD>
			</tr>
				<tr>
					<td>Date de sortie : </td>
					<td><INPUT type="text" size="20" style="text-transform: uppercase"
					class="sigp2-saisie" name="<%=process.getNOM_EF_DATESORTIE() %>"
					value="<%=process.getVAL_EF_DATESORTIE() %>"></td>
					<td><IMG src="images/calendrier.gif"
					onclick="return showCalendar('<%=process.getNOM_EF_DATESORTIE()%>', 'dd/mm/y');"></td></tr>
<%if(process.isModif()){ %>
			<TR>
				<TD>Prix : </TD>
				<TD><INPUT type="text" size="20" style="text-transform: uppercase"
					class="sigp2-saisie" name="<%=process.getNOM_EF_PRIX() %>"
					value="<%=process.getVAL_EF_PRIX() %>"></TD>
				<TD></TD>
				<TD></TD>
			</TR>
<%} %>
		</TABLE>
<%} %>

<%}else{ %>
			<FIELDSET>
				<TABLE border="1" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
						<TD width="140">Pièce</TD>
						<TD width="140">PU</TD>
						<TD width="140">Quantité</TD>
						<TD width="140">Date de sortie</TD>
					</TR>
					<TR align="center">
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_PIECE() %></TD>
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_PU() %></TD>
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_QTE() %></TD>
						<TD width="140" style="text-transform: uppercase" class="sigp2-saisie"><%=process.getVAL_ST_DSORTIE() %></TD>
					</TR>
				</TABLE>
		</FIELDSET><br>

<%} %>
			<FIELDSET>
				<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
					<TR align="center">
<%if(process.isAction()){ %>
						<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
<%} %>
						<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
					</TR>
				</TABLE>
		</FIELDSET>
		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
