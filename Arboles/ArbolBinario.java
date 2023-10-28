/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;
import java.util.ArrayList;
/**
 * Clase de arbol binario (estructuras no lineales)
 * @author ivancadena
 */
public class ArbolBinario <G extends Integer>{
    //******* Varibles de atributo *******//
    private NodoArbol raiz;
    private ArrayList<NodoArbol> hojas;
    //******* Metodo Constructor *******//
    public ArbolBinario(){
        raiz = null;
        hojas = new ArrayList();
    }
    //******* Metodos de instancia *******//
    /**
     * Metodo que añade un nodo al arbol con el criterio binario
     * @param dato 
     */
    public void add(G dato){
        //revisamos si existe la raiz
        if(raiz == null){
            raiz = new NodoArbol(dato);
        }else{
            addRecursivo(raiz,new NodoArbol(dato));
        }
    }//fin add
    
    //metodo recursivo de añadir nodo
    private void addRecursivo(NodoArbol raiz,NodoArbol nodo){
        //criterio binario de insercion
        if(raiz.getDato() < nodo.getDato()){
            if(raiz.der==null){
                raiz.der = nodo;
                raiz.der.padre = raiz;
                balancear(raiz);
            }else
                addRecursivo(raiz.der,nodo);
        }else if(raiz.getDato() > nodo.getDato()){
            if(raiz.izq==null){
                raiz.izq = nodo;
                raiz.izq.padre = raiz;
                balancear(raiz);
            }else
                addRecursivo(raiz.izq,nodo);
        }//fin criterio
    }//fin addRecursivo
    
    /**
     * Metodo que retorna el numero de nodos del arbol
     * @return numero de nodos o elementos del arbol
     */
    public int size(){
        return size(raiz);
    }//fin height
    
    //metodo recursivo de altura
    private int size(NodoArbol raiz){
        if(raiz != null){
            return 1 + size(raiz.izq)+size(raiz.der);
        }else{
            return 0;
        }
    }//fin height recursivo
    
    /**
     * Metodo que retorna la altura del arbol
     * @return añtura del arbol, 0 si no existe
     */
    public int height(){
        if(raiz!= null){
            return height(raiz);
        }else{
            return 0;
        }
    }
    
    //metodo recursivo de altura
    private int height(NodoArbol raiz){
        if(raiz!= null){
            return 1 + Math.max(height(raiz.izq),height(raiz.der));
        }else{
            return 0;
        }
    }//fin altura
    /**
     * metodo que retorna el numero de hojas del arbol
     * @return numero de hojas
     */
    public int leafs(){
        return leafs(raiz);
    }//fin leafs
    
    //metodo recursivo de hojas
    private int leafs(NodoArbol raiz){
        //caso dos hijos dirente de null
        if(raiz.der != null && raiz.izq != null){
            return leafs(raiz.der)+leafs(raiz.izq);
        //caso de hijo derecho diferente de null e izquierdo null
        }else if(raiz.der != null && raiz.izq ==null){
            return leafs(raiz.der);
        //caso de hijo izquierdo diferente de null y derecho null
        }else if(raiz.izq != null && raiz.der == null){
            return leafs(raiz.izq);
        //caso de hoja sin hijos
        }else{
            hojas.add(raiz);
            return 1;
        }//fin casos
    }//fin leafs
    
    /**
     * Metodo que muestra los nodos hoja
     */
    public void showLeafs(){
        //ciclo de muestra de hojas
        for(NodoArbol hoja : hojas){
            System.out.print(hoja+" ");
        }
    }//fin showLeafs
    
    /**
     * Metod que retorna la raiz del arbol
     * @return raiz del arbol
     */
    public NodoArbol root(){
        return raiz;
    }//fin root
    
    //metodo que calcula el factor de equlibrio de la ruta (valores permitidos:-2,-1,0,1,2)
    private int factorEquilibrio(NodoArbol raiz){
        if(raiz!=null)
            return height(raiz.izq) - height(raiz.der);
        else
            return 0;
    }//fin factor de equilibrio
    
    //metodo para balancear los nodos en ruta hacia la raiz
    private void balancear(NodoArbol raiz){
        while(raiz != null){
            int factor = factorEquilibrio(raiz);
            if(factor==-2){
               balanceoDD(raiz); 
            }else if(factor==2){
               balanceoII(raiz); 
            }
            raiz = raiz.padre;
        }
    }//fin balancear
    
    //metodo para balancear por derecha-derecha
    private void balanceoDD(NodoArbol raiz) {
        NodoArbol padre = raiz.padre;//padre de la raiz temporal
        //copias de nodos en derecha-derecha
        NodoArbol nodoA = raiz.copia();
        NodoArbol nodoB = raiz.der.copia();
        NodoArbol nodoC = raiz.der.der.copia();
        //enlazar copias
        nodoB.padre = padre;
        nodoB.izq = nodoA;
        nodoB.der = nodoC;
        nodoA.padre = nodoC.padre = nodoB;
        //validacion de padre
        if(nodoB.padre == null){
            this.raiz = nodoB;
        }else{
            raiz.padre = null;
            if(padre.der == raiz)
                padre.der = nodoB;
            else
                padre.izq = nodoB;
        }//fin validacion
        //ciclo de extraccion de hijos (si existen)
        for(int i = 0; i<=2; i++){
            //incersion directa de nodos
            if(raiz.izq != null && raiz.izq.isNode()){
                addRecursivo(this.raiz,raiz.izq.copia());
            //incersion de subarbol
            }else if(raiz.izq!=null){
                NodoArbol subArbol = raiz.izq;
                raiz.izq = subArbol.padre = null;
                addSubArbol(this.raiz,subArbol);
            }//fin casos de incercion
            //desplazamiento
            raiz = raiz.der;
        }//fin ciclo de extraccion de hijos
        //validacion de hijo derecho en nodo C
        if(raiz != null){
            //incersion directa de nodo
            if(raiz.isNode()){
                addRecursivo(this.raiz,raiz.copia());
            }else{
                addSubArbol(this.raiz,raiz);
            }
        }//fin validacion de derecha nodo C
    }
    
    //metodo para balancear por izquierda-izquierda
    private void balanceoII(NodoArbol raiz) {
        NodoArbol padre = raiz.padre;//padre de la raiz temporal
        //copias de nodos en derecha-derecha
        NodoArbol nodoA = raiz.copia();
        NodoArbol nodoB = raiz.izq.copia();
        NodoArbol nodoC = raiz.izq.izq.copia();
        //enlazar copias
        nodoB.padre = padre;
        nodoB.der = nodoA;
        nodoB.izq = nodoC;
        nodoA.padre = nodoC.padre = nodoB;
        //validacion de padre
        if(nodoB.padre == null){
            this.raiz = nodoB;
        }else{
            raiz.padre = null;
            raiz.padre = null;
            if(padre.izq == raiz)
                padre.izq = nodoB; 
            else
                padre.der = nodoB;                  
        }//fin validacion
        //ciclo de extraccion de hijos (si existen)
         for(int i = 0; i<=2; i++){
            //incersion directa de nodos
            if(raiz.der != null && raiz.der.isNode()){
                addRecursivo(this.raiz,raiz.der.copia());
            }else if(raiz.der!=null){
                NodoArbol subArbol = raiz.der;
                raiz.der = subArbol.padre = null;
                addSubArbol(this.raiz,subArbol);
            }//fin casos de incercion
            //desplazamiento
            raiz = raiz.izq;
        }//fin ciclo de extraccion de hijos
        //validacion de hijo derecho en nodo C
        if(raiz != null){
            //incersion directa de nodo
            if(raiz.isNode()){
                addRecursivo(this.raiz,raiz.copia());
            }else{
                addSubArbol(this.raiz,raiz);
            }
        }//fin validacion de derecha nodo C
    }
    
    //metodo recursivo de añadir un subarbol
    private void addSubArbol(NodoArbol raiz,NodoArbol nodo){
        //criterio binario de insercion
        if(raiz.getDato() < nodo.getDato()){
            if(raiz.der==null){
                raiz.der = nodo;
                raiz.der.padre = raiz;
            }else
                addSubArbol(raiz.der,nodo);
        }else if(raiz.getDato() > nodo.getDato()){
            if(raiz.izq==null){
                raiz.izq = nodo;
                raiz.izq.padre = raiz;
            }else
                addSubArbol(raiz.izq,nodo);
        }//fin criterio
    }//fin addRecursivo
    //******* Metodo toString *******//
    @Override
    public String toString() {
        if(raiz!=null)
            return "Recorrido Inorden: "+rec_inorden(raiz)+"\n"+"Recorrido Preorden: "+rec_preorden(raiz)+"\n"+"Recorrido posorden: "+rec_posorden(raiz);
        else
            return "Recorrido: Arbol Vacio";
    }//fin toString
    
    //metodo privado de recorrido inorden
    private String rec_inorden(NodoArbol raiz){
        if(raiz != null){
            return rec_inorden(raiz.izq)+raiz+rec_inorden(raiz.der);
        }else{
            return " ";
        }
    }//fin rec_inorden
    
    //metodo privado de recorrido preorden
    private String rec_preorden(NodoArbol raiz){
        if(raiz != null){
            return raiz+" "+rec_preorden(raiz.izq)+rec_preorden(raiz.der);
        }else{
            return "";
        }
    }//fin rec_preorden
    
    //metodo privado de recorrido posorden
    private String rec_posorden(NodoArbol raiz){
        if(raiz != null){
            return rec_posorden(raiz.izq)+rec_posorden(raiz.der)+raiz+" ";
        }else{
            return "";
        }
    }//fin metodo posorden
    
}
