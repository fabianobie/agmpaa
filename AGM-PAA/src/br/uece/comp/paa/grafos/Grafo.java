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

import br.uece.comp.paa.agm.Kruskal;
import br.uece.comp.paa.agm.Prim;
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
				if (!(a && b) || (!a && b) || (a && !b)) {
					arestas.add(E);
				}
			}
		}
		return null;
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

	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Grafo<String> grf = GrafosUtil.fileToGrafo("files/grafo.txt");
		System.out.println(grf);
		Kruskal<String> kru = new Kruskal<String>();
		System.out.println(grf);
		JGraph graph = GrafosUtil.desenhaGrafo(grf);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.setBounds(10, 10, 500, 600);
		frame.pack();
		frame.setVisible(true);
		
		
		Vertice<String> a = new Vertice<String>("D"); 
		Vertice<String> b = new Vertice<String>("C");
		grf.deleteEdge(new Aresta<String>(a, b, 1.0));
	    JGraph graph2 = GrafosUtil.desenhaGrafo(grf);
		JFrame frame2 = new JFrame();
		frame2.getContentPane().add(new JScrollPane(graph2));
		frame2.setBounds(10, 10, 500, 600);
		frame2.pack();
		frame2.setVisible(true);
	
	}
	
}
