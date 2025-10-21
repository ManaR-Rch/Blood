package com.bloodbank.model;

import javax.persistence.*;

@Entity
@Table(name = "receveurs")
public class Receveur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nom")
    private String nom;
    
    @Column(name = "prenom")
    private String prenom;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "cin")
    private String cin;
    
    @Column(name = "groupe_sanguin")
    private String groupeSanguin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priorite")
    private Priorite priorite;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private Etat etat;
    
    @OneToMany(mappedBy = "receveurAssocie")
    private java.util.List<Donneur> donneursAssocies = new java.util.ArrayList<>();
    
    // Constructeur par défaut
    public Receveur() {
    }
    
    // Constructeur avec paramètres
    public Receveur(String nom, String prenom, String telephone, String cin, 
                    String groupeSanguin, Priorite priorite, Etat etat) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.cin = cin;
        this.groupeSanguin = groupeSanguin;
        this.priorite = priorite;
        this.etat = etat;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public String getCin() {
        return cin;
    }
    
    public String getGroupeSanguin() {
        return groupeSanguin;
    }
    
    public Priorite getPriorite() {
        return priorite;
    }
    
    public Etat getEtat() {
        return etat;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public void setCin(String cin) {
        this.cin = cin;
    }
    
    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }
    
    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }
    
    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public java.util.List<Donneur> getDonneursAssocies() {
        return donneursAssocies;
    }

    public void setDonneursAssocies(java.util.List<Donneur> donneursAssocies) {
        this.donneursAssocies = donneursAssocies;
    }
    
    // Enum pour la priorité
    public enum Priorite {
        NORMAL,
        URGENT,
        CRITIQUE
    }
    
    // Enum pour l'état
    public enum Etat {
        EN_ATTENTE,
        SATISFAIT
    }
}
