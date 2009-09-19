/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Kruskal<T>  implements Iagm<T>{

	private Boolean rgrau =  false;
	private int grauMax;
	private Boolean rdmax =  false;
	private Double dMax;
	
	/* (non-Javadoc)
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo)  {
		
		Grafo<T> result = new Grafo<T>();
		ArrayList<Vertice<T>> vrtxs = grafo.getVertices();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		for (Vertice<T> vertice : vrtxs) {
			grafo.makeSet(vertice);
		}
		
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(grafo.getArestas()); 
		
		
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg =  nohHeap.getInfo();
			
			if(!grafo.findSet(edg.getA()).equals(grafo.findSet(edg.getB()))){
				if(restricaoDeGrau(result, edg)){
					if(restricaoDeDmax(result, edg)){
					result.addElem(edg.clone());
					grafo.union(edg.getA(), edg.getB());
					}
				}
			}
		}
		
		return result;

	}


	public Grafo<T> obterAGM(Grafo<T> grafo ,int grau , Double distMax)  {
		if (grau >= 2) {
			this.rgrau = true;
			this.grauMax = grau;
		}

		if (distMax > 0) {
			this.rdmax = true;
			this.dMax = distMax;
		}

		return obterAGM(grafo);
	}

	/**
	 * @param result
	 * @param edg
	 * @return
	 */
	private boolean restricaoDeDmax(Grafo<T> result, Aresta<T> edg) {
		if (rdmax) {
			ArrayList<Aresta<T>> arestas = result.getArestas();
			Double distancia = 0.0;
			for (Aresta<T> aresta : arestas) {
				distancia += aresta.getPeso();
			}
			if (distancia + edg.getPeso() < dMax)
				return true;
			else
				return false;
		} else
			return true;
	}

	/**
	 * @param result
	 * @param edg
	 */
	private boolean restricaoDeGrau(Grafo<T> result, Aresta<T> edg) {
		if (rgrau) {
			ArrayList<Vertice<T>> vertices = result.getVertices();
			int id = -1;
			if (result.getIdVertice(edg.getA().clone()) != -1) {
				id = result.getIdVertice(edg.getA().clone());
			} else {
				id = result.getIdVertice(edg.getB().clone());
			}
			if (id != -1) {
				Vertice<T> vertice = vertices.get(id);
				if (vertice.getListAdj().size() + 1 > this.grauMax)
					return false;
				else
					return true;
			} else
				return true;
		} else {
			return true;
		}
	}


	public Boolean getRgrau() {
		return rgrau;
	}


	public void setRgrau(Boolean rgrau) {
		this.rgrau = rgrau;
	}


	public int getGrauMax() {
		return grauMax;
	}


	public void setGrauMax(int grauMax) {
		this.grauMax = grauMax;
	}


	public Boolean getRdmax() {
		return rdmax;
	}


	public void setRdmax(Boolean rdmax) {
		this.rdmax = rdmax;
	}


	public Double getdMax() {
		return dMax;
	}


	public void setdMax(Double dMax) {
		this.dMax = dMax;
	}


}
