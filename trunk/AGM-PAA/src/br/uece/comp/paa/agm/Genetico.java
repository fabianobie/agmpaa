/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;
import java.util.Random;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class Genetico<T> implements Iagm<T> {

	private Boolean rgrau = false;
	private int grauMax;
	private Boolean rdmax = false;
	private Double dMax;
	private Grafo<T> result;

	public Grafo<T> getInit(Grafo<T> grafo) {
		ArrayList<Aresta<T>> arestas = grafo.getArestas();
		Random randomico = new Random();
		result = new Grafo<T>();

		for (Vertice<T> vertice : grafo.getVertices()) {
			grafo.makeSet(vertice);
		}

		while (!arestas.isEmpty()) {
			int index = Math.abs(randomico.nextInt() % arestas.size());
			System.out.println(index);
			Aresta<T> edg = arestas.get(index);
			arestas.remove(index);

			if (!grafo.findSet(edg.getA()).equals(grafo.findSet(edg.getB()))) {
				if (restricaoDeGrau(result, edg)) {
					if (restricaoDeDmax(result, edg)) {
						result.addElem(edg.clone());
						grafo.union(edg.getA(), edg.getB());
					}
				}
			}
		}

		return result;
	}

	public ArrayList<Grafo<T>> gerarPopulacao(Grafo<T> grafo, int tempo) {
		ArrayList<Grafo<T>> grafos = new ArrayList<Grafo<T>>();

		for (int i = 0; i < tempo; i++) {
			grafos.add(getInit(grafo));
		}
		return grafos;
	}

	public Grafo<T> obterAGM(Grafo<T> grafo) {
		// TODO Auto-generated method stub
		return null;
	}

	public Grafo<T> obterAGM(Grafo<T> grafo, int grau, Double distMax) {
		if (grau >= 2) {
			this.rgrau = true;
			this.grauMax = grau;
		}

		if (distMax > 0) {
			this.rdmax = true;
			this.dMax = distMax;
		}

		return obterAGM(grafo);
	}

	/**
	 * @param result
	 * @param edg
	 * @return
	 */
	private boolean restricaoDeDmax(Grafo<T> result, Aresta<T> edg) {
		if (rdmax) {
			ArrayList<Aresta<T>> arestas = result.getArestas();
			Double distancia = 0.0;
			for (Aresta<T> aresta : arestas) {
				distancia += aresta.getPeso();
			}
			if (distancia + edg.getPeso() < dMax)
				return true;
			else
				return false;
		} else
			return true;
	}

	/**
	 * @param result
	 * @param edg
	 */
	private boolean restricaoDeGrau(Grafo<T> result, Aresta<T> edg) {
		if (rgrau) {
			ArrayList<Vertice<T>> vertices = result.getVertices();
			int id = -1;
			if (result.getIdVertice(edg.getA().clone()) != -1) {
				id = result.getIdVertice(edg.getA().clone());
			} else {
				id = result.getIdVertice(edg.getB().clone());
			}
			if (id != -1) {
				Vertice<T> vertice = vertices.get(id);
				if (vertice.getListAdj().size() + 1 > this.grauMax)
					return false;
				else
					return true;
			} else
				return true;
		} else {
			return true;
		}
	}

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

}
