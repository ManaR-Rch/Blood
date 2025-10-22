<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des Receveurs</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .bg-blood-red { background-color: #dc2626; }
        .text-blood-red { color: #dc2626; }
        .priority-CRITIQUE { background-color: #fee2e2; color: #dc2626; font-weight: 600; }
        .priority-URGENT { background-color: #fffbeb; color: #f59e0b; font-weight: 600; }
        .priority-NORMAL { background-color: #eff6ff; color: #2563eb; }
        .etat-SATISFAIT { color: #22c55e; font-weight: 600; }
        .etat-EN_ATTENTE { color: #f97316; font-weight: 600; }
    </style>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-7xl mx-auto bg-white p-8 rounded-xl shadow-2xl">
        <h1 class="text-3xl font-bold text-blood-red border-b pb-4 mb-6">
            Liste des Receveurs
        </h1>
        
        <div class="mb-6 flex space-x-4">
            <a href="ajouter-receveur" class="py-2 px-4 bg-green-500 text-white font-semibold rounded-lg shadow-md hover:bg-green-600 transition duration-300">
                Ajouter un nouveau receveur
            </a>
            <a href="home" class="py-2 px-4 bg-gray-600 text-white font-semibold rounded-lg shadow-md hover:bg-gray-700 transition duration-300">
                Retour à l'accueil
            </a>
        </div>
        
        <h2 class="text-2xl font-semibold text-gray-700 mb-2">Receveurs enregistrés</h2>
        <p class="text-sm text-gray-500 mb-6"><em>Tri automatique : CRITIQUE → URGENT → NORMAL</em></p>
        
        <% 
        List<Receveur> receveurs = (List<Receveur>) request.getAttribute("receveurs");
        if (receveurs != null && !receveurs.isEmpty()) {
        %>
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200 shadow-md rounded-lg">
                    <thead class="bg-blood-red">
                        <tr>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider rounded-tl-lg">ID</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Nom & Prénom</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Téléphone / CIN</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Groupe Sanguin</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Priorité</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">État</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Nb Donneurs</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider rounded-tr-lg">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <% for (Receveur receveur : receveurs) { %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900"><%= receveur.getId() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= receveur.getNom() %> <%= receveur.getPrenom() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= receveur.getTelephone() %><br><span class="text-xs text-gray-500"><%= receveur.getCin() %></span></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-bold text-blood-red"><%= receveur.getGroupeSanguin() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm priority-<%= receveur.getPriorite() %>"><%= receveur.getPriorite() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm etat-<%= receveur.getEtat() %>"><%= receveur.getEtat() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= (receveur.getDonneursAssocies() != null) ? receveur.getDonneursAssocies().size() : 0 %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium space-x-2">
                                <a href="modifier-receveur?id=<%= receveur.getId() %>" class="text-indigo-600 hover:text-indigo-900">Modifier</a>
                                <span class="text-gray-300">|</span>
                                <a href="supprimer-receveur?id=<%= receveur.getId() %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce receveur ?')" class="text-red-600 hover:text-red-900">Supprimer</a>
                                <span class="text-gray-300">|</span>
                                <a href="association" class="text-blue-600 hover:text-blue-900">Associer</a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <p class="mt-8 p-4 bg-yellow-50 border-l-4 border-yellow-400 text-yellow-700 rounded-lg">Aucun receveur enregistré.</p>
        <% } %>
    </div>
</body>
</html>