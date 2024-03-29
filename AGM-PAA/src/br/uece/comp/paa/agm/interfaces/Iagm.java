/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm.interfaces;

import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 * @param <T>
 */

/**
 * Interface que define os metodos primitivos de uma AGM
 */

public interface Iagm <T>{
	//obter AGM
	public Grafo<T> obterAGM(Grafo<T> grafo);
	//obterAgm com retsrição de grau e/ou Dmax
	public Grafo<T> obterAGM(Grafo<T> grafo, int grau, Double distMax);
	//retsrição de Dmax
	public boolean restricaoDeDmax(Grafo<T> result, Aresta<T> edg);
	//restrição de Grau
	public boolean restricaoDeGrau(Grafo<T> result, Aresta<T> edg);

	
}
