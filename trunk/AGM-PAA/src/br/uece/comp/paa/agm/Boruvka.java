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
 * @param <T>
 */
public class Boruvka<T> extends Agm<T>{
	
	/**
	 * 
	 * Obtem AGM
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		result = new Grafo<T>();
		//pega todos os vertices do grafo
		ArrayList<Vertice<T>> vrtxs = grafo.getVertices();
		GrafosUtil<T> gutil = new GrafosUtil<T>();

		for (Vertice<T> vertice : vrtxs) {
			//gera uma heap para cada vertice
			HeapFibonacci<Aresta<T>> heap = new HeapFibonacci<Aresta<T>>();
			for (Aresta<T> aresta : vertice.getListAdj()) {
				//ordena pela menor
				heap.inserir(aresta.getPeso(), aresta.clone());
			}
			//extrai a menor aresta do vertice e addicionaao grafo
			if(!heap.isVazio()){
				Aresta<T> edg = heap.extrairMin().getInfo();
				//verifica se não ha quebra de restrição(ciclo, grau , dmax)
				if (!result.doCiclo(edg)) {
					if (restricaoDeGrau(result, edg)) {
						if (restricaoDeDmax(result, edg)) {
							//adciona aresta
							result.addElem(edg.clone());
						}
					}
				}
			}
		}
		//recupera todas as arestas do grafo de forma ordenada na heap
		HeapFibonacci<Aresta<T>> hArestas = gutil.arestaToHeap(grafo
				.getArestas());
		// equanto não tiver mais arestas para adicionar
		while (!hArestas.isVazio()) {
			HeapFibonacciNoh<Aresta<T>> nohHeap = hArestas.extrairMin();
			Aresta<T> edg = nohHeap.getInfo();
			// verifica se não ha quebra de restrição(ciclo, grau , dmax)
			if (!result.doCiclo(edg)) {
				if (restricaoDeGrau(result, edg)) {
					if (restricaoDeDmax(result, edg)) {
						// adciona aresta
						result.addElem(edg.clone());
					}
				}
			}
		}
		//retorna AGM
		return result;
	}

}
