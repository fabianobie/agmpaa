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
import br.uece.comp.paa.util.Metricas;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */

/**
 *Classe para obtenção da KAGMs
 *de acordo com um algoritmo passado
 */
public class KAgm<T> extends Agm<T> {
	
	private  Iagm<T> algoritmo =  new Kruskal<T>();
	

	/**
	 * obter AGM
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		return algoritmo.obterAGM(grafo);
	}
	
	
	/**
	 * Obtem as KAGMS a partir de grafo
	 * @param grafo
	 * @param K
	 * @param algoritmo
	 * @param grauMax
	 * @param dmax
	 * @return
	 */
	public ArrayList<Grafo<T>> obterKAGMS(Grafo<T> grafo, Integer K, Iagm<T> algoritmo , int grauMax , Double dmax){
		HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
		ArrayList<Grafo<T>> kagms = new ArrayList<Grafo<T>>();
		// Get grafo global
		Grafo<T> gtemp = grafo.clone();
		//minimo
		Grafo<T> gmin;
		//contabilizar tempo
		metrica.start();
		
		//obter o menor por um metodo exato
		gmin = algoritmo.obterAGM(gtemp,grauMax,dmax);
		
		//insiro na floresta de das K's
		grafos.inserir(gmin.getPesoTotal(), gmin.clone());
		//Se mais de uma AGM
		//executa o backTrack para obtenção das Agm a  partir da combinação das KAGMs
		if(K>1) backTrack(grafo, K, grafos, gtemp, gmin , algoritmo);
		//retorna a heap em foram de array do menor para o maior
		while (!grafos.isVazio()) {
			kagms.add(grafos.extrairMin().getInfo());
		}
		//finaliza o tempo
		metrica.finish();
		
		//retorna todas as KAGM
		return kagms;
	}
	
    private void backTrack(Grafo<T> grafo, Integer K, HeapFibonacci<Grafo<T>> grafos, Grafo<T> gtemp,Grafo<T> gmin,Iagm<T> algoritmo) {
	
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		//Ordena arestas total
		HeapFibonacci<Aresta<T>> hfarestas = gutil.arestaToHeap(gtemp.getArestas());
		ArrayList<Aresta<T>> arestas = gutil.heapToAresta(hfarestas);
		
		DFS<T> dfs = new DFS<T>();
		
		//Ordena aresta da agm minima antes da obtida
		HeapFibonacci<Aresta<T>> heapMin = gutil.arestaToHeap(gmin.getArestas());
		ArrayList<Aresta<T>> arestasMin = gutil.heapToAresta(heapMin);
		
		//enqnto ha aresta do minimo removemos a maior aresta
		//entao substituimos pela segunda depois da maior
		for (int i = arestasMin.size() - 1; i >= 0; i--) {
			
			Aresta<T> arestaMax = arestasMin.get(i);
			//deleto aresta max
			gmin.deleteEdge(arestaMax);
			
			//escolho a aresta a seguna maior depois da primeia
			for (int j = arestas.indexOf(arestaMax)+1; j < arestas.size(); j++) {
				Aresta<T> edg = arestas.get(j);
				
				//se não quebrar restrição
				if (algoritmo.restricaoDeGrau(gmin, edg.clone())) {
					if (algoritmo.restricaoDeDmax(gmin, edg.clone())) {
						//tem q ser diferente da que acabamos de retirar
						if (!edg.equals(arestaMax)) {
							boolean a, b;
							a = gmin.hasVertice(edg.getA());
							b = gmin.hasVertice(edg.getB());
							
							//verificamos a presença de ciclo
							if ((a || b)) {
								//gmin não pode ter a aresta
								if (!gmin.hasAresta(edg)) {
									
									//adiconamos
									gmin.addElem(edg.clone());
									
									//vericicamos se continua sendo umaAGM de pois de adicionado
									if (dfs.isConexo(gmin) 	&& (grafo.getVertices().size() == gmin.getVertices().size())) {
										
										//Do grafo total deletamos aresta
										gtemp.deleteEdge(arestaMax);
										
										//se ainda mantem se uma AGM
										if (gmin.getVertices().size() == grafo.getVertices().size() && !grafos.hasInfo(gmin)) {
											
											//contabiliza K
											if (grafos.getNumNoh() >= K) return;
											//inserimos
											grafos.inserir(gmin.getPesoTotal(), gmin.clone()); 
											
											// a partir do grafo obtido a
											// combinação gulosa gerar uma nova
											// agm maior mas não tao maior que a
											// anterior
											backTrack(grafo, K, grafos, gtemp, gmin.clone(), algoritmo);
											
											//ao retornar deletamos a aresta que foi adicionada no minimo
											gmin.deleteEdge(edg.clone());
											
											//ao retornar adicionamos a aresta que foi a retirada do global
											gtemp.addElem(arestaMax);
											
											break;
										}
									}
									//caso o grafo não seja uma AGM colocamos de volta a aresta minima retirada
									gmin.deleteEdge(edg.clone());
								}
							}
						}
					}
				}
			}
			
			//ao reiniciar, precisamos repor o max retirado no começo
			gmin.addElem(arestaMax);
		}
		return;
    }


	/**
	 * @return
	 */
	public Iagm<T> getAlgoritmo() {
		return algoritmo;
	}


	/**
	 * @param algoritmo
	 */
	public void setAlgoritmo(Iagm<T> algoritmo) {
		this.algoritmo = algoritmo;
	}


}
