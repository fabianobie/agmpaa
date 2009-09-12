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
import java.util.Scanner;

import javax.swing.BorderFactory;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import br.uece.comp.paa.grafos.Grafo;
import br.uece.comp.paa.grafos.Vertice;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 * 
 */
public class GrafosUtil{

	public static DefaultGraphCell createCell(String name, Rectangle2D bounds,
			boolean raised) {
		DefaultGraphCell cell = new DefaultGraphCell(name);
		cell.addPort();
		GraphConstants.setBounds(cell.getAttributes(), bounds);
		GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createRaisedBevelBorder());
		GraphConstants.setGradientColor(cell.getAttributes(),Color.gray);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		return cell;
	}
	
	public static JGraph desenhaGrafo(Grafo<String> G){
		return null;
	}
	
	public static Grafo<String> fileToGrafo(String nameFile){
		return null;
	}
	
	public static Double distancia(Vertice<String> V1 ,Vertice<String> V2) {
		
		double x = Math.abs(V1.getPosX() - V2.getPosX());
		double y = Math.abs(V1.getPosY() - V2.getPosY());
		double d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		int casas = 2;
		d = Math.round(d * Math.pow(10, (double) casas))/ Math.pow(10, (double) casas);
		
		return d;
	}
	
}
