/*
 * JanelaGrafo.java
 *
 * Created on 3 de Outubro de 2009, 12:06
 */

package br.uece.comp.paa.grafos.gui;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;


/**
 *
 * @author  Fabiano Tavares 
 */
public class JanelaGrafo extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JGraph grafoGui;
    /** Creates new form JanelaGrafo */
    public JanelaGrafo(JGraph jgrafo) {
    	grafoGui = jgrafo;
        initComponents();
    }
    

	public JGraph getGrafoGui() {
		return grafoGui;
	}

	public void setGrafoGui(JGraph grafoGui) {
		this.grafoGui = grafoGui;
	}
	
	public void setGrafoPanel(JGraph graph) {
		grafoGui = graph;
		jScrollPane1.setSize(100, 500);
	//	this.getContentPane().add(new JScrollPane(graph));
		//jPanel1.setBounds(10, 10, 500, 600);
	}

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    	  jToolBar1 = new javax.swing.JToolBar();
          jSlider1 = new javax.swing.JSlider();
          jPanel1 = new javax.swing.JPanel();
          jScrollPane1 = new javax.swing.JScrollPane(grafoGui);

          setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

          jToolBar1.setRollover(true);

          jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
              public void stateChanged(javax.swing.event.ChangeEvent evt) {
                  jSlider1StateChanged(evt);
              }
          });
          jToolBar1.add(jSlider1);

          javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
          jPanel1.setLayout(jPanel1Layout);
          jPanel1Layout.setHorizontalGroup(
              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
          );
          jPanel1Layout.setVerticalGroup(
              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
          );

          javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
          getContentPane().setLayout(layout);
          layout.setHorizontalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
              .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          );
          layout.setVerticalGroup(
              layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
          );

          pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {
    	int val = jSlider1.getValue();
    	grafoGui.setScale(val*2.0/100.0);
    	//System.out.println(val*2.0/100.0);
    	
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

}
