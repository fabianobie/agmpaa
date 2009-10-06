/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa.util;

import java.text.SimpleDateFormat;

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
	
	public String getTempoInicial() {
		return converte(tempoInicial);
	}

	/**
	 * @param tempoInicial2
	 */
	private String converte(double tempoInicial) {
		   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");  
		   return sdf.format(tempoInicial);  
	}

	public String getTempoFinal() {
		return converte(tempoFinal);
	}

	public int getNumIteracao() {
		return numIteracao;
	}

}
