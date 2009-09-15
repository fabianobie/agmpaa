/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.agm;

import java.util.ArrayList;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Boruvka<T> {
	
	public Grafo<T> obterAGM(Grafo<T> grafo) {
		ArrayList<Grafo<T>> listG = new ArrayList<Grafo<T>>();
		Grafo<T> result = new Grafo<T>();
		
		for(Vertice<T> V : grafo.getVertices()){
		
					try {
						Grafo<T> grf = new Grafo<T>();
						grf.addElem((Vertice<T>) V.clone());
						listG.add(grf );
						
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
		}
		
		result.setNome(grafo.getNome()+"_MINIMO");
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>(); 
		
		DFS<T> dfs = new DFS<T>();
		
		while(!(dfs.isConexo(result) && (result.getVertices().size()==grafo.getVertices().size()))){
			for (Grafo<T> G : listG) {
				Aresta<T> edg = obterMinimo(G,grafo);
				G.addElem(edg);
				try {
					result = (Grafo<T>) G.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return result;
	}
	
	
	/**
	 * Mtodo que retorna a aresta mnima de um conjunto de vrtices de entrada
	 * 
	 * @param subgrafo
	 * @param grafo
	 * @return
	 */
	public Aresta<T> obterMinimo(Grafo<T> subgrafo, Grafo<T> grafo) {
		Aresta<T> retorno = null;
		
		HeapFibonacci<Aresta<T>> arestas = new HeapFibonacci<Aresta<T>>();
		ArrayList<Aresta<T>> edgs = grafo.getArestas();
		
		for (Aresta<T> E : edgs) {
			arestas.inserir(E.getPeso(), E);
		}
		
		// Laos que percorrem todos os vrtices e todas as arestas e obtm a
		// aresta com o peso mnimo.
		while(!arestas.isVazio()){
			HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
			Aresta<T> edg;
			try {
				edg = (Aresta<T>) nohHeap.getInfo().clone();
				boolean a,b;
				a=subgrafo.hasVertice(edg.getA());
				b=subgrafo.hasVertice(edg.getB());
				if ((a || b) && !(a && b)) {
					retorno = edg;
					break;
				}
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return retorno;

	}
	
	
}

/*
package br.uece.comp.paa.agm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;

*//**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 *//*
public class Boruvka<T> {
    public Grafo<T> appliquer(Grafo<T> graphe) {
        List<Aresta<T>> Arestas = new ArrayList<Aresta<T>>(graphe.getArestas());
        List<Aresta> l = new ArrayList<Aresta>();
        Map<Aresta, Aresta> Arestas_originales = pondererArestas(Arestas, l);

        List<Aresta> r = doBoruvka(new Grafo(l));
        Grafo<T> result = new Grafo<T>();
        for (Aresta a : r)
                result.ajouterAresta(Arestas_originales.get(a));
        return result;
}

protected Map<Aresta, Aresta> pondererArestas(Collection<Aresta> Arestas,
                List<Aresta> l_out) {
        // maps copy -> original
        Map<Aresta, Aresta> Arestas_originales = new HashMap<Aresta, Aresta>();

        // création des arêtes
        final int mul = Arestas.size() + 1;
        int i = 0;
        for (Aresta a : Arestas) {
                Aresta cp = new Aresta(a.getU(), a.getV(), a.getPoids() * mul + i);
                l_out.add(cp);
                Arestas_originales.put(cp, a);
                ++i;
        }
        return Arestas_originales;
}

protected List<Aresta> doBoruvka(Grafo<T> g) {
        List<Aresta> acpm = new ArrayList<Aresta>();
        while (g.getSommets().size() > 1) {
                phaseBoruvka(g, acpm);
        }
        return acpm;
}

protected void phaseBoruvka(Grafo<T> g, List<Aresta> acpm) {
        if (g.getSommets().size() > 1) {
                Set<Aresta> Arestas_mini = new HashSet<Aresta>();
                for (Integer s : g.getSommets()) {
                        // obtention de l'Aresta minimale
                        Aresta Aresta_min = null;
                        for (Aresta a : g.getArestasAdjacentes(s.intValue())) {
                                if (Aresta_min == null || Aresta_min.getPoids() > a.getPoids())
                                        Aresta_min = a;
                        }
                        assert (Aresta_min != null);
                        Arestas_mini.add(Aresta_min);
                }

                for (Aresta a : Arestas_mini)
                        g.unir(a);

                acpm.addAll(Arestas_mini);
        }
}

public String getName() {
        return "Boruvka";
}

}
*/