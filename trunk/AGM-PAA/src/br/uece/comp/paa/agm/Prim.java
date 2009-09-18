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

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

public class Prim<T> implements Iagm<T>{
/*
 * (non-Javadoc)
 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo){


		Grafo<T> retorno = new Grafo<T>();
		Vertice<T> inicial = grafo.getVertices().get(0);
		ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
		vertices.add(inicial);
		retorno.addElem(inicial.clone());
		

		while (grafo.getVertices().size() >= retorno.getVertices().size()) {

			Grafo<T> subgrafo = new Grafo<T>();
			subgrafo.setVertices(vertices);
			Aresta<T> aresta = obterMinimo(subgrafo, grafo);

			if (aresta == null) {
				return retorno;
			} else {
				retorno.addElem(aresta.clone());
				if (vertices.contains(aresta.getA()))
					vertices.add(aresta.getB());
				else
					vertices.add(aresta.getA());
			}
		}
		
		return retorno;
		
		
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
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(grafo.getArestas()); 

		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg =  nohHeap.getInfo();
			
			boolean a,b;
			a=subgrafo.hasVertice(edg.getA());
			b=subgrafo.hasVertice(edg.getB());
				if ((a && !b) || (!a && b)) {
					retorno = edg;
					break;
				}
	
		}

		return retorno;

	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Grafo<Integer> grafo = new Grafo<Integer>();
		Vertice<Integer> a = new Vertice<Integer>(1);
		Vertice<Integer> b = new Vertice<Integer>(2);
		Vertice<Integer> c = new Vertice<Integer>(3);
		Vertice<Integer> d = new Vertice<Integer>(4);
		Vertice<Integer> e = new Vertice<Integer>(5);
		Vertice<Integer> f = new Vertice<Integer>(6);
		Vertice<Integer> g = new Vertice<Integer>(7);
		Vertice<Integer> h = new Vertice<Integer>(8);
		Vertice<Integer> i = new Vertice<Integer>(9);
		Vertice<Integer> j = new Vertice<Integer>(10);
		Vertice<Integer> l = new Vertice<Integer>(11);
		Vertice<Integer> m = new Vertice<Integer>(12);
		Vertice<Integer> n = new Vertice<Integer>(13);
		Vertice<Integer> o = new Vertice<Integer>(14);

		grafo.addEdge(a, b, 7.0);
		grafo.addEdge(a, e, 6.0);
		grafo.addEdge(b, d, 5.0);
		grafo.addEdge(c, d, 3.0);
		grafo.addEdge(c, f, 2.0);
		grafo.addEdge(d, h, 2.0);
		grafo.addEdge(e, f, 6.0);
		grafo.addEdge(e, g, 5.0);
		grafo.addEdge(e, j, 3.0);
		grafo.addEdge(f, i, 2.0);
		grafo.addEdge(g, h, 3.0);
		grafo.addEdge(g, i, 10.0);
		grafo.addEdge(g, j, 2.0);
		grafo.addEdge(g, l, 2.0);
		grafo.addEdge(h, l, 5.0);
		grafo.addEdge(i, m, 6.0);
		grafo.addEdge(i, n, 9.0);
		grafo.addEdge(m, n, 5.0);
		grafo.addEdge(m, o, 1.0);

		// Teste do Prim e do DFS
		
		Kruskal<Integer> kru = new Kruskal<Integer>();
		Prim<Integer> prim = new Prim<Integer>();
		System.out.println(grafo);
		//System.out.println(kru.obterAGM(grafo));
		System.out.println(prim.obterAGM(grafo));

	}

}
