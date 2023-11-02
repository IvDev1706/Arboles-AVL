/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package estructurasnolineales; 
import Arboles.*;
/**
 *
 * @author ivancadena
 */
public class EstructurasNoLineales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArbolBinario arbol = new ArbolBinario();
//        for (int i = 1; i <= 10; i++) {
//            arbol.add(i);
//        }
        arbol.add(-1);
        arbol.add(-3);
        arbol.add(-2);
        System.out.println(arbol);
        System.out.println("Numero de nodos del arbol: "+arbol.size());
        System.out.println("Altura del arbol: "+arbol.height());
        System.out.println("Numero de hojas: "+arbol.leafs());
        System.out.print("Hojas: ");
        arbol.showLeafs();
        System.out.println("");
        System.out.println("Raiz: "+arbol.root());
//        System.out.println("Se elimino: "+arbol.remove(4));
//        arbol.showLeafs();
//        System.out.println("");
//        System.out.println(arbol);
//        System.out.println("Esta 4 en el arbol: "+arbol.search(4));
    }
    
}
