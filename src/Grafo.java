/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alan-
 */
public class Grafo {
    NodoVertice vertice;
    
    public Grafo(){
        vertice=null;
    }
    
    public boolean insertarVertice(char dato){
        NodoVertice nuevo= new NodoVertice(dato);
        if(nuevo==null) return false;
        
        if(vertice==null){
            vertice=nuevo;
            return true;
        }

        irUltimo();
        vertice.sig=nuevo;
        nuevo.ant=vertice;
        return true;
    }

    private void irUltimo() {
        while(vertice.sig!=null){ 
            vertice=vertice.sig;
        }
    }
    
    private void irPrimero(){
        while(vertice.ant!=null){
            vertice=vertice.ant;
        }
    }
    
    private NodoVertice buscarVertice(char dato){
        if(vertice==null) return null;
        irPrimero();
        for(NodoVertice buscar=vertice; buscar!=null; buscar=buscar.sig){
            if(buscar.dato==dato){
                return buscar;
            }
        }
        return null;
    }
    
    public boolean insertarArista(char origen, char destino){
        NodoVertice nodoOrigen=buscarVertice(origen);
        NodoVertice nodoDestino=buscarVertice(destino);
        
        if(nodoOrigen==null || nodoDestino==null){
            return false;
        }
        
        return nodoOrigen.insertarArista(nodoDestino);
    }
    
    public boolean eliminarArista(char origen, char destino){
        NodoVertice nodoOrigen=buscarVertice(origen);
        NodoVertice nodoDestino=buscarVertice(destino);
        if(nodoOrigen==null || nodoDestino==null){
            return false;
        }
        return nodoOrigen.eliminarArista(nodoDestino);
    }
    
    public boolean unSoloVertice(){
        return vertice.ant==null && vertice.sig==null;
    }
    
    public boolean eliminarVertice(char dato){
        if(vertice==null) return false;
        NodoVertice temp=buscarVertice(dato);
        if(temp==null) return false;
        
        //Verifica que NO tenga aristas a otros vertices
        if(temp.arista!=null) return false;
        //Verifica que otros vertices no tengan arista a este vertice
        quitaAristasDeOtrosVertice(temp);
        //Esta temp en primero
        if(temp==vertice){
            if(unSoloVertice()) vertice=null;
            else{
                vertice = temp.sig;
                temp.sig.ant=temp.sig=null;
            }
            return true;
        }
         //Esta temp al ultimo
         if(temp.sig==null){
             temp.ant.sig=temp.ant=null;
             return true;
         }
         //Esta temp esta en medio
         temp.ant.sig=temp.sig;
         temp.sig.ant=temp.ant;
         temp.sig=temp.ant=null;
         return true;
    }

    private void quitaAristasDeOtrosVertice(NodoVertice NodoEliminar) {
        irPrimero();
        for(NodoVertice buscar=vertice; buscar!=null; buscar=buscar.sig){
            buscar.eliminarArista(NodoEliminar);
        }
    }    
    
    public String listaAdyacencia(char dato) {      
        if(buscarVertice(dato)== null){
            return " ";
        }else{
            return buscarVertice(dato).toString();
        }
    }
    public boolean[][]matrizAdyacencia() {
        int tam = Tamaño();
        boolean Matriz[][] = new boolean[tam][tam];
        if (vertice == null) return null;
        
        for (int i=0; i<tam; i++) {
            for (int j=0; j < tam; j++) {
                Matriz[i][j] = false;
            }
        }
        for (int i=0; i<tam; i++) {
            int cont = 0;
            while (i != cont) {
                cont++;
                vertice = vertice.sig;
            }
            NodoArista temp = vertice.arista;
            irPrimero();
            while (temp != null) {
                int cont2 = 0;
                while (temp.direccion != vertice) {
                    vertice = vertice.sig;
                    cont2++;
                }
                Matriz[i][cont2] = true;
                temp = temp.abajo;
                irPrimero();
            }
        }
        return Matriz;
    }
        
    public int Tamaño() {
        NodoVertice temp = vertice;
        int cont=0;
        if (vertice == null) return 0;
        irPrimero();
        while (temp != null) {
            cont++;
            temp = temp.sig;
        }
        return cont;
    }      
    
}

