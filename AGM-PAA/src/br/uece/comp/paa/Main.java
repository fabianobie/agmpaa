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
public class Main {
    /**
	    * @param args the command line arguments
	    */
	    public static void main(String args[]) {
	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
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
