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

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.util.GrafosUtil;

public class Prim<T> extends Agm<T> {

	/**
	 * 
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		result = new Grafo<T>();
		Vertice<T> inicial = grafo.getVertices().get(0);

		grafo.makeSet();

		result.addElem(inicial.clone());


		while (grafo.getVertices().size() >= result.getVertices().size()) {
			Aresta<T> aresta = obterMinimo(result, grafo);
			if (aresta == null)
				break;
			else
			//if (restricaoDeGrau(result, aresta)) {
				//if (restricaoDeDmax(result, aresta)) {
					result.addElem(aresta.clone());
				//}
			//}
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
	public Aresta<T> obterMinimo(Grafo<T> grafo, Grafo<T> grafoGerador) {
		Aresta<T> retorno = null;
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		Double minimo = Double.MAX_VALUE;

		ArrayList<Vertice<T>> vertices = grafoGerador.getSubSet(grafo);
		Grafo<T> subgrafo = new Grafo<T>();
		subgrafo.setVertices(vertices);

		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(subgrafo
				.getArestas());

		while (!arestas.isVazio()) {
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> aresta = nohHeap.getInfo();

			if (!result.findSet(aresta.getA()).equals(
					result.findSet(aresta.getB()))) {
				if (aresta.getPeso() < minimo) {
					if (restricaoDeGrau(result, aresta)) {
						if (restricaoDeDmax(result, aresta)) {
							retorno = aresta;
							break;
						}
					}
				}
			}
		}

		return retorno;
	}

}
