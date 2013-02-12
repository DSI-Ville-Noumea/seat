<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
if (document.formu.elements[nom] != null)
	document.formu.elements[nom].focus();
}

</SCRIPT>
<TITLE>OePePerso.jsp</TITLE>
</HEAD>
<jsp:useBean class="nc.mairie.seat.process.OePePerso" id="process" scope="session"></jsp:useBean>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY" onload="return setfocus('<%=process.getFocus() %>')">
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST">
		<FIELDSET>
		<TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Rech Equipement</TD>
				<TD></TD>
				<TD class="sigp2-titre"><INPUT type="text" name="<%=process.getNOM_EF_EQUIP() %>" value="<%=process.getVAL_EF_EQUIP() %>" class="sigp2-saisie" size="10"></TD>
				<TD><INPUT type="submit" name="<%= process.getNOM_PB_EQUIP() %>"
					value="Rechercher" class="sigp2-Bouton-100"></TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" name="<%=process.getNOM_PB_RECHERCHEEQUIP() %>"><INPUT
					type="submit" value="OK" name="<%=process.getNOM_PB_TRI() %>"
					style="visibility : hidden;"></TD>
			</TR>
		</TABLE>
		</FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="3" bordercolor="#669999" cellpadding="2"
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
								<TD class="sigp2-saisiemajuscule"><%= process.getVAL_ST_NOINVENT()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD class="sigp2-saisie" width="10"></TD>
								<TD><B>N°immatriculation</B></TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisiemajuscule"><%= process.getVAL_ST_NOIMMAT()%></TD>
								<TD class="sigp2-saisiemajuscule"></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement</TD>
								<TD width="10"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOMEQUIP()%></TD>
								<TD class="sigp2-saisie"></TD>
								<TD class="sigp2-saisie"></TD>
								<td colspan="4">
									<table border="0" class="sigp2" cellpadding="0" cellspacing="0">
										<tr>
												<TD>Type</TD>
												<TD width="10"></TD>
												<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TYPE() %></TD>
												<TD width="10"></TD>
												<td>Version</td>
												<td width="10"></td>
<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_VERSION() %></td>
										</tr>
									</table>
								</td>
								
							</TR>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<%if ( "".equals(process.getVAL_ST_TITRE_ACTION()) ) { %><TABLE class="sigp2" border="0">
			<TR>
				<TD>Affichage des entretiens</TD>
				<TD><INPUT type="radio"
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_FAIT(),process.getVAL_RG_AFFICHAGE()) %>
					onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
				<TD>Faits</TD>
				<TD><INPUT type="radio"
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_AFAIRE(),process.getVAL_RG_AFFICHAGE()) %>
					onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
				<TD>A faire </TD>
				<TD><INPUT type="radio"
					<%=process.forRadioHTML(process.getNOM_RG_AFFICHAGE(),process.getNOM_RB_AFFICHAGE_TOUS(),process.getVAL_RG_AFFICHAGE()) %>
					onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
				<TD>Tous</TD>
			</TR>
		</TABLE>
		
		<TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left">
				<TABLE class="sigp2" border="1">
					<TR>
						<TD width="209">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_ENT(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste">Entretien</TD>
							</TR>
						</TABLE>
						</TD>
						<TD height="29" width="69">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_PREVU(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste">Prévu</TD>
							</TR>
						</TABLE>
						</TD>
						<TD width="76">
						<TABLE border="0" class="sigp2">
							<TR>
								<TD><INPUT type="radio"
									<%=process.forRadioHTML(process.getNOM_RG_TRI(),process.getNOM_RB_TRI_REAL(),process.getVAL_RG_TRI()) %>
									onclick='executeBouton("<%= process.getNOM_PB_TRI()%>")'></TD>
								<TD class="sigp2-titre-liste">Fait</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="sigp2-titre-liste">
						Durée
						</TD>
						<TD class="sigp2-titre-liste" width="100">Intervalle
						
						
						
						
						
						
						
						
						
						
						
						
						</TD>
					</TR>
				</TABLE>
</TD>
				</TR>
				<TR>
					<TD align="left"><SELECT size="8" name="<%= process.getNOM_LB_PE() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_PE(),process.getVAL_LB_PE_SELECT()) %>
				</SELECT>
				</TR>
			</TABLE>
	<%if (!"".equals(process.getVAL_ST_NOINVENT())){ %>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit"
					name="<%=process.getNOM_PB_AJOUTER() %>" value="Ajouter"
					class="sigp2-Bouton-100"><%if (process.getIsVide()>0){ %></TD>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"><%} %></TD>
				</TR>
			
		</TABLE>
<FIELDSET>
	<table>
		<TR>
			<td></td>
			<TD width="155"align="center">
			<%if(process.isDetails()){ %>
			<INPUT type="submit"
				name="<%=process.getNOM_PB_RETOUROT() %>" value="Annuler" class="sigp2-Bouton-100">
			<%}else{ %>
			<INPUT type="submit"
				name="<%=process.getNOM_PB_ACCES_OT() %>" value="Gestion des OT" class="sigp2-Bouton-100">
			<%} %>
			</TD>
			<td></td>
		</TR>
	</table>
</FIELDSET>
	<%} %>
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
						<td colspan="3" align="center">
								<TABLE border="1" class="sigp2">
									<tr>
										<td>Entretien</td>
										<td>Durée </td>
										<td>Intervalle</td>
										<td>Type d'intervalle</td>
						<TD>Commentaire</TD>
					</tr>
									<tr>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_ENTRETIEN() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DUREE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_INTERVALLE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_TINT() %></td>
						<TD class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMMENTAIRE() %></TD>
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
