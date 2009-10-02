/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Kruskal<T>  extends Agm<T>{

	public Grafo<T> obterAGM(Grafo<T> grafo)  {
		
		result = new Grafo<T>();
		
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		grafo.makeSet();
		
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(grafo.getArestas()); 
		
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg =  nohHeap.getInfo().clone();
			
			if(!result.findSet(edg.getA()).equals(result.findSet(edg.getB()))){
				if(restricaoDeGrau(result, edg)){
					if(restricaoDeDmax(result, edg)){
					result.addElem(edg.clone());
					}
				}
			}
		}
		
		return result;

	}

}
