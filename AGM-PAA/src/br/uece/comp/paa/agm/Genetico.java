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
	private StringBuffer log = new StringBuffer();


	/**
	 * Gerar uma grafo randomicamente
	 * @param grafo
	 * @return
	 */
	public Grafo<T> getInit(Grafo<T> grafo) {
		ArrayList<Aresta<T>> arestas = grafo.getArestas();
		Grafo<T> result = new Grafo<T>();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		//randomiza valor inicial da população
		arestas = gutil.randomK(arestas.size()/(alpha/2), arestas);
	
		//adiciona valores
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
	
	
	
	/**
	 * Operação de cruzamento de Grafos
	 * @param grafo
	 * @param G1
	 * @param G2
	 */
	public void crossOver(Grafo<T>grafo ,Grafo<T> G1, Grafo<T> G2){
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		//gerar o template para o filho
		Grafo<T> G = G1.intersec(G2);
		Grafo<T> F = G1.union(G2);
		
		//randomiza aresta
		ArrayList<Aresta<T>> edgs = gutil.randomK(getK(F), F.getArestas());
		
		//adiciona as menores da Uniao a Interceção
		for (Aresta<T> aresta : edgs) {
			if(!G.doCiclo(aresta))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta.clone());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		//Arestas que sobraram
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB()))){
				arestas.add(aresta.clone());
			}
		}
		//cria filhos
		G1 = G.clone();
		G2 = G.clone();
		
		//randomiza aresta que faltam a serem adicionadas
		edgs = gutil.randomK(getK(G1), arestas);
		
		//addiciona respeitando as restrições
		for (Aresta<T> aresta : edgs) {
			if(!G1.doCiclo(aresta))
				if(restricaoDeGrau(G1, aresta))
					if(restricaoDeDmax(G1, aresta)){
						G1.addElem(aresta.clone());
					}
			if(G1.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		//randomiza aresta que faltam a serem adicionadas
		edgs = gutil.randomK(getK(G2), arestas);
		
		//addiciona respeitando as restrições
		for (Aresta<T> aresta : edgs) {
			if(!G2.doCiclo(aresta))
				if(restricaoDeGrau(G2, aresta))
					if(restricaoDeDmax(G2, aresta)){
						G2.addElem(aresta.clone());
					}
			if(G2.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}//addiciona respeitando as restrições
		
	}
	
	/**
	 * Fator de aleatoriedade
	 * @param f
	 * @return
	 */
	private int getK(Grafo<T> G) {
		return G.getNumAresta()/alpha;
	}


	/**
	 * Mutação de grafo
	 * @param grafo
	 * @param G
	 * @return
	 */
	public Grafo<T> mutation(Grafo<T> grafo , Grafo<T> G){
		Random randomico = new Random();
		
		//verifica arestas que não estao no grafo
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : grafo.getArestas()) {
			if (!G.hasAresta(aresta)) {
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						arestas.add(aresta.clone());
					}
			}
		}
		
		// randomiza a escolha dos menores e escolhe um pra adicionar, isso
		// gerara um ciclo entao eliminamos uma aresta maior do ciclo
		if(!arestas.isEmpty()){
			int index = Math.abs(randomico.nextInt() % arestas.size());
			Aresta<T> edgAdd = arestas.get(index).clone();
			//adiciona
			G.addElem(edgAdd);
			//deleta do ciclo
			G = deleteEdgCiclo(G,edgAdd);
		}
		return G;
	}
	
	/**
	 * Deleta Aresta de um Ciclo
	 * @param grafo
	 * @param aresta
	 * @return
	 */
	public Grafo<T> deleteEdgCiclo(Grafo<T> grafo , Aresta<T> aresta)  {
		//semelhante ao codigo do kruskal
		// mas adicionando diversividade
		// a diversividade que vai definir se podemos ou não eliminar a aresta
		// que accabou de ser adicionada
		
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
	


	/**
	 * Gerar a População inicial
	 * @param grafo
	 * @return
	 */
	public HeapFibonacci<Grafo<T>> gerarPopulacao(Grafo<T> grafo) {
		HeapFibonacci<Grafo<T>> grafos = new HeapFibonacci<Grafo<T>>();
        int i=0;
       
        //de acordo cm o parametro de pop inicial geramos a população
        while (i < populacaoInit ){
        	Grafo<T>  grf = getInit(grafo);
			grafos.inserir(grf.getPesoTotal(),grf);
			i++;
		}       
        
		return grafos;
	}



	/**
	 * Executa a resprodução entre os grafos
	 * @param grafo
	 * @param pais
	 * @return
	 */
	private HeapFibonacci<Grafo<T>> reproducao(Grafo<T> grafo, ArrayList<Grafo<T>> pais) {
		HeapFibonacci<Grafo<T>> newPopulacao = new HeapFibonacci<Grafo<T>>();
		int tam = pais.size();
		//Dib=vidimos a lista de Pais e Maes
		List<Grafo<T>> pai = pais.subList(0,(tam/2));
		List<Grafo<T>> mae = pais.subList( (tam/2) ,tam);
		
		int total = (tam/2);
		
		if(tam>1){
		for (int i=0; i < total ; i++) {
				//randomizando (gerando encontros)
				Collections.shuffle(pai);
			    Collections.shuffle(mae);
			
				Grafo<T> G1 = pai.get(i);
				Grafo<T> G2 = mae.get(i);
				//reprodução depois do encontro
				crossOver(grafo, G1, G2 );
				
				//mutação dos filhos gerados
				G1 = mutation(grafo, G1 );
				G2 = mutation(grafo, G2 );
				
				//insere o filho a nova geração
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
	 * Executa a seleção dos mais aptos(no caso as menores AGM)
	 * @param populacao
	 * @return
	 */
	private ArrayList<Grafo<T>> selecao(HeapFibonacci<Grafo<T>> populacao) {
		ArrayList<Grafo<T>>  grafos = new ArrayList<Grafo<T>>();
		int qtde = populacao.getNumNoh();
		
		//Executa a seleção dos mais aptos(no caso as menores AGM)
		for(int i=0; i< qtde ;i++){
			grafos.add(populacao.extrairMin().getInfo());
		}
		return grafos;
	}
	
	
	/**
	 * Algoritmo Genetico
	 * @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		//otimo local
		HeapFibonacci<Grafo<T>> populacaoLocal;
		//otimo global
		HeapFibonacci<Grafo<T>> populacaoGlobal = new HeapFibonacci<Grafo<T>>();

		//lista de pais
		ArrayList<Grafo<T>> pais = new ArrayList<Grafo<T>>();
		
		//menor grafo
		result = new Grafo<T>();
		
		//metricas de execução
		int    i = 1 ;
 		double tempInicio = 0;
        double tempFim = 0;
        tempInicio = System.currentTimeMillis();
        
        //Gerar população inical randomicamente
		populacaoLocal = gerarPopulacao(grafo);

        do {
        	//obtenho o minimo local
        	Grafo<T> grafoMin = populacaoLocal.getMinNoh().getInfo();
			log.append(populacaoInit+"\t"+i+"\t"+ grafoMin.getPesoTotal()+"\n");
			// seleção
			pais = selecao(populacaoLocal);
			//reprodução
			populacaoLocal = reproducao(grafo, pais);
			//otimo global
			populacaoGlobal.inserir(grafoMin.getPesoTotal() ,grafoMin.clone());
			//calculamos o tempo das gerações
			tempFim = System.currentTimeMillis();
			//numero de gerações
			i++;
		} while(tempFim - tempInicio < tempo);
        
        //extarimos o otimo global
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


	/**
	 * @return
	 */
	public double getTempo() {
		return tempo;
	}



	/**
	 * @param tempo
	 */
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}



	/**
	 * @return
	 */
	public int getAlpha() {
		return alpha;
	}



	/**
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}



	/**
	 * @return
	 */
	public int getPopulacaoInit() {
		return populacaoInit;
	}



	/**
	 * @param populacaoInit
	 */
	public void setPopulacaoInit(int populacaoInit) {
		this.populacaoInit = populacaoInit;
	}



	/**
	 * @return
	 */
	public StringBuffer getLog() {
		return log;
	}



	/**
	 * @param log
	 */
	public void setLog(StringBuffer log) {
		this.log = log;
	}

}
