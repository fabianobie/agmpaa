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
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;

import br.uece.comp.paa.agm.Boruvka;
import br.uece.comp.paa.agm.DFS;
import br.uece.comp.paa.agm.Kruskal;
import br.uece.comp.paa.agm.Prim;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private String nome="GRAFO_PAA";
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	private int numAresta=0;
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
		if(!hasAresta(edg)){
			int ia , ib;
	
			if(!hasVertice(a))
				addElem(a);
			if(!hasVertice(b))
				addElem(b);
			
			ia = getIdVertice(a);
			ib = getIdVertice(b);
			
			Aresta<T> nedg = new Aresta<T>(vertices.get(ia),vertices.get(ib),edg.getPeso());
			
			vertices.get(ia).addAdj(nedg);
			vertices.get(ib).addAdj(nedg);
			
			numAresta++;
		}
	}
	
	public void addElem(Vertice<T> vrtx){
		if(!hasVertice(vrtx))
			this.vertices.add(vrtx);
	}
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public boolean hasAresta(Aresta<T> edg){
			Vertice<T> a = edg.getA(); 
			Vertice<T> b = edg.getB();
			
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
			for (Aresta<T> E : V.getListAdj()) {
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
			numAresta--;
		}
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

	public void setNumAresta(int numAresta) {
		this.numAresta = numAresta;
	}

	@Override
	public String toString() {
		String res = "Grafo[\n";
		for (Vertice<T> V : vertices) {
			res +="\t"+V.toString()+"\n";
		}
		res += " ]";
		return res;
	}
	
	
	public ArrayList<Grafo<T>> obterKAGMS()
			throws CloneNotSupportedException {
		ArrayList<Grafo<T>> grafos = new ArrayList<Grafo<T>>();
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		ArrayList<Aresta<T>> arestasVisitadas = new ArrayList<Aresta<T>>();
		DFS<T> dfs = new DFS<T>();
		int contInterno = 1;
		int contExterno = 1;
		int numArestas = 7; // numero fornecido pelo arquivo.
		Boolean noVertice = false;
		Aresta<T> aresta = null;
		for (int j = 0; j < arestasVisitadas.size()
				|| arestasVisitadas.size() == 0; j++) {
			for (int i = 0; i < numArestas; i++) {
				System.out.println(i);
				for (Aresta<T> aresta1 : arestas) {

					this.deleteEdge((Aresta<T>) aresta1);

				}
				for (int n = 0; n < j; n++) {

					this.deleteEdge((Aresta<T>) arestasVisitadas.get(n));

				}
				if (obterArestaMinima(this) == null) {
					break;
				}
				
				Aresta<T> aux =(Aresta<T>) obterArestaMinima(this); 
				aresta = (Aresta<T>) aux.clone();

				this.deleteEdge(aresta);

				for (Aresta<T> aresta1 : arestas) {
					this.addElem((Aresta<T>) aresta1);
				}

				arestas.add((Aresta<T>) aresta);

				if (dfs.isConexo(this)) {
					System.out.println("adicionou retirando  a aresta de peso:"
							+ aresta.getPeso() + ", do vertice:"
							+ aresta.getA().getInfo() + "--"
							+ aresta.getB().getInfo());
					
					Kruskal kru = new Kruskal<T>(); 
					grafos.add(kru.obterAGM((Grafo<T>) this.clone()));

				} else
					;

			}
			System.out.println("adicionando aresta superior");
			if (arestasVisitadas.size() < 1) {
				arestasVisitadas = (ArrayList<Aresta<T>>) arestas.clone();
			}
			arestas = new ArrayList<Aresta<T>>();
		}
		return grafos;
	}

	/**
	 * @param grafo
	 * @return
	 */
	private Object obterArestaMinima(Grafo<T> grafo) {
		Aresta<T> retorno = null;
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>();
		ArrayList<Aresta<T>> edgs = grafo.getArestas();
		
		for (Aresta<T> E : edgs) {
			arestas.inserir(E.getPeso(), E);
		}
		
		// Laos que percorrem todos os vrtices e todas as arestas e obtm a
		// aresta com o peso mnimo.
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg;
			try {
				edg = (Aresta<T>) nohHeap.getInfo().clone();
				boolean a,b;
				a=grafo.hasVertice(edg.getA());
				b=grafo.hasVertice(edg.getB());
				if ((a || b) && !(a && b)) {
					retorno = edg;
					break;
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retorno;
	}
	
	 @Override
     public Object clone() throws CloneNotSupportedException {
            
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
		Grafo<String> grf = GrafosUtil.fileToGrafo("files/grafo.txt");
		System.out.println(grf);
		Boruvka<String> kru = new Boruvka<String>();
		System.out.println(grf);
		JGraph graph = GrafosUtil.desenhaGrafo(grf);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.setBounds(10, 10, 500, 600);
		frame.pack();
		frame.setVisible(true);
		
	    JGraph graph2 = GrafosUtil.desenhaGrafo(kru.obterAGM(grf));
		JFrame frame2 = new JFrame();
		frame2.getContentPane().add(new JScrollPane(graph2));
		frame2.setBounds(10, 10, 500, 600);
		frame2.pack();
		frame2.setVisible(true);
	}
	
}
