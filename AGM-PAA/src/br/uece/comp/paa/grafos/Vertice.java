/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.util.ArrayList;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */

public class Vertice<T> {
	//Informação
	private T info;
	
	//Posição
	private Double posX;
	private Double posY;
	
	//lista de adjacencia
	private ArrayList<Aresta<T>> listAdj = new ArrayList<Aresta<T>>();
	private Vertice<T> pai = this;
	
	//ordem do pai
	private int ordem;
	
	//pega o representante
	public Vertice<T> getPai() {
		return pai;
	}
	
	//marca o representante
	public void setPai(Vertice<T> pai) {
		this.pai = pai;
	}

	/**
	 * @param string
	 * @param i
	 */
	public Vertice(T info) {
		this.info = info;
	}

	/**
	 * @param info
	 * @param posX
	 * @param posY
	 */
	public Vertice(T info, Double posX, Double posY) {
		this.posX = posX;
		this.posY = posY;
		this.info = info;
	}

	/**
	 * @return
	 */
	public T getInfo() {
		return info;
	}

	/**
	 * @param info
	 */
	public void setInfo(T info) {
		this.info = info;
	}

	/**
	 * @return
	 */
	public ArrayList<Aresta<T>> getListAdj() {
		return listAdj;
	}

	/**
	 * @param listAdj
	 */
	public void setListAdj(ArrayList<Aresta<T>> listAdj) {
		this.listAdj = listAdj;
	}

	/**
	 * @return
	 */
	public Double getPosX() {
		return posX;
	}

	/**
	 * @param posX
	 */
	public void setPosX(Double posX) {
		this.posX = posX;
	}

	/**
	 * @return
	 */
	public Double getPosY() {
		return posY;
	}

	/**
	 * @param posY
	 */
	public void setPosY(Double posY) {
		this.posY = posY;
	}

	/**
	 * @param edg
	 */
	public void addAdj(Aresta<T> edg) {
		listAdj.add(edg);
	}

	/**
	 * @param edg
	 * @return
	 */
	public int getIdAresta(Aresta<T> edg) {
		for (int j = 0; j < this.getListAdj().size(); j++) {
			if (this.getListAdj().get(j).equals(edg))
				return j;
		}
		return -1;
	}

	/**
	 * @return
	 */
	public int getGrau() {
		return listAdj.size();
	}

	/**
	 * @return
	 */
	public int getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem
	 */
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Vertice<T> vrtx = (Vertice<T>) obj;

		if (vrtx.getInfo().equals(info))
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String res = "V [info=" + info + ",Pai=" + pai.info + " listAdj=[";
		for (Aresta<T> e : listAdj) {
			res += e.toString();
		}
		res += "] ]";
		return res;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Vertice<T> clone() {
		Vertice<T> cln = new Vertice<T>(info);
		cln.posX = posX;
		cln.posY = posY;
		cln.pai = pai;
		cln.ordem = ordem;
		return cln;
	}

}
