package br.uece.comp.paa.agm;


/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Cear - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */

/**
 * @author Diego Sá Cardoso (diegoccx@gmail.com)
 *
 */
import java.util.ArrayList;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

public class Prim<T> implements Iagm<T>{
	
	private Boolean rgrau =  false;
	private int grauMax;
	private Boolean rdmax =  false;
	private Double dMax;
	
	private Grafo<T> result= new Grafo<T>();
	
/*
 * (non-Javadoc)
 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
 */

	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo){
		Vertice<T> inicial = grafo.getVertices().get(0);
		result.addElem(inicial.clone());
		
		
		for (Vertice<T> vertice : grafo.getVertices()) {
			grafo.makeSet(vertice);
		}
		
		Vertice<T> pai = inicial.getPai();
			
		while (grafo.getVertices().size() >= result.getVertices().size()) {
			Aresta<T> aresta = obterMinimo(pai, grafo);		
			if (aresta == null) break;
			pai = aresta.getA().getPai();
			if(restricaoDeGrau(result, aresta)){
				if(restricaoDeDmax(result, aresta)){
					result.addElem(aresta.clone());
				}
			}
		}
		
		return result;
		
		
	}

	/**
	 * Mtodo que retorna a aresta mnima de um conjunto de vrtices de entrada
	 * 
	 * @param subgrafo
	 * @param grafo
	 * @return
	 */
	public Aresta<T> obterMinimo(Vertice<T> pai, Grafo<T> grafo) {
		Aresta<T> retorno = null;
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		Double minimo = Double.MAX_VALUE;
		Vertice<T> a , b ; 
			
		ArrayList<Vertice<T>> vertices =  grafo.getSubSet(pai);
		Grafo<T> subgrafo = new Grafo<T>();
		subgrafo.setVertices(vertices);
		
		
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(subgrafo.getArestas());
			
		while (!arestas.isVazio()) {
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> aresta = nohHeap.getInfo();
			a = grafo.findSet(aresta.getA());
			b = grafo.findSet(aresta.getB());

			if ((a.equals(pai) && !b.equals(pai))
					|| (!a.equals(pai) && b.equals(pai))) {
				if (aresta.getPeso() < minimo) {
					if(restricaoDeGrau(result, aresta)){
						if(restricaoDeDmax(result, aresta)){
							retorno = aresta;
							break;
						}
					}
				}
			}
		}
		
		
		if(retorno != null)
			grafo.union(retorno.getA(), retorno.getB());
		
		return retorno;
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
