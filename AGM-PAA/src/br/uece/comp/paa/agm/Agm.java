/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.util.Metricas;

/**
 * Abstrct Class AGM: possui o metodos primitivos de obtenção de uma AGM,
 * qualquer algoritmo de AGM deve herdar suas caracteristicas
 */
/**

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 * @param <T>
 */
abstract class Agm<T> implements Iagm<T> {
	/*
	 * Define se ha restrição de Grau
	 */
	protected Boolean rgrau = false;
	
	/*
	 * Define o grau maximo dos vertices
	 */
	protected int grauMax;
	
	/*
	 * Define se ha restrição de Dmax
	 */
	protected Boolean rdmax = false;
	
	/*
	 * Define o distancia maxima das arestas
	 */
	protected Double dMax;
	
	/*
	 * Possui o grafo resultado do obter AGM
	 */
	protected Grafo<T> result;
	
	/*
	 * Salva o tempo de execução e demais metricas
	 */
	protected Metricas metrica = new Metricas();

	/*
	 * Obtem a Arvore Geradora Minima
	 * 
	 */
	public Grafo<T> obterAGM(Grafo<T> grafo, int grau, Double distMax) {
		//verifica se ha restrição de grau
		if (grau >= 2) {
			this.rgrau = true;
			this.grauMax = grau;
		}
		
		//verifica se ha restrição dMax
		if (distMax > 0) {
			this.rdmax = true;
			this.dMax = distMax;
		}
		//iniicaliza a metrificação
		metrica.start();
			//obtem a AGM 
			Grafo<T>  G = obterAGM(grafo);
		//marca o tempo final
		metrica.finish();
		return G;
	}

	/**
	 * Metodo geral eu verifica se a proxima aresta quebra a restrição de Dmax
	 * @param result
	 * @param edg
	 * @return
	 */
	public boolean restricaoDeDmax(Grafo<T> result, Aresta<T> edg) {
		if (rdmax) {
			ArrayList<Aresta<T>> arestas = result.getArestas();
			Double distancia = 0.0;
			for (Aresta<T> aresta : arestas) {
				distancia += aresta.getPeso();
			}
			if (distancia + edg.getPeso() <= dMax)
				return true;
			else
				return false;
		} else
			return true;
	}

	/**
	 * Metodo geral eu verifica se a proxima aresta quebra a restrição de Grau
	 * @param result
	 * @param edg
	 */
	public boolean restricaoDeGrau(Grafo<T> result, Aresta<T> edg) {
		if (rgrau) {
			boolean okA = true, okB = true;
			ArrayList<Vertice<T>> vertices = result.getVertices();
			int idA = -1;
			int idB = -1;

			idA = result.getIdVertice(edg.getA().clone());
			idB = result.getIdVertice(edg.getB().clone());

			if (idA != -1)
				if (vertices.get(idA).getListAdj().size() + 1 > this.grauMax)
					okA = false;
			if (idB != -1)
				if (vertices.get(idB).getListAdj().size() + 1 > this.grauMax)
					okB = false;
			if (okA && okB)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Getteres e Setters para obtenção dos atributos da AGM
	 * @return
	 */
	public Boolean getRgrau() {
		return rgrau;
	}

	public void setRgrau(Boolean rgrau) {
		this.rgrau = rgrau;
	}

	public int getGrauMax() {
		return grauMax;
	}

	public void setGrauMax(int grauMax) {
		this.grauMax = grauMax;
	}

	public Boolean getRdmax() {
		return rdmax;
	}

	public void setRdmax(Boolean rdmax) {
		this.rdmax = rdmax;
	}

	public Double getdMax() {
		return dMax;
	}

	public void setdMax(Double dMax) {
		this.dMax = dMax;
	}
	
	public Grafo<T> getResult() {
		return result;
	}

	public void setResult(Grafo<T> result) {
		this.result = result;
	}

	public Metricas getMetrica() {
		return metrica;
	}

	public void setMetrica(Metricas metrica) {
		this.metrica = metrica;
	}
	

}
