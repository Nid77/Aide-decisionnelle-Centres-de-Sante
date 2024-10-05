/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sae;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Rayan Tail et Lucas laiguillon
 */
public class Graphe {
    private String nomFichier;
    private ArrayList<Sommet> liste_sommet = new ArrayList<>();
    private ArrayList<Arete> liste_arete = new ArrayList<>();
    
    public Graphe(String nomFichier) {
	super();
	this.nomFichier = nomFichier;
	File fichier = new File(nomFichier);
	if (fichier.exists())
		chargerFichier(fichier);
        else
            System.out.println("fichier non trouver");
    }
    
    
    private void chargerFichier(File fichier) {
        liste_sommet.clear();
        liste_arete.clear();
        String numFiabilite=""; // enregistrer la fiabilité d'un chemin
        String numDistance=""; // enregistrer la distance d'un chemin
        String numDuree=""; // enregistrer la duree d'un chemin   
        typeSommet type=typeSommet.MATERNITE; // type de chaque sommet a enregister

        int nb_ligne=1;
        int index=0;

        int[] indices =new int[2] ;
        try {
            // Lecture du fichier
            FileReader reader = new FileReader(fichier);
             BufferedReader bufferedReader = new BufferedReader(reader);
            List<String[]> lignes = new ArrayList<>();
            String ligne;

            while ((ligne = bufferedReader.readLine()) != null) {
                if(!ligne.equals("")){
                    char premierCaractere = ligne.charAt(0);
                    if(premierCaractere=='S'){
                        lignes.add(ligne.split(";"));
                    }
                }
                     
                    
                   
     
                
            }
            
            bufferedReader.close();
            reader.close();

            
            // Ajout des Sommets
            int size=0;
          
            if(!lignes.isEmpty())
                size=lignes.get(0).length;
               
                
            for (String[] e : lignes) {


                if(e[0].length()<10){ // pour eviter de prendre les lignes qui ne sont pas Si ( par exemple un commentaire )
                        
                    if(e[1].equals("M")){
                        type=typeSommet.MATERNITE;
                    }else if(e[1].equals("N")){
                        type=typeSommet.CENTRE_DE_NUTRITION;
                    }else{
                        type=typeSommet.BLOC_OPERATOIRE;
                    }

                    liste_sommet.add(new Sommet(e[0],type));

                }
            }
            

            // Ajout des Aretes
            
            for(int i=2;i<size;i++){ // colones
              
                nb_ligne=1;
                index=0;
                indices[0]=0;
                indices[1]=0;
                for (String[] e : lignes) { // lignes

                    if(e[0].length()<10){

                        //System.out.println(e[i]+":"+nb_ligne);
                        if(e[i].equals("0")==false){ // pour reperer les sommets connectés
                            indices[index]=nb_ligne;
                           // System.out.println(nb_ligne);
                            if(index<1){
                                index++;
                                
                            }
                            
                            String[] donnee =e[i].split(",");
                             numFiabilite=donnee[0].replace(" ", "");
                             numDistance=donnee[1].replace(" ", "");
                             numDuree=donnee[2].replace(" ", "");

                        }

                         nb_ligne++;

                    }
                    
                }
                  if(indices[1]!=0 ){
             
                   // System.out.println(liste_sommet.get(indices[0]-1)+"---"+liste_sommet.get(indices[1]-1));
                  
                 //System.out.println(numFiabilite);
                 //System.out.println(numDistance);
                 //System.out.println(numDuree);
                 
                 liste_arete.add(new Arete(liste_sommet.get(indices[0]-1),liste_sommet.get(indices[1]-1),String.valueOf(i-1),Double.valueOf(numFiabilite),Double.valueOf(numDistance),Integer.valueOf(numDuree)));
                
                  }
               
                   
                
            }




        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'a pas été trouvé : " + nomFichier);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + nomFichier);
            e.printStackTrace();
        }
    }
    


    public void enregistrerSous() { // enregistrer un graphe dans un fichier
           
            try {
            // Ecriture dans un fichier
           BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src\\sae\\fichier.csv") ));
           
           String t; 
           for(int i=0;i<this.getNombresSommet();i++){
               t=liste_sommet.get(i).getNum()+";";
               if(liste_sommet.get(i).getType()==typeSommet.MATERNITE){
                   t+="M;";
               }else if(liste_sommet.get(i).getType()==typeSommet.CENTRE_DE_NUTRITION){
                   t+="N;";
               }else{
                   t+="O;";
               }
                    
               for(int j=0;j<this.getNombresArete();j++){
                 if( liste_arete.get(j).getSrc().equals(liste_sommet.get(i))){
                     t+=String.format("%.0f",liste_arete.get(j).getFiabilite())+","+String.format("%.0f",liste_arete.get(j).getDistance())+","+Integer.toString(liste_arete.get(j).getDuree())+";";
                 }else if( liste_arete.get(j).getDest().equals(liste_sommet.get(i))){
                     t+=String.format("%.0f",liste_arete.get(j).getFiabilite())+","+String.format("%.0f",liste_arete.get(j).getDistance())+","+Integer.toString(liste_arete.get(j).getDuree())+";";
                 }else{
                     t+="0;";
                 }
                 
               }
               writer.write(t);
               writer.newLine();
           }
        

           writer.flush();
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier n'a pas été trouvé : " + nomFichier);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + nomFichier);
            e.printStackTrace();
        }
    
       }
    


    public String getNomFichier() {
        return nomFichier;
    }

    public ArrayList<Sommet> getListe_sommet() {
        return liste_sommet;
    }

    public ArrayList<Arete> getListe_arete() {
        return liste_arete;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setListe_sommet(ArrayList<Sommet> liste_sommet) {
        this.liste_sommet = liste_sommet;
    }

    public void setListe_arete(ArrayList<Arete> liste_arete) {
        this.liste_arete = liste_arete;
    }


    // methode en rapport avec les Sommets
    public int getNombresSommet(){
        return liste_sommet.size();
    }
    public Sommet getSommetAt(int index){
        return liste_sommet.get(index);
    }
    
    public int getSommetParNom(String nom){
        int i=0;
        for(Sommet e:liste_sommet){
            if(e.getNum().equals(nom)){
                return i;
            }
            i++;
        }
        return -1;
    }
    
    public void getTousSommets(){
        
        for (Sommet e: liste_sommet){
            System.out.println(e);
        }
 
    }
    
     public void suprimmerSommet(String nom){
        liste_sommet.remove(getSommetParNom(nom));
        Iterator<Arete> iterator = liste_arete.iterator();
        while (iterator.hasNext()) {
            Arete e = iterator.next();
            if (e.getSrc().getNum().equals(nom)) {
                iterator.remove();
            }
            if (e.getDest().getNum().equals(nom)) {
                iterator.remove();
            }
        }
    }
     
     public void ajoutSommet(String nom, typeSommet type){
         liste_sommet.add(new Sommet(nom,type));
     }
     
    
    public void modifierSommet(String nom, typeSommet type){
        getSommetAt(getSommetParNom(nom)).setType(type);
    }
    
    // methode en rapport avec les Aretes
    
    public int getNombresArete(){
        return liste_arete.size();
    }

    public void getTousAretes(){
        
        for (Arete e: liste_arete){
            System.out.println(e);
        }
 
    }
    public Arete getAreteAt(int index){
        return liste_arete.get(index);
    }
    public int getAreteParNom(String nom){
        int i=0;
        for(Arete e:liste_arete){
            if(e.getNom().equals(nom)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public void suprimmerArete(String nom){
        liste_arete.remove(getAreteParNom(nom));
    }
    
    public void ajoutArete(Sommet src, Sommet dest, String nom, double fiabilite, double distance, int duree){
        liste_arete.add(new Arete(src,dest,nom,fiabilite,distance,duree));
    }
    
    //modification du graphe via l'ihm
    public void modifierFiabilite(double fiabilite,String nom){
        getAreteAt(getAreteParNom(nom)).setFiabilite(fiabilite);
    }
    
    public void modifierDistance(double distance,String nom){
        getAreteAt(getAreteParNom(nom)).setDistance(distance);
    }
    
    public void modifierDuree(int duree,String nom){
        getAreteAt(getAreteParNom(nom)).setDuree(duree);
    }
    
    //Afficher les elements du graphe
    public ArrayList<Sommet> chercherTypeSommet(typeSommet type){
        ArrayList<Sommet> new_liste=new ArrayList<>();
        for(Sommet e:liste_sommet){
            if(e.getType().equals(type)){
                new_liste.add(e);
            }
            
        }
        return new_liste;
    }
    public int[] decompteType(){
        int[] l = new int[3];
        for(Sommet e:liste_sommet){
            if(e.getType().equals(typeSommet.MATERNITE)){
                l[0]+=1;
            }else if(e.getType().equals(typeSommet.BLOC_OPERATOIRE)){
                l[1]+=1;
            }else{
                l[2]+=1;
            }
            
        }
        return l;
    }
    
    public ArrayList<String> trajetRisque(double seuil){
        ArrayList<String> new_liste = new ArrayList<>();
        for (Arete e: liste_arete){
            if(e.getFiabilite()*10<seuil){
                new_liste.add(e.getNom());
            }
        }
        return new_liste;   
    }
    
    
    // algorithme
    public double[] getDistAreteAdj(String s){
        double []donnees = new double[liste_sommet.size()];
        for (int i = 0; i < liste_sommet.size(); i++)
                donnees[i] = Integer.MAX_VALUE;                //tableau remplit de valeur très grande pour signifier qu'il n'y a pas d'arete entre les 2 sommets

        for(Arete e : liste_arete){
            if (e.getSrc().getNum().equals(s)){                //On regarde si la source ou la destination de l'arête correspond à notre sommet s
                String source=e.getDest().getNum();
                donnees[getSommetParNom(source)]=e.getDistance();    
                                                                      //Si oui on ajoute la distance de l'arête à l'indice de l'opposé (la destination si la source est égale à s et inversement)
            }else if(e.getDest().getNum().equals(s)){
                String source=e.getSrc().getNum();
                donnees[getSommetParNom(source)]=e.getDistance();
                
            }
        }
        return donnees;
    }
     
     public double[] getDureeAreteAdj(String s){
        double []donnees = new double[liste_sommet.size()];
        for (int i = 0; i < liste_sommet.size(); i++)
                donnees[i] = Integer.MAX_VALUE;                     //tableau remplit de valeur très grande pour signifier qu'il n'y a pas d'arete entre les 2 sommets

        for(Arete e : liste_arete){
            if (e.getSrc().getNum().equals(s)){                     //On regarde si la source ou la destination de l'arête correspond à notre sommet s
                String source=e.getDest().getNum();
                donnees[getSommetParNom(source)]=e.getDuree();
                                                                            //Si oui on ajoute la durée de l'arête à l'indice de l'opposé (la destination si la source est égale à s et inversement)
            }else if(e.getDest().getNum().equals(s)){
                String source=e.getSrc().getNum();
                donnees[getSommetParNom(source)]=e.getDuree();
                
            }
        }
        return donnees;
    }
    
    public double[] getFiaAreteAdj(String s){
        double []donnees = new double[liste_sommet.size()];
        for (int i = 0; i < liste_sommet.size(); i++)
                donnees[i] = Integer.MIN_VALUE;                     //tableau remplit de valeur très petite pour signifier qu'il n'y a pas d'arete entre les 2 sommets
        for(Arete e : liste_arete){
            if (e.getSrc().getNum().equals(s)){                         //On regarde si la source ou la destination de l'arête correspond à notre sommet s
                String source=e.getDest().getNum();
                donnees[getSommetParNom(source)]=e.getFiabilite()/10;           //On divise la fiabilité par 10 pour les multiplier ensuite
                                                                                     //Si oui on ajoute la fiabilité de l'arête à l'indice de l'opposé (la destination si la source est égale à s et inversement)
            }else if(e.getDest().getNum().equals(s)){
                String source=e.getSrc().getNum();
                donnees[getSommetParNom(source)]=e.getFiabilite()/10;
                
            }
        }
        return donnees;
    }
    
    public List<String> cheminpluscourtdist(String s1, String s2){
        int taille = liste_sommet.size();
        boolean[] visite = new boolean[taille];        //liste des sommets déjà visité pour effectuer le dijkstra
        double[] distances = new double[taille];   //tableau à une dimension pour mettre les distances qui seront changés si l'on trouve un nombre plus petit
        int[] prec = new int[taille];                  //Contient les sommets qui précédènt chaque sommet
        List<String> chemin = new ArrayList<>();           //Liste qui va déterminer le chemin du sommet s1 au sommet s2
        for (int i = 0; i < taille; i++) {
            distances[i] = Integer.MAX_VALUE;         //tableau remplit avec des nombres très grand pour pouvoir les remplacer plus tard
        }
        int ind_source=getSommetParNom(s1);
        int ind_fin=getSommetParNom(s2);
        distances[ind_source] = 0;            //On met l'indice du sommet s1 à 0 pour commencer le dijkstra
        while(visite[ind_fin]==false){
            double min=1000.0;
            int indmin=-1;
            for (int k = 0; k < taille; k++) {
                if (distances[k] < min && visite[k]==false) {    //Recherche de la valeur la plus petite pour relacher les arêtes
                    min = distances[k];
                    indmin=k;                       
                }
            }
            visite[indmin]=true;   //Sommet mit à true pour que l'on ne le change plus car parcouru
            double[] dist=getDistAreteAdj(getSommetAt(indmin).getNum());                //On stock les distances des aretes qui conduisent au sommet adjacents
            for(int j=0;j<taille;j++){
                if(distances[j]>dist[j]+distances[indmin] && visite[j]==false){         // Si la durée de l'arête de indmin à j plus celle du chemin de indmin est plus grande que celle de ind_source à l'indice j et que le sommet à l'indice j n'a pas été déjà visité
                        distances[j]=dist[j]+distances[indmin];                         //Alors on la remplace pour garder uniquement la distance la plus grande
                        prec[j]=indmin;                                                 //On change aussi l'indice à indmin dans le tableau des précédents pour pouvoir remonter plus tard
                }
            }
        }
        int prece=getSommetParNom(s2);                                              //Prece est initialisé avec l'indice du sommet 2
        while(!chemin.contains(s1)){                                               //On boucle tant que le sommet 1 n'est pas dans le chemin 
            chemin.add(0,getSommetAt(prece).getNum());               //On ajoute au début de la liste le sommet à l'indice prece
            prece=prec[getSommetParNom(getSommetAt(prece).getNum())];        //Prece prend la valeur à l'indice du sommet ajouté qui est donc l'indice de son précédent
        }
        return chemin;
    }
    
    
    public List<String> cheminpluscourtduree(String s1, String s2){
        int taille = liste_sommet.size();
        boolean[] visite = new boolean[taille];        //liste des sommets déjà visité pour effectuer le dijkstra
        double[] durees = new double[taille];   //tableau à une dimension pour mettre les durées qui seront changés si l'on trouve un nombre plus petit
        int[] prec = new int[taille];                  //Contient les sommets qui précédènt chaque sommet
        List<String> chemin = new ArrayList<>();           //Liste qui va déterminer le chemin du sommet s1 au sommet s2
        for (int i = 0; i < taille; i++) {
            durees[i] = Integer.MAX_VALUE;         //tableau remplit avec des nombres très grand pour pouvoir les remplacer plus tard
        }
        int ind_source=getSommetParNom(s1);
        int ind_fin=getSommetParNom(s2);
        durees[ind_source] = 0;            //On met l'indice du sommet s1 à 0 pour commencer le dijkstra
        while(visite[ind_fin]==false){
            double min=1000.0;
            int indmin=-1;
            for (int k = 0; k < taille; k++) {
                if (durees[k] < min && visite[k]==false) {    //Recherche de la valeur la plus petite pour relacher les arêtes
                    min = durees[k];
                    indmin=k;
                }
            }
            visite[indmin]=true;   //Sommet mit à true pour que l'on ne le change plus car parcouru
            double[] dist=getDureeAreteAdj(getSommetAt(indmin).getNum());                //On stock les durées des aretes qui conduisent au sommet adjacents
            for(int j=0;j<taille;j++){
                if(durees[j]>dist[j]+durees[indmin] && visite[j]==false){     // Si la durée de l'arête de indmin à l'indice j plus  celle du chemin de indmin est plus grande que celle de ind_source à l'indice j et que le sommet à l'indice j n'a pas été déjà visité
                        durees[j]=dist[j]+durees[indmin];                    //Alors on la remplace pour garder uniquement la durée la plus grande
                        prec[j]=indmin;                                      //On change aussi l'indice à indmin dans le tableau des précédents pour pouvoir remonter plus tard
                }
            }
        }
        int prece=getSommetParNom(s2);                                      //Prece est initialisé avec l'indice du sommet 2
        while(!chemin.contains(s1)){                                               //On boucle tant que le sommet 1 n'est pas dans le chemin 
            chemin.add(0,getSommetAt(prece).getNum());               //On ajoute au début de la liste le sommet à l'indice prece
            prece=prec[getSommetParNom(getSommetAt(prece).getNum())];        //Prece prend la valeur à l'indice du sommet ajouté qui est donc l'indice de son précédent
        }
        return chemin;
    }
  
    
    public List<String> cheminplusfiable(String s1, String s2){
        int taille = liste_sommet.size();
        boolean[] visite = new boolean[taille];        //liste des sommets déjà visité pour effectuer le dijkstra
        double[] fiabilite = new double[taille];   //tableau à une dimension pour mettre les fiabilites qui seront changés si l'on trouve un nombre plus grand
        int[] prec = new int[taille];                  //Contient les sommets qui précédènt chaque sommet
        List<String> chemin = new ArrayList<>();           //Liste qui va déterminer le chemin du sommet s1 au sommet s2
        for (int i = 0; i < taille; i++) {
            fiabilite[i] = Integer.MIN_VALUE;         //tableau remplit avec des nombres très petit pour pouvoir les remplacer plus tard
        }
        int ind_source=getSommetParNom(s1);
        int ind_fin=getSommetParNom(s2);
        fiabilite[ind_source] = 1;            //On met l'indice du sommet s1 à 1 pour commencer le dijkstra              
        while(visite[ind_fin]==false){
            double max=-1;
            int indmin=-1;
            for (int k = 0; k < taille; k++) {
                if (fiabilite[k] > max && visite[k]==false) {    //Recherche de la valeur la plus petite pour relacher les arêtes
                    max = fiabilite[k];
                    indmin=k;
                }
            }
            visite[indmin]=true;   //Sommet mit à true pour que l'on ne le change plus car parcouru
            double[] Fiab=getFiaAreteAdj(getSommetAt(indmin).getNum());                //On stock les fiabilites des aretes qui conduisent au sommet adjacents
            for(int j=0;j<taille;j++){
                if(fiabilite[j]<Fiab[j]*fiabilite[indmin] && visite[j]==false){     // Si la fiabilité de l'arête de indmin à l'indice j multiplié par celle du chemin de indmin est plus grande que celle de ind_source à l'indice j et que le sommet à l'indice j n'a pas été déjà visité
                        fiabilite[j]=Fiab[j]*fiabilite[indmin];                     //Alors on la remplace pour garder uniquement la fiabilité la plus grande
                        prec[j]=indmin;                                             //On change aussi l'indice à indmin dans le tableau des précédents pour pouvoir remonter plus tard
                }
            }
        }
        int prece=getSommetParNom(s2);                                            //Prece est initialisé avec l'indice du sommet 2
        while(!chemin.contains(s1)){                                               //On boucle tant que le sommet 1 n'est pas dans le chemin 
            chemin.add(0,getSommetAt(prece).getNum());               //On ajoute au début de la liste le sommet à l'indice prece
            prece=prec[getSommetParNom(getSommetAt(prece).getNum())];        //Prece prend la valeur à l'indice du sommet ajouté qui est donc l'indice de son précédent
        }
        return chemin;
    }
    
    public List<String> voisinsdirects(String s){
        List<String> liste_voisins= new ArrayList<>();
        for(Arete e : liste_arete){             // On regarde si la source ou la destination d'une arête est le sommet s pour ensuite ajouter l'opposé à la liste de voisins directs
            if (e.getSrc().getNum().equals(s)){
                liste_voisins.add(e.getDest().getNum());

            }else if(e.getDest().getNum().equals(s)){
                liste_voisins.add(e.getSrc().getNum());  
            }
        }
        return liste_voisins;
    }
    
    public List<String> voisinsdirectstype(String s, String type){
        List<String> liste_voisins=voisinsdirects(s);        // On récupère tous les voisins directs de s
        List<String> liste_voisinstype= new ArrayList<>();
        for (String a : liste_voisins){
            typeSommet typea=getSommetAt(getSommetParNom(a)).getType();     // On récupère le type du sommet a
            if(typea.name().equals(type.toUpperCase())){      // On transforme le type en string pour pouvoir le comparer et on met tout en majuscule pour comparer dans tous les cas
                liste_voisinstype.add(a);
            }
        }
        return liste_voisinstype;
    }
        
    public List<String> voisins2distances(String s){
        List<String> liste_voisins= new ArrayList<>();
        liste_voisins=voisinsdirects(s);         // On récupère les voisins directs du sommets s
        List<String> liste= new ArrayList<>();
        List<String> liste_2distances= new ArrayList<>();
        for(String so : liste_voisins){
            liste=voisinsdirects(so);      // On fait une boucle pour prendre les voisins directs de chaque sommet dans liste_voisins
            if (liste.contains(s))
                liste.remove(s);          // On supprime le sommet s s'il est présent pour ne pas l'avoir dans les sommets à 2 distances
            for(int i=0;i<liste.size();i++){
                if (!liste_2distances.contains(liste.get(i)) && !liste_voisins.contains(liste.get(i)))    // On boucle pour les ajouter à notre liste en évitant les doubles et ceux qui sont des voisins directs
                    liste_2distances.add(liste.get(i));
            }
        }
        return liste_2distances;
    }
    
    public boolean voisins2distancesounon(String s1,String s2){
        boolean cond=false;         // On initialise à non la condition
        List<String> liste_2distances=voisins2distances(s1);     // On récupère tous les sommets à 2 distances du sommet s1
        for(String sommet : liste_2distances){        
            if(sommet.equals(s2)){   // On regarde pour chaque sommet de la liste s'ils correspondent au sommet s2
                 cond=true;
                 return cond;           //Si c'est vrai on met la condition à true et on la return
            }      
        }
        return cond;
    }
        public List<String> voisinsdirectstype2sommets(String s1, String s2, String type){
        List<String> liste_s1 = voisinsdirectstype(s1, type);          
        List<String> liste_s2 = voisinsdirectstype(s2, type);           //On récupère les voisins directs des deux sommets qui correspondent au type que l'on veut
        List<String> liste2 = new ArrayList<>();    //On crée une nouvelle liste qui va contenir les voisins directs des 2 sommets et qui est du bon type
        for(int i=0;i<liste_s1.size();i++){
            for(int j=0;j<liste_s2.size();j++){
                if(liste_s1.get(i).equals(liste_s2.get(j)))      //On compare chacune des valeurs pour les ajouter en cas d'équivalence
                    liste2.add(liste_s1.get(i));
            }
        }
        return liste2;   //On return la liste des voisins directs des deux sommets et qui sont du bon type
    }
        
     public int[] nbtypesommet(String s1){
        List<String> liste_sommet = voisins2distances(s1);
        List<String> liste = voisinsdirects(s1);
        for(String s: liste){
            liste_sommet.add(s);                 // On récupère les sommets à 1 et 2 distances et le sommet en lui même et on les stocks dans une liste
        }
        liste_sommet.add(s1);
        int[] type= new int[3];      // Tableau qui va permettre de stocker le nombre de chaque type de sommet
        for(String s : liste_sommet){
            typeSommet sommet=getSommetAt(getSommetParNom(s1)).getType();    // On récupère le type du sommet
            if(sommet.equals(typeSommet.MATERNITE)){
                type[0]+=1;
            }else if(sommet.equals(typeSommet.BLOC_OPERATOIRE)){         //On regarde de quel type est le sommet puis on l'ajoute dans le tableau
                type[1]+=1;
            }else{
                type[2]+=1;
            }
            
        }
        
        return type;         // On return le tableau avec le nombre de maternités, bloc opératoires et centre de nutrition
        
    }
    
    @SuppressWarnings("empty-statement")
    public boolean[] compare(String s1, String s2){
        int[] type= nbtypesommet(s1);
        int[] type2= nbtypesommet(s2);      // On récupère le nombre de chaque type pour les 2 sommets
        boolean M=false;
        boolean O=false;     // On initiliase à false pour signifier que le sommet 2 en a plus 
        boolean N=false;
        if(type[0]>type2[0])
            M=true;
        if(type[1]>type2[1])        // Si le sommet 1 en a plus alors on remplacera par true
            O=true;
        if(type[2]>type2[2])
            N=true;
        boolean[] a ={ M, O, N };
        System.out.println(a[0] + ", " + a[1] + " , " + a[2]);
        return new boolean[] { M, O, N };  // On return un tableau de booléen qui dira true si le sommet 1 en a plus et false sinon
    }
    
    //Pour faire des tests
    public static void main(String[] args){
       Graphe g = new Graphe("src\\sae\\liste-adjacence-jeuEssai.csv");
        //System.out.println(g.getNombresArete());
        //g.enregistrerSous("test.txt");
        //g.getTousAretes();
        //g.enregistrerSous();
        //System.out.println(g.getNombresArete());
        //System.out.println(g.compare("S1","S2"));
        
    }
}

