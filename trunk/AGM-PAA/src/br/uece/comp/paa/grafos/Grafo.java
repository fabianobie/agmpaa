/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import br.uece.comp.paa.grafos.ui.GrafosUtil;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Grafo <T>{
	
	private ArrayList<Vertice<T>> vertices = new ArrayList<Vertice<T>>();
	
	public void addEdge(Vertice<T> va, Vertice<T> vb , Double peso){
		Aresta<T> edg = new Aresta<T>(va, vb, peso);
		addElem(edg);
	}
	
	public void addElem(Aresta<T> edg){
		Vertice<T> a = edg.getA(); 
		Vertice<T> b = edg.getB();
	
		if(!hasVertice(a)){
			a.addAdj(edg);
			vertices.add(a);
		}else{
			int i = getVertice(a);
			vertices.get(i).addAdj(edg);
		}
	
		if(!hasVertice(b)){
			b.addAdj(edg);
			vertices.add(b);
		}else{
			int i = getVertice(b);
			vertices.get(i).addAdj(edg);
		}
	}
	
	public boolean hasVertice(Vertice<T> vrtx){
		for (Vertice<T> V : vertices) {
			if(V.equals(vrtx)) return true;	
		}
		return false;
	}
	
	public int getVertice(Vertice<T> vrtx){
		for (int i=0 ; i< vertices.size() ; i++) {
			if(vertices.get(i).equals(vrtx)) return i;	
		}
		return -1;
	}
	
	public void deleteEdge(Aresta<T> edg){
		edg.getA().getListAdj().remove(edg.getB());
		edg.getB().getListAdj().remove(edg.getA());
	}

	public ArrayList<Vertice<T>> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice<T>> vertices) {
		this.vertices = vertices;
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
		Grafo<String> grf = GrafosUtil.fileToGrafo("nomeArqGrafos.txt");
		JGraph graph = GrafosUtil.desenhaGrafo(grf);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JScrollPane(graph));
		frame.setBounds(10, 10, 500, 600);
		frame.pack();
		frame.setVisible(true);
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


