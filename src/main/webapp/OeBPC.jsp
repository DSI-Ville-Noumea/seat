<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="theme/sigp2.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="js/GestionCalendrier.js"></SCRIPT>
<SCRIPT type="text/javascript" src="js/GestionBoutonDroit.js"></SCRIPT>
<TITLE>OeBPC.jsp</TITLE>
</HEAD>
<BODY BGPROPERTIES="FIXED" background="images/fond.jpg" class="sigp2-BODY"><jsp:useBean class="nc.mairie.seat.process.OeBPC" id="process" scope="session"></jsp:useBean>
<TABLE border="0" width="580" style="text-align : center;" class="sigp2">
<%@ include file="BanniereErreur.jsp" %>
    <TR>
      <TD style="text-align : center;">
			<FORM name="formu" method="POST"><SPAN class="sigp2-titre"><%if ( "".equals(process.getVAL_ST_TITRE_ACTION()) ) { %><INPUT
			type="submit" value="OK" name="<%=process.getNOM_PB_OK() %>"
			style="visibility : hidden;">
		<BR>
		</SPAN>
		<FIELDSET><TABLE border="0" class="sigp2">
			<TR>
				<TD class="sigp2-titre">Equipement </TD>
				<TD class="sigp2-titre"></TD>
				<TD class="sigp2-titre"><INPUT type="text"
					name="<%=process.getNOM_EF_EQUIP() %>"
					value="<%=process.getVAL_EF_EQUIP() %>" class="sigp2-saisie"
					size="10"></TD>
				<TD class="sigp2-titre"><INPUT type="submit"
					name="<%= process.getNOM_PB_EQUIP() %>" value="Rechercher"
					class="sigp2-Bouton-100"></TD>

				<TD><input type="image" border="0" src="images/jumelle.gif" width="21" height="17" name="<%=process.getNOM_PB_RECHERCHE() %>"></td>
				<TD class="sigp2-titre" width="35" align="right">PM</TD>
				<TD><INPUT type="image" border="0" src="images/jumelle.gif"
					width="21" height="17" alt="Sélection d'un petit matériel"
					name="<%=process.getNOM_PB_SEL_PM() %>"></TD>
			</TR>
		</TABLE>
<TABLE border="3" bordercolor="#669999" cellpadding="2" cellspacing="2">
			<TBODY>
				<TR>
					<TD colspan="6" bgcolor="#669999" class="sigp2-titre">Infos concernant l'équipement</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
						<TBODY>
							<TR>
								<TD><B>N° d'inventaire</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_INVENTAIRE() %></TD>
								<TD width="10"></TD>
								<TD><B>N° d'immatriculation</B></TD>
								<TD width="10" style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisiemajuscule" style="text-transform: uppercase"><%=process.getVAL_ST_IMMAT() %></TD>

							</TR>
							<TR>
								<TD>Nom d'équipement </TD>
								<TD style="text-transform: uppercase"></TD>
								<TD class="sigp2-saisie" style="text-transform: uppercase"><%= process.getVAL_ST_NOMEQUIP()%></TD>
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
		</TABLE>
</FIELDSET>

		<FIELDSET><LEGEND class="sigp2Fieldset" align="left"></LEGEND><TABLE border="1" class="sigp2">
				<TR align="center">
					<TD align="left"><TABLE width="100%" border="1">
					<TBODY>
						<TR>
							<TD class="sigp2-titre-liste">N°BPC         Date     Compteur  Quanti Km
							parcouru  Moyenne   </TD>
						</TR>
					</TBODY>
				</TABLE>

</TD>
				</TR>
				<TR>
					<TD align="left">
						<SELECT size="12" name="<%= process.getNOM_LB_BPC() %>"
					class="sigp2-liste" style="text-transform: uppercase; width: 100%">
					<%= process.forComboHTML(process.getVAL_LB_BPC(),process.getVAL_LB_BPC_SELECT()) %>
				</SELECT></TD>
				</TR>
			</TABLE>
	<%if (!"".equals(process.getVAL_ST_INVENTAIRE())){ %>
			<TABLE border="0" cellspacing="0" cellpadding="0" class="sigp2">
				<TR>
					<TD width="151"><INPUT type="submit" name="<%=process.getNOM_PB_AJOUTER() %>" value="Ajouter" class="sigp2-Bouton-100"></TD>
					<%if (process.getIsVide()>0){ %>
					<TD width="151"><INPUT type="submit" name="<%= process.getNOM_PB_MODIFIER() %>" value="Modifier" class="sigp2-Bouton-100"></TD>
					<TD width="49"><INPUT type="submit" name="<%= process.getNOM_PB_SUPPRIMER() %>" value="Supprimer" class="sigp2-Bouton-100"></TD>
<%} %>
				</TR>
			</TABLE>
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
						<td colspan="3">
								<TABLE border="1" class="sigp2">
									<tr>
										<td>N°BPC</td>
										<td>Date </td>
										<td>Heure </td>
										<td>Compteur</td>
										<td>Quantité</td>
										<td>N° de pompe</td>
										<td>Carburat</td>
										<!-- <TD>Mode de prise</TD> -->
					</tr>
									<tr>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_BPC() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_DATE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_HEURE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_COMPTEUR() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_QTE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_POMPE() %></td>
										<td class="sigp2-saisie" style="text-transform: uppercase"><%=process.getVAL_ST_CARBU() %></td>
										 <!--<TD class="sigp2-saisie" style="text-transform: uppercase"><%//=process.getVAL_ST_MODEPRISE() %></TD> -->
					</tr>
								</TABLE>
				
							<br>
							<TABLE border="0" cellpadding="0" cellspacing="0" class="sigp2" align="center">
								<TR align="center">
									<TD width="140"><INPUT type="submit" name="<%= process.getNOM_PB_VALIDER() %>" value="Valider" class="sigp2-Bouton-100"></TD>
									<TD width="137"><INPUT type="submit" name="<%= process.getNOM_PB_ANNULER() %>" value="Annuler" class="sigp2-Bouton-100"></TD>
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
