<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Ajouter un Donneur</title>
    <meta charset="UTF-8">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .bg-blood-red { background-color: #dc2626; }
        .hover-blood-red:hover { background-color: #b91c1c; }
    </style>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-3xl mx-auto bg-white p-6 rounded-xl shadow-lg">
        <h1 class="text-3xl font-bold text-blood-red border-b pb-4 mb-6">
            Ajouter un nouveau Donneur
        </h1>
        
        <div class="mb-6 flex space-x-4">
            <a href="donneurs" class="text-blue-600 hover:text-blue-800 font-medium">Retour à la liste</a>
            <a href="home" class="text-gray-600 hover:text-gray-800 font-medium">Retour à l'accueil</a>
        </div>
        
        <% String erreur = (String) request.getAttribute("erreur"); %>
        <% if (erreur != null && !erreur.isEmpty()) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-6" role="alert">
                <strong class="font-bold">Erreurs :</strong>
                <span class="block sm:inline"><%= erreur %></span>
            </div>
        <% } %>
        
        <form method="post" action="donneurs" class="space-y-4">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                
                <div class="col-span-1">
                    <label for="nom" class="block text-sm font-medium text-gray-700">Nom :</label>
                    <input type="text" id="nom" name="nom" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>
                
                <div class="col-span-1">
                    <label for="prenom" class="block text-sm font-medium text-gray-700">Prénom :</label>
                    <input type="text" id="prenom" name="prenom" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>

                <div class="col-span-1">
                    <label for="telephone" class="block text-sm font-medium text-gray-700">Téléphone :</label>
                    <input type="text" id="telephone" name="telephone" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>

                <div class="col-span-1">
                    <label for="cin" class="block text-sm font-medium text-gray-700">CIN :</label>
                    <input type="text" id="cin" name="cin" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>

                <div class="col-span-1">
                    <label for="groupeSanguin" class="block text-sm font-medium text-gray-700">Groupe Sanguin :</label>
                    <select id="groupeSanguin" name="groupeSanguin" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
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
                </div>

                <div class="col-span-1">
                    <label for="poids" class="block text-sm font-medium text-gray-700">Poids (kg) :</label>
                    <input type="number" id="poids" name="poids" step="0.1" min="30" max="200" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>

                <div class="col-span-1">
                    <label for="age" class="block text-sm font-medium text-gray-700">Âge :</label>
                    <input type="number" id="age" name="age" min="16" max="120" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                </div>
                
                <div class="col-span-1">
                    <label for="sexe" class="block text-sm font-medium text-gray-700">Sexe :</label>
                    <select id="sexe" name="sexe" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                        <option value="">Sélectionner</option>
                        <option value="M">Masculin</option>
                        <option value="F">Féminin</option>
                    </select>
                </div>
                
                <div class="col-span-2">
                    <label for="contreIndications" class="block text-sm font-medium text-gray-700">Contre-indications médicales :</label>
                    <select id="contreIndications" name="contreIndications" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                        <option value="false">Non</option>
                        <option value="true">Oui</option>
                    </select>
                </div>
                
            </div>
            
            <div class="pt-5 flex justify-end space-x-3">
                <input type="reset" value="Effacer" class="py-2 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                <input type="submit" value="Enregistrer le donneur" class="py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blood-red hover-blood-red focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
            </div>
        </form>
    </div>
</body>
</html>