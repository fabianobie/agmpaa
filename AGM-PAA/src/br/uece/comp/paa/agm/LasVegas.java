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
public class LasVegas<T> extends Agm<T>{

	
	@Override
	public Grafo<T> obterAGM(Grafo<T> grafo){
		return grafo;
	}
	
	
	/**
     * Método Obter grafos Las Vegas
     *
     * @param grafo
     * @param tempo
     * @return
     */
    public Grafo<T> GrafoLasVegas(Grafo<T> grafo, double tempo , int grau , double dmax) {
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
                a = random.nextInt(tam); //O método obtem randomicamente um elemento
                indicesVisitados.add(a);
                if (indicesVisitados.size() >= tam) {
                   
                    break;
                }
               
                if(!indicesVisitados.contains(a)){// testa a primaridade
                aresta = novoGrafo.getArestas().get(a).clone();

                novoGrafo.deleteEdge(aresta);
                // Verifica se dado grafo está conexo
                if (!dfs.isConexo(novoGrafo)
                        || novoGrafo.getVertices().size() < tam) {
						if (restricaoDeGrau(novoGrafo, aresta)) {
							if (restricaoDeDmax(novoGrafo, aresta)) {
								novoGrafo.addElem(aresta.clone());
							}
						}
                }
            }
            }
            // Verifica se dado grafo resultante é uma AGM
            if (novoGrafo.getArestas().size() < novoGrafo.getVertices().size()) {

                grafos.inserir(novoGrafo.getPesoTotal(), novoGrafo.clone());
            }
            indicesVisitados = new ArrayList<Integer>();
            tempFim = System.currentTimeMillis();
        }
        
        metrica.finish();
        if(grafos.isVazio())
        	return null;
        else 
        	return grafos.extrairMin().getInfo().clone();

    }
	
}
