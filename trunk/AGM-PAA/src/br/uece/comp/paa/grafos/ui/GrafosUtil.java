/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.grafos.ui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.smartcardio.ATR;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;

import br.uece.comp.paa.estruturas.HeapFibonacci;
import br.uece.comp.paa.estruturas.HeapFibonacciNoh;
import br.uece.comp.paa.grafos.Aresta;
import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class GrafosUtil<T>{
			
	public  DefaultGraphCell createCell(T name, Rectangle2D bounds,
			boolean raised) {
		DefaultGraphCell cell = new DefaultGraphCell(name);
		cell.addPort();
		GraphConstants.setBounds(cell.getAttributes(), bounds);
		GraphConstants.setResize(cell.getAttributes(), true);
		GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
		GraphConstants.setGradientColor(cell.getAttributes(),Color.gray);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setAbsolute(cell.getAttributes(), false);
		
		return cell;
	}
	
	public  JGraph desenhaGrafo(Grafo<T> G){
		GraphModel model = new DefaultGraphModel();
		GraphLayoutCache view = new GraphLayoutCache(model,new DefaultCellViewFactory());
		
		
		JGraph graph = new JGraph(model, view);
		
		graph.setEditable(false);
		
		
		
		graph.setScale(2);
		
		
		
//		JGraph graph = getGraph(); // Replace with your own graph instance
//		OutputStream out = getOutputStream(); // Replace with your output stream
//		Color bg = null; // Use this to make the background transparent
//		bg = graph.getBackground(); // Use this to use the graph background
//		color
//		BufferedImage img = graph.getImage(bg, inset);
//		ImageIO.write(img, ext, out);
//		out.flush();
//		out.close();

		
		GrafosUtil<String> gutil = new GrafosUtil<String>();
		
		ArrayList<Vertice<T>> vrtxs = G.getVertices();
		DefaultGraphCell[] cells = new DefaultGraphCell[vrtxs.size()];
		AttributeMap map = new AttributeMap();
		
		GraphConstants.setResize(map, true);

		view.setVisible(cells,true);
		for(int i=0; i< vrtxs.size();i++){
			//cells[i].setAttributes(map);
			cells[i] = gutil.createCell(vrtxs.get(i).getInfo().toString(),
					new Rectangle2D.Double(vrtxs.get(i).getPosX(),vrtxs.get(i).getPosY(), 50, 20), false);
		}
		
		vrtxs = new ArrayList<Vertice<T>>();
		int i=0;
		for (Vertice<T> V : G.getVertices()){
			vrtxs.add(V);
			for (Aresta<T> E : V.getListAdj()) {
				boolean a,b;
				a=vrtxs.contains(E.getA());
				b=vrtxs.contains(E.getB());
				if (!(a && b) || (!a && b) || (a && !b)) {
					DefaultEdge edge = new DefaultEdge(E.getPeso());
					
					i =  searchIdCell(E.getA(),cells);
					edge.setSource(cells[i].getChildAt(0));
					
					i =  searchIdCell(E.getB(),cells);
					edge.setTarget(cells[i].getChildAt(0));
					
					view.setAutoSizeOnValueChange(false);
					int arrow = GraphConstants.ARROW_NONE;
					GraphConstants.setLineEnd(edge.getAttributes(), arrow);
					GraphConstants.setEndFill(edge.getAttributes(), true);
					GraphConstants.setLabelAlongEdge(edge.getAttributes(), true);

					graph.getGraphLayoutCache().insert(edge);	
					//System.out.println(E.getA()+"----"+E.getB());
				}
			}
		}
				graph.getGraphLayoutCache().collapse(graph.getSelectionCells());
				graph.getGraphLayoutCache().insert(cells);
		
		return graph;
	}
	
	private  int searchIdCell(Vertice<T> V, DefaultGraphCell[] cells){
		for (int i=0 ; i< cells.length; i++) {
			if(cells[i].toString().equals(V.getInfo())) return i;
		}
		return -1;
	}
	
	public  Grafo<T> fileToGrafo(String nameFile) throws FileNotFoundException{
		Grafo<T> G =  new Grafo<T>();
		File fgrafo = new File(nameFile);
		Scanner scan  = new Scanner(fgrafo);
		String nome =  scan.next();
		int numVertice = scan.nextInt();
		int tipo = scan.nextInt();
		int numAresta = scan.nextInt();
		
		G.setNome(nome);
		if(tipo==1){
			for (int i = 0; i < numVertice; i++) {
				Vertice<T> vrtx = new Vertice<T>((T) scan.next());
				vrtx.setPosX(Double.parseDouble(scan.next()));
				vrtx.setPosY(Double.parseDouble(scan.next()));
				G.addElem(vrtx);
			}
			for (int i = 0; i < numAresta; i++) {
				Aresta<T> edg = new Aresta<T>(new Vertice<T>((T) scan.next()),new Vertice<T>((T) scan.next()),Double.parseDouble(scan.next()));
				G.addElem(edg);
			}
		}else if(tipo==0){
			Vertice[] vertices = new Vertice[numVertice];
			for (int i = 0; i < numVertice; i++) {
				Vertice<T> vrtx = new Vertice<T>((T) scan.next());
				vrtx.setPosX(Double.parseDouble(scan.next()));
				vrtx.setPosY(Double.parseDouble(scan.next()));
				vertices[i] = vrtx;
			}
			for (int i = 0; i < numVertice; i++) {
				for (int j = i+1; j < numVertice; j++) {
					Aresta<T> edg = new Aresta<T>(vertices[i],vertices[j],distancia(vertices[i], vertices[j]));
					G.addElem(edg);
				}
			}
		}else{
			System.out.println("Erro de Formato: leitura ainda nao implementada");
		}
		return G;
	}
	
	public  Double distancia(Vertice<T> V1 ,Vertice<T> V2) {
		
		double x = Math.abs(V1.getPosX() - V2.getPosX());
		double y = Math.abs(V1.getPosY() - V2.getPosY());
		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		int casas = 2;
		d = Math.round(d * Math.pow(10, (double) casas))/ Math.pow(10, (double) casas);
		
		return d;
	}
	
	public HeapFibonacci<Aresta<T>> arestaToHeap(ArrayList<Aresta<T>> arestas){
		HeapFibonacci<Aresta<T>> heap = new HeapFibonacci<Aresta<T>>(); 
			for (Aresta<T> aresta : arestas) {
				heap.inserir(aresta.getPeso(), aresta);
			}
			return  heap;
	}
	
	public ArrayList<Aresta<T>> heapToAresta(HeapFibonacci<Aresta<T>> heap){
		ArrayList<Aresta<T>> arestas = new ArrayList<Aresta<T>>();
		
			while (!heap.isVazio()) {
				HeapFibonacciNoh<Aresta<T>> nohHeap = heap.extrairMin();
				Aresta<T> edg =  nohHeap.getInfo();
				arestas.add(edg);
			}
		return  arestas;
	}
	
	public void telaGrafos(Grafo<T> grafo){
		JFrame frame = new JFrame();
		frame.setTitle(grafo.getPesoTotal().toString());
		frame.getContentPane().add(new JScrollPane(desenhaGrafo(grafo)));
		frame.setBounds(10, 10, 500, 600);
		frame.pack();
		frame.setVisible(true);
	}
}