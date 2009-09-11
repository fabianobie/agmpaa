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

public class Vertice<T>{
	private T info;
	private ArrayList<Aresta<T>> listAdj = new ArrayList<Aresta<T>>();
	
	/**
	 * @param string
	 * @param i
	 */
	public Vertice(T info) {
		this.info = info;
	}
	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}

	public ArrayList<Aresta<T>> getListAdj() {
		return listAdj;
	}
	public void setListAdj(ArrayList<Aresta<T>> listAdj) {
		this.listAdj = listAdj;
	}
	public void addAdj(Aresta<T> edg){
		listAdj.add(edg);
	}
	
	@Override 
	public boolean equals(Object obj) {
		//if (obj instanceof Vertice)
			//return false;
		Vertice<T> vrtx = (Vertice<T>) obj;
		
		if (vrtx.getInfo().equals(info))
			return true;
		else
			return false;
	}
		
}
