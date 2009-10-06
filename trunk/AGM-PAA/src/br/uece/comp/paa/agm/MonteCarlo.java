/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;
import java.util.Random;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;


/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class MonteCarlo<T> extends Agm<T> {

	/* (non-Javadoc)
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Grafo<T> GrafoMonteCarlos(Grafo<T> grafo, double tempo , int grau , double dmax) {
		this.grauMax = grau;
		this.setRgrau(true);
		this.setRdmax(true);
		this.dMax = dmax;
		metrica.start();
        Grafo<T> novoGrafo = null;
        ArrayList<Integer> indicesVisitados = new ArrayList<Integer>();
        Aresta<T> aresta = null;
        HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
        int k = grafo.getArestas().size();
        int a;
        double tempInicio = 0;
        double tempFim = 0;
        Random random = new Random();
        int numVertices = grafo.getVertices().size();
        DFS<T> dfs = new DFS<T>();
        int l = 0, l2 = 0;
        tempInicio = System.currentTimeMillis();
        novoGrafo = grafo.clone();
        while ((tempFim - tempInicio) < tempo) {
            a = random.nextInt(k);
            novoGrafo = grafo.clone();
            int tam;
            // Enquanto o novoGrafo não for uma AGM ele irá pegar aleatóriamente
            // uma aresta e remover do grafo raiz
            while (novoGrafo.getArestas().size() > novoGrafo.getVertices()
                    .size() - 1) {
                tam = grafo.getVertices().size();
                a = random.nextInt(tam);//O método obtem randomicamente um elemento

                aresta = novoGrafo.getArestas().get(a).clone();

                novoGrafo.deleteEdge(aresta);
                // Verifica se dado grafo está conexo
                if (!dfs.isConexo(novoGrafo)
                        || novoGrafo.getVertices().size() < tam) {
                	if (restricaoDeGrau(novoGrafo, aresta)) {
						if (restricaoDeDmax(novoGrafo, aresta)) {
							novoGrafo.addElem(aresta);
						}
                	}

                }
            }
            // Verifica se dado grafo resultante é uma AGM
            if (novoGrafo.getArestas().size() < novoGrafo.getVertices().size()) {

            	 grafos.inserir(novoGrafo.getPesoTotal(), novoGrafo);
            }
            indicesVisitados = new ArrayList<Integer>();
            tempFim = System.currentTimeMillis();
        }
        metrica.finish();
        return grafos.extrairMin().getInfo();

    }
	
	
}
