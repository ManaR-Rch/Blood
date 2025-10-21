<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Donneur" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Associer un Donneur à un Receveur</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .bg-blood-red { background-color: #dc2626; }
        .hover-blood-red:hover { background-color: #b91c1c; }
    </style>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-4xl mx-auto bg-white p-6 rounded-xl shadow-lg">
        <h1 class="text-3xl font-bold text-blood-red border-b pb-4 mb-6">
            Associer un Donneur à un Receveur
        </h1>
        
        <div class="mb-6 flex space-x-4">
            <a href="donneurs" class="text-blue-600 hover:text-blue-800 font-medium">Retour à la liste des Donneurs</a>
            <a href="home" class="text-gray-600 hover:text-gray-800 font-medium">Retour à l'accueil</a>
        </div>
        
        <% String erreur = (String) request.getAttribute("erreur"); %>
        <% if (erreur != null && !erreur.isEmpty()) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6" role="alert">
                <strong class="font-bold">Erreur :</strong>
                <span class="block sm:inline"><%= erreur %></span>
            </div>
        <% } %>

        <% 
            List<Donneur> donneursDisponibles = (List<Donneur>) request.getAttribute("donneursDisponibles");
            List<Receveur> receveursEnAttente = (List<Receveur>) request.getAttribute("receveursEnAttente");
        %>

        <form method="post" action="associer" class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                
                <div class="col-span-1">
                    <label for="donneurId" class="block text-sm font-medium text-gray-700 mb-1">Donneurs disponibles et compatibles :</label>
                    <select id="donneurId" name="donneurId" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                        <option value="">Sélectionner un Donneur</option>
                        <% if (donneursDisponibles != null) { %>
                            <% for (Donneur d : donneursDisponibles) { %>
                                <option value="<%= d.getId() %>">
                                    <%= d.getNom() %> <%= d.getPrenom() %> - <%= d.getGroupeSanguin() %>
                                </option>
                            <% } %>
                        <% } %>
                    </select>
                    <% if (donneursDisponibles == null || donneursDisponibles.isEmpty()) { %>
                        <p class="text-sm text-yellow-700 mt-2 p-2 bg-yellow-50 border border-yellow-300 rounded-md font-semibold">Aucun donneur disponible pour association</p>
                    <% } %>
                </div>

                <div class="col-span-1">
                    <label for="receveurId" class="block text-sm font-medium text-gray-700 mb-1">Receveurs en attente et compatibles :</label>
                    <select id="receveurId" name="receveurId" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                        <option value="">Sélectionner un Receveur</option>
                        <% if (receveursEnAttente != null) { %>
                            <% for (Receveur r : receveursEnAttente) { %>
                                <option value="<%= r.getId() %>">
                                    <%= r.getNom() %> <%= r.getPrenom() %> - <%= r.getGroupeSanguin() %> - <%= r.getPriorite() %>
                                </option>
                            <% } %>
                        <% } %>
                    </select>
                    <% if (receveursEnAttente == null || receveursEnAttente.isEmpty()) { %>
                        <p class="text-sm text-yellow-700 mt-2 p-2 bg-yellow-50 border border-yellow-300 rounded-md font-semibold">Aucun receveur en attente</p>
                    <% } %>
                </div>
            </div>
            
            <div class="pt-5 flex justify-end">
                <input type="submit" value="Associer" class="py-3 px-6 border border-transparent rounded-lg shadow-lg text-lg font-extrabold text-white bg-blood-red hover-blood-red focus:outline-none focus:ring-4 focus:ring-offset-2 focus:ring-red-500/50">
            </div>
        </form>
    </div>
</body>
</html>