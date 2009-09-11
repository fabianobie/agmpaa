/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
	
		if(!hasVertice(a)){
			a.addAdj(edg);
			vertices.add(a);
		}else{
			int i = getVertice(a);
			vertices.get(i).addAdj(edg);
		}
	
		if(!hasVertice(b)){
			b.addAdj(edg);
			vertices.add(b);
		}else{
			int i = getVertice(b);
			vertices.get(i).addAdj(edg);
		}
	}
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public int getVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	public void deleteEdge(Aresta<T> edg){
		edg.getA().getListAdj().remove(edg.getB());
		edg.getB().getListAdj().remove(edg.getA());
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}
	
	
	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) {
		Grafo<String> grf = new Grafo<String>();
		
		String grafo = "A-B:5,B-C:10,C-A:15";
		Scanner sc = new Scanner(grafo);
		String[] edges = grafo.split(",");
		
		
		Aresta<String>  aresta = new Aresta<String>(new Vertice<String>("Fabiano",1.0),new Vertice<String>("tavares",2.0),10.0);
		Aresta<String>  aresta1 = new Aresta<String>(new Vertice<String>("Fabiano",1.0),new Vertice<String>("silva",3.0),15.0);
		Aresta<String>  aresta2 = new Aresta<String>(new Vertice<String>("silva",3.0),new Vertice<String>("tavares",2.0),5.0);
		
		grf.addElem(aresta);
		grf.addElem(aresta1);
		grf.addElem(aresta2);
		
		System.out.println("Fim !");
		
	}
	
}
