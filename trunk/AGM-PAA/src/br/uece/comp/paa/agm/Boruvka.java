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

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Boruvka<T> implements Iagm<T>{
	
	/* (non-Javadoc)
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo) throws CloneNotSupportedException {
		ArrayList<Grafo<T>> listG = new ArrayList<Grafo<T>>();
		Grafo<T> result = new Grafo<T>();
		
		for(Vertice<T> V : grafo.getVertices()){
		
					try {
						Grafo<T> grf = new Grafo<T>();
						grf.addElem((Vertice<T>) V.clone());
						listG.add(grf );
						
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
		}
		
		result.setNome(grafo.getNome()+"_MINIMO");
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>(); 
		
		DFS<T> dfs = new DFS<T>();
		
		while(!(dfs.isConexo(result) && (result.getVertices().size()==grafo.getVertices().size()))){
			for (Grafo<T> G : listG) {
				Aresta<T> edg = obterMinimo(G,grafo);
				G.addElem(edg);
				try {
					result = (Grafo<T>) G.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
	public Aresta<T> obterMinimo(Grafo<T> subgrafo, Grafo<T> grafo) {
		Aresta<T> retorno = null;
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>();
		ArrayList<Aresta<T>> edgs = grafo.getArestas();
		
		for (Aresta<T> E : edgs) {
			arestas.inserir(E.getPeso(), E);
		}
		
		// Laos que percorrem todos os vrtices e todas as arestas e obtm a
		// aresta com o peso mnimo.
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg;
			try {
				edg = (Aresta<T>) nohHeap.getInfo().clone();
				boolean a,b;
				a=subgrafo.hasVertice(edg.getA());
				b=subgrafo.hasVertice(edg.getB());
				if ((a || b) && !(a && b)) {
					retorno = edg;
					break;
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retorno;

	}
	
}
