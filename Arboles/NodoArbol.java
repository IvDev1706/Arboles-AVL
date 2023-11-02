/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;

/**
 *
 * @author ivancadena
 */
public class NodoArbol <G extends Integer> implements Cloneable{
    //******* Variables de atributo *******//
    private G dato;
    public NodoArbol der;
    public NodoArbol izq;
    public NodoArbol padre;
    //******* Metodo constructor *******//
    //metodo constructor de clase
    public NodoArbol(G dato){
        this.dato = dato;
    }//fin constructor
    //******* Metodos de isntancia *******//
    public NodoArbol copia(){
        NodoArbol nuevo = null;
        try{
            nuevo = (NodoArbol) this.clone();
            nuevo.der = nuevo.izq = nuevo.padre = null;
        }catch(CloneNotSupportedException ex){
            System.out.println("Imposible clonar");
        }
        return nuevo;
    }
    public boolean isLeaf(){
        if(izq == null && der == null)
            return true;
        else
            return false;
    }
    //******* Metodos de atributo *******//
    //metodo get para dato
    public G getDato() {
        return dato;
    }//fin getDato
    //******* Metodo toString *******//
    @Override
    public String toString() {
        return dato+"";
    }//fin del metodo toString
}
