/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Cear� - UECE
 * Alunos: Fabiano Tavares e Diego S�
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

	private Boolean rgrau = true;
	private int grauMax=2;
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
			Aresta<T> edg = arestas.get(index).clone();
			arestas.remove(index);

			if (!grafo.findSet(edg.getA()).equals(grafo.findSet(edg.getB()))) {
				if (restricaoDeGrau(result, edg)) {
					if (restricaoDeDmax(result,edg)) {
						result.addElem(edg.clone());
						grafo.union(edg.getA(), edg.getB());
					}
				}
			}
		}

		return result;
	}
	
	
	public Grafo<T> crossOver(Grafo<T>grafo ,Grafo<T> G1, Grafo<T> G2){
		Grafo<T> G = G1.intersec(G2);
		Grafo<T> F = G.union(G1, G2);
		
		//TODO: randomizar os menores 
		for (Aresta<T> aresta : F.getArestas()) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB())))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta);
						G.union(aresta.getA(),aresta.getB());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				return G;
		}
		
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : G.getArestas()) {
			if(!grafo.findSet(aresta.getA()).equals(grafo.findSet(aresta.getB()))){
				arestas.add(aresta.clone());
			}
		}
		
		//TODO: randomizar os menores 
		for (Aresta<T> aresta : arestas) {
			if(!G.findSet(aresta.getA()).equals(G.findSet(aresta.getB())))
				if(restricaoDeGrau(G, aresta))
					if(restricaoDeDmax(G, aresta)){
						G.addElem(aresta);
						G.union(aresta.getA(),aresta.getB());
					}
			if(G.getNumAresta() == grafo.getNumVertice()-1)
				break;
		}
		
		return G;
	}
	
	public void mutation(Grafo<T> grafo , Grafo<T> G){
		Random randomico = new Random();
		DFS<T> dfs = new DFS<T>();

		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		for (Aresta<T> aresta : G.getArestas()) {
			if (!grafo.findSet(aresta.getA()).equals(
					grafo.findSet(aresta.getB()))) {
					arestas.add(aresta.clone());
			}
		}

		int index = Math.abs(randomico.nextInt() % arestas.size());
		Aresta<T> edgAdd = arestas.get(index).clone();
		Aresta<T> edgDel;
		G.addElem(edgAdd);

		ArrayList<Aresta<T>> ciclo = dfs.getCiclo(G, edgAdd);

		if (edgAdd.getA().getGrau() == grauMax) {
				edgDel=find(ciclo , edgAdd.getA());
		} else if (edgAdd.getB().getGrau() == grauMax) {
				edgDel=find(ciclo , edgAdd.getB());
		} else {
			index = Math.abs(randomico.nextInt() % ciclo.size());
			 edgDel = ciclo.get(index).clone();
			G.addElem(edgDel);
		}
	}
	
	/**
	 * @param ciclo
	 * @param a
	 * @return
	 */
	private Aresta<T> find(ArrayList<Aresta<T>> ciclo, Vertice<T> V) {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<Grafo<T>> gerarPopulacao(Grafo<T> grafo, int qtd) {
		ArrayList<Grafo<T>> grafos = new ArrayList<Grafo<T>>();

		for (int i = 0; i < qtd; i++) {
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
