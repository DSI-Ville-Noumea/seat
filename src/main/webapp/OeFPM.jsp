<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
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
<TITLE>OeOT.jsp</TITLE>
</HEAD>
<BODY background="images/fond.jpg" class="sigp2-BODY"><jsp:useBean class="nc.mairie.seat.process.OeFPM" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND>
<%if (null==process.getVAL_ST_TITRE_ACTION()||process.getVAL_ST_TITRE_ACTION().equals("")){ %>
<FIELDSET><!-- style="border-color : #555555;border-style : 'outset';" > -->
		<TABLE class="sigp2" border="0">
			<TR>
				<TD>Affichage des fiches d'entretiens</TD>
				<TD> </TD>
				<TD>
				<TABLE border="0" class="sigp2">
					<TR>
						<TD><INPUT type="radio"
							<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_VALIDE(),process.getVAL_RG_AFFICHAGE()) %>
							onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
						<TD>Validé</TD>
					</TR>
				</TABLE>
				</TD>
				<TD> </TD>
				<TD>
				<TABLE border="0" class="sigp2">
					<TR>
						<TD><INPUT type="radio"
							<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_ENCOURS(),process.getVAL_RG_AFFICHAGE()) %>
							onclick='executeBouton("<%= process.getNOM_PB_OK_TRI()%>")'></TD>
						<TD>En cours</TD>
					</TR>
				</TABLE>
				</TD>
			</TR>
		</TABLE>
</FIELDSET>
		<INPUT type="submit" value="OK"
			name="<%=process.getNOM_PB_OK_TRI() %>" style="visibility : hidden;"><INPUT
			type="submit" value="TRI" name="<%=process.getNOM_PB_TRI() %>"
			style="visibility : hidden;"><BR>
		<TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left">
				<TABLE class="sigp2" border="0">
					<TR>
						<TD height="29" width="66">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_NUMFICHE(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste" width="65">Fiche</TD>
							</TR>
						</TABLE>
						</TD>
						<TD height="29" width="59">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_NUMINV(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste" width="36">Inv.</TD>
							</TR>
						</TABLE>
						</TD>
						<TD height="29" width="116">
						<TABLE border="0" class="sigp2" class="sigp2-titre-liste">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_NUMSERIE(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste" width="92">Série</TD>
							</TR>
						</TABLE>
						</TD>
						<TD height="29" width="67">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_DENTREE(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste">Entrée</TD>
							</TR>
						</TABLE>
						</TD>
						<TD height="29" width="74">
						<TABLE border="0" class="sigp2" width="87">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_DSORTIE(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste" width="59">Sortie</TD>
							</TR>
						</TABLE>
						</TD>
						<!-- <TD height="29" width="87">Service d'affectation</TD>-->
					</TR>
				</TABLE>
				<!-- <TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°OT       Inv.  Immat.     Entrée     Sortie     Compteur        </TD>
						</TR>
					</TBODY>
				</TABLE>
 -->

</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="15" name="<%= process.getNOM_LB_FPM() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_FPM(),process.getVAL_LB_FPM_SELECT()) %>
				</SELECT>
					</TD>
				</TR>
			</TABLE>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<!-- <TD width="151"></TD> -->
					<TD width="151"><INPUT type="submit"
					name="<%= process.getNOM_PB_AJOUTER() %>" value="Ajouter"
					class="sigp2-Bouton-100"></TD>
				<TD width="151"><%if (process.getIsVide()>0){ %><INPUT type="submit"
					name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier"
					class="sigp2-Bouton-100"></TD>
				<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
				<%} %>
				</TR>
			</TABLE>
			<br>
	</FIELDSET>
<%}else{%><BR>
	<FIELDSET>
				<TABLE border="0" class="sigp2" width="90%">
					<tr>
						<td colspan="3" align="center" class="sigp2-titre"><%= process.getVAL_ST_TITRE_ACTION() %></td>
					</tr>
					<tr>
						<td height="10" colspan="3"></td>
					</tr>
					<tr>
						<td colspan="3">
								<TABLE border="1" class="sigp2">
									<tr>
										<td>N°Fiche</td>
										<td>Inventaire </td>
										<td>Entree</td>
										<td>Sortie</td>
					</tr>
									<tr>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NOOT() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_NOINVENT() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DENTREE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DSORTIE() %></td>
					</tr>
								</TABLE>
				
							<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="151" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137" align="left"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
								</TR>
							</TABLE>
						</td>
					</tr>
				</table>
				<br>
	</FIELDSET>
	<%} %>

		<INPUT name="JSP" type="hidden" value="<%= process.getJSP() %>"></FORM>
	</TD>
    </TR>
</TABLE>
</BODY>
</HTML>
