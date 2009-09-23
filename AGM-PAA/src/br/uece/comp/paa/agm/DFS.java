package br.uece.comp.paa.agm;



/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego S
 * Prof. Marcos Negreiro
 *
 */

/**
 * @author Diego Sá Cardoso (diegoccx@gmail.com)
 *
 */

import java.util.HashMap;

import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

public class DFS<T> {

	private Object[] indiceVertice = null;
	private HashMap<Object, Integer> indices = new HashMap<Object, Integer>(); // HashMap
	// para
	// guardar
	// as
	// info's
	// de
	// cada
	// vrtice.
	Grafo<T> grafoDFS = new Grafo<T>();

	/**
	 * Mtodo que verifica se o grafo  conexo ou desconexo atravs de uma busca
	 * em profundidade DFS
	 * 
	 * @param grafo
	 * @return
	 */
	public Boolean isConexo(Grafo<T> grafo) {
		grafoDFS = grafo;
		Boolean conexo = true; // Para testar ao final do mtodo se o grafo 
		// conexo.
		if(grafo.getVertices().isEmpty()) return false;
		
		HashMap<Object, Integer> param = null;
		int numVertices = grafo.getVertices().size(); // nmero de vrtices
		indiceVertice = new Object[numVertices];
		for (int i = 0; i < numVertices; i++) {

			indices.put(grafo.getVertices().get(i).getInfo(), 0);

		}
		// Mtodo para realizar a busca
		dfs(grafo.getVertices().get(0));

		// Trecho que testa se todos os vrtices foram visitados , em caso
		// afirmativo a varivel conexo permanece true
		for (int i = 0; i < grafoDFS.getVertices().size(); i++) {

			if (indices.get(grafoDFS.getVertices().get(i).getInfo()) == 0) {
				conexo = false;
				break;
			}
		}
		// Trecho para apresentar a lista de vrtices e um ndice se ele foi
		// visitado(indice 1) ou no foi visitado(indice 0)
		/*System.out.println("\nResultado final da busca(Vrtices visitados):");
		for (int i = 0; i < grafoDFS.getVertices().size(); i++) {
			System.out.print("\t"
					+ indices.get(grafoDFS.getVertices().get(i).getInfo())
					+ "(" + grafoDFS.getVertices().get(i).getInfo() + ")");
		}
		*/
		
		if (conexo) {

			return true;
		}

		return false;
	}

	/**
	 * Chamada Recursiva para percorrer todo o grafo e verificar se  conexo ou nao
	 * 
	 * @param verticeOrigem
	 */
	public void dfs(Vertice<T> verticeOrigem) {
		indices.put(verticeOrigem.getInfo(), 1);
		Boolean conexo = true;

		Vertice<T> vertice = null;
		//Laço para percorrer todas as arestas do vrtice selecionado
		for (Aresta<T> aresta : verticeOrigem.getListAdj()) {
			if (verticeOrigem.equals(aresta.getA())) {
				vertice = aresta.getB();
			} else
				vertice = aresta.getA();
			
			if (indices.get(vertice.getInfo()) == 0) {
				dfs(vertice);

			}
		}

	}

}
