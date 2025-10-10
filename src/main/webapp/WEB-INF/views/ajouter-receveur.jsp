<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter un Receveur</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Ajouter un nouveau Receveur</h1>
    
    <p><a href="receveurs">Retour à la liste</a></p>
    <p><a href="home">Retour à l'accueil</a></p>
    
    <% String erreur = (String) request.getAttribute("erreur"); %>
    <% if (erreur != null && !erreur.isEmpty()) { %>
        <div style="color: red; border: 1px solid red; padding: 10px; margin: 10px 0;">
            <strong>Erreurs :</strong><br>
            <%= erreur %>
        </div>
    <% } %>
    
    <form method="post" action="receveurs">
        <table>
            <tr>
                <td><label for="nom">Nom :</label></td>
                <td><input type="text" id="nom" name="nom" required></td>
            </tr>
            <tr>
                <td><label for="prenom">Prénom :</label></td>
                <td><input type="text" id="prenom" name="prenom" required></td>
            </tr>
            <tr>
                <td><label for="telephone">Téléphone :</label></td>
                <td><input type="text" id="telephone" name="telephone" required></td>
            </tr>
            <tr>
                <td><label for="cin">CIN :</label></td>
                <td><input type="text" id="cin" name="cin" required></td>
            </tr>
            <tr>
                <td><label for="groupeSanguin">Groupe Sanguin :</label></td>
                <td>
                    <select id="groupeSanguin" name="groupeSanguin" required>
                        <option value="">Sélectionner</option>
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="priorite">Priorité :</label></td>
                <td>
                    <select id="priorite" name="priorite" required>
                        <option value="">Sélectionner</option>
                        <option value="NORMAL">Normal</option>
                        <option value="URGENT">Urgent</option>
                        <option value="CRITIQUE">Critique</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Enregistrer le receveur">
                    <input type="reset" value="Effacer">
                </td>
            </tr>
        </table>
    </form>
    
</body>
</html>
