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
	//Vertices
	private Vertice<T> A;
	private Vertice<T> B;
	
	//peso
	private Double peso;
	
	/**
	 * Construtor
	 * @param A
	 * @param B
	 * @param peso
	 */
	public Aresta(Vertice<T> A, Vertice<T> B, Double peso) {
		this.A = A;
		this.B = B;
		this.peso = peso;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "E [" + A.getInfo() + "-" + B.getInfo() + ":" + peso + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Aresta<T> clone(){
		Vertice<T> v1 =  A.clone();
		Vertice<T> v2 =  B.clone();
		Aresta<T> cln = new Aresta<T>(v1,v2,peso);
		return cln;
	}
	
	/**
	 * @return
	 */
	public Vertice<T> getA() {
		return A;
	}

	/**
	 * @param a
	 */
	public void setA(Vertice<T> a) {
		A = a;
	}

	/**
	 * @return
	 */
	public Vertice<T> getB() {
		return B;
	}

	/**
	 * @param b
	 */
	public void setB(Vertice<T> b) {
		B = b;
	}

	/**
	 * @return
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param peso
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	
	
}
