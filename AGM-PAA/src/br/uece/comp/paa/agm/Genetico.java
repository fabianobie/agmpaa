/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Cear� - UECE
 * Alunos: Fabiano Tavares e Diego S�
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class Genetico<T> extends Agm<T> {

	private double tempo=1000;

	public Grafo<T> getInit(Grafo<T> grafo) {
		ArrayList<Aresta<T>> arestas = grafo.getArestas();
		Random randomico = new Random();
		Grafo<T> result = new Grafo<T>();
		
		while (!arestas.isEmpty()) {
			int index = Math.abs(randomico.nextInt() % arestas.size());
			Aresta<T> edg = arestas.get(index).clone();
			arestas.remove(index);

			if (!result.findSet(edg.getA()).equals(result.findSet(edg.getB()))) {
				if (restricaoDeGrau(result, edg)) {
					if (restricaoDeDmax(result,edg)) {
						result.addElem(edg.clone());
					}
				}
			}
		}

		return result.clone();
	}
	
	
	public Grafo<T> crossOver(Grafo<T>grafo ,Grafo<T> G1, Grafo<T> G2){
		Grafo<T> G = G1.intersec(G2);
		Grafo<T> F = G1.union(G2);
		
		//TODO: randomizar os menores 
		for (Aresta<T> aresta : F.getArestas()) {
			if(!grafo.findSet(aresta.getA()).equals(grafo.findSet(aresta.getB())))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta);
						G.union(aresta.getA(),aresta.getB());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				return G;
		}
		
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB()))){
				arestas.add(aresta.clone());
			}
		}
		
		//TODO: randomizar os menores 
		for (Aresta<T> aresta : arestas) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB())))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta);
						G.union(aresta.getA(),aresta.getB());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		return G;
	}
	
	public Grafo<T> mutation(Grafo<T> grafo , Grafo<T> G){
		Random randomico = new Random();
		Prim<T> prim = new Prim<T>();

		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if (!G.hasAresta(aresta)) {
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						arestas.add(aresta.clone());
					}
			}
		}

		int index = Math.abs(randomico.nextInt() % arestas.size());
		Aresta<T> edgAdd = arestas.get(index).clone();
		G.addElem(edgAdd);
		G = prim.obterAGM(G);
		return G;
	}
	


	public HeapFibonacci<Grafo<T>> gerarPopulacao(Grafo<T> grafo, double tempo) {
		HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
		double tempInicio = 0;
        double tempFim = 0;
        tempInicio = System.currentTimeMillis();
        while ((tempFim - tempInicio) < tempo) {
        	Grafo<T>  grf = getInit(grafo);
			grafos.inserir(grf.getPesoTotal(),grf);
			tempFim = System.currentTimeMillis();
		}        
		return grafos;
	}

	
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		HeapFibonacci<Grafo<T>> populacao;
		ArrayList<Grafo<T>> pais = new ArrayList<Grafo<T>>();
		result = new Grafo<T>();
		double tempInicio = 0;
        double tempFim = 0;
        tempInicio = System.currentTimeMillis();

		populacao = gerarPopulacao(grafo, tempo);
        do {
        	
        	pais=selecao(populacao);
        	populacao=reproducao(grafo, pais);
        	
			tempFim = System.currentTimeMillis();
		} while(tempFim - tempInicio < tempo);
        result = populacao.extrairMin().getInfo();
		return result;
	}

	/**
	 * @param pais
	 * @return
	 */
	private HeapFibonacci<Grafo<T>> reproducao(Grafo<T> grafo, ArrayList<Grafo<T>> pais) {
		HeapFibonacci<Grafo<T>> newPopulacao = new HeapFibonacci<Grafo<T>>();
		int tam = pais.size();
		
		for (int i=0; i< tam  ; i+=2) {
			Grafo<T> G1 = pais.get(i);
			if(tam>i+1){
				Grafo<T> G2 = pais.get(i+1);
					G2 = crossOver(grafo, G1, G2);
					G2 = mutation(grafo, G2);
					newPopulacao.inserir(G1.getPesoTotal(),G1);
					newPopulacao.inserir(G2.getPesoTotal(),G2);
				}else{
					G1 = mutation(grafo, G1);
					newPopulacao.inserir(G1.getPesoTotal(),G1);
				}
				
		}
		return newPopulacao;
	}


	/**
	 * @param populacao
	 * @return
	 */
	private ArrayList<Grafo<T>> selecao(HeapFibonacci<Grafo<T>> populacao) {
		ArrayList<Grafo<T>>  grafos = new ArrayList<Grafo<T>>();
		int qtde = populacao.getNumNoh();
		
		for(int i=0; i< qtde ;i++){
			grafos.add(populacao.extrairMin().getInfo());
		}
		return grafos;
	}

	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		GrafosUtil<String> gutil = new GrafosUtil<String>();
		Grafo<String> grf = gutil.fileToGrafo("files/g_quadrado.txt");
		Genetico<String> gen = new Genetico<String>();
		
		//gutil.telaGrafos(grf);
		gutil.telaGrafos(gen.obterAGM(grf));
		
		
		/*System.out.println(grf);
		gutil.telaGrafos(grf);
		int k = 1;
		
		for (Grafo<String> grafo : gen.gerarPopulacao(grf,10)) {
			System.out.println("grafo " + k++ + " "
					+ (grafo.getPesoTotal()) + "->"
					+ grafo.getVertices().size() + " \n");
			gutil.telaGrafos(grafo);
		}*/


	}
}
