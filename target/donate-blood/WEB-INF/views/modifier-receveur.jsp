<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Modifier un Receveur</title>
    <meta charset="UTF-8">
</head>
<body>
    <h1>Modifier le Receveur</h1>
    
    <p><a href="receveurs">Retour à la liste</a></p>
    <p><a href="home">Retour à l'accueil</a></p>
    
    <% String erreur = (String) request.getAttribute("erreur"); %>
    <% if (erreur != null && !erreur.isEmpty()) { %>
        <div style="color: red; border: 1px solid red; padding: 10px; margin: 10px 0;">
            <strong>Erreurs :</strong><br>
            <%= erreur %>
        </div>
    <% } %>
    
    <form method="post" action="modifier-receveur">
        <input type="hidden" name="id" value="${receveur.id}">
        <table>
            <tr>
                <td><label for="nom">Nom :</label></td>
                <td><input type="text" id="nom" name="nom" value="${receveur.nom}" required></td>
            </tr>
            <tr>
                <td><label for="prenom">Prénom :</label></td>
                <td><input type="text" id="prenom" name="prenom" value="${receveur.prenom}" required></td>
            </tr>
            <tr>
                <td><label for="telephone">Téléphone :</label></td>
                <td><input type="text" id="telephone" name="telephone" value="${receveur.telephone}" required></td>
            </tr>
            <tr>
                <td><label for="cin">CIN :</label></td>
                <td><input type="text" id="cin" name="cin" value="${receveur.cin}" required></td>
            </tr>
            <tr>
                <td><label for="groupeSanguin">Groupe Sanguin :</label></td>
                <td>
                    <select id="groupeSanguin" name="groupeSanguin" required>
                        <option value="${receveur.groupeSanguin}" selected>${receveur.groupeSanguin}</option>
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
                        <option value="${receveur.priorite}" selected>${receveur.priorite}</option>
                        <option value="NORMAL">Normal</option>
                        <option value="URGENT">Urgent</option>
                        <option value="CRITIQUE">Critique</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Mettre à jour le receveur">
                    <input type="reset" value="Réinitialiser">
                </td>
            </tr>
        </table>
    </form>
    
</body>
</html>


