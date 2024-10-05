/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import sae.Cercle;
import sae.Graphe;
import sae.typeSommet;
import sae.Sommet;
import sae.Arete;
import java.awt.BasicStroke;

/**
 *
 * @author Rayan Tail et Lucas laiguillon
 */
public class PanneauDessin extends javax.swing.JPanel {
        private final List<Cercle> liste_cercle = new ArrayList<>();
        private String nomfichier="src\\sae\\liste-adjacence-jeuEssai.csv" ;
        private Graphe graphe=new Graphe(nomfichier);
        private int mouseX,mouseY;
        private Sommet sommet_selectionne;
        private int rayon=25;
    /**
     * Creates new form PanneauDessin
     */
    public PanneauDessin() {
       
        initComponents();
        //setSize(600, 500);
    }

     
     
    public void Enregistrer(){
        graphe.enregistrerSous();
    }
    
    
    
    
     public void reDessiner(String nom) {
        liste_cercle.clear();  // Supprime tous les cercles de la liste
        nomfichier=nom;
        this.graphe=new Graphe(nomfichier);
        repaint();  // Redessine le panneau
    }
     public void reDessinerContour(int x,int y) {
        liste_cercle.clear();  // Supprime tous les cercles de la liste
        this.mouseX=x;this.mouseY=y;
        //this.graphe=new Graphe(nomfichier);
        
        
         repaint();
          // Redessine le panneau
    }
     
    public void mettreAJour(){
        liste_cercle.clear();  
        repaint();
    }
    
    public void dessinVoisin(){
        
        liste_cercle.clear();  
        repaint();
    }
    
    
    
    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int x=30;
        int y=30;

        Color couleur=Color.BLUE;
        for(int i=0;i<graphe.getNombresSommet();i++){
            if(graphe.getSommetAt(i).getType().equals(typeSommet.BLOC_OPERATOIRE)){
                couleur=Color.GREEN;
            }else if(graphe.getSommetAt(i).getType().equals(typeSommet.MATERNITE)){
                couleur=Color.RED;
            }else{
                couleur=Color.BLUE;
            }
            liste_cercle.add(new Cercle(x, y, rayon,couleur, graphe.getSommetAt(i).getNum()));
           
            if(x+100+rayon>getWidth()){
                y+=120;
                x=30;
            }else{
                x+=100;
            }         
        }
        
        //Dessiner les segments
        int xSrc=0,ySrc=0,xDest=0,yDest=0;
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < graphe.getNombresArete(); i++) {
             g2d.setColor(Color.BLACK);
             for(int j=0;j<liste_cercle.size();j++){
                if(liste_cercle.get(j).getText().equals(graphe.getAreteAt(i).getSrc().getNum())){
                    xSrc=liste_cercle.get(j).getX();
                    ySrc=liste_cercle.get(j).getY(); 
                }
                if(liste_cercle.get(j).getText().equals(graphe.getAreteAt(i).getDest().getNum())){
                    xDest=liste_cercle.get(j).getX();
                    yDest=liste_cercle.get(j).getY();
                } 
                 
            }
 
             g2d.setStroke(new BasicStroke(2.0f));
             g2d.drawLine(xSrc, ySrc, xDest, yDest);
        }
        
        
        //Permet de mettre en bleu les segments qui relie les sommets voisin de sommet_selectionne
        boolean estUnVoisin=false;
        for (int i = 0; i < graphe.getNombresArete(); i++) {
             g2d.setColor(Color.BLACK);
             estUnVoisin=false;
             for(int j=0;j<liste_cercle.size();j++){
                if(liste_cercle.get(j).getText().equals(graphe.getAreteAt(i).getSrc().getNum())){
                   
                    xSrc=liste_cercle.get(j).getX();
                    ySrc=liste_cercle.get(j).getY(); 
                }
                if(liste_cercle.get(j).getText().equals(graphe.getAreteAt(i).getDest().getNum())){
                    
                    
                    
                    xDest=liste_cercle.get(j).getX();
                    yDest=liste_cercle.get(j).getY();
                } 
                 
            }
             if(graphe.getAreteAt(i).getSrc().equals(sommet_selectionne)||graphe.getAreteAt(i).getDest().equals(sommet_selectionne)){ // Met les sgements en bleu pour Afficher les voisin
                        
                        estUnVoisin=true;
                    } 
             if(estUnVoisin){
                 g2d.setColor(Color.BLUE);
                 g2d.setStroke(new BasicStroke(2.0f));
             g2d.drawLine(xSrc, ySrc, xDest, yDest);
             }
             
        }
        
        
        
        
         //Dessiner les cercles
        
        for (Cercle circle : liste_cercle) {
            g2d.setColor(circle.getColor());
            g2d.fillOval(circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(),
                circle.getRadius() * 2, circle.getRadius() * 2);

            // Dessiner le texte au centre du cercle
            g2d.setColor(Color.BLACK);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(circle.getText());
            int textHeight = fm.getHeight();
            int textX = circle.getX() - (textWidth / 2);
            int textY = circle.getY() + (textHeight / 2);
            g2d.drawString(circle.getText(), textX, textY);
        }
        
             
        // Ajout d'un contour a un cercle selectionné
       
         for (Cercle cercle : liste_cercle) {
                        
            if (cercle.contains(mouseX, mouseY, cercle.getX(), cercle.getY(), rayon)) {
                 
                 g2d.setColor(Color.BLACK);
                 g2d.drawOval(cercle.getX() - cercle.getRadius(), cercle.getY() - cercle.getRadius(),cercle.getRadius() * 2, cercle.getRadius() * 2);

                   break;
            }

         }
    
    }

    public Sommet getSommet_selectionne(int x,int y) { // Pour obtenir le sommet selectionner par le clique de la souris
        boolean trouver=false;
        int compt=0;
        for (Cercle cercle : liste_cercle) {       
            if (cercle.contains(x, y, cercle.getX(), cercle.getY(), rayon)) {
                 sommet_selectionne=graphe.getSommetAt(compt);
                 trouver=true;
                   break;
            }    
         compt++;
         }
        if(!trouver){
              sommet_selectionne = null;
         }
        return sommet_selectionne;
    }
    
    public void suprimmerSommet(String nom){
        graphe.suprimmerSommet(nom);
    }
    public void suprimmerArete(String nom){
        graphe.suprimmerArete(nom);
    }
    
    public int getNbSommet(){
        return graphe.getNombresSommet();
    }
    public int getNbArete(){
        return graphe.getNombresArete();
    }
    public ArrayList<Sommet> getListeSommet(){
        return graphe.getListe_sommet();
    }
    public ArrayList<Arete> getListeArete(){
        return graphe.getListe_arete();
    }
    public boolean sommetPresent(String nom){
        if(graphe.getSommetParNom(nom)==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean aretePresent(String nom){
        if(graphe.getAreteParNom(nom)==-1){
            return false;
        }else{
            return true;
        }
    }
     
    public int getAreteParNom(String nom){
        return graphe.getAreteParNom(nom);
    }
    
    public Arete getAreteA(int index){
        return graphe.getAreteAt(index);
    }
    
    public Sommet getSommetA(int index){
        return graphe.getSommetAt(index);
    }
     public int getSommetParNom(String nom){
        return graphe.getSommetParNom(nom);
    }
    
    public List<String> cheminfiable(String s1, String s2) {
        return graphe.cheminplusfiable(s1,s2);
    }
    public List<String> chemindistance(String s1, String s2) {
        return graphe.cheminpluscourtdist(s1,s2);
    }
    public List<String> cheminduree(String s1, String s2) {
        return graphe.cheminpluscourtduree(s1,s2);
    }
    
    public boolean[] comparer(String s1,String s2){
        return graphe.compare(s1,s2);
    }
    
    public void modifsommet(String s, typeSommet type){
        graphe.modifierSommet(s, type);
    }
    
    public void modifierFiab(double fiabilite,String nom){
        graphe.modifierFiabilite(fiabilite, nom);
    }
    
    public void modifierDist(double distance,String nom){
        graphe.modifierDistance( distance, nom);
    }
    
    public void modifierDur(int duree,String nom){
        graphe.modifierDuree( duree, nom);
    }
    
    public void ajoutArete(Sommet src, Sommet dest, String nom, double fiabilite, double distance, int duree){
        graphe.ajoutArete(src,dest,nom,fiabilite,distance,duree);
    }
    
    public void ajoutSommet(String nom, typeSommet type){
         graphe.ajoutSommet(nom,type);
     }
    
    public Sommet getSommetAt(int index){
        return graphe.getSommetAt(index);
    }
    
    public int getSommetAvecNom(String nom){
        return graphe.getSommetParNom(nom);
    }
    
    public boolean voisins2distancesounon(String s1,String s2){
        return graphe.voisins2distancesounon(s1,s2);
    }
    
    public List<String> voisinsdirects(String s){
        return graphe.voisinsdirects(s);
    }
    
    public List<String> voisinsdirectstype2sommets(String s1, String s2, String type){
        return graphe.voisinsdirectstype2sommets(s1, s2, type);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
