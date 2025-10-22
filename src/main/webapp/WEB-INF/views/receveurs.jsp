<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<%@ page import="com.bloodbank.model.Receveur.Priorite" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste des Receveurs</title>
    </head>
<body class="bg-gray-100 p-8">

    <%! 
        // Fonction utilitaire pour déterminer le besoin en poches basé sur la priorité
        public int getPochesRequises(Priorite priorite) {
            if (priorite == Priorite.CRITIQUE) return 4;
            if (priorite == Priorite.URGENT) return 3;
            return 1; // NORMAL
        }
    %>
    
    <div class="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
                <tr>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nom & Prénom</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Groupe Sanguin</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priorité</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Poches Requises</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Poches Reçues</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Besoin Restant</th>
                    <th scope="col" class="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">État</th>
                    <th scope="col" class="relative px-4 py-3">Actions</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <% List<Receveur> receveurs = (List<Receveur>) request.getAttribute("receveurs"); %>
                <% if (receveurs != null) { %>
                <% for (Receveur receveur : receveurs) { %>
                    <% 
                        // Calcul des besoins
                        int pochesRequises = getPochesRequises(receveur.getPriorite());
                        int pochesRecues = (receveur.getDonneursAssocies() != null) ? receveur.getDonneursAssocies().size() : 0;
                        int besoinRestant = pochesRequises - pochesRecues;
                        String besoinClass = besoinRestant > 0 ? "text-red-600 font-bold" : "text-green-600 font-bold";
                    %>
                    <tr>
                        <td class="px-4 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                            <%= receveur.getNom() %> <%= receveur.getPrenom() %>
                        </td>
                        <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-500"><%= receveur.getGroupeSanguin() %></td>
                        <td class="px-4 py-4 whitespace-nowrap text-sm priority-<%= receveur.getPriorite() %>"><%= receveur.getPriorite() %></td>
                        
                        <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700 font-medium"><%= pochesRequises %></td>
                        
                        <td class="px-4 py-4 whitespace-nowrap text-sm text-gray-700"><%= pochesRecues %></td>
                        
                        <td class="px-4 py-4 whitespace-nowrap text-sm <%= besoinClass %>">
                            <%= receveur.getEtat() == Receveur.Etat.SATISFAIT ? "0 (Complété)" : Math.max(0, besoinRestant) %>
                        </td>
                        
                        <td class="px-4 py-4 whitespace-nowrap text-sm etat-<%= receveur.getEtat() %>"><%= receveur.getEtat() %></td>
                        
                        <td class="px-4 py-4 whitespace-nowrap text-sm font-medium space-x-2">
                            <a href="modifier-receveur?id=<%= receveur.getId() %>" class="text-indigo-600 hover:text-indigo-900">Modifier</a>
                            <span class="text-gray-300">|</span>
                            <a href="supprimer-receveur?id=<%= receveur.getId() %>" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce receveur ?')" class="text-red-600 hover:text-red-900">Supprimer</a>
                            <span class="text-gray-300">|</span>
                            <a href="association" class="text-blue-600 hover:text-blue-900">Associer</a>
                        </td>
                    </tr>
                    <% } %>
                <% } else { %>
            ```

---

## 2. Modification de `association.jsp` (Formulaire d'Association)

Dans la page d'association, nous allons enrichir les options du menu déroulant des receveurs pour afficher clairement leur besoin actuel.

### `association.jsp`

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bloodbank.model.Donneur" %>
<%@ page import="com.bloodbank.model.Receveur" %>
<%@ page import="com.bloodbank.model.Receveur.Priorite" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    </head>
<body class="bg-gray-100 p-8">

    <%! 
        // Fonction utilitaire pour déterminer le besoin en poches basé sur la priorité
        public int getPochesRequises(Priorite priorite) {
            if (priorite == Priorite.CRITIQUE) return 4;
            if (priorite == Priorite.URGENT) return 3;
            return 1; // NORMAL
        }
    %>

    <form action="associer" method="POST" class="mt-8 space-y-6">
        <div class="grid grid-cols-2 gap-6">
            <div class="col-span-2">
                <label for="receveurId" class="block text-sm font-medium text-gray-700">Receveurs en attente et compatibles :</label>
                <select id="receveurId" name="receveurId" required class="mt-1 block w-full border-gray-300 rounded-md shadow-sm focus:border-red-500 focus:ring-red-500 p-2">
                    <option value="">Sélectionner un Receveur</option>
                    <% if (receveursEnAttente != null) { %>
                        <% for (Receveur r : receveursEnAttente) { %>
                            <% 
                                // Calcul des besoins
                                int pochesRequises = getPochesRequises(r.getPriorite());
                                int pochesRecues = (r.getDonneursAssocies() != null) ? r.getDonneursAssocies().size() : 0;
                            %>
                            <option value="<%= r.getId() %>">
                                <%= r.getNom() %> <%= r.getPrenom() %> - <%= r.getGroupeSanguin() %> - <%= r.getPriorite() %> 
                                **(Reçu: <%= pochesRecues %> / Requis: <%= pochesRequises %>)**
                            </option>
                        <% } %>
                    <% } %>
                </select>
                </div>
        </div>
        </form>
    
    ```

### 3. Vérification du tri

Comme noté précédemment, la logique de tri dans **`ReceveurServlet.java`** est déjà en place pour afficher les receveurs par ordre de priorité décroissant:

```java
// Dans ReceveurServlet.java
// Trier par priorité : CRITIQUE → URGENT → NORMAL (en inversant compareTo)
if (receveurs != null) {
    Collections.sort(receveurs, (r1, r2) -> r2.getPriorite().compareTo(r1.getPriorite()));
}
