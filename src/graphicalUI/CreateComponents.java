package graphicalUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CreateComponents {
	
	// Field
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
	private ArrayList<JCheckBox> checkBoxes;

	// Constructor
	public CreateComponents(DataBase dataBase) {
		createLabels(dataBase);
		createTextFields(dataBase);
		createButtons(dataBase);
		createBoxes(dataBase);
		createPanels(dataBase);
	}

	// Label definition
	private void createLabels(DataBase dataBase) {
		labelKeys = new JLabel(" Amount of keys: ");
		labelLocks = new JLabel("Amount of locks: ");
		
		// Store to dataBase
		dataBase.setKeyLabel(labelKeys);
		dataBase.setLockLabel(labelLocks);
	}
	
	// TextField definition
	private void createTextFields(DataBase dataBase) {
		final int FIELD_WIDTH = 10;
		keyField = new JTextField(FIELD_WIDTH);
		keyField.setText("");
		
		lockField = new JTextField(FIELD_WIDTH);
		lockField.setText("");
		
		// Store to dataBase
		dataBase.setKeyField(keyField);
		dataBase.setLockField(lockField);
	}
	
	// Button definition
	private void createButtons(DataBase dataBase) {

		button1 = new JButton("OK");
		button2 = new JButton("Calculate");
		
		// Store to dataBase
		dataBase.setButton1(button1);
		dataBase.setButton2(button2);
	}
		
	// Box definition
	private void createBoxes(DataBase dataBase) {
		
		// Define firstBox for Input Data 
		// --> specifies size of the Input Matrix 
		// (Amount of keys = amount of columns in Input Matrix)
		// (Amount of locks = amount of rows in Input Matrix)
		lineOne = Box.createHorizontalBox();
		lineOne.add(labelKeys);
		lineOne.add(keyField);
		
		lineTwo = Box.createHorizontalBox();
		lineTwo.add(labelLocks);
		lineTwo.add(lockField);
		
		inputBox = Box.createVerticalBox();
		inputBox.add(lineOne);
		inputBox.add(lineTwo);
		
		// Store to dataBase
		dataBase.setLineOne(lineOne);
		dataBase.setLineTwo(lineTwo);
		dataBase.setInputBox(inputBox);
	}
	
	// Panel definitions
	private void createPanels(DataBase dataBase) {
		inputPanel = new JPanel(new GridLayout(0, 1));
	    Border borderInput = BorderFactory.createTitledBorder("Input Data");
	    inputPanel.setBorder(borderInput);
	    inputPanel.add(inputBox);
	    
	    matrixPanel = new JPanel(new GridLayout(0, 1));
	    Border borderMatrix = BorderFactory.createTitledBorder("Input Matrix");
	    matrixPanel.setBorder(borderMatrix);
	    
		// Store to dataBase
		dataBase.setInputPanel(inputPanel);
		dataBase.setMatrixPanel(matrixPanel);
	}
		
	// GridBagLayout to Input Matrix
	public JPanel boxInputField(ArrayList<String> keys, ArrayList<String> locks) {

		Box inputBox = Box.createVerticalBox();
		
		Box line = Box.createHorizontalBox();
		JLabel inputLabel = new JLabel();

		// Add first row with key labels to inputPane
		for(int i = 0; i < keys.size(); i++) {
			inputLabel = new JLabel(keys.get(i) + "\n");
			line.add(inputLabel);
		}
		
		inputBox.add(line);
		
		// Add remaining rows with lock labels and check boxes for the Input Matrix
		JCheckBox newBox;
		checkBoxes = new ArrayList<JCheckBox>();
		
		for(int i = 0; i < locks.size(); i++) {
			inputLabel = new JLabel(locks.get(i));
			line = Box.createHorizontalBox();
			line.add(inputLabel);
			for(int j = 1; j < keys.size(); j++) {
				newBox = new JCheckBox();
				line.add(newBox);
				checkBoxes.add(newBox);
			}
			inputBox.add(line);
		}
		matrixPanel.add(inputBox);

		return matrixPanel;
	}
}
