<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Donneur" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Donneurs</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Liste des Donneurs</h1>
    
    <p><a href="ajouter-donneur">Ajouter un nouveau donneur</a></p>
    <p><a href="home">Retour à l'accueil</a></p>
    
    <h2>Donneurs enregistrés</h2>
    
    <% 
    List<Donneur> donneurs = (List<Donneur>) request.getAttribute("donneurs");
    if (donneurs != null && !donneurs.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>CIN</th>
                <th>Groupe Sanguin</th>
                <th>Poids</th>
                <th>Sexe</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
            <% for (Donneur donneur : donneurs) { %>
            <tr>
                <td><%= donneur.getId() %></td>
                <td><%= donneur.getNom() %></td>
                <td><%= donneur.getPrenom() %></td>
                <td><%= donneur.getTelephone() %></td>
                <td><%= donneur.getCin() %></td>
                <td><%= donneur.getGroupeSanguin() %></td>
                <td><%= donneur.getPoids() %> kg</td>
                <td><%= donneur.getSexe() %></td>
                <td><%= donneur.getStatutDisponibilite() %></td>
                <td>
                    <a href="modifier-donneur?id=<%= donneur.getId() %>">Modifier</a>
                    |
                    <a href="association">Associer</a>
                </td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Aucun donneur enregistré.</p>
    <% } %>
    
</body>
</html>
