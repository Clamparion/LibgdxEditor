package com.clamparion.engine.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;

public class JointPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;

	/**
	 * Create the panel.
	 */
	DektopEditor mainFrame;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	
	
	public JointPanel(DektopEditor mainFrame) {
		this.mainFrame = mainFrame;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblJoint = new JLabel("Joint");
		GridBagConstraints gbc_lblJoint = new GridBagConstraints();
		gbc_lblJoint.insets = new Insets(0, 0, 5, 5);
		gbc_lblJoint.gridx = 0;
		gbc_lblJoint.gridy = 0;
		add(lblJoint, gbc_lblJoint);
		
		JLabel lblJointtype = new JLabel("JointType");
		GridBagConstraints gbc_lblJointtype = new GridBagConstraints();
		gbc_lblJointtype.anchor = GridBagConstraints.EAST;
		gbc_lblJointtype.insets = new Insets(0, 0, 5, 5);
		gbc_lblJointtype.gridx = 0;
		gbc_lblJointtype.gridy = 1;
		add(lblJointtype, gbc_lblJointtype);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		GridBagConstraints gbc_textField_11 = new GridBagConstraints();
		gbc_textField_11.insets = new Insets(0, 0, 5, 0);
		gbc_textField_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_11.gridx = 1;
		gbc_textField_11.gridy = 1;
		add(textField_11, gbc_textField_11);
		textField_11.setColumns(10);
		
		JLabel lblLocalanchora = new JLabel("localAnchorA");
		GridBagConstraints gbc_lblLocalanchora = new GridBagConstraints();
		gbc_lblLocalanchora.insets = new Insets(0, 0, 5, 5);
		gbc_lblLocalanchora.anchor = GridBagConstraints.EAST;
		gbc_lblLocalanchora.gridx = 0;
		gbc_lblLocalanchora.gridy = 2;
		add(lblLocalanchora, gbc_lblLocalanchora);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		add(panel, gbc_panel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(5);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(5);
		
		JLabel lblNewLabel = new JLabel("localAnchorB");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();	
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 3;
		add(panel_1, gbc_panel_1);
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(5);
		
		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(5);
		
		JLabel lblNewLabel_12 = new JLabel("groundAnchA");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 0;
		gbc_lblNewLabel_12.gridy = 4;
		add(lblNewLabel_12, gbc_lblNewLabel_12);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 1;
		gbc_panel_2.gridy = 4;
		add(panel_2, gbc_panel_2);
		
		textField_14 = new JTextField();
		textField_14.setColumns(5);
		panel_2.add(textField_14);
		
		textField_15 = new JTextField();
		textField_15.setColumns(5);
		panel_2.add(textField_15);
		
		JLabel lblNewLabel_13 = new JLabel("groundAnchB");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 0;
		gbc_lblNewLabel_13.gridy = 5;
		add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 5;
		add(panel_3, gbc_panel_3);
		
		textField_16 = new JTextField();
		textField_16.setColumns(5);
		panel_3.add(textField_16);
		
		textField_17 = new JTextField();
		textField_17.setColumns(5);
		panel_3.add(textField_17);
		
		JLabel lblNewLabel_11 = new JLabel("bounce in Hz");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 0;
		gbc_lblNewLabel_11.gridy = 6;
		add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		textField_10 = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 5, 0);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 1;
		gbc_textField_10.gridy = 6;
		add(textField_10, gbc_textField_10);
		textField_10.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("referenceAngle");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 7;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 0);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 1;
		gbc_textField_7.gridy = 7;
		add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("upperAngle");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 0;
		gbc_lblNewLabel_9.gridy = 8;
		add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 5, 0);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 8;
		add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("lowerAngle");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 0;
		gbc_lblNewLabel_10.gridy = 9;
		add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		textField_9 = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 0);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 1;
		gbc_textField_9.gridy = 9;
		add(textField_9, gbc_textField_9);
		textField_9.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("collideConnected");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 10;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JCheckBox chckbxCollidecon = new JCheckBox("");
		GridBagConstraints gbc_chckbxCollidecon = new GridBagConstraints();
		gbc_chckbxCollidecon.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxCollidecon.gridx = 1;
		gbc_chckbxCollidecon.gridy = 10;
		add(chckbxCollidecon, gbc_chckbxCollidecon);
		
		JLabel lblNewLabel_4 = new JLabel("(max)length");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 11;
		add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		textField_4 = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 11;
		add(textField_4, gbc_textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("enableLimit");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 12;
		add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JCheckBox chckbxLimit = new JCheckBox("");
		GridBagConstraints gbc_chckbxLimit = new GridBagConstraints();
		gbc_chckbxLimit.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxLimit.gridx = 1;
		gbc_chckbxLimit.gridy = 12;
		add(chckbxLimit, gbc_chckbxLimit);
		
		JLabel lblNewLabel_7 = new JLabel("upperTranslation");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 13;
		add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		textField_5 = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 13;
		add(textField_5, gbc_textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("lowerTranslation");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 14;
		add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		textField_6 = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 0);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 14;
		add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("enableMotor");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 15;
		add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JCheckBox chckbxMotor = new JCheckBox("");
		GridBagConstraints gbc_chckbxMotor = new GridBagConstraints();
		gbc_chckbxMotor.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxMotor.gridx = 1;
		gbc_chckbxMotor.gridy = 15;
		add(chckbxMotor, gbc_chckbxMotor);
		
		JLabel lblNewLabel_8 = new JLabel("motorSpeed");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 0;
		gbc_lblNewLabel_8.gridy = 16;
		add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		textField_12 = new JTextField();
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.insets = new Insets(0, 0, 5, 0);
		gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_12.gridx = 1;
		gbc_textField_12.gridy = 16;
		add(textField_12, gbc_textField_12);
		textField_12.setColumns(10);
		
		JLabel lblMaxmotorforce = new JLabel("maxMotorForce");
		GridBagConstraints gbc_lblMaxmotorforce = new GridBagConstraints();
		gbc_lblMaxmotorforce.anchor = GridBagConstraints.EAST;
		gbc_lblMaxmotorforce.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxmotorforce.gridx = 0;
		gbc_lblMaxmotorforce.gridy = 17;
		add(lblMaxmotorforce, gbc_lblMaxmotorforce);
		
		textField_13 = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 5, 0);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 1;
		gbc_textField_13.gridy = 17;
		add(textField_13, gbc_textField_13);
		textField_13.setColumns(10);

	}
	
	public void setJoint(Joint joint){
		textField_11.setText(joint.getType().toString());
	
		Vector2 anchorA = joint.getBodyA().getLocalPoint(joint.getAnchorA());
		Vector2 anchorB = joint.getBodyB().getLocalPoint(joint.getAnchorB());
		textField.setText(anchorA.x + "");
		textField_1.setText(anchorA.y + "");
		textField_2.setText(anchorB.x + "");
		textField_3.setText(anchorB.y + "");
	}

}
