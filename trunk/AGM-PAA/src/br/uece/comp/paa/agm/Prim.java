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
/*
 * (non-Javadoc)
 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo){

		Grafo<T> retorno = new Grafo<T>();
		Vertice<T> inicial = grafo.getVertices().get(0);
		retorno.addElem(inicial.clone());
		
		
		for (Vertice<T> vertice : grafo.getVertices()) {
			grafo.makeSet(vertice);
		}
		
		Vertice<T> pai = inicial.getPai();
			
		while (grafo.getVertices().size() >= retorno.getVertices().size()) {
			Aresta<T> aresta = obterMinimo(pai, grafo);		
			if (aresta == null) break;
			pai = aresta.getA().getPai();
			retorno.addElem(aresta.clone());	
		}
		
		return retorno;
		
		
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
					retorno = aresta;
					break;
				}
			}
		}
		
		
		if(retorno != null)
			grafo.union(retorno.getA(), retorno.getB());
		
		return retorno;
	}

}
