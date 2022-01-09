package metier;

import java.time.LocalDate;

public class Professeur {
    private String nom;
    private String prenom;
    private String cin;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDate date_recrutement;
    private int id_prof;
    private Departement departement;

    public Professeur(String nom, String prenom, String cin, String adresse, String telephone, String email, LocalDate date_recrutement, int id_prof, Departement departement) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.date_recrutement = date_recrutement;
        this.id_prof = id_prof;
        this.departement = departement;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getCin() {
        return cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDate_recrutement() {
        return date_recrutement;
    }

    public int getId_prof() {
        return id_prof;
    }

    public Departement getDepartement() {
        return departement;
    }

    @Override
    public String toString() {
        return  "ID: " + id_prof +
                ", Departement : " + departement.getNom() +
                ", Nom: " + nom +
                ", Prenom: " + prenom +
                ", CIN: " + cin +
                ", Adresse: " + adresse +
                ", Telephone: " + telephone +
                ", Email: " + email +
                ", Date de Recrutement: " + date_recrutement
                ;
    }

}
