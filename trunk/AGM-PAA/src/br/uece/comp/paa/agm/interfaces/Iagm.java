/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm.interfaces;

import br.uece.comp.paa.grafos.Grafo;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public interface Iagm <T>{
	
	public Grafo<T> obterAGM(Grafo<T> grafo) throws CloneNotSupportedException;
	
}