/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae;

/**
 *
 * @author Rayan Tail et Lucas laiguillon
 */
public class Sommet {
    private String num;
    private typeSommet type;

   


    public Sommet(String num, typeSommet type) {
        this.num = num;
        this.type = type;
      
    }

    public String getNum() {
        return num;
    }

    public typeSommet getType() {
        return type;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setType(typeSommet type) {
        this.type = type;
    }

 
   
    @Override
    public String toString() {
        return "Sommet{" + "num=" + num + ", type=" + type + '}';
    }

    
    
}
