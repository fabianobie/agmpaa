package br.uece.comp.paa.agm.jgraph;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.CellEditorListener;

import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.GraphConstants;
import org.jgraph.plaf.basic.BasicGraphUI;

public class PaaGraphModel extends JGraph {

	public static JFrame myFrame;

	public static void main(String[] args) {
		myFrame = new JFrame("Tutorial");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JGraph graph = new PaaGraphModel();
		myFrame.getContentPane().add(graph);
		myFrame.setSize(520, 390);
		myFrame.show();
		ToolTipManager.sharedInstance().registerComponent(graph);
	}

	//
	// Adding Tooltips
	//

	// Return Cell Label as a Tooltip
	public String getToolTipText(MouseEvent e) {
		if (e != null) {
			// Fetch Cell under Mousepointer
			Object c = getFirstCellForLocation(e.getX(), e.getY());
			if (c != null)
				// Convert Cell to String and Return
				return convertValueToString(c);
		}
		return null;
	}

	//
	// Customizing In-Place Editing
	//

	public void updateUI() {
		// Install a new UI
		setUI(new DialogUI());
		invalidate();
	}

	public class DialogUI extends BasicGraphUI {

		protected CellEditorListener cellEditorListener;

		protected JFrame editDialog = null;

		protected void completeEditing(boolean messageStop,
				boolean messageCancel, boolean messageGraph) {
			if (stopEditingInCompleteEditing && editingComponent != null
					&& editDialog != null) {
				Component oldComponent = editingComponent;
				Object oldCell = editingCell;
				GraphCellEditor oldEditor = cellEditor;
				Object newValue = oldEditor.getCellEditorValue();
				Rectangle2D editingBounds = graph.getCellBounds(editingCell);
				boolean requestFocus = (graph != null && (graph.hasFocus() || editingComponent
						.hasFocus()));
				editingCell = null;
				editingComponent = null;
				if (messageStop)
					oldEditor.stopCellEditing();
				else if (messageCancel)
					oldEditor.cancelCellEditing();
				editDialog.dispose();
				if (requestFocus)
					graph.requestFocus();
				if (messageGraph) {
					Map map = new Hashtable();
					GraphConstants.setValue(map, newValue);
					Map insert = new Hashtable();
					insert.put(oldCell, map);
					graphLayoutCache.edit(insert, null, null, null);
				}
				updateSize();
				// Remove Editor Listener
				if (oldEditor != null && cellEditorListener != null)
					oldEditor.removeCellEditorListener(cellEditorListener);
				cellEditor = null;
				editDialog = null;
			}
		}

		protected boolean startEditing(Object cell, MouseEvent event) {
			completeEditing();
			if (graph.isCellEditable(cell) && editDialog == null) {
				CellView tmp = graphLayoutCache.getMapping(cell, false);
				cellEditor = tmp.getEditor();
				editingComponent = cellEditor.getGraphCellEditorComponent(
						graph, cell, graph.isCellSelected(cell));
				if (cellEditor.isCellEditable(event)) {
					editingCell = cell;
					Dimension editorSize = editingComponent.getPreferredSize();
					editDialog = new JFrame("Edit "
							+ graph.convertValueToString(cell));
					// new JDialog(myFrame,
					// "Edit "+graph.convertValueToString(cell), true);
					editDialog.setSize(editorSize.width, editorSize.height);
					editDialog.getContentPane().add(editingComponent);
					editingComponent.validate();
					editDialog.pack();
					editDialog.show();
					// Add Editor Listener
					if (cellEditorListener == null)
						cellEditorListener = createCellEditorListener();
					if (cellEditor != null && cellEditorListener != null)
						cellEditor.addCellEditorListener(cellEditorListener);

					if (cellEditor.shouldSelectCell(event)) {
						stopEditingInCompleteEditing = false;
						try {
							graph.setSelectionCell(cell);
						} catch (Exception e) {
							System.err.println("Editing exception: " + e);
						}
						stopEditingInCompleteEditing = true;
					}

					if (event instanceof MouseEvent) {
						/*
						 * Find the component that will get forwarded all the
						 * mouse events until mouseReleased.
						 */
						Point componentPoint = SwingUtilities.convertPoint(
								graph, new Point(event.getX(), event.getY()),
								editingComponent);

						/*
						 * Create an instance of BasicTreeMouseListener to
						 * handle passing the mouse/motion events to the
						 * necessary component.
						 */
						// We really want similiar behavior to
						// getMouseEventTarget,
						// but it is package private.
						Component activeComponent = SwingUtilities
								.getDeepestComponentAt(editingComponent,
										componentPoint.x, componentPoint.y);
						if (activeComponent != null) {
							new MouseInputHandler(graph, activeComponent, event);
						}
					}
					return true;
				} else
					editingComponent = null;
			}
			return false;
		}

	}

}
