package graphicalUI;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class DataBase {
	
	/**
     * Create the GUI for DEFINITION of solution
     */
	
	// Components for GUI DEFINITION of solution
	private String fileName;
	private String latestUpdate;
	private JTabbedPane tabbedPane;
	private JPanel startPanel;
	private JLabel labelKeys;
	private JLabel labelLocks;
	private JTextField keyField;
	private JTextField lockField;
	private JButton button1;
	private JButton button2;
	private Box lineOne;
	private Box lineTwo;
	private Box inputBox;
	private JPanel inputPanel;
	private JPanel matrixPanel;
	private int keyAmount;
	private int lockAmount;
	private ArrayList<JCheckBox> checkBoxes;
	
	// Setters for GUI DEFINITION of solution
	public void setFileName(String name) {
		fileName = name;
	}
	
	public void setLatestUpdate(String date) {
		latestUpdate = date;
	}
	
	public void setTabbedPane(JTabbedPane newTabbedPane) {
		tabbedPane = newTabbedPane;
	}
	
	public void setStartPanel(JPanel panel) {
		startPanel = panel;
	}
	
	public void setKeyLabel(JLabel label) {
		labelKeys = label;
	}
	
	public void setLockLabel(JLabel label) {
		labelLocks = label;
	}
	
	public void setKeyField(JTextField field) {
		keyField = field;
	}
	
	public void setLockField(JTextField field) {
		lockField = field;
	}
	
	public void setButton1(JButton button) {
		button1 = button;
	}
	
	public void setButton2(JButton button) {
		button2 = button;
	}
	
	public void setLineOne(Box box) {
		lineOne = box;
	}
	
	public void setLineTwo(Box box) {
		lineTwo = box;
	}
	
	public void setInputBox(Box box) {
		inputBox = box;
	}
	
	public void setInputPanel(JPanel panel) {
		inputPanel = panel;
	}
	
	public void setMatrixPanel(JPanel panel) {
		matrixPanel = panel;
	}
	
	public void setKeyAmount(int amount) {
		keyAmount = amount;
	}
	
	public void setLockAmount(int amount) {
		lockAmount = amount;
	}
	
	public void setCheckBoxes(ArrayList<JCheckBox> boxes) {
		checkBoxes = boxes;
	}
	
	// Getters for GUI DEFINITION of solution
	public String getFileName() {
		return fileName;
	}
	
	public String getLatestUpdate() {
		return latestUpdate;
	}
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public JPanel getStartPanel() {
		return startPanel;
	}

	public JLabel getKeyLabel() {
		return labelKeys;
	}
	
	public JLabel getLockLabel() {
		return labelLocks;
	}
	
	public JTextField getKeyField() {
		return keyField;
	}
	
	public JTextField getLockField( ) {
		return lockField;
	}
	
	public JButton getButton1( ) {
		return button1;
	}
	
	public JButton getButton2() {
		return button2;
	}
	
	public Box getLineOne() {
		return lineOne;
	}
	
	public Box getLineTwo() {
		return lineTwo;
	}
	
	public Box getInputBox() {
		return inputBox;
	}
	
	public JPanel getInputPanel() {
		return inputPanel;
	}
	
	public JPanel getMatrixPanel() {
		return matrixPanel;
	}
	
	public int getKeyAmount() {
		return keyAmount;
	}
	
	public int getLockAmount() {
		return lockAmount;
	}
	
	public ArrayList<JCheckBox> getCheckBoxes() {
		return checkBoxes;
	}
	
	/**
     * Create the GUI for PRESENTATION of solution
     */
	private Box matrixBox;
	private Box labelBox;
	private int positions;
	private ArrayList<String> keyText;
	private ArrayList<String> lockText;
	private ArrayList<String> keySolution;
	private ArrayList<String> lockSolution;
	
	// Setters for GUI PRESENTATION of solution
	public void setMatrixBox(Box box) {
		matrixBox = box;
	}
	
	public void setLabelBox(Box labels) {
		labelBox = labels;
	}
	
	public void setPositions(int pos) {
		positions = pos;
	}
	
	public void setKeyText(ArrayList<String> text) {
		keyText = text;
	}
	
	public void setLockText(ArrayList<String> text) {
		lockText = text;
	}
	
	public void setKeySolution(ArrayList<String> keys) {
		keySolution = keys;
	}
	
	public void setLockSolution(ArrayList<String> locks) {
		lockSolution = locks;
	}

	public void printResult() {
		
		// Create frame for the presentation of the result
		JFrame presentationFrame = new JFrame("Lock System Solution");
		presentationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create and set up content pane
        Container pane = new Container();
        presentationFrame.setContentPane(pane);
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        // Create the topPanel with a logo for Unlocker Software
        JPanel topPanel = new JPanel();
           	
        ImageIcon image = new ImageIcon(getClass().getResource("Unlocker_loggo_h60.png"));
    	JLabel loggo = new JLabel(image);
    	
    	topPanel.add(loggo);
    	
    	GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;

		// Add logo panel to the top of the pane
		c.ipady = 20;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		pane.add(topPanel, c);
		
		// Add labelPanel to the pane
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		pane.add(labelBox, c);
				
		// Create box for the variables date, name, positions, keyAmount and lockAmount
		Box resultBox = Box.createVerticalBox();
		JLabel dateLabel = new JLabel(latestUpdate);
		JLabel nameLabel = new JLabel(fileName);
		JLabel positionLabel = new JLabel(String.valueOf(positions));
		JLabel keyLabel = new JLabel(String.valueOf(keyAmount));
		JLabel lockLabel = new JLabel(String.valueOf(lockAmount));
		
		resultBox.add(dateLabel);
		resultBox.add(nameLabel);
		resultBox.add(positionLabel);
		resultBox.add(keyLabel);
		resultBox.add(lockLabel);
		
		// Add resultBox to the pane
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(resultBox, c);
		
		// Create panel for the input matrix and add the matrixBix to the panel
		JPanel inputMatrixPanel = new JPanel();
		Border border = BorderFactory.createTitledBorder("Input Matrix");
		inputMatrixPanel.setBorder(border);
		inputMatrixPanel.add(matrixBox);

		// Add the matrix panel to the pane
		c.ipady = 20;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		pane.add(inputMatrixPanel, c);
		
		// Define rowAmount to display a lock and key manifest with same height
		int rowAmount = 0;
		if(keyAmount > lockAmount) {
			rowAmount = keyAmount;
		} else {
			rowAmount = lockAmount;
		}
		
		// Create boxes and panels for the key manifest presentation
		Box keyLabelBox = Box.createVerticalBox();
		Box keySolutionBox = Box.createVerticalBox();
		for(int i = 0; i < keySolution.size(); i++) {
			keyLabelBox.add(new JLabel(keyText.get(i) + ":----- "));
			keySolutionBox.add(new JLabel("     " + keySolution.get(i)));
		}
		
		int rowDiff = 0;
		
		if(rowAmount > keyAmount) {
			rowDiff = rowAmount - keyAmount;
			for(int i = 0; i < rowDiff; i++) {
				keyLabelBox.add(new JLabel("     "));
				keySolutionBox.add(new JLabel("     "));
			}
		}

		Box keyFinalBox = Box.createHorizontalBox();
		keyFinalBox.add(keyLabelBox);
		keyFinalBox.add(keySolutionBox);
		
		JPanel keyPanel = new JPanel();
		Border borderKey = BorderFactory.createTitledBorder("Key Manifest");
		keyPanel.setBorder(borderKey);
		keyPanel.add(keyFinalBox);
		
		// Create boxes and panels for the lock manifest presentation
		Box lockLabelBox = Box.createVerticalBox();
		Box lockSolutionBox = Box.createVerticalBox();
		for(int i = 0; i < lockAmount; i++) {
			lockLabelBox.add(new JLabel(lockText.get(i + 1) + ":----- "));
			lockSolutionBox.add(new JLabel("     " + lockSolution.get(i)));
		}
		
		if(rowAmount > lockAmount) {
			rowDiff = rowAmount - lockAmount;
			for(int i = 0; i < rowDiff; i++) {
				lockLabelBox.add(new JLabel("     "));
				lockSolutionBox.add(new JLabel("     "));
			}
		}
		
		Box lockFinalBox = Box.createHorizontalBox();
		lockFinalBox.add(lockLabelBox);
		lockFinalBox.add(lockSolutionBox);
		
		JPanel lockPanel = new JPanel();
		Border borderLock = BorderFactory.createTitledBorder("Lock Manifest");
		lockPanel.setBorder(borderLock);
		lockPanel.add(lockFinalBox);
		
		// Add the keyPanel and lockPanel to the pane
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		pane.add(keyPanel, c);
		
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 3;
		pane.add(lockPanel, c);
		
		 // Create the bottomPanel with a logo for LIECHA
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
           	
        ImageIcon image1 = new ImageIcon(getClass().getResource("Liecha_Logga_w60.png"));
    	JLabel liechaLoggo = new JLabel(image1);
    	liechaLoggo.setAlignmentX(Component.CENTER_ALIGNMENT);
    	
    	bottomPanel.add(liechaLoggo);
    	
    	ImageIcon image2 = new ImageIcon(getClass().getResource("developed@Liecha_Text_h30.png"));
    	JLabel textLoggo = new JLabel(image2);
    	textLoggo.setAlignmentX(Component.CENTER_ALIGNMENT);
    	bottomPanel.add(textLoggo);
    	
    	JLabel webAdress = new JLabel("www.liecha.se");
    	webAdress.setAlignmentX(Component.CENTER_ALIGNMENT);
    	bottomPanel.add(webAdress);
    	
    	// Add the keyPanel and lockPanel to the pane
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		pane.add(bottomPanel, c);
		
        // Display the window.
	    presentationFrame.setSize(400, 650);
	    presentationFrame.setVisible(true);
	}
}
