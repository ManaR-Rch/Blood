<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Donneur" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Associer un Donneur à un Receveur</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Associer un Donneur à un Receveur</h1>
    
    <p><a href="donneurs">Retour à la liste</a></p>
    <p><a href="home">Retour à l'accueil</a></p>
    
    <% String erreur = (String) request.getAttribute("erreur"); %>
    <% if (erreur != null && !erreur.isEmpty()) { %>
        <div style="color: red; border: 1px solid red; padding: 10px; margin: 10px 0;">
            <strong>Erreur :</strong><br>
            <%= erreur %>
        </div>
    <% } %>

    <% 
        List<Donneur> donneursDisponibles = (List<Donneur>) request.getAttribute("donneursDisponibles");
        List<Receveur> receveursEnAttente = (List<Receveur>) request.getAttribute("receveursEnAttente");
    %>

    <form method="post" action="associer">
        <table>
            <tr>
                <td><label for="donneurId">Donneurs disponibles et compatibles :</label></td>
                <td>
                    <select id="donneurId" name="donneurId" required>
                        <option value="">Sélectionner</option>
                        <% if (donneursDisponibles != null) { %>
                            <% for (Donneur d : donneursDisponibles) { %>
                                <option value="<%= d.getId() %>">
                                    <%= d.getNom() %> <%= d.getPrenom() %> - <%= d.getGroupeSanguin() %>
                                </option>
                            <% } %>
                        <% } %>
                    </select>
                    <% if (donneursDisponibles == null || donneursDisponibles.isEmpty()) { %>
                        <p style="color: orange;">Aucun donneur disponible pour association</p>
                    <% } %>
                </td>
            </tr>
            <tr>
                <td><label for="receveurId">Receveurs en attente et compatibles :</label></td>
                <td>
                    <select id="receveurId" name="receveurId" required>
                        <option value="">Sélectionner</option>
                        <% if (receveursEnAttente != null) { %>
                            <% for (Receveur r : receveursEnAttente) { %>
                                <option value="<%= r.getId() %>">
                                    <%= r.getNom() %> <%= r.getPrenom() %> - <%= r.getGroupeSanguin() %> - <%= r.getPriorite() %>
                                </option>
                            <% } %>
                        <% } %>
                    </select>
                    <% if (receveursEnAttente == null || receveursEnAttente.isEmpty()) { %>
                        <p style="color: orange;">Aucun receveur en attente</p>
                    <% } %>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Associer">
                </td>
            </tr>
        </table>
    </form>

</body>
</html>


