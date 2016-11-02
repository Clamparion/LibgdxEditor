package com.clamparion.engine.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.clamparion.engine.Starter;
import com.clamparion.engine.UiHandler;
import com.clamparion.engine.physicsTools.WorldUtils.FixtureType;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;

public class DektopEditor extends JFrame implements UiHandler{
	private static final long serialVersionUID = 1L;
	private final JPanel contentPane;
	private final Starter game; 
	private JTextField textField_2;
	private JTextField textField_3;
	ButtonGroup bG = new ButtonGroup();
	private JTextField textField_4;
	private JTextField textField_5;
	JComboBox<String> fixtureCombobox;
	Body selectedBody;
	Fixture selectedFixture;

	JCheckBox chckbxSensorcheckbox;

	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_14;
	private JTextField textField_15;

	final JPanel panel_5;
	JPanel panel_8;

	ModifyVertexPanel modifyVertexPanel = null;
	JointPanel jointPanel = null;
	
	boolean modifyFixtureActivated = false;
	public final DektopEditor me;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DektopEditor frame = new DektopEditor();
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					frame.setVisible(true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */


	public DektopEditor() {
		me = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1164, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = 600; //600;
		//config.height = 960; //960
		config.vSyncEnabled = false;
		config.samples = 8;

		game = new Starter(this);

		LwjglAWTCanvas canvas = new LwjglAWTCanvas(game, config);
		//panel.add(canvas.getCanvas());
		contentPane.add(canvas.getCanvas(), BorderLayout.CENTER);
		//contentPane.add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setPreferredSize(new Dimension(150, panel_1.getHeight()));

		JLabel lblX = new JLabel("x: ");
		panel_1.add(lblX);

		textField_4 = new JTextField();
		textField_4.setText("0.0");
		panel_1.add(textField_4);
		textField_4.setColumns(10);

		JLabel lblY = new JLabel("y: ");
		panel_1.add(lblY);

		textField_5 = new JTextField();
		textField_5.setText("0.0");
		panel_1.add(textField_5);
		textField_5.setColumns(10);

		JLabel lblSize = new JLabel("Size/Radius:");
		panel_1.add(lblSize);

		textField_3 = new JTextField();
		textField_3.setText("1.0");
		panel_1.add(textField_3);
		textField_3.setColumns(5);

		JRadioButton rdbtnStaticBody = new JRadioButton("Static Body");
		rdbtnStaticBody.setPreferredSize(new Dimension(120, 25));
		panel_1.add(rdbtnStaticBody);

		JRadioButton rdbtnDynamicBody = new JRadioButton("Dynamic Body");
		rdbtnDynamicBody.setSelected(true);
		rdbtnDynamicBody.setPreferredSize(new Dimension(120, 25));
		panel_1.add(rdbtnDynamicBody);

		JRadioButton rdbtnKinematicBody = new JRadioButton("Kinematic Body");
		rdbtnKinematicBody.setPreferredSize(new Dimension(120, 25));
		panel_1.add(rdbtnKinematicBody);


		bG.add(rdbtnStaticBody);
		bG.add(rdbtnDynamicBody);
		bG.add(rdbtnKinematicBody);
		JButton btnTest = new JButton("Add Circle");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.createBody(getPosX(), getPosY(),getSelectedBodyType(), FixtureType.CIRCLE, getSizeRadius());
			}
		});
		btnTest.setPreferredSize(new Dimension(120, 25));


		panel_1.add(btnTest);

		JButton btnAddBox = new JButton("Add Square");
		btnAddBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.createBody(getPosX(), getPosY(),getSelectedBodyType(), FixtureType.BOX, getSizeRadius());
			}
		});
		btnAddBox.setPreferredSize(new Dimension(120, 25));
		panel_1.add(btnAddBox);

		JButton btnAddTriangle = new JButton("Add Triangle");
		btnAddTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.createBody(getPosX(), getPosY(),getSelectedBodyType(), FixtureType.TRIANGLE, getSizeRadius());
			}
		});
		btnAddTriangle.setPreferredSize(new Dimension(120, 25));
		panel_1.add(btnAddTriangle);

		JButton btnAddPentagon = new JButton("Add Pentagon");
		btnAddPentagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.createBody(getPosX(), getPosY(),getSelectedBodyType(), FixtureType.PENTAGON, getSizeRadius());
			}
		});
		btnAddPentagon.setPreferredSize(new Dimension(120, 25));
		panel_1.add(btnAddPentagon);

		JButton btnAddHexagon = new JButton("Add Hexagon");
		btnAddHexagon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.createBody(getPosX(), getPosY(),getSelectedBodyType(), FixtureType.HEXAGON, getSizeRadius());
			}
		});
		btnAddHexagon.setPreferredSize(new Dimension(120, 25));
		panel_1.add(btnAddHexagon);

		JPanel panel_11 = new JPanel();
		panel_1.add(panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[]{0, 0};
		gbl_panel_11.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_11.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);

		JLabel lblJoints = new JLabel("Joints:");
		GridBagConstraints gbc_lblJoints = new GridBagConstraints();
		gbc_lblJoints.insets = new Insets(0, 0, 5, 0);
		gbc_lblJoints.gridx = 0;
		gbc_lblJoints.gridy = 0;
		panel_11.add(lblJoints, gbc_lblJoints);

		final JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"DistanceJoint", /*"FrictionJoint", "GearJoint", "MotorJoint",*/ "PrismaticJoint", "PulleyJoint", "RevoluteJoint", "RopeJoint", "WeldJoint" /*,"WheelJoint"*/}));
		comboBox_2.setSelectedIndex(0);
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 0;
		gbc_comboBox_2.gridy = 1;
		panel_11.add(comboBox_2, gbc_comboBox_2);

		JButton btnAddJoint = new JButton("Add Joint");
		btnAddJoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedJoint = comboBox_2.getSelectedItem().toString();
				game.addNewJoint(selectedJoint);
			}
		});
		GridBagConstraints gbc_btnAddJoint = new GridBagConstraints();
		gbc_btnAddJoint.gridx = 0;
		gbc_btnAddJoint.gridy = 2;
		panel_11.add(btnAddJoint, gbc_btnAddJoint);

		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(250, panel_2.getHeight()));
		contentPane.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("New button");
		panel_4.add(btnNewButton);

		panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.NORTH);
		panel_5.setLayout(new BorderLayout(0, 0));

		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(250, 400));
		panel_6.add(panel_7);
		panel_7.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_8 = new JPanel();
		panel_8.setVisible(false);
		panel_5.add(panel_8, BorderLayout.NORTH);
		GridBagLayout lay = new GridBagLayout();
		lay.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		lay.columnWeights = new double[]{0.0, 1.0};

		//c.fill = GridBagConstraints.HORIZONTAL;

		KeyAdapter updateBodyKA = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					updateBody();
				}
			}
		};

		panel_8.setLayout(lay);

		JLabel lblNewLabel_1 = new JLabel("Pos(x,y):");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_8.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JPanel panel_10 = new JPanel();
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.insets = new Insets(0, 0, 5, 5);
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 1;
		gbc_panel_10.gridy = 0;
		panel_8.add(panel_10, gbc_panel_10);

		textField = new JTextField();
		textField.setColumns(6);
		panel_10.add(textField);

		textField_1 = new JTextField();
		textField_1.setColumns(6);
		panel_10.add(textField_1);

		JLabel lblAngle = new JLabel("angle:");

		lblAngle.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAngle = new GridBagConstraints();
		gbc_lblAngle.insets = new Insets(0, 0, 5, 5);
		gbc_lblAngle.gridx = 0;
		gbc_lblAngle.gridy = 2;
		panel_8.add(lblAngle, gbc_lblAngle);



		textField_2 = new JTextField();
		textField.addKeyListener(updateBodyKA);
		textField_1.addKeyListener(updateBodyKA);
		textField_2.addKeyListener(updateBodyKA);





		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 2;
		panel_8.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JLabel lblVelocity = new JLabel("Velocity");
		lblVelocity.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVelocity.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblVelocity = new GridBagConstraints();
		gbc_lblVelocity.insets = new Insets(5, 0, 10, 5);
		gbc_lblVelocity.gridx = 0;
		gbc_lblVelocity.gridy = 3;
		gbc_lblVelocity.gridwidth = 2;
		panel_8.add(lblVelocity, gbc_lblVelocity);

		JLabel lblLinvel = new JLabel("Linear(x,y):");
		GridBagConstraints gbc_lblLinvel = new GridBagConstraints();
		gbc_lblLinvel.insets = new Insets(0, 0, 5, 5);
		gbc_lblLinvel.gridx = 0;
		gbc_lblLinvel.gridy = 4;
		panel_8.add(lblLinvel, gbc_lblLinvel);

		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.insets = new Insets(0, 0, 5, 5);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 1;
		gbc_panel_9.gridy = 4;
		panel_8.add(panel_9, gbc_panel_9);

		textField_11 = new JTextField();
		panel_9.add(textField_11);
		textField_11.setColumns(6);

		textField_12 = new JTextField();
		panel_9.add(textField_12);
		textField_12.setColumns(6);

		JLabel lblAngular = new JLabel("Angular:");
		GridBagConstraints gbc_lblAngular = new GridBagConstraints();
		gbc_lblAngular.anchor = GridBagConstraints.EAST;
		gbc_lblAngular.insets = new Insets(0, 0, 5, 5);
		gbc_lblAngular.gridx = 0;
		gbc_lblAngular.gridy = 5;
		panel_8.add(lblAngular, gbc_lblAngular);

		textField_13 = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 5, 5);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 1;
		gbc_textField_13.gridy = 5;
		panel_8.add(textField_13, gbc_textField_13);
		textField_13.setColumns(10);

		JLabel lblDamping = new JLabel("Damping");
		lblDamping.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDamping.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblDamping = new GridBagConstraints();
		gbc_lblDamping.insets = new Insets(5, 0, 10, 0);
		gbc_lblDamping.gridx = 0;
		gbc_lblDamping.gridy = 6;
		gbc_lblDamping.gridwidth = 6;
		panel_8.add(lblDamping, gbc_lblDamping);

		JLabel lblNewLabel_3 = new JLabel("Linear:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 7;
		panel_8.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField_14 = new JTextField();
		GridBagConstraints gbc_textField_14 = new GridBagConstraints();
		gbc_textField_14.insets = new Insets(0, 0, 5, 5);
		gbc_textField_14.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_14.gridx = 1;
		gbc_textField_14.gridy = 7;
		panel_8.add(textField_14, gbc_textField_14);
		textField_14.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Angular:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 8;
		panel_8.add(lblNewLabel_4, gbc_lblNewLabel_4);

		textField_15 = new JTextField();
		GridBagConstraints gbc_textField_15 = new GridBagConstraints();
		gbc_textField_15.insets = new Insets(0, 0, 5, 5);
		gbc_textField_15.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_15.gridx = 1;
		gbc_textField_15.gridy = 8;
		panel_8.add(textField_15, gbc_textField_15);
		textField_15.setColumns(10);

		JLabel lblFixturesettings = new JLabel("FixturesSettings:");

		lblFixturesettings.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFixturesettings.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblFixturesettings = new GridBagConstraints();
		gbc_lblFixturesettings.insets = new Insets(5, 0, 10, 5);
		gbc_lblFixturesettings.gridx = 0;
		gbc_lblFixturesettings.gridy = 9;
		gbc_lblFixturesettings.gridwidth = 2;
		panel_8.add(lblFixturesettings, gbc_lblFixturesettings);


		JLabel lblFixture = new JLabel("Fixture");
		lblFixture.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblFixture = new GridBagConstraints();
		gbc_lblFixture.insets = new Insets(0, 0, 5, 5);
		gbc_lblFixture.gridx = 0;
		gbc_lblFixture.gridy = 10;
		panel_8.add(lblFixture, gbc_lblFixture);

		fixtureCombobox = new JComboBox<String>();
		GridBagConstraints gbc_fixtureCombobox = new GridBagConstraints();
		gbc_fixtureCombobox.insets = new Insets(0, 0, 5, 5);
		gbc_fixtureCombobox.gridx = 1;
		gbc_fixtureCombobox.gridy = 10;
		panel_8.add(fixtureCombobox, gbc_fixtureCombobox);
		fixtureCombobox.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				int index = fixtureCombobox.getSelectedIndex();
				selectFixture(index);
			}
		});

		JLabel lblIssensor = new JLabel("isSensor:");
		GridBagConstraints gbc_lblIssensor = new GridBagConstraints();
		gbc_lblIssensor.insets = new Insets(0, 0,5, 5);
		gbc_lblIssensor.gridx = 0;
		gbc_lblIssensor.gridy = 11;
		panel_8.add(lblIssensor, gbc_lblIssensor);

		chckbxSensorcheckbox = new JCheckBox("");
		chckbxSensorcheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setFixture();
			}
		});

		GridBagConstraints gbc_chckbxSensorcheckbox = new GridBagConstraints();
		gbc_chckbxSensorcheckbox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSensorcheckbox.gridx = 1;
		gbc_chckbxSensorcheckbox.gridy = 11;
		panel_8.add(chckbxSensorcheckbox, gbc_chckbxSensorcheckbox);

		JLabel lblShape = new JLabel("Shape:");
		GridBagConstraints gbc_lblShape = new GridBagConstraints();
		gbc_lblShape.anchor = GridBagConstraints.EAST;
		gbc_lblShape.insets = new Insets(0, 0, 5, 5);
		gbc_lblShape.gridx = 0;
		gbc_lblShape.gridy = 12;
		panel_8.add(lblShape, gbc_lblShape);

		textField_6 = new JTextField();
		textField_6.setEditable(false);
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 5, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 12;
		panel_8.add(textField_6, gbc_textField_6);
		textField_6.setColumns(10);

		JLabel lblRadius = new JLabel("Radius");
		GridBagConstraints gbc_lblRadius = new GridBagConstraints();
		gbc_lblRadius.anchor = GridBagConstraints.EAST;
		gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
		gbc_lblRadius.gridx = 0;
		gbc_lblRadius.gridy = 13;
		panel_8.add(lblRadius, gbc_lblRadius);

		textField_10 = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 5, 5);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 1;
		gbc_textField_10.gridy = 13;
		panel_8.add(textField_10, gbc_textField_10);
		textField_10.setColumns(10);

		JLabel lblDensity = new JLabel("Density:");
		GridBagConstraints gbc_lblDensity = new GridBagConstraints();
		gbc_lblDensity.anchor = GridBagConstraints.EAST;
		gbc_lblDensity.insets = new Insets(0, 0, 5, 5);
		gbc_lblDensity.gridx = 0;
		gbc_lblDensity.gridy = 14;
		panel_8.add(lblDensity, gbc_lblDensity);

		textField_7 = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 5, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 1;
		gbc_textField_7.gridy = 14;
		panel_8.add(textField_7, gbc_textField_7);
		textField_7.setColumns(10);

		JLabel lblRestitution = new JLabel("Restitution");
		GridBagConstraints gbc_lblRestitution = new GridBagConstraints();
		gbc_lblRestitution.anchor = GridBagConstraints.EAST;
		gbc_lblRestitution.insets = new Insets(0, 0, 5, 5);
		gbc_lblRestitution.gridx = 0;
		gbc_lblRestitution.gridy = 15;
		panel_8.add(lblRestitution, gbc_lblRestitution);

		textField_8 = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 5, 5);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 15;
		panel_8.add(textField_8, gbc_textField_8);
		textField_8.setColumns(10);



		JLabel lblFriction = new JLabel("Friction:");
		GridBagConstraints gbc_lblFriction = new GridBagConstraints();
		gbc_lblFriction.anchor = GridBagConstraints.EAST;
		gbc_lblFriction.insets = new Insets(0, 0, 5, 5);
		gbc_lblFriction.gridx = 0;
		gbc_lblFriction.gridy = 16;
		panel_8.add(lblFriction, gbc_lblFriction);

		textField_9 = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 5, 5);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 1;
		gbc_textField_9.gridy = 16;
		panel_8.add(textField_9, gbc_textField_9);
		textField_9.setColumns(10);



		JLabel lblCategorybits = new JLabel("CategoryBits:");
		GridBagConstraints gbc_lblCategorybits = new GridBagConstraints();
		gbc_lblCategorybits.anchor = GridBagConstraints.EAST;
		gbc_lblCategorybits.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategorybits.gridx = 0;
		gbc_lblCategorybits.gridy = 17;
		panel_8.add(lblCategorybits, gbc_lblCategorybits);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 17;
		panel_8.add(comboBox, gbc_comboBox);

		JLabel lblMaskbits = new JLabel("MaskBits:");
		GridBagConstraints gbc_lblMaskbits = new GridBagConstraints();
		gbc_lblMaskbits.anchor = GridBagConstraints.EAST;
		gbc_lblMaskbits.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaskbits.gridx = 0;
		gbc_lblMaskbits.gridy = 18;
		panel_8.add(lblMaskbits, gbc_lblMaskbits);

		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 18;
		panel_8.add(comboBox_1, gbc_comboBox_1);

		JButton btnModifyFixture = new JButton("Modify");
		btnModifyFixture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedFixture.getShape() instanceof PolygonShape){
					panel_8.setVisible(false);
					modifyFixtureActivated = true;
					Array<Vector2> selecVector = game.modifyFixture(selectedFixture);
					modifyVertexPanel = (new ModifyVertexPanel(me));
					panel_5.add(modifyVertexPanel, BorderLayout.NORTH);
					modifyVertexPanel.setCombobox(selecVector);
				}
			}
		});
		GridBagConstraints gbc_btnModifyFixture = new GridBagConstraints();
		gbc_btnModifyFixture.insets = new Insets(0, 0, 5, 5);
		gbc_btnModifyFixture.gridx = 0;
		gbc_btnModifyFixture.gridy = 19;
		panel_8.add(btnModifyFixture, gbc_btnModifyFixture);

		JButton btnDeleteFixture = new JButton("Delete ");
		GridBagConstraints gbc_btnDeleteFixture = new GridBagConstraints();
		gbc_btnDeleteFixture.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteFixture.gridx = 1;
		gbc_btnDeleteFixture.gridy = 19;
		panel_8.add(btnDeleteFixture, gbc_btnDeleteFixture);

		JButton btnAddFixture = new JButton("Add Fixture");
		GridBagConstraints gbc_btnAddFixture = new GridBagConstraints();
		gbc_btnAddFixture.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddFixture.insets = new Insets(0, 0, 0, 5);
		gbc_btnAddFixture.gridx = 1;
		gbc_btnAddFixture.gridy = 20;
		panel_8.add(btnAddFixture, gbc_btnAddFixture);

		KeyAdapter ka = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					setFixture();
				}
			}
		};

		textField_6.addKeyListener(ka);
		textField_7.addKeyListener(ka);
		textField_8.addKeyListener(ka);
		textField_9.addKeyListener(ka);
		textField_10.addKeyListener(ka);



		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_12 = new JPanel();
		panel_3.add(panel_12, BorderLayout.CENTER);

		JButton btnPlay = new JButton("Play");
		panel_12.add(btnPlay);

		JButton btnPause = new JButton("Pause");
		panel_12.add(btnPause);

		JButton btnStop = new JButton("Stop");
		panel_12.add(btnStop);

		JPanel panel_13 = new JPanel();
		panel_3.add(panel_13, BorderLayout.WEST);

		final JToggleButton tglbtnBodiesjointMode = new JToggleButton("enable Joint-Mode");
		panel_13.add(tglbtnBodiesjointMode);
		tglbtnBodiesjointMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				game.toggleJointMode(tglbtnBodiesjointMode.isSelected());
			}
		});
		
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.reset();
			}
		});
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.pausePhysics();
			}
		});
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.playPhysics();
			}
		});


		textField_11.addKeyListener(updateBodyKA);
		textField_12.addKeyListener(updateBodyKA);
		textField_13.addKeyListener(updateBodyKA);
		textField_14.addKeyListener(updateBodyKA);
		textField_15.addKeyListener(updateBodyKA);
	}

	public BodyType getSelectedBodyType(){
		for (Enumeration<AbstractButton> buttons = bG.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				if(button.getText().equals("Static Body")){
					return BodyType.StaticBody;
				} else if(button.getText().equals("Dynamic Body")){
					return BodyType.DynamicBody;
				} else {
					return BodyType.KinematicBody;
				}
			}
		}
		return null;
	}

	public float getSizeRadius(){
		try {
			return (float)Float.parseFloat(textField_3.getText().trim());
		} catch (Exception e) {
			return 1f;
		}
	}
	public float getPosX(){
		try {
			return (float)Float.parseFloat(textField_4.getText().trim());
		} catch (Exception e) {
			return 0f;
		}
	}
	public float getPosY(){
		try {
			return (float)Float.parseFloat(textField_5.getText().trim());
		} catch (Exception e) {
			return 0f;
		}
	}





	@Override
	public void setPositionAndAngle(Body body) {
		textField.setText(body.getPosition().x + "");
		textField_1.setText(body.getPosition().y  + "");
		textField_2.setText((body.getAngle() * MathUtils.radiansToDegrees) + "");
		//velocity
		textField_11.setText(body.getLinearVelocity().x + "");
		textField_12.setText(body.getLinearVelocity().y + "");
		textField_13.setText(body.getAngularVelocity() + "");

		//damping
		textField_14.setText(body.getLinearDamping() + "");
		textField_15.setText(body.getAngularDamping() + "");

	}

	private void selectFixture(int index){
		if(selectedBody != null && index >= 0){
			Array<Fixture> fixtureList = selectedBody.getFixtureList();
			if(fixtureList.size > index){
				Fixture fix = fixtureList.get(index);
				selectedFixture = fix;
				chckbxSensorcheckbox.setSelected(fix.isSensor());
				if(fix.getShape() instanceof PolygonShape){
					textField_6.setText("PolygonShape");
				}else if(fix.getShape() instanceof CircleShape){
					textField_6.setText("CircleShape");
				}
				textField_10.setText(fix.getShape().getRadius() + ""); 
				textField_7.setText(fix.getDensity() + "");
				textField_8.setText(fix.getRestitution() + "");
				textField_9.setText(fix.getFriction() + "");
			}
		}

	}
	private void setFixture(){
		if(selectedFixture != null){
			try {
				selectedFixture.setSensor(chckbxSensorcheckbox.isSelected());
				selectedFixture.getShape().setRadius(Float.parseFloat(textField_10.getText()));
				selectedFixture.setDensity(Float.parseFloat(textField_7.getText()));
				selectedFixture.setRestitution(Float.parseFloat(textField_8.getText()));
				selectedFixture.setFriction(Float.parseFloat(textField_9.getText()));

			} catch (Exception e) {
			}
		}
	}


	public void updateBody(){
		try {
			if(selectedBody != null){
				selectedBody.setTransform(Float.parseFloat(textField.getText()), Float.parseFloat(textField_1.getText()), (Float.parseFloat(textField_2.getText()) * MathUtils.degreesToRadians));

				selectedBody.setLinearVelocity(Float.parseFloat(textField_11.getText()), Float.parseFloat(textField_12.getText()));
				selectedBody.setAngularVelocity(Float.parseFloat(textField_13.getText()));
				selectedBody.setLinearDamping(Float.parseFloat(textField_14.getText()));
				selectedBody.setAngularDamping(Float.parseFloat(textField_15.getText()));

				game.uiValuesChanged(selectedBody);
			}

		} catch (Exception e) {
		}

	}

	public void addVertexToFixture(){
		game.addVertexToFixture();
	}
	public void addModifyedFixtureToBody(){


		game.addModifyedFixtureToBody();
		if(modifyVertexPanel != null)
			panel_5.remove(modifyVertexPanel);

		panel_5.remove(panel_8);
		panel_8.setVisible(true);
		panel_5.add(panel_8);

		panel_5.revalidate();
		panel_5.repaint();
	}

	@Override
	public void setSelectedBody(Body body) {
		selectedBody = body;
		Array<Fixture> fixtureList = body.getFixtureList();
		fixtureCombobox.removeAllItems();
		for (int i = 0; i < fixtureList.size; i++) {
			fixtureCombobox.addItem("Fixture " + i);

		}
		panel_8.setVisible(true);
		selectFixture(0);
	}

	@Override
	public void noBodySelected() {
		panel_8.setVisible(false);
		selectedBody = null;
		selectedFixture = null;

	}

	@Override
	public void updateVertexInputs() {
		if(modifyVertexPanel != null)
			modifyVertexPanel.updateVertexInputs();
	}

	@Override
	public void updateVertexCount() {
		if(modifyVertexPanel != null)
			modifyVertexPanel.updateVertexCount();

	}

	@Override
	public void setSelectedJoint(Joint joint) {
		
		panel_8.setVisible(false);
		jointPanel = (new JointPanel(me));
		if(joint != null){
			jointPanel.setJoint(joint);
		} else {
			jointPanel.setVisible(false);
		}
		
		
		panel_5.add(jointPanel, BorderLayout.NORTH);
		panel_5.revalidate();
		panel_5.repaint();
	}
}
