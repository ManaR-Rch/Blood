<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Receveurs</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Liste des Receveurs</h1>
    
    <p><a href="ajouter-receveur.jsp">Ajouter un nouveau receveur</a></p>
    <p><a href="home">Retour à l'accueil</a></p>
    
    <h2>Receveurs enregistrés</h2>
    
    <% 
    List<Receveur> receveurs = (List<Receveur>) request.getAttribute("receveurs");
    if (receveurs != null && !receveurs.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>CIN</th>
                <th>Groupe Sanguin</th>
                <th>Priorité</th>
                <th>État</th>
                <th>Actions</th>
            </tr>
            <% for (Receveur receveur : receveurs) { %>
            <tr>
                <td><%= receveur.getId() %></td>
                <td><%= receveur.getNom() %></td>
                <td><%= receveur.getPrenom() %></td>
                <td><%= receveur.getTelephone() %></td>
                <td><%= receveur.getCin() %></td>
                <td><%= receveur.getGroupeSanguin() %></td>
                <td><%= receveur.getPriorite() %></td>
                <td><%= receveur.getEtat() %></td>
                <td>
                    <a href="modifier-receveur?id=<%= receveur.getId() %>">Modifier</a>
                    |
                    <a href="association">Associer</a>
                </td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Aucun receveur enregistré.</p>
    <% } %>
    
</body>
</html>
