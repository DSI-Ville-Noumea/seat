<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="nc.mairie.technique.BasicProcess"%>
<%@page import="nc.mairie.technique.VariableGlobale"%>
<%
BasicProcess processCourant = (BasicProcess)VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_PROCESS);
if (processCourant !=null && processCourant.getTransaction() != null && processCourant.getTransaction().isErreur()) { %>

<TR>
<TD align="center">
  <FIELDSET style="border-color : red red red red;">
  <TABLE border="" style="width : 100%">
  <TBODY>
    <TR>
	<%String message = processCourant.getTransaction().getMessageErreur();%>
        <TD width="30" class="sigp2-titre">
	   <% if (message.toUpperCase().startsWith("ERR")) { %>
		<IMG src="images/stop.gif" width="20" height="20" border="0">
	   <%} else { %>
		<IMG src="images/info.gif" width="20" height="20" border="0">
	   <%}%>
      </TD>
        <TD valign="middle" class="sigp2-titre"><%=message%></TD>
      </TR>
  </TBODY>
</TABLE>
  </FIELDSET>
  </TD>
</TR>
<%}%>
