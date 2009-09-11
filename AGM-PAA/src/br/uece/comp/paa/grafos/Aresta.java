/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Aresta<T>{

	private Vertice<T> A;
	private Vertice<T> B;
	private Double peso;
	
	public Aresta(Vertice<T> A, Vertice<T> B, Double peso) {
		this.A = A;
		this.B = B;
		this.peso = peso;
	}

	public Vertice<T> getA() {
		return A;
	}

	public void setA(Vertice<T> a) {
		A = a;
	}

	public Vertice<T> getB() {
		return B;
	}

	public void setB(Vertice<T> b) {
		B = b;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "E [" + A.getInfo() + "-" + B.getInfo() + ":" + peso + "]";
	}

	@Override 
	public boolean equals(Object obj) {
		//if (obj instanceof Aresta)
			//return false;
		Aresta<T> edg = (Aresta<T>) obj;
		if ((edg.getA().equals(A) && edg.getB().equals(B))
				|| (edg.getB().equals(A) && edg.getA().equals(B)))
			return true;
		else
			return false;
	}
	
	
	
}
