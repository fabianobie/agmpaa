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
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.util.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class KAgm<T> extends Agm<T> {
	
	private  Iagm<T> algoritmo =  new Kruskal<T>();

	/* (non-Javadoc)
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		return algoritmo.obterAGM(grafo);
	}
	
	
	public ArrayList<Grafo<T>> obterKAGMS(Grafo<T> grafo, Integer K, Iagm<T> algoritmo , int grauMax , Double dmax){
		HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
		ArrayList<Grafo<T>> kagms = new ArrayList<Grafo<T>>();

		Grafo<T> gtemp = grafo.clone();
		Grafo<T> gmin;
		
		gmin = algoritmo.obterAGM(gtemp,grauMax,dmax);
		
		grafos.inserir(gmin.getPesoTotal(), gmin.clone());
		
		backTrack(grafo, K, grafos, gtemp, gmin , algoritmo);

		while (!grafos.isVazio()) {
			kagms.add(grafos.extrairMin().getInfo());
		}

		return kagms;
	}
	
    private void backTrack(Grafo<T> grafo, Integer K, HeapFibonacci<Grafo<T>> grafos, Grafo<T> gtemp,Grafo<T> gmin,Iagm<T> algoritmo) {
	
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		HeapFibonacci<Aresta<T>> hfarestas = gutil.arestaToHeap(gtemp.getArestas());
		ArrayList<Aresta<T>> arestas = gutil.heapToAresta(hfarestas);
		
		DFS<T> dfs = new DFS<T>();
		
		HeapFibonacci<Aresta<T>> heapMin = gutil.arestaToHeap(gmin.getArestas());
		ArrayList<Aresta<T>> arestasMin = gutil.heapToAresta(heapMin);
		
		for (int i = arestasMin.size() - 1; i >= 0; i--) {
			Aresta<T> arestaMax = arestasMin.get(i);	
			gmin.deleteEdge(arestaMax);
			for (int j = arestas.indexOf(arestaMax)+1; j < arestas.size(); j++) {
				Aresta<T> edg = arestas.get(j);
				if (algoritmo.restricaoDeGrau(gmin, edg.clone())) {
					if (algoritmo.restricaoDeDmax(gmin, edg.clone())) {
						if (!edg.equals(arestaMax)) {
							boolean a, b;
							a = gmin.hasVertice(edg.getA());
							b = gmin.hasVertice(edg.getB());
							if ((a || b)) {
								if (!gmin.hasAresta(edg)) {
									gmin.addElem(edg.clone());
									if (dfs.isConexo(gmin)
											&& (grafo.getVertices().size() == gmin
													.getVertices().size())) {
										gtemp.deleteEdge(arestaMax);
										if (gmin.getVertices().size() == grafo.getVertices().size()
												&& !grafos.hasInfo(gmin)) {
											if (grafos.getNumNoh() >= K)
												return;
											grafos.inserir(gmin.getPesoTotal(),
													gmin.clone());
											backTrack(grafo, K, grafos, gtemp, gmin
													.clone(), algoritmo);
											gmin.deleteEdge(edg.clone());
											gtemp.addElem(arestaMax);
											break;
										}
									}
									gmin.deleteEdge(edg.clone());
								}
							}
						}
					}
				}
			}
			gmin.addElem(arestaMax);
		}
		return;
    } 
	

}
