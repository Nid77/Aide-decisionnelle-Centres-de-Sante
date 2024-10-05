/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

/**
 *
 * @author Rayan Tail et Lucas laiguillon
 */
public class Arete {
    private Sommet src,dest;
    private String nom;
    private double fiabilite;
    private double distance;
    private int duree;

    public Arete(Sommet src, Sommet dest, String nom) {
        this.src = src;
        this.dest = dest;
        this.nom = nom;
    }

    public Arete(Sommet src, Sommet dest, String nom, double fiabilite, double distance, int duree) {
        this.src = src;
        this.dest = dest;
        this.nom = nom;
        this.fiabilite = fiabilite;
        this.distance = distance;
        this.duree = duree;
    }

    public Sommet getSrc() {
        return src;
    }

    public Sommet getDest() {
        return dest;
    }

    public String getNom() {
        return nom;
    }

    public double getFiabilite() {
        return fiabilite;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuree() {
        return duree;
    }

   
    public void setSrc(Sommet src) {
        this.src = src;
    }

    public void setDest(Sommet dest) {
        this.dest = dest;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setFiabilite(double fiabilite) {
        this.fiabilite = fiabilite;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Arete{" + "src=" + src + ", dest=" + dest + ", nom=" + nom + ", fiabilite=" + fiabilite + ", distance=" + distance + ", duree=" + duree + '}';
    }

  
    
    

}
