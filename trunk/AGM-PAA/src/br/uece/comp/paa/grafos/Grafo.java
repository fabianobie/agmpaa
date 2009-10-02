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
import br.uece.comp.paa.agm.Genetico;
import br.uece.comp.paa.agm.KAgm;
import br.uece.comp.paa.agm.Kruskal;
import br.uece.comp.paa.agm.Prim;
import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private String nome="GRAFO_PAA";
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	private int numAresta=0;
	
	
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
	
	public void makeSet(){
		for (Vertice<T> vertice : vertices) {
			this.makeSet(vertice);
		}
	}

	public void makeSet(Vertice<T> V) {
		V.setPai(V);
	}

	public void union(Vertice<T> v1, Vertice<T> v2) {
		Vertice<T> i = findSet(v1);
		Vertice<T> j = findSet(v2);
		if (i.getOrdem() == 0 &&  j.getOrdem() == 0) {
			v2.setPai(v1.getPai());
			i.setOrdem(1);
		} else if (i.getOrdem() == 0) {
			v1.setPai(v2.getPai());
			j.setOrdem(j.getOrdem()+1);
		} else if (j.getOrdem() == 0) {
			v2.setPai(v1.getPai());
			i.setOrdem(i.getOrdem()+1);
		} else {
			if (i.getOrdem() >= j.getOrdem()) {
				for (Vertice<T> V : vertices) {
					if(V.getPai().equals(j)){
						V.setPai(i);
						i.setOrdem(i.getOrdem()+1);
					}
				}
			} else {
				for (Vertice<T> V : vertices) {
					if(V.getPai().equals(i)){
						V.setPai(j);
						j.setOrdem(j.getOrdem()+1);
					}
				}
			}
		}

	}

	public  Grafo<T> union(Grafo<T> grafo){
		Grafo<T> result = new Grafo<T>();
		
		for (Aresta<T> aresta : this.getArestas()) {
				result.addElem(aresta.clone());
		}
		for (Aresta<T> aresta : grafo.getArestas()) {
				result.addElem(aresta.clone());
		}
		
		return result;
	}
	
	public  Grafo<T> intersec(Grafo<T> grafo){
		Grafo<T> result = new Grafo<T>();
		
		for (Aresta<T> aresta : this.getArestas()) {
			for (Aresta<T> outraAresta : grafo.getArestas()) {
				if(aresta.equals(outraAresta)){
					result.addElem(aresta.clone());
					result.union(aresta.getA(),aresta.getB());
				}
			}
		}
		
		return result;
	}
	
	public  int getNumVertice(){
		return vertices.size();
	}
	
	public  ArrayList<Vertice<T>> getSubSet(Grafo<T> grafo ){
		ArrayList<Vertice<T>> result = new ArrayList<Vertice<T>>(); 
		
		for (Vertice<T> v1 : vertices) {
			for (Vertice<T> vertice : grafo.getVertices()) {
				if(v1.equals(vertice))
					result.add(v1);
			}
		}
		return result;
	}
	
/*	public ArrayList<Grafo<T>> obterKAGMS(){
		
		ArrayList<Grafo<T>> grafos = new ArrayList<Grafo<T>>();
		Kruskal<T> kru = new Kruskal<T>();
		Grafo<T> gtemp = this.clone();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		
		Grafo<T> gmin = kru.obterAGM(gtemp);
		grafos.add(gmin.clone());
		gutil.telaGrafos(gmin.clone());
		
		listAGM(grafos , gtemp, gmin); 
		
		return grafos;
	}

	private void listAGM(ArrayList<Grafo<T>> grafos, Grafo<T> gtemp, Grafo<T> gmin){
		Kruskal<T> kru = new Kruskal<T>();
		GrafosUtil<T> gutil = new GrafosUtil<T>();
		HeapFibonacci<Aresta<T>> heapMin = gutil.arestaToHeap(gmin.getArestas());
		ArrayList<Aresta<T>> arestasMin = gutil.heapToAresta(heapMin);
		
		if(gtemp.getArestas().size() == getArestas().size() - (getVertices().size()-2)) return;
			
		for (int i = arestasMin.size() - 1; i >= 0; i--) {
		
			Aresta<T> arestaMax = arestasMin.get(i);
			gtemp.deleteEdge(arestaMax);
			gmin = kru.obterAGM(gtemp);
			if(gmin.getVertices().size()==getVertices().size() &&  !gutil.hasGrafo(grafos,gmin)){
				grafos.add(gmin.clone());
				gutil.telaGrafos(gmin.clone());
				listAGM(grafos, gtemp, gmin);
				gtemp.addElem(arestaMax);
			}else{
				return;
			}
		}
		return;
	}
	*/
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
		boolean hasA=false, hasB=false;
		if(!hasAresta(edg)){
			int ia , ib;
			
			if(!hasVertice(a)){
				this.vertices.add(a);
			}else{
				hasA = true;
			}
			
			if(!hasVertice(b)){
				this.vertices.add(b);
			}else{
				hasB =true;	
			}
			
			ia = getIdVertice(a);
			ib = getIdVertice(b);
			
			Vertice<T> va = vertices.get(ia);
			Vertice<T> vb = vertices.get(ib);
			
			if(!hasA)
				makeSet(va);
			if(!hasB)
				makeSet(vb);
			
			Aresta<T> nedg = new Aresta<T>(va,vb,edg.getPeso());
			
			union(va,vb);
			
			va.addAdj(nedg);
			vb.addAdj(nedg);
			
			numAresta++;
		}
	}
	
	public void addElem(Vertice<T> vrtx){
		if(!hasVertice(vrtx)){
			this.vertices.add(vrtx);
		}
	}
	
	/**
	 * @param vertices1
	 */
	public void addElem(ArrayList<Vertice<T>> vertices1) {
		for (Vertice<T> vertice : vertices1) {
			addElem(vertice);
		}
	}
	
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public boolean hasAresta(Aresta<T> edg){
		for (Vertice<T> V : vertices) {
			for (Aresta<T> E : V.getListAdj()) {
				if(E.equals(edg)) return true;
			}
		}
		return false;
	}
	
	public int getIdVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	
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

	public boolean doCiclo(Aresta<T> aresta) {
		boolean a, b;
		a = hasVertice(aresta.getA());
		b = hasVertice(aresta.getB());

		if ((a && b))
			return true;
		else
			return false;
	}

	public void deleteEdge(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();

		if(hasAresta(edg)){
			int ia = getIdVertice(a);
			Vertice<T> v1 = vertices.get(ia);
			v1.getListAdj().remove(v1.getIdAresta(edg));
			int ib = getIdVertice(b);
			Vertice<T> v2 = vertices.get(ib);
			v2.getListAdj().remove(v2.getIdAresta(edg));
			//if(v1.getListAdj().isEmpty()) vertices.remove(v1);
			//if(v2.getListAdj().isEmpty()) vertices.remove(v2);
			numAresta--;
		}
	}
	
	public Double getPesoTotal() {
		ArrayList<Aresta<T>> arestas = getArestas();
		Double pesoTotal = 0.0;
		for (Aresta<T> aresta : arestas) {
			pesoTotal += aresta.getPeso();
		}
		return pesoTotal;
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumAresta() {
		return numAresta;
	}


	@Override
	public String toString() {
		String res = "Grafo[\n";
		for (Vertice<T> V : vertices) {
			res +="\t"+V.toString()+"\n";
		}
		res += "]";
		return res;
	}
	
	@Override
	public boolean equals(Object obj) {
		Grafo<T> grafo = (Grafo<T>) obj;
		for (Aresta<T> aresta : grafo.getArestas()) {
			if(!this.hasAresta(aresta)) return false;
		}
		return true;
	}
	
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

	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
		
		
		
		String[] arqs = {"palmeiras2008.txt","quadrado2009.txt","serrinha2009.txt","DONI22009.txt","DONI12009.txt"};
		
		for (String file : arqs) {
			GrafosUtil<String> gutil = new GrafosUtil<String>();
			Grafo<String> grf = gutil.fileToGrafo("files/"+file);
			
			System.out.println("Leu Arquivo...");
			
			Boruvka<String> bor = new Boruvka<String>();
			Kruskal<String> kru = new Kruskal<String>();
			Prim<String> prim = new Prim<String>();
			DFS<String> dfs = new DFS<String>();
			
			System.out.println(dfs.isConexo(grf));
			
			Grafo<String> grafo;
			grafo  = prim.obterAGM(grf,0,0.0);
			System.out.println("\n"+file+"--------------------------------------------------");
			System.out.println("Algoritmo | PesoTotal | TempoTotal | numVetices\n");
			System.out.println("PRIM  | "+ (grafo.getPesoTotal()) + " | " + prim.getMetrica().getTempoTotal() + " | "+ grafo.getVertices().size() +" \n");
			grafo  = kru.obterAGM(grf,0,0.0);
			System.out.println("KRUSKAL | "+ (grafo.getPesoTotal()) + " | " + kru.getMetrica().getTempoTotal() + " | "+ grafo.getVertices().size() +" \n");
			grafo  = bor.obterAGM(grf,0,0.0);
			System.out.println("BORUVKA | "+ (grafo.getPesoTotal()) + "| " + bor.getMetrica().getTempoTotal() + " | "+ grafo.getVertices().size() +" \n");
		
		}
			

	}

}

