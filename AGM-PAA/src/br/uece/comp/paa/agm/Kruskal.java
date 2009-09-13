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

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Kruskal<T> {
	
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		Grafo<T> result = new Grafo<T>();
		result.setNome(grafo.getNome()+"_MINIMO");
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>(); 

		ArrayList<Vertice<T>> vrtxs = new ArrayList<Vertice<T>>();
		for (Vertice<T> V : grafo.getVertices()){
			vrtxs.add(V);
			for (Aresta<T> E : V.getListAdj()) {
				boolean a,b;
				a=vrtxs.contains(E.getA());
				b=vrtxs.contains(E.getB());
				if (!(a && b) || (!a && b) || (a && !b)) {
					arestas.inserir(E.getPeso(), E);
				}
			}
		}
		
		DFS<T> dfs = new DFS<T>(); 
		
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Vertice<T> v1 = new Vertice<T>(nohHeap.getInfo().getA().getInfo());
			Vertice<T> v2 = new Vertice<T>(nohHeap.getInfo().getB().getInfo());
			Aresta<T> edg = new Aresta<T>(v1,v2,nohHeap.getInfo().getPeso());
			
			result.addElem(edg);
			if(dfs.isConexo(result) && (result.getVertices().size()==grafo.getVertices().size())) return result;
		}
		
		return result;
	}
	
}
