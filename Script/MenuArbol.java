/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Script;
import Arboles.*;
import java.util.Scanner;
/**
 *
 * @author Ivan Cadena
 */
public class MenuArbol {
    //objeto de arbol binario
    private static ArbolBinario arbol = new ArbolBinario();
    //objeto scanner
    private static Scanner teclado = new Scanner(System.in);
     /**
      * Metodo de meni de acciones
      */
    public static void Menu(){
        int opcion=0;
        //ciclo de menu
        while(opcion != 5){
            //menu de opciones
            System.out.println("    Arboles AVL    ");
            System.out.println("*******************");
            System.out.println("1.- Añadir elemento");
            System.out.println("2.- Eliminar elemento");
            System.out.println("3.- Buscar elemento");
            System.out.println("4.- Datos de arbol");
            System.out.println("5.- Salir");
            System.out.println("*******************");
            //captura de opcion
            System.out.print(">> ");
            opcion = teclado.nextInt();
            //evaluacion de casos
            switch(opcion){
                //caso 1
                case 1: agregarElemento(); break;
                //caso 2
                case 2: eliminarElemento(); break;
                //caso 3
                case 3: buscarElemento(); break;
                //caso 4
                case 4: datosArbol(); break;
                //caso 5
                case 5: System.out.println("Adios!!!!"); break;
                //caso default
                default: System.out.println("Opcion no existente");
            }
        }//fin ciclo de menu
    }//fin 
    
    //metodo de adicion de dato capturado
    private static void agregarElemento(){
        //mensaje de captura
        System.out.println("Ingrese el numero a ingresar:");
        System.out.print(">> ");
        //captura de dato
        int elemento = teclado.nextInt();
        //adicion en el arbol
        arbol.add(elemento);
    }//fin añadirelemento
    
    //metodo de eliminacion de dato capturado
    private static void eliminarElemento(){
        //mensaje de captura
        System.out.println("Ingrese el numero a eliminar:");
        System.out.print(">> ");
        //captura de dato
        int elemento = teclado.nextInt();
        //eliminacion de elemento
        arbol.remove(elemento);
    }//fin eliminarelemento
    
    //metodo de busqueda de un dato capturado
    private static void buscarElemento(){
        //mensaje de captura
        System.out.println("Ingrese el numero a buscar:");
        System.out.print(">> ");
        //captura de dato
        int elemento = teclado.nextInt();
        //busqueda de elemento
        if(arbol.search(elemento)){
            System.out.println("El elemento "+elemento+" si se encuentra en el arbol");
        }else{
            System.out.println("El elemento "+elemento+" no se encuentra en el arbol");
        }//fin busqueda de elemento
    }//fin buscarelemento
    
    //metodo que muestra las caracteristicas y recorridos del arbol
    private static void datosArbol(){
        //impresion de elementos
        System.out.println("Elementos del arbol en los 3 recorridos");
        System.out.println("***************************************");
        System.out.println(arbol);
        System.out.println("***************************************");
        System.out.println("");//salto de formato
        System.out.println("Datos del arbol");
        System.out.println("***************************************");
        System.out.println("Altura: "+arbol.height());
        System.out.println("Numero de nodos: "+arbol.size());
        System.out.println("Raiz del arbol: "+arbol.root());
        System.out.println("Numero de hojas: "+arbol.leafs());
        System.out.print("Hojas: ");
        arbol.showLeafs();
        System.out.println("");
        System.out.println("***************************************");
    }
}
