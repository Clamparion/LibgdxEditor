package com.clamparion.engine.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyVertexPanel extends JPanel {
	Array<Vector2> vertexArray;
	
	Array<VertexInput> inputs;
	
	/**
	 * Create the panel.
	 */
	public ModifyVertexPanel(final DektopEditor deskEditor) {
		inputs = new Array<VertexInput>();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblVertexEdit = new JLabel("Vertex Edit");
		GridBagConstraints gbc_lblVertexEdit = new GridBagConstraints();
		gbc_lblVertexEdit.insets = new Insets(0, 0, 5, 5);
		gbc_lblVertexEdit.gridx = 0;
		gbc_lblVertexEdit.gridy = 0;
		gbc_lblVertexEdit.gridwidth = 1;
		add(lblVertexEdit, gbc_lblVertexEdit);
		
		JButton btnAddVertex = new JButton("Add Vertex");
		btnAddVertex.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deskEditor.addVertexToFixture();
				
			}
		});
		GridBagConstraints gbc_btnAddVertex = new GridBagConstraints();
		gbc_btnAddVertex.insets = new Insets(15, 0, 5, 5);
		gbc_btnAddVertex.gridx = 0;
		gbc_btnAddVertex.gridy = 1;
		gbc_btnAddVertex.gridwidth = 2;
		add(btnAddVertex, gbc_btnAddVertex);
		 
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deskEditor.addModifyedFixtureToBody();
			}
		});
		GridBagConstraints gbc_btnApply = new GridBagConstraints();
		gbc_btnApply.insets = new Insets(0, 0, 5, 5);
		gbc_btnApply.gridx =1;
		gbc_btnApply.gridy = 0;
		gbc_btnApply.gridwidth = 1;
		add(btnApply, gbc_btnApply);
		
	}
	
	public void setCombobox(Array<Vector2> vertexArray){
		this.vertexArray = vertexArray;
		//comboBox.removeAllItems();
		for (int i = 0; i < vertexArray.size; i++) {
			//comboBox.addItem("Vertex " + i);
			addVertex(i);
		}
	}
	
	public void updateVertexInputs(){
		if(vertexArray.size == inputs.size){
			for (int i = 0; i < vertexArray.size; i++) {
				inputs.get(i).setXandY(vertexArray.get(i).x, vertexArray.get(i).y);
			}
		}
	}
	public void updateVertexCount() {
		for (int i = 0; i < inputs.size; i++) {
			inputs.get(i).remove(this);
		}
		inputs.clear();
		for (int i = 0; i < vertexArray.size; i++) {
			//comboBox.addItem("Vertex " + i);
			addVertex(i);
		}
		this.repaint();
		this.revalidate();
	}
	
	public void addVertex(int index){
		JLabel lblVertex = new JLabel("Vertex"+ index + ":");
		GridBagConstraints gbc_lblVertex = new GridBagConstraints();
		gbc_lblVertex.insets = new Insets(0, 0, 0, 0);
		gbc_lblVertex.anchor = GridBagConstraints.EAST;
		gbc_lblVertex.gridx = 0;
		gbc_lblVertex.gridy = 2+index;
		add(lblVertex, gbc_lblVertex);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2+index;
		add(panel, gbc_panel);
		
		JTextField textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(6);
		textField_2.setText(vertexArray.get(index).x + "");
		
		JTextField textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(6);
		textField_3.setText(vertexArray.get(index).y + "");
		
		
		inputs.add(new VertexInput(lblVertex, panel, textField_2, textField_3));
	}

}
