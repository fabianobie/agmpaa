/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.util.ArrayList;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private ArrayList<Vertice<T>> vertices;
	
	public void addElem(Vertice<T> va, Vertice<T> vb){
		Aresta<T> edg = new Aresta<T>(va, vb);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getA();
		if(!vertices.contains(a)) 
			vertices.add(a);
		
		int id = vertices.indexOf(a);
		vertices.get(id).addAdj(b);
	
		if(!vertices.contains(b)) 
			vertices.add(b);
		
		id = vertices.indexOf(b);
		vertices.get(id).addAdj(a);
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}
	
	
	
}
