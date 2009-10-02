/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.util;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */
public class Metricas {
	
	private double tempoInicial;
	
	private double tempoFinal;
	
	private int numIteracao;
	
	
	public double getTempoTotal(){
		return tempoFinal - tempoInicial;
	}
	
	public void count() {
		numIteracao++;
	}
	
	public void start() {
		tempoInicial=System.currentTimeMillis();
	}
	
	public void finish() {
		tempoFinal=System.currentTimeMillis();
	}
	
	public double getTempoInicial() {
		return tempoInicial;
	}

	public double getTempoFinal() {
		return tempoFinal;
	}

	public int getNumIteracao() {
		return numIteracao;
	}

}
