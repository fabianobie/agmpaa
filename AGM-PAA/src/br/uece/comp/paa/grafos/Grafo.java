/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import br.uece.comp.paa.agm.Kruskal;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private String nome="GRAFO_PAA";
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
		if(!hasAresta(edg)){
			int ia , ib;
	
			if(!hasVertice(a))
				addElem(a);
			if(!hasVertice(b))
				addElem(b);
			
			ia = getIdVertice(a);
			ib = getIdVertice(b);
			
			Aresta<T> nedg = new Aresta<T>(vertices.get(ia),vertices.get(ib),edg.getPeso());
			
			vertices.get(ia).addAdj(nedg);
			vertices.get(ib).addAdj(nedg);
			
		}
	}
	
	public void addElem(Vertice<T> vrtx){
		if(!hasVertice(vrtx))
			this.vertices.add(vrtx);
	}
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public boolean hasAresta(Aresta<T> edg){
			Vertice<T> a = edg.getA(); 
			Vertice<T> b = edg.getB();
			
		for (Vertice<T> V : vertices) {
			for (Aresta<T> E : V.getListAdj()) {
				if(E.equals(edg)) return true;
			}
		}
		return false;
	}
	
	public int getIdVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	
	public ArrayList<Aresta<T>> getArestas(){
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		
		for (Vertice<T> V : vertices) {
			for (Aresta<T> E : V.getListAdj()) {
	//			if(E.equals(edg)) return true;
			}
		}
		
		return null;
	}

	
	private int getIdAresta(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
		
		for (int i=0 ; i< vertices.size() ; i++) {
			for(int j=0 ; j< vertices.get(i).getListAdj().size() ; j++) {
				if(vertices.get(i).getListAdj().get(j).equals(edg)) return j;
			}
		}
		return -1;
	}
	
	public void deleteEdge(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();

		if(hasAresta(edg)){
			int ia = getIdVertice(a);
			vertices.get(ia).getListAdj().remove(getIdAresta(edg));
			int ib = getIdVertice(b);
			vertices.get(ib).getListAdj().remove(getIdAresta(edg));
		}
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		String res = "Grafo[\n";
		for (Vertice<T> V : vertices) {
			res +="\t"+V.toString()+"\n";
		}
		res += " ]";
		return res;
	}

	/*
	 * Testando Grafos
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Grafo<String> grf = new Grafo<String>(); //GrafosUtil.fileToGrafo("nomeArqGrafos.txt");
		/*
		JGraph graph = GrafosUtil.desenhaGrafo(grf);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.setBounds(10, 10, 500, 600);
		frame.pack();
		frame.setVisible(true);
		*/
		
		String fgrafo = "A B 10.0\nC B 20.0\nA C 5.0";
		Scanner sc = new Scanner(fgrafo);
	
		
		while(sc.hasNext()){
			String sv1 = sc.next();
			String sv2 = sc.next();
			String peso = sc.next();
			Vertice<String> v1 = new Vertice<String>(sv1);
			Vertice<String> v2 = new Vertice<String>(sv2);
			grf.addEdge(v1, v2, Double.parseDouble(peso));
		}
		
		String sv1 = "A";
		String sv2 = "B";
		String peso = "0.0";
		Vertice<String> v1 = new Vertice<String>(sv1);
		Vertice<String> v2 = new Vertice<String>(sv2);
		
		//grf.deleteEdge(new Aresta<String>(v1, v2 , 0.0));
		
		Kruskal<String> kru = new Kruskal<String>();
		
		System.out.println(grf);
		System.out.println(kru.obterAGM(grf));
	}
	
	
}

/*		String fgrafo = "A B 10.0\nC B 20.0\nA C 5.0";
Scanner sc = new Scanner(fgrafo);


while(sc.hasNext()){
	String sv1 = sc.next();
	String sv2 = sc.next();
	String peso = sc.next();
	Vertice<String> v1 = new Vertice<String>(sv1);
	Vertice<String> v2 = new Vertice<String>(sv2);
	grf.addEdge(v1, v2, Double.parseDouble(peso));
}


System.out.println(grf);



GraphModel model = new DefaultGraphModel();
GraphLayoutCache view = new GraphLayoutCache(model,new DefaultCellViewFactory());
JGraph graph = new JGraph(model, view);

ArrayList<Vertice<String>> vrtx = grf.getVertices();
DefaultGraphCell[] cells = new DefaultGraphCell[vrtx.size()];

for(int i=0; i< vrtx.size();i++){
	cells[i] = GrafosUtil.createCell(vrtx.get(i).getInfo(),
			new Rectangle2D.Double(Math.random()*100, Math.random()*100, 20, 20), false);
}


DefaultEdge edge = new DefaultEdge();
edge.setSource(cells[0].getChildAt(0));
edge.setTarget(cells[1].getChildAt(0));
//cells[2] = edge;
view.setAutoSizeOnValueChange(false);

int arrow = GraphConstants.ARROW_NONE;
GraphConstants.setLineEnd(edge.getAttributes(), arrow);
GraphConstants.setEndFill(edge.getAttributes(), true);
graph.getGraphLayoutCache().insert(edge);
graph.getGraphLayoutCache().insert(cells);*/


