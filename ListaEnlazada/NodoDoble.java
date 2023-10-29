/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ListaEnlazada;

/**
 *
 * @author ivancadena
 */
public class NodoDoble <G> implements Cloneable{
    //******* Variables de atributo *******//
    private G dato;
    public NodoDoble der;
    public NodoDoble izq;
    //******* Metodo constructor *******//
    //metodo constructor de clase
    public NodoDoble(G valor){
        this.dato = valor;
    }//fin constructor
    //******* Metodos de isntancia *******//
    public NodoDoble copia(){
        NodoDoble nuevo = null;
        try{
            nuevo = (NodoDoble) this.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("Imposible clonar");
        }
        return nuevo;
    }
    //******* Metodos de atributo *******//
    public G getDato(){
        return dato;
    }
    //******* Metodo toString *******//
    @Override
    public String toString() {
        return dato + "";
    }//fin del metodo toString
    
}
