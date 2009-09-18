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

	/* (non-Javadoc)
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo) throws CloneNotSupportedException {
		
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
					result.addElem(edg.clone());
					grafo.union(edg.getA(), edg.getB());
			}
		}
		
		return result;

	}
	
	
}
