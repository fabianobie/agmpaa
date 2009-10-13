/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import br.uece.comp.paa.agm.Boruvka;
import br.uece.comp.paa.agm.DFS;
import br.uece.comp.paa.agm.KAgm;
import br.uece.comp.paa.agm.Kruskal;
import br.uece.comp.paa.agm.Prim;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.util.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private String nome="GRAFO_PAA";
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	private int numAresta=0;
	
	/**
	 * Encontra o elemento representante(pai) do conjunto de V
	 * @param V
	 * @return
	 */
	public  Vertice<T> findSet(Vertice<T> V){
		Vertice<T> pai;
		int id = getIdVertice(V);
		if(id!=-1){
			pai = vertices.get(id).getPai();
		}else{
			pai  = V.clone();
		}	
		return pai; 
	}
	
	/**
	 * torna todo os vertices disjunto
	 */
	public void makeSet(){
		for (Vertice<T> vertice : vertices) {
			this.makeSet(vertice);
		}
	}

	/**
	 * Torna o vertice o elemento representatante do conjunto
	 * @param V
	 */
	public void makeSet(Vertice<T> V) {
		V.setPai(V);
	}
	

	/**
	 * Executa a união de dois vertices no mesmo conjunto
	 * @param v1
	 * @param v2
	 */
	public void union(Vertice<T> v1, Vertice<T> v2) {
		//encontra o pai de cada
		Vertice<T> i = findSet(v1);
		Vertice<T> j = findSet(v2);
		
		// verifica a ordem do vertice representate do conjunto para tomar
		// a decisao de quem vai respresentar e qnto tempo representa
		if (i.getOrdem() == 0 && j.getOrdem() == 0) {
			v2.setPai(v1.getPai());
			i.setOrdem(1);
		} else if (i.getOrdem() == 0) {
			v1.setPai(v2.getPai());
			j.setOrdem(j.getOrdem() + 1);
		} else if (j.getOrdem() == 0) {
			v2.setPai(v1.getPai());
			i.setOrdem(i.getOrdem() + 1);
		} else {
			if (i.getOrdem() >= j.getOrdem()) {
				for (Vertice<T> V : vertices) {
					if (V.getPai().equals(j)) {
						V.setPai(i);
						i.setOrdem(i.getOrdem() + 1);
					}
				}
			} else {
				for (Vertice<T> V : vertices) {
					if (V.getPai().equals(i)) {
						V.setPai(j);
						j.setOrdem(j.getOrdem() + 1);
					}
				}
			}
		}

	}

	/**
	 * União de grafos G1 U G2 = G
	 * @param grafo
	 * @return
	 */
	public  Grafo<T> union(Grafo<T> grafo){
		Grafo<T> result = new Grafo<T>();
		//add todas as arestas de G1
		for (Aresta<T> aresta : this.getArestas()) {
				result.addElem(aresta.clone());
		}
		//add todas as arestas de G2
		for (Aresta<T> aresta : grafo.getArestas()) {
				result.addElem(aresta.clone());
		}
		
		return result;
	}
	
	/**
	 * Interceção: G1 interceção G2 = as aresta que estaso em G1 e G2
	 * @param grafo
	 * @return
	 */
	public  Grafo<T> intersec(Grafo<T> grafo){
		Grafo<T> result = new Grafo<T>();
		
		for (Vertice<T> vertice : this.getVertices()) {
			int id = grafo.getIdVertice(vertice);
			if(id!=-1){
				for (Aresta<T> aresta: vertice.getListAdj()) {
					for (Aresta<T> outraAresta: vertice.getListAdj()) {
						if(aresta.equals(outraAresta))
							result.addElem(aresta.clone());
							
					}
				}
			}
			
		}

		return result;
	}
	
	public  int getNumVertice(){
		return vertices.size();
	}
	
	/**
	 * Dado um subconjunto de Grafo retorna todas arestas d subconjunto
	 * @param grafo
	 * @return
	 */
	public  ArrayList<Vertice<T>> getSubSet(Grafo<T> grafo ){
		ArrayList<Vertice<T>> result = new ArrayList<Vertice<T>>(); 
		
		for (Vertice<T> v1 : vertices) {
			for (Vertice<T> vertice : grafo.getVertices()) {
				if(v1.equals(vertice)){
					result.add(v1);
					break;
				}
			}
		}
		return result;
	}
	
	
	/**
	 * Adiciona uma aresta
	 * @param va
	 * @param vb
	 * @param peso
	 */
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	/**
	 * Adiciona uma aresta/vertice
	 * @param edg
	 */
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
		boolean hasA=false, hasB=false;
		
		//verifica se existe determinada aresta
		if(!hasAresta(edg)){
			int ia , ib;
			
			// verifica se ha os vertices
			if (!hasVertice(a))
				this.vertices.add(a);
			else
				hasA = true;

			if (!hasVertice(b))
				this.vertices.add(b);
			else
				hasB = true;		
			
			// recupera a psoição do vertice
			ia = getIdVertice(a);
			ib = getIdVertice(b);
			
			Vertice<T> va = vertices.get(ia);
			Vertice<T> vb = vertices.get(ib);
			
			//detremina quem sera seu representante
			if(!hasA)
				makeSet(va);
			if(!hasB)
				makeSet(vb);
			//cria aresta
			Aresta<T> nedg = new Aresta<T>(va,vb,edg.getPeso());
			
			//faz a uniao de vertices
			union(va,vb);
			
			//adiciona a lista de adjacencia
			va.addAdj(nedg);
			vb.addAdj(nedg);
			
			//contabiliza numero de aresta
			numAresta++;
		}
	}
	
	/**
	 * Adicionar por vertice
	 * @param vrtx
	 */
	public void addElem(Vertice<T> vrtx){
		if(!hasVertice(vrtx)){
			this.vertices.add(vrtx);
		}
	}
	
	/**
	 *Adicionar por um array de vertices
	 * @param vertices1
	 */
	public void addElem(ArrayList<Vertice<T>> vertices1) {
		for (Vertice<T> vertice : vertices1) {
			addElem(vertice);
		}
	}
	
	
	/**
	 * Verifica a exitencia de um vertice
	 * @param vrtx
	 * @return
	 */
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	/**
	 * Olha se grafo tem a aresta
	 * @param edg
	 * @return
	 */
	public boolean hasAresta(Aresta<T> edg){
		for (Vertice<T> V : vertices) {
			if(V.equals(edg.getA()) || V.equals(edg.getB())){
				for (Aresta<T> E : V.getListAdj()) {
					if(E.equals(edg)) return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * recupera a posição do vertice
	 * @param vrtx
	 * @return
	 */
	public int getIdVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	
	/**
	 * Arestas do grafo na forma de Array
	 * @return
	 */
	public ArrayList<Aresta<T>> getArestas(){
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		ArrayList<Vertice<T>> vrtxs = new ArrayList<Vertice<T>>();
		for (Vertice<T> V : this.getVertices()){
			vrtxs.add(V);
			for (Aresta<T> E : V.getListAdj()){
				boolean a,b;
				a=vrtxs.contains(E.getA());
				b=vrtxs.contains(E.getB());
				if (!(a && b)) {
					arestas.add(E);
				}
			}
		}
		return arestas;
	}
	
	/**
	 * Arestas do grafo na forma de Heap
	 * @return
	 */
	public HeapFibonacci<Aresta<T>> getHeapAresta(){
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>();
		ArrayList<Vertice<T>> vrtxs = new ArrayList<Vertice<T>>();
		for (Vertice<T> V : this.getVertices()){
			vrtxs.add(V);
			for (Aresta<T> E : V.getListAdj()){
				boolean a,b;
				a=vrtxs.contains(E.getA());
				b=vrtxs.contains(E.getB());
				if (!(a && b)) {
					arestas.inserir(E.getPeso(),E);
				}
			}
		}
		return arestas;
	}

	/**
	 * Verifica se a aresta fora do grafo faz um ciclo ao ser adicionada
	 * @param aresta
	 * @return
	 */
	public boolean doCiclo(Aresta<T> aresta) {
		if(this.findSet(aresta.getA()).equals(this.findSet(aresta.getB())))
			return true;
		else
			return false;
	}

	/**
	 * Deleta uma aresta
	 * @param edg
	 */
	public void deleteEdge(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();

		//verifica se tem a aresta
		if(hasAresta(edg)){
			//remove da lista de adjacencia
			int ia = getIdVertice(a);
			Vertice<T> v1 = vertices.get(ia);
			v1.getListAdj().remove(v1.getIdAresta(edg));
			int ib = getIdVertice(b);
			Vertice<T> v2 = vertices.get(ib);
			v2.getListAdj().remove(v2.getIdAresta(edg));
			numAresta--;
		}
	}
	
	/**
	 * Pega o peso Total da Grafo
	 * @return
	 */
	public Double getPesoTotal() {
		ArrayList<Aresta<T>> arestas = getArestas();
		Double pesoTotal = 0.0;
		for (Aresta<T> aresta : arestas) {
			pesoTotal += aresta.getPeso();
		}
		return pesoTotal;
	}

	/**
	 * get array de vertices
	 * @return
	 */
	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	/**
	 * @param vertices
	 */
	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}
	
	
	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return
	 */
	public int getNumAresta() {
		return numAresta;
	}

	/**
	 * @param numAresta
	 */
	public void setNumAresta(int numAresta) {
		this.numAresta = numAresta;
	}

	/* Impressão
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res = "Grafo[\n";
		for (Vertice<T> V : vertices) {
			res +="\t"+V.toString()+"\n";
		}
		res += "]";
		return res;
	}
	
	/* Igualdade
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Grafo<T> grafo = (Grafo<T>) obj;
		for (Aresta<T> aresta : grafo.getArestas()) {
			if(!this.hasAresta(aresta)) return false;
		}
		return true;
	}
	
	/* Copia
	 * @see java.lang.Object#clone()
	 */
	@Override
     public Grafo<T> clone(){
            
             Grafo<T> grafoNovo = new Grafo<T>();
             Aresta<T> arestaNova = null;
           
             for(Vertice<T> vertice: vertices){
                 for(Aresta<T> aresta : vertice.getListAdj()){
                     arestaNova = ((Aresta<T>) aresta.clone());
                     grafoNovo.addElem(arestaNova);
                 }
             }
             return grafoNovo;
     }


}

