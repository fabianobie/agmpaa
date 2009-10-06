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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.util.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class Genetico<T> extends Agm<T> {

	private double tempo=5000000;
	private int alpha = 2;
	private int populacaoInit = 4;
	private boolean diversividade = false;

	public Grafo<T> getInit(Grafo<T> grafo) {
		ArrayList<Aresta<T>> arestas = grafo.getArestas();
		Grafo<T> result = new Grafo<T>();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		arestas = gutil.randomK(arestas.size(), arestas);
	
		for (Aresta<T> edg : arestas){
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
	
	
	public void crossOver(Grafo<T>grafo ,Grafo<T> G1, Grafo<T> G2){
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		Grafo<T> G = G1.intersec(G2);
		Grafo<T> F = G1.union(G2);

		ArrayList<Aresta<T>> edgs = gutil.randomK(getK(F), F.getArestas());
		
		for (Aresta<T> aresta : edgs) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB())))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta.clone());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB()))){
				arestas.add(aresta.clone());
			}
		}
		
		G1 = G.clone();
		G2 = G.clone();
		
		edgs = gutil.randomK(getK(G1), arestas);
		
		for (Aresta<T> aresta : edgs) {
			if(!G1.findSet(aresta.getA()).equals(G1.findSet(aresta.getB())))
				if(restricaoDeGrau(G1, aresta))
					if(restricaoDeDmax(G1, aresta)){
						G.addElem(aresta.clone());
					}
			if(G1.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		edgs = gutil.randomK(getK(G2), arestas);
		
		for (Aresta<T> aresta : edgs) {
			if(!G2.findSet(aresta.getA()).equals(G2.findSet(aresta.getB())))
				if(restricaoDeGrau(G2, aresta))
					if(restricaoDeDmax(G2, aresta)){
						G2.addElem(aresta.clone());
					}
			if(G2.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
	}
	
	/**
	 * @param f
	 * @return
	 */
	private int getK(Grafo<T> G) {
		return G.getNumAresta()/alpha;
	}


	public Grafo<T> mutation(Grafo<T> grafo , Grafo<T> G){
		Random randomico = new Random();

		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if (!G.hasAresta(aresta)) {
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						arestas.add(aresta.clone());
					}
			}
		}
		
		if(!arestas.isEmpty()){
			//GrafosUtil<T> gutil = new GrafosUtil<T>();
			//ArrayList<Aresta<T>> edgs = gutil.randomK(arestas.size()/alpha,arestas);
			int index = Math.abs(randomico.nextInt() % arestas.size());
			Aresta<T> edgAdd = arestas.get(index).clone();
			G.addElem(edgAdd);
			G = deleteEdgCiclo(G,edgAdd);
		}
		return G;
	}
	
	public Grafo<T> deleteEdgCiclo(Grafo<T> grafo , Aresta<T> aresta)  {
		
		Grafo<T> result = new Grafo<T>();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(grafo.getArestas());
		
		if(diversividade) result.addElem(aresta.clone());
		
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg =  nohHeap.getInfo().clone();
			
			if(!result.findSet(edg.getA()).equals(result.findSet(edg.getB()))){
				if(restricaoDeGrau(result, edg)){
					if(restricaoDeDmax(result, edg)){
							result.addElem(edg.clone());
					}
				}
			}
		}
		
		return result;

	}
	


	public HeapFibonacci<Grafo<T>> gerarPopulacao(Grafo<T> grafo) {
		HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
        int i=0;
       
        while (i < populacaoInit ){
        	Grafo<T>  grf = getInit(grafo);
			grafos.inserir(grf.getPesoTotal(),grf);
			i++;
		}       
        
		return grafos;
	}


	/**
	 * @param pais
	 * @return
	 */
	private HeapFibonacci<Grafo<T>> reproducao(Grafo<T> grafo, ArrayList<Grafo<T>> pais ,int K) {
		HeapFibonacci<Grafo<T>> newPopulacao = new HeapFibonacci<Grafo<T>>();
		int tam = pais.size();
		
		List<Grafo<T>> pai = pais.subList(0,(tam/2));
		List<Grafo<T>> mae = pais.subList( (tam/2) ,tam);
		int total = (tam/2);
		
		if(tam>1){
		for (int i=0; i < total ; i++) {
				Collections.shuffle(pai);
			    Collections.shuffle(mae);
			
				Grafo<T> G1 = pai.get(i);
				Grafo<T> G2 = mae.get(i);
			
				crossOver(grafo, G1, G2 );
				G1 = mutation(grafo, G1 );
				G2 = mutation(grafo, G2 );

				newPopulacao.inserir(G1.getPesoTotal(),G1);
				newPopulacao.inserir(G2.getPesoTotal(),G2);
		}
		}else{
			Grafo<T> G1 =mae.get(0);
			crossOver(grafo, G1, G1 );
			G1 = mutation(grafo, G1 );
			newPopulacao.inserir(G1.getPesoTotal(),G1);
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
	
	/**
	 * 
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		HeapFibonacci<Grafo<T>> populacaoLocal;
		HeapFibonacci<Grafo<T>> populacaoGlobal = new HeapFibonacci<Grafo<T>>();
		ArrayList<Grafo<T>> pais = new ArrayList<Grafo<T>>();
		result = new Grafo<T>();
		int    i = 1 , K = grafo.getNumVertice();
 		double tempInicio = 0;
        double tempFim = 0;
        tempInicio = System.currentTimeMillis();

		populacaoLocal = gerarPopulacao(grafo);

        do {
			K-=alpha;
			System.out.println(populacaoInit+"\t"+tempFim/1000 +"\t"+ K  +"\t"+ populacaoLocal.getMinNoh().getInfo().getPesoTotal());
			
			pais = selecao(populacaoLocal);
			populacaoLocal = reproducao(grafo, pais, K);
			
			populacaoGlobal.inserir(populacaoLocal.getMinNoh().getInfo()
					.getPesoTotal(), populacaoLocal.getMinNoh().getInfo()
					.clone());
			i++;
			tempFim = System.currentTimeMillis();		
			
		} while(tempFim - tempInicio < tempo);
        result = populacaoGlobal.extrairMin().getInfo();
		return result;
	}

	/**
	 * @param diversividade the diversividade to set
	 */
	public void setDiversividade(boolean diversividade) {
		this.diversividade = diversividade;
	}


	/**
	 * @return the diversividade
	 */
	public boolean isDiversividade() {
		return diversividade;
	}


	/**
	 * @param populacao the populacao to set
	 */
	public void setPopulacao(int populacao) {
		this.populacaoInit = populacao;
	}


	/**
	 * @return the populacao
	 */
	public int getPopulacao() {
		return populacaoInit;
	}


	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		GrafosUtil<String> gutil = new GrafosUtil<String>();
		Grafo<String> grf = gutil.fileToGrafo("files/serrinha2009.txt");
		Genetico<String> gen = new Genetico<String>();
		Prim<String> prim = new Prim<String>(); 
		Kruskal<String> kru = new Kruskal<String>();
		
		gutil.telaGrafos(grf).setVisible(true);
		gutil.telaGrafos(gen.obterAGM(grf)).setVisible(true);	
		gutil.telaGrafos(kru.obterAGM(grf)).setVisible(true);
		


	}
}
