<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Donate Blood - Accueil</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        /* Configuration Tailwind personnalisée pour le thème de sang */
        .bg-blood-red { background-color: #dc2626; } /* Rouge vif pour le thème */
        .hover-blood-red:hover { background-color: #b91c1c; }
        .text-accent { color: #2563eb; } /* Bleu pour les liens/accents */
        .bg-accent-light { background-color: #eff6ff; }
    </style>
</head>
<body class="bg-gray-50 flex items-center justify-center min-h-screen">
    <div class="container max-w-xl mx-auto p-8 bg-white shadow-2xl rounded-xl text-center">
        <h1 class="text-4xl font-extrabold text-blood-red mb-6 border-b pb-3">
            Bienvenue sur Donate Blood
        </h1>
        
        <% String message = (String) session.getAttribute("message"); %>
        <% if (message != null) { %>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4" role="alert">
                <span class="block sm:inline"><%= message %></span>
            </div>
            <% session.removeAttribute("message"); %>
        <% } %>
        
        <h2 class="text-2xl font-semibold text-gray-700 mb-4 mt-8">Menu Principal</h2>
        <div class="flex flex-col space-y-3">
            <a href="donneurs" class="py-3 px-6 bg-blue-600 text-white font-bold rounded-lg shadow-md hover:bg-blue-700 transition duration-300">
                Gestion des Donneurs
            </a>
            <a href="receveurs" class="py-3 px-6 bg-blue-600 text-white font-bold rounded-lg shadow-md hover:bg-blue-700 transition duration-300">
                Gestion des Receveurs
            </a>
        </div>
        
        <h3 class="text-xl font-semibold text-gray-700 mb-4 mt-8">Actions Rapides</h3>
        <div class="flex justify-center space-x-4">
            <a href="ajouter-donneur" class="py-2 px-4 bg-blood-red text-white font-semibold rounded-lg shadow-lg hover-blood-red transition duration-300">
                Ajouter un Donneur
            </a>
            <a href="ajouter-receveur" class="py-2 px-4 bg-blood-red text-white font-semibold rounded-lg shadow-lg hover-blood-red transition duration-300">
                Ajouter un Receveur
            </a>
        </div>
        
        <p class="mt-10 text-gray-500 text-sm">Application de gestion de don de sang - Version 1.0</p>
    </div>
</body>
</html>