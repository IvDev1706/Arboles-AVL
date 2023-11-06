/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Arboles;
import ListaEnlazada.ListaDoble;
/**
 * Clase de arbol binario (estructuras no lineales)
 * @author ivancadena
 */
public class ArbolBinario <G extends Integer>{
    //******* Varibles de atributo *******//
    private NodoArbol raiz;
    private ListaDoble<NodoArbol> hojas;
    //******* Metodo Constructor *******//
    public ArbolBinario(){
        raiz = null;
        hojas = new ListaDoble();
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
        return leafs(raiz,hojas);
    }//fin leafs
    
    //metodo recursivo de hojas
    private int leafs(NodoArbol raiz, ListaDoble lista){
        //caso dos hijos dirente de null
        if(raiz.der != null && raiz.izq != null){
            return leafs(raiz.der,lista)+leafs(raiz.izq,lista);
        //caso de hijo derecho diferente de null e izquierdo null
        }else if(raiz.der != null && raiz.izq ==null){
            return leafs(raiz.der,lista);
        //caso de hijo izquierdo diferente de null y derecho null
        }else if(raiz.izq != null && raiz.der == null){
            return leafs(raiz.izq,lista);
        //caso de hoja sin hijos
        }else{
            //validacion de datos repetidos
            if(!lista.isInList(raiz)){
                lista.add(raiz);
            }//fin validacion de datos repetidos
            return 1;
        }//fin casos
    }//fin leafs
    
    /**
     * Metodo que muestra los nodos hoja
     */
    public void showLeafs(){
        System.out.print(hojas);
    }//fin showLeafs
    
    /**
     * Metodo que retorna la raiz del arbol
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
                if(factorEquilibrio(raiz.der)==-1)
                    balanceoDD(raiz);
                else
                    balanceoDI(raiz);
            }else if(factor==2){
                if(factorEquilibrio(raiz.izq)==1)
                    balanceoII(raiz);
                else
                    balanceoID(raiz);
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
            if(raiz.izq != null && raiz.izq.isLeaf()){
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
            if(raiz.isLeaf()){
                addRecursivo(this.raiz,raiz.copia());
            }else{
                addSubArbol(this.raiz,raiz);
            }
        }//fin validacion de derecha nodo C
    }//fin DD
    
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
            if(raiz.der != null && raiz.der.isLeaf()){
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
            if(raiz.isLeaf()){
                addRecursivo(this.raiz,raiz.copia());
            }else{
                addSubArbol(this.raiz,raiz);
            }
        }//fin validacion de derecha nodo C
    }//fin II
    
    //metodo para balancear por derecha-derecha
    private void balanceoDI(NodoArbol raiz) {
        NodoArbol padre = raiz.padre;//padre de la raiz temporal
        //copias de nodos en derecha-derecha
        NodoArbol nodoA = raiz.copia();
        NodoArbol nodoB = raiz.der.copia();
        NodoArbol nodoC = raiz.der.izq.copia();
        //enlazar copias
        nodoC.padre = padre;
        nodoC.izq = nodoA;
        nodoC.der = nodoB;
        nodoA.padre = nodoB.padre = nodoC;
        //validacion de padre
        if(nodoC.padre == null){
            this.raiz = nodoC;
        }else{
            raiz.padre = null;
            if(padre.der == raiz)
                padre.der = nodoC;
            else
                padre.izq = nodoC;
        }//fin validacion
        //validacion de hijos (si existen)
        if(raiz.izq != null){
            //validacion de hijo izquierdo
            if(raiz.izq.isLeaf()){
                //adicion de nodo
                addRecursivo(this.raiz,raiz.izq.copia());
            //caso de subarbol
            }else{
                NodoArbol subArbol = raiz.izq;
                //desconexion de nodo
                raiz.izq.padre = raiz.izq = null;
                //adicion de subarbol
                addSubArbol(this.raiz,subArbol);
            }//fin validacion
        }//validacion de nodo nulo
        //validacion de hijo derecho nodo medio
        if(raiz.der.der != null){
            //validacion de hijo derecho
            if(raiz.der.der.isLeaf()){
                //adicion de nodo
                addRecursivo(this.raiz,raiz.der.der.copia());
            //caso de subarbol
            }else{
                NodoArbol subArbol = raiz.der.der;
                //desconexion de nodo
                raiz.der.der.padre = raiz.der.der = null;
                //adicion de subarbol
                addSubArbol(this.raiz,subArbol);
            }//fin validacion
        }//fin validacion denodo derecho de nodo medio
        //validacion de hijos de nodo c
        if(raiz.der.izq.der != null || raiz.der.izq.izq != null){
            //validacion de hijo derecho
            if(raiz.der.izq.der != null){
                //validacion de hijo derecho
                if(raiz.der.izq.der.isLeaf()){
                    //adicion de nodo
                    addRecursivo(this.raiz,raiz.der.izq.der.copia());
                //caso de subarbol
                }else{
                    NodoArbol subArbol = raiz.der.izq.der;
                    //desconexion de nodo
                    raiz.der.izq.der.padre = raiz.der.izq.der = null;
                    //adicion de subarbol
                    addSubArbol(this.raiz,subArbol);
                }//fin validacion
            }//fin validacion dehijo derecho
            
            //validacion de hijo izquierdo
            if(raiz.der.izq.izq != null){
                //validacion de hijo derecho
                if(raiz.der.izq.izq.isLeaf()){
                    //adicion de nodo
                    addRecursivo(this.raiz,raiz.der.izq.izq.copia());
                //caso de subarbol
                }else{
                    NodoArbol subArbol = raiz.der.izq.izq;
                    //desconexion de nodo
                    raiz.der.izq.izq.padre = raiz.der.izq.izq = null;
                    //adicion de subarbol
                    addSubArbol(this.raiz,subArbol);
                }//fin validacion
            }//fin validacion de hijo izquierdo
        }//fin validacion de hijos de nodo c
    }//fin DI
    
        //metodo para balancear por derecha-derecha
    private void balanceoID(NodoArbol raiz) {
        NodoArbol padre = raiz.padre;//padre de la raiz temporal
        //copias de nodos en derecha-derecha
        NodoArbol nodoA = raiz.copia();
        NodoArbol nodoB = raiz.izq.copia();
        NodoArbol nodoC = raiz.izq.der.copia();
        //enlazar copias
        nodoC.padre = padre;
        nodoC.der = nodoA;
        nodoC.izq = nodoB;
        nodoA.padre = nodoB.padre = nodoC;
        //validacion de padre
        if(nodoC.padre == null){
            this.raiz = nodoC;
        }else{
            raiz.padre = null;
            if(padre.der == raiz)
                padre.der = nodoC;
            else
                padre.izq = nodoC;
        }//fin validacion
        //validacion de hijos (si existen)
        if(raiz.der != null){
            //validacion de hijo izquierdo
            if(raiz.der.isLeaf()){
                //adicion de nodo
                addRecursivo(this.raiz,raiz.der.copia());
            //caso de subarbol
            }else{
                NodoArbol subArbol = raiz.der;
                //desconexion de nodo
                raiz.der.padre = raiz.der = null;
                //adicion de subarbol
                addSubArbol(this.raiz,subArbol);
            }//fin validacion
        }//validacion de nodo nulo
        //validacion de hijo derecho nodo medio
        if(raiz.izq.izq != null){
            //validacion de hijo derecho
            if(raiz.izq.izq.isLeaf()){
                //adicion de nodo
                addRecursivo(this.raiz,raiz.izq.izq.copia());
            //caso de subarbol
            }else{
                NodoArbol subArbol = raiz.izq.izq;
                //desconexion de nodo
                raiz.izq.izq.padre = raiz.izq.izq = null;
                //adicion de subarbol
                addSubArbol(this.raiz,subArbol);
            }//fin validacion
        }//fin validacion denodo derecho de nodo medio
        //validacion de hijos de nodo c
        if(raiz.izq.der.der != null || raiz.izq.der.izq != null){
            //validacion de hijo derecho
            if(raiz.izq.der.izq != null){
                //validacion de hijo derecho
                if(raiz.izq.der.izq.isLeaf()){
                    //adicion de nodo
                    addRecursivo(this.raiz,raiz.izq.der.izq.copia());
                //caso de subarbol
                }else{
                    NodoArbol subArbol = raiz.izq.der.izq;
                    //desconexion de nodo
                    raiz.izq.der.izq.padre = raiz.izq.der.izq = null;
                    //adicion de subarbol
                    addSubArbol(this.raiz,subArbol);
                }//fin validacion
            }//fin validacion dehijo derecho
            
            //validacion de hijo izquierdo
            if(raiz.izq.der.der != null){
                //validacion de hijo derecho
                if(raiz.izq.der.der.isLeaf()){
                    //adicion de nodo
                    addRecursivo(this.raiz,raiz.izq.der.der.copia());
                //caso de subarbol
                }else{
                    NodoArbol subArbol = raiz.izq.der.der;
                    //desconexion de nodo
                    raiz.izq.der.der.padre = raiz.izq.der.der = null;
                    //adicion de subarbol
                    addSubArbol(this.raiz,subArbol);
                }//fin validacion
            }//fin validacion de hijo izquierdo
        }//fin validacion de hijos de nodo c
    }//fin ID
    
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
    }//fin addSubarbol
    
    /**
     * Metodo que busca y verifica la existencia de un valor
     * @param numero
     * @return true si encntro el valor, false si no existe
     */
    public boolean search(int numero){
        //validacion de existencia de nodo
        if(search(this.raiz, numero)!=null){
            return true;
        }else{
            return false;
        }//fin validacion de existencia
    }//fin search
    
    //metodo privado para buscar un nodo
    private NodoArbol search(NodoArbol raiz, int numero){
        if(raiz != null){
            //caso 1 (valor coincide)
            if(numero == raiz.getDato()){
                return raiz;
            //caso 2 (valopr mayor)
            }else if(numero > raiz.getDato()){
                return search(raiz.der, numero);
            //caso 3 (valor menor)
            }else if(numero < raiz.getDato()){
                return search(raiz.izq, numero);
            }else{
                return null;
            }//fin casos
        }else{
            return null;
        }
    }//fin search
    
    /**
     * Metodo que elimina un nodo especifico del arbol
     * @param numero
     * @return nodo del arbol eliminado, nulo si no se encontro nada
     */
    public NodoArbol remove(int numero){
        if(hojas.size()==0)
            leafs();
        //nodo temporal
        NodoArbol eliminado = null;
        //casos de eliminacion
        if(numero!=raiz.getDato()){
            NodoArbol temp = search(this.raiz,numero);
            if(temp.isLeaf()){
                //caso 1 (hoja)
                eliminado = eliminarHoja(numero);
             //caso 2 subarbol o nodo medio
            }else{
                eliminado = eliminarNodoMedio(temp);
            }//fin validacion de nodos
        }else{
            //caso 3 (raiz)
            eliminado = eliminarRaiz(this.raiz,this.hojas);
        }
        return eliminado;
    }//fin eliminar nodo
    
    //metodo para eliminar una hoja
    private NodoArbol eliminarHoja(int numero){
        NodoArbol temp = null;
        //caso 1 (hoja)
        int tamano = hojas.size();
        for(int i = 1; i<=tamano; i++){
            NodoArbol hoja = (NodoArbol) hojas.remove().getDato();
            //buscamos entre las hojas
            if(numero == hoja.getDato()){
                temp = hoja.copia();
                //validacion de rama ziquierda o derecha
                if(hoja.padre.der == hoja){
                    //desconexion
                    hoja.padre = hoja.padre.der = null;
                }else{
                    //desconexion
                    hoja.padre = hoja.padre.izq = null;
                }//fin validacion de ramas
            }else{
                hojas.add(hoja);
            }//fin busqueda de hojas
        }//ciclo de barrido de hojas
        return temp;
    }//fin eliminar hojas
    
    //metodo que elimina un nodo medio
    private NodoArbol eliminarNodoMedio(NodoArbol temp){
        NodoArbol eliminado = null;
        //lista de hojas desde el nodo temporal dado
        ListaDoble hojasNodo = new ListaDoble();
        leafs(temp,hojasNodo);
        //validacion de casos
        if(hojasNodo.size() == 1){
            //extraccion de hoja
            NodoArbol hoja = (NodoArbol) hojasNodo.remove().getDato();
            //enlace padre-hoja y hoja-padre
            hoja.padre = temp.padre;
            if(hoja.padre.der==temp){
                hoja.padre.der = hoja;
            }else{
                 hoja.padre.izq = hoja;
            }
            //desconectar temporal
            temp.padre = temp.der = temp.izq = null;
            eliminado = temp;
        }else if(hojasNodo.size() == 2){
            //extraccion de elementos
            NodoArbol mayor = (NodoArbol) hojasNodo.remove().getDato();
            NodoArbol menor = (NodoArbol) hojasNodo.remove().getDato();
            //eliminacion de hoja
            eliminarHoja(menor.getDato());
            //reconexiones
            menor.padre = temp.padre;
             if(menor.padre.der==temp){
                menor.padre.der = menor;
            }else{
                 menor.padre.izq = menor;
            }
             menor.der = mayor;
            //desconectar temporal
            temp.padre = temp.der = temp.izq = null;
            eliminado = temp;
        }else if(hojasNodo.size()>2){
            eliminarRaiz(temp, hojasNodo);
        }
        return eliminado;
    }//fin eliminarNodoMedio
    
    //metodo para cambiar la raiz
    private NodoArbol eliminarRaiz(NodoArbol raiz, ListaDoble hojas){
        //lista de hojas menores
        ListaDoble menores = new ListaDoble();
        //variable temporal
        NodoArbol temp = null;
        //tamaño de la lista de hojas
        int tamano = hojas.size();
        //ciclo de extraccion
        for(int i = 1; i<=tamano; i++){
            //extraccion de hoja
            NodoArbol hoja = (NodoArbol) hojas.remove().getDato();
            //validacion de hojas menores
            if(hoja.getDato()<this.raiz.getDato()){
                menores.add(hoja.copia());
                hojas.add(hoja);
            }else{
                hojas.add(hoja);
            }//fin validacion demhojas menores
        }//fin ciclo de extraccion
        //reemplazo de raiz
        NodoArbol nuevaRaiz = (NodoArbol) menores.remove().getDato();
        nuevaRaiz.der = this.raiz.der;
        nuevaRaiz.izq = this.raiz.izq;
        //desconexion
        this.raiz.der.padre = this.raiz.izq.padre = nuevaRaiz;
        this.raiz.der = this.raiz.izq = null;
        temp = this.raiz;
        this.raiz = nuevaRaiz;
        //eliminacion de hoja
        eliminarHoja(nuevaRaiz.getDato());
        return temp;
    }//fin de eliminar raiz
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