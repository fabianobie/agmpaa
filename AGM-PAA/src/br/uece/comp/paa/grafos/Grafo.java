/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
	
		if(!hasVertice(a)){
			a.addAdj(edg);
			vertices.add(a);
		}else{
			int i = getVertice(a);
			vertices.get(i).addAdj(edg);
		}
	
		if(!hasVertice(b)){
			b.addAdj(edg);
			vertices.add(b);
		}else{
			int i = getVertice(b);
			vertices.get(i).addAdj(edg);
		}
	}
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public int getVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	public void deleteEdge(Aresta<T> edg){
		edg.getA().getListAdj().remove(edg.getB());
		edg.getB().getListAdj().remove(edg.getA());
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
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
		Grafo<String> grf = new Grafo<String>();
		
		String fgrafo = "A B 10.0\nC B 20.0\nA C 5.0";
		Scanner sc = new Scanner(fgrafo);
	
		
		while(sc.hasNext()){
			String sv1 = sc.next();
			String sv2 = sc.next();
			String peso = sc.next();
			Vertice<String> v1 = new Vertice<String>(sv1);
			Vertice<String> v2 = new Vertice<String>(sv2);
			grf.addEdge(v1, v2, Double.parseDouble(peso));
		}
		

		System.out.println(grf);
		
	}
	
}
