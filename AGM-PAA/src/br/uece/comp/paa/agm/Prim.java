package br.uece.comp.paa.agm;


/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Cear� - UECE
 * Alunos: Fabiano Tavares e Diego S�
 * Prof. Marcos Negreiro
 *
 */

/**
 * @author Diego S� Cardoso (diegoccx@gmail.com)
 *
 */
import java.util.ArrayList;

import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

public class Prim<T> {

	public Grafo<T> obterAGM(Grafo<T> grafo) {

		int numElementos = grafo.getVertices().size();
		Grafo<T> retorno = new Grafo<T>();
		Vertice<T> a1 = null;
		Vertice<T> b1 = null;
		Vertice inicial = grafo.getVertices().get(0);

		// Array para guardar os vrtices adicionados
		ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
		// Array para guardar as arestas que sero adicionadas ao final.
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		vertices.add(inicial);
		Aresta<T> aresta;

		// A cada iterao o programa testa se j temos todos os vrtices
		// adicionados
		while (vertices.size() < numElementos) {

			// Trecho para mostrar na tela os passos da escolha do mnimo
			for (int i = 0; i < vertices.size(); i++) {
				System.out.print("\t" + vertices.get(i).getInfo() + "("
						+ vertices.get(i).getListAdj().size() + ")");
			}
			aresta = obterMinimo(vertices, grafo);
			// Neste trecho o algoritmo j retorna o resultado parcial caso o
			// grafo seja desconexo
			if (aresta == null) {
				return retorno;
			}
			// O mnimo a cada iterao para visualizar os mnimos encontrados
			System.out.println("Peso mnimo:" + aresta.getPeso());
			a1 = new Vertice<T>(aresta.getA().getInfo());
			b1 = new Vertice<T>(aresta.getB().getInfo());

			// Sempre que  encontrada a aresta mnima do array de vrtices
			// adicionados  adicionada ela  adicionada ao retorno do mtodo

			arestas.add(new Aresta<T>(a1, b1, aresta.getPeso()));
			retorno.addElem(new Aresta<T>(a1, b1, aresta.getPeso()));

			// O trecho testa qual aresta ser adicionada a lista de vrtices
			if (vertices.contains(aresta.getA())) {
				vertices.add(aresta.getB());
			} else {
				vertices.add(aresta.getA());
			}

		}
		return retorno;
	}

	/**
	 * Mtodo que retorna a aresta mnima de um conjunto de vrtices de entrada
	 * 
	 * @param vertices
	 * @param grafo
	 * @return
	 */
	public Aresta<T> obterMinimo(ArrayList<Vertice<T>> vertices, Grafo<T> grafo) {
		Aresta<T> retorno = null;

		Vertice<T> a;
		Vertice<T> b;
		Double minimo = 1000.0;
		int i = 0;
		// Laos que percorrem todos os vrtices e todas as arestas e obtm a
		// aresta com o peso mnimo.
		for (Vertice<T> vertice : vertices) {

			for (Aresta<T> aresta : vertice.getListAdj()) {

				if (vertice.equals(aresta.getA())) {
					a = aresta.getA();
					b = aresta.getB();
				}

				else {
					b = aresta.getA();
					a = aresta.getB();
				}
				if (aresta.getPeso() < minimo && !vertices.contains(b)) {
					minimo = aresta.getPeso();

					retorno = aresta;
				}
			}

		}

		return retorno;

	}

	public static void main(String[] args) {
		Grafo<Integer> grafo = new Grafo<Integer>();
		Vertice<Integer> a = new Vertice<Integer>(1);
		Vertice<Integer> b = new Vertice<Integer>(2);
		Vertice<Integer> c = new Vertice<Integer>(3);
		Vertice<Integer> d = new Vertice<Integer>(4);
		Vertice<Integer> e = new Vertice<Integer>(5);
		Vertice<Integer> f = new Vertice<Integer>(6);
		Vertice<Integer> g = new Vertice<Integer>(7);
		Vertice<Integer> h = new Vertice<Integer>(8);
		Vertice<Integer> i = new Vertice<Integer>(9);
		Vertice<Integer> j = new Vertice<Integer>(10);
		Vertice<Integer> l = new Vertice<Integer>(11);
		Vertice<Integer> m = new Vertice<Integer>(12);
		Vertice<Integer> n = new Vertice<Integer>(13);
		Vertice<Integer> o = new Vertice<Integer>(14);

		grafo.addEdge(a, b, 7.0);
		grafo.addEdge(a, e, 6.0);
		grafo.addEdge(b, d, 5.0);
		grafo.addEdge(c, d, 3.0);
		grafo.addEdge(c, f, 2.0);
		grafo.addEdge(d, h, 2.0);
		grafo.addEdge(e, f, 6.0);
		grafo.addEdge(e, g, 5.0);
		grafo.addEdge(e, j, 3.0);
		grafo.addEdge(f, i, 2.0);
		grafo.addEdge(g, h, 3.0);
		grafo.addEdge(g, i, 10.0);
		grafo.addEdge(g, j, 2.0);
		grafo.addEdge(g, l, 2.0);
		grafo.addEdge(h, l, 5.0);
		grafo.addEdge(i, m, 6.0);
		grafo.addEdge(i, n, 9.0);
		grafo.addEdge(m, n, 5.0);
		grafo.addEdge(m, o, 1.0);

		// Teste do Prim e do DFS

		Prim<Integer> prim = new Prim<Integer>();
		DFS<Integer> DFS = new DFS<Integer>();
		System.out.println("\n O grafo  Conexo?:"+DFS.isConexo(grafo));
		// System.out.println(grafo.getVertices().size());
		// System.out.println(prim.obterMinimo(vertices, grafo).toString());
		System.out.println(grafo);
		System.out.println(prim.obterAGM(grafo));

	}

}
