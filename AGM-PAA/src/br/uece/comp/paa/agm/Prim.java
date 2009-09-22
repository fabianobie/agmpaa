package br.uece.comp.paa.agm;


/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Cear - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */

/**
 * @author Diego Sá Cardoso (diegoccx@gmail.com)
 *
 */
import java.util.ArrayList;

import br.uece.comp.paa.agm.interfaces.Iagm;
import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;
import br.uece.comp.paa.grafos.ui.GrafosUtil;

public class Prim<T> implements Iagm<T>{
	   
    private Boolean rgrau =  false;
    private int grauMax;
    private Boolean rdmax =  false;
    private Double dMax;
  
    private Grafo<T> result= new Grafo<T>();
  
/*
* (non-Javadoc)
* @see br.uece.comp.paa.agm.interfaces.Iagm#obterAGM(br.uece.comp.paa.grafos.Grafo)
*/

    public Grafo<T> obterAGM(Grafo<T> grafo){
            Vertice<T> inicial = grafo.getVertices().get(0);
            result.addElem(inicial.clone());
          
          
            for (Vertice<T> vertice : grafo.getVertices()) {
                    grafo.makeSet(vertice);
            }
          
            Vertice<T> pai = inicial.getPai();
                  
            while (grafo.getVertices().size() >= result.getVertices().size()) {
                    Aresta<T> aresta = obterMinimo(pai, grafo);           
                    if (aresta == null) break;
                    pai = aresta.getA().getPai();
                    if(restricaoDeGrau(result, aresta)){
                            if(restricaoDeDmax(result, aresta)){
                                    result.addElem(aresta.clone());
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
    public Aresta<T> obterMinimo(Vertice<T> pai, Grafo<T> grafo) {
            Aresta<T> retorno = null;
            GrafosUtil<T> gutil = new GrafosUtil<T>();
            Double minimo = Double.MAX_VALUE;
            Vertice<T> a , b ;
                  
            ArrayList<Vertice<T>> vertices =  grafo.getSubSet(pai);
            Grafo<T> subgrafo = new Grafo<T>();
            subgrafo.setVertices(vertices);
          
          
            HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(subgrafo.getArestas());
                  
            while (!arestas.isVazio()) {
                    HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
                    Aresta<T> aresta = nohHeap.getInfo();
                    a = grafo.findSet(aresta.getA());
                    b = grafo.findSet(aresta.getB());

                    if ((a.equals(pai) && !b.equals(pai))
                                    || (!a.equals(pai) && b.equals(pai))) {
                            if (aresta.getPeso() < minimo) {
                                    if(restricaoDeGrau(result, aresta)){
                                            if(restricaoDeDmax(result, aresta)){
                                                    retorno = aresta;
                                                    break;
                                            }
                                    }
                            }
                    }
            }
          
          
            if(retorno != null)
                    grafo.union(retorno.getA(), retorno.getB());
          
            return retorno;
    }
  

    public Aresta<T> obterMinimoGrafo( Grafo<T> grafo) {
        if(grafo.getVertices().size()<1){
            return null;
        }
        Vertice<T> inicial = grafo.getVertices().get(0);
        Vertice<T> pai = inicial.getPai();
       
        Aresta<T> retorno = null;
        GrafosUtil<T> gutil = new GrafosUtil<T>();
        Double minimo = Double.MAX_VALUE;
        Vertice<T> a , b ;
              
        ArrayList<Vertice<T>> vertices =  grafo.getSubSet(pai);
        Grafo<T> subgrafo = new Grafo<T>();
        subgrafo.setVertices(vertices);
      
      
        HeapFibonacci<Aresta<T>> arestas = gutil.arestaToHeap(subgrafo.getArestas());
              
        while (!arestas.isVazio()) {
                HeapFibonacciNoh<Aresta<T>> nohHeap = arestas.extrairMin();
                Aresta<T> aresta = nohHeap.getInfo();
                a = grafo.findSet(aresta.getA());
                b = grafo.findSet(aresta.getB());

              
                        if (aresta.getPeso() < minimo) {
                                if(restricaoDeGrau(grafo, aresta)){
                                        if(restricaoDeDmax(grafo, aresta)){
                                                retorno = aresta;
                                                break;
                                        }
                                }
                        }
                }
       
      
      
        if(retorno != null)
                grafo.union(retorno.getA(), retorno.getB());
      
        return retorno;
}
   
   
    public Grafo<T> obterAGM(Grafo<T> grafo ,int grau , Double distMax)  {
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

    
	public ArrayList<Grafo<T>> obterKAGMS(Grafo<T> grafo , int K)
			throws CloneNotSupportedException {
		ArrayList<Grafo<T>> grafos = new ArrayList<Grafo<T>>();
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		ArrayList<Aresta<T>> arestasVisitadas = new ArrayList<Aresta<T>>();
		DFS<T> dfs = new DFS<T>();
		int numVertices = grafo.getVertices().size();
		int numArestas = 7; // numero fornecido pelo arquivo.
		Aresta<T> aresta = null;
		for (int j = 0; j < arestasVisitadas.size()
				|| arestasVisitadas.size() == 0 ; j++) {
			for (int i = 0; i < numArestas; i++) {
				System.out.println(i);
				for (Aresta<T> aresta1 : arestas) {

					grafo.deleteEdge((Aresta<T>) aresta1.clone());

				}
				for (int n = 0; n < j; n++) {

					grafo.deleteEdge((Aresta<T>) arestasVisitadas.get(n)
							.clone());

				}
				if (obterMinimoGrafo(grafo) == null) {
					break;
				}

				aresta = (Aresta<T>) obterMinimoGrafo(grafo).clone();

				grafo.deleteEdge(aresta);

				for (Aresta<T> aresta1 : arestas) {
					grafo.addElem((Aresta<T>) aresta1.clone());

				}

				arestas.add((Aresta<T>) aresta.clone());

				if (dfs.isConexo(grafo)
						&& grafo.getVertices().size() >= numVertices) {
					System.out.println("adicionou retirando  a aresta de peso:"
							+ aresta.getPeso() + ", do vertice:"
							+ aresta.getA().getInfo() + "--"
							+ aresta.getB().getInfo());
					Grafo<T> novoGrafo = obterAGM((Grafo<T>) grafo).clone();
					if (!grafos.contains(novoGrafo)) {
						grafos.add(novoGrafo);
					}
					result = new Grafo<T>();

				} else;

			}
			System.out.println("adicionando aresta superior");
			if (arestasVisitadas.size() < 1) {
				arestasVisitadas = (ArrayList<Aresta<T>>) arestas.clone();
			}
			arestas = new ArrayList<Aresta<T>>();
		}
		return grafos;
	}
   
       

        public static void main(String[] args) throws CloneNotSupportedException {
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
                grafo.addEdge(a,d, 3.0);
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
              
                Kruskal<Integer> kru = new Kruskal<Integer>();
                Prim<Integer> prim = new Prim<Integer>();
                
                System.out.println(grafo);
               int k =1;
                for(Grafo<Integer> grafo1 : prim.obterKAGMS(grafo,5)){
                    //System.out.println("tamanho:"+prim.obterKAGMS(grafo).size());
                    System.out.println("grafo  "+ (k++)+"\n"+grafo1);
                   
                }
                //System.out.println(kru.obterAGM(grafo));
              //  System.out.println(prim.obterAGM(grafo));

        }

}
