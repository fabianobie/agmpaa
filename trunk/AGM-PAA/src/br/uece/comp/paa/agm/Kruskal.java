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
import br.uece.comp.paa.util.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */

/**
 * Algoritmo de Kruskal O algoritmo de Kruskal é um algoritmo em teoria dos
 * grafos que busca uma árvore geradora mínima para um grafo conexo com pesos.
 * Isto significa que ele encontra um subconjunto das arestas que forma uma
 * árvore que inclui todos os vértices, onde o peso total, dado pela soma dos
 * pesos das arestas da árvore, é minimizado. Se o grafo não for conexo, então
 * ele encontra uma floresta geradora mínima (uma árvore geradora mínima para
 * cada componente conexo do grafo). O algoritmo de Kruskal é um exemplo de um
 * algoritmo guloso (também conhecido como ganancioso ou greedy).(Wikipedia)
 * 
 */
public class Kruskal<T>  extends Agm<T>{
	/**
	 * Obtem AGM
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo)  {
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		result = new Grafo<T>();
		
		//pega todas as arestas em ordem crescente
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(grafo.getArestas()); 
		
		//enquanto tiver aresta para serem adicionas
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg =  nohHeap.getInfo().clone();
			//verifica se não há quebra de restrição(ciclo , grau , dmax)
			if(!result.doCiclo(edg)){
				if(restricaoDeGrau(result, edg)){
					if(restricaoDeDmax(result, edg)){
					//addiciona elemento
					result.addElem(edg.clone());
					}
				}
			}
		}
		
		return result;

	}

}
