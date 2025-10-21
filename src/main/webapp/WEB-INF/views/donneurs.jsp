<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Donneur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des Donneurs</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .bg-blood-red { background-color: #dc2626; }
        .text-blood-red { color: #dc2626; }
        .status-DISPONIBLE { color: #22c55e; font-weight: 600; }
        .status-NON_DISPONIBLE { color: #f97316; font-weight: 600; }
        .status-NON_ELIGIBLE { color: #dc2626; font-weight: 600; }
    </style>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-7xl mx-auto bg-white p-8 rounded-xl shadow-2xl">
        <h1 class="text-3xl font-bold text-blood-red border-b pb-4 mb-6">
            Liste des Donneurs
        </h1>
        
        <div class="mb-6 flex space-x-4">
            <a href="ajouter-donneur" class="py-2 px-4 bg-green-500 text-white font-semibold rounded-lg shadow-md hover:bg-green-600 transition duration-300">
                Ajouter un nouveau donneur
            </a>
            <a href="home" class="py-2 px-4 bg-gray-600 text-white font-semibold rounded-lg shadow-md hover:bg-gray-700 transition duration-300">
                Retour à l'accueil
            </a>
        </div>
        
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Donneurs enregistrés</h2>
        
        <% 
        List<Donneur> donneurs = (List<Donneur>) request.getAttribute("donneurs");
        if (donneurs != null && !donneurs.isEmpty()) {
        %>
            <div class="overflow-x-auto">
                <table class="min-w-full divide-y divide-gray-200 shadow-md rounded-lg">
                    <thead class="bg-blood-red">
                        <tr>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider rounded-tl-lg">ID</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Nom & Prénom</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Téléphone / CIN</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Groupe Sanguin</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Poids / Sexe</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Statut</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider">Receveur Associé</th>
                            <th class="px-4 py-3 text-left text-xs font-medium text-white uppercase tracking-wider rounded-tr-lg">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <% for (Donneur donneur : donneurs) { %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900"><%= donneur.getId() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= donneur.getNom() %> <%= donneur.getPrenom() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= donneur.getTelephone() %><br><span class="text-xs text-gray-500"><%= donneur.getCin() %></span></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-bold text-blood-red"><%= donneur.getGroupeSanguin() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= donneur.getPoids() %> kg / <%= donneur.getSexe() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm status-<%= donneur.getStatutDisponibilite() %>"><%= donneur.getStatutDisponibilite() %></td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700">
                                <% if (donneur.getReceveurAssocie() != null) { %>
                                    <%= donneur.getReceveurAssocie().getNom() %> <%= donneur.getReceveurAssocie().getPrenom() %>
                                <% } else { %>
                                    <span class="text-gray-400 italic">Aucun</span>
                                <% } %>
                            </td>
                            <td class="px-4 py-4 whitespace-nowrap text-sm font-medium space-x-2">
                                <a href="modifier-donneur?id=<%= donneur.getId() %>" class="text-indigo-600 hover:text-indigo-900">Modifier</a>
                                <span class="text-gray-300">|</span>
                                <a href="supprimer-donneur?id=<%= donneur.getId() %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce donneur ?')" class="text-red-600 hover:text-red-900">Supprimer</a>
                                <span class="text-gray-300">|</span>
                                <a href="association" class="text-blue-600 hover:text-blue-900">Associer</a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <p class="mt-8 p-4 bg-yellow-50 border-l-4 border-yellow-400 text-yellow-700 rounded-lg">Aucun donneur enregistré.</p>
        <% } %>
    </div>
</body>
</html>