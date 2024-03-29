/**
 * PAA - Projeto de Analise e Algoritmos
 * Universidade Estadual do Ceará - UECE
 * Alunos: Fabiano Tavares e Diego Sá
 * Prof. Marcos Negreiro
 *
 */
package br.uece.comp.paa;

import javax.swing.UIManager;

import br.uece.comp.paa.grafos.gui.AGMapp;

/**
 * @author Fabiano Tavares (fabiano.bie@gmail.com)
 *
 */

/**
 * Programa Principal iicializa a Aplicação de Arvores Geradoras Minimas
 */
public class Main {
    /**
	    * @param args the command line arguments
	    */
	    public static void main(String args[]) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
				try {
					
					//Escolhe como interface a do sitema local
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					
					//inicializa a Janela principal da Aplicação
					AGMapp janela = new AGMapp();
					janela.setVisible(true);
					janela.setMaximizar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	    }
}
