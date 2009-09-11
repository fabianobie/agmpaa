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
	private Double chave;
	private ArrayList<Aresta<T>> listAdj = new ArrayList<Aresta<T>>();
	
	/**
	 * @param string
	 * @param i
	 */
	public Vertice(T info, Double chave) {
		this.info = info;
		this.chave = chave;
	}
	public T getInfo() {
		return info;
	}
	public void setInfo(T info) {
		this.info = info;
	}
	public Double getChave() {
		return chave;
	}
	public void setChave(Double chave) {
		this.chave = chave;
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
		
		if (vrtx.getChave().equals(chave) && vrtx.getInfo().equals(info))
			return true;
		else
			return false;
	}
		
}
