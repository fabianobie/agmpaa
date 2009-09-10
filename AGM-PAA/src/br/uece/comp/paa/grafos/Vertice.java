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
	private ArrayList<Aresta<T>> listAdj;
	
	
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
	public void addAdj(Vertice<T> vrtx){
		Aresta<T> edg = new Aresta<T>(this,vrtx);
		listAdj.add(edg);
	}
		
}
