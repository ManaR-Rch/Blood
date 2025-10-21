package com.bloodbank.model;

import javax.persistence.*;

@Entity
@Table(name = "donneurs")
public class Donneur {
    
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
    
    @Column(name = "poids")
    private double poids;
    
    @Column(name = "sexe")
    private String sexe;
    
    @Column(name = "age")
    private Integer age;

    @Column(name = "contre_indications")
    private boolean contreIndications;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_disponibilite")
    private StatutDisponibilite statutDisponibilite;
    
    @ManyToOne
    @JoinColumn(name = "receveur_id")
    private Receveur receveurAssocie;
    
    // Constructeur par défaut
    public Donneur() {
    }
    
    // Constructeur avec paramètres
    public Donneur(String nom, String prenom, String telephone, String cin, 
                   String groupeSanguin, double poids, String sexe, 
                   StatutDisponibilite statutDisponibilite) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.cin = cin;
        this.groupeSanguin = groupeSanguin;
        this.poids = poids;
        this.sexe = sexe;
        this.statutDisponibilite = statutDisponibilite;
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
    
    public double getPoids() {
        return poids;
    }
    
    public String getSexe() {
        return sexe;
    }
    
    public StatutDisponibilite getStatutDisponibilite() {
        return statutDisponibilite;
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
    
    public void setPoids(double poids) {
        this.poids = poids;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    public void setStatutDisponibilite(StatutDisponibilite statutDisponibilite) {
        this.statutDisponibilite = statutDisponibilite;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean getContreIndications() {
        return contreIndications;
    }

    public void setContreIndications(boolean contreIndications) {
        this.contreIndications = contreIndications;
    }

    public Receveur getReceveurAssocie() {
        return receveurAssocie;
    }

    public void setReceveurAssocie(Receveur receveurAssocie) {
        this.receveurAssocie = receveurAssocie;
    }
    
    // Enum pour le statut de disponibilité
    public enum StatutDisponibilite {
        DISPONIBLE,
        NON_DISPONIBLE,
        NON_ELIGIBLE
    }
}
