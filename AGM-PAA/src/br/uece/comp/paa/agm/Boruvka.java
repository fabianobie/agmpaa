/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.util.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Boruvka<T> extends Agm<T>{

	public Grafo<T> obterAGM(Grafo<T> grafo) {
		result = new Grafo<T>();
		ArrayList<Vertice<T>> vrtxs = grafo.getVertices();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		grafo.makeSet();
		
		for (Vertice<T> vertice : vrtxs) {
			HeapFibonacci<Aresta<T>> heap = new HeapFibonacci<Aresta<T>>();
			for (Aresta<T> aresta : vertice.getListAdj()) {
				heap.inserir(aresta.getPeso(), aresta.clone());
			}
			if(!heap.isVazio()){
				Aresta<T> edg = heap.extrairMin().getInfo();
				result.addElem(edg);
			}
		}
		
		grafo.makeSet();
		
		HeapFibonacci<Aresta<T>> hArestas = gutil.arestaToHeap(grafo
				.getArestas());

		while (!hArestas.isVazio()) {
			HeapFibonacciNoh<Aresta<T>> nohHeap = hArestas.extrairMin();
			Aresta<T> edg = nohHeap.getInfo();

			if (!result.findSet(edg.getA()).equals(result.findSet(edg.getB()))) {
				if (restricaoDeGrau(result, edg)) {
					if (restricaoDeDmax(result, edg)) {
						result.addElem(edg.clone());
					}
				}
			}
		}

		return result;
	}

}
