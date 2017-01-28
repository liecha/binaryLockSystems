package graphicalUI;

import javax.swing.*;

import mathAlgorithm.LockSystem;
import mathAlgorithm.InputMatrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UnLockerMain {
	
	JTextArea output;
	JScrollPane scrollPane;
	
	public JMenuBar createMenuBar(JFrame frame, DataBase dataBase) {
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		JMenuItem menuItemNew;

		// Create the menu bar
		menuBar = new JMenuBar();
		
		// Build the first menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);
        
        // Create a group of JMenuItems
        // String[] fileMenu = {"New", "Open file...", "Save", "Save As...", "Close"};
        menuItemNew = new JMenuItem("New", KeyEvent.VK_T);
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItemNew.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItemNew);

        menuItemNew.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String tabName = JOptionPane.showInputDialog("Enter a file name: ");
        		dataBase.getTabbedPane().setTitleAt(0, tabName);
        		
        		// Store file name and latest update time/date to dataBase
        		dataBase.setFileName(tabName);
        		
        		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        		Date date = new Date();
        		dataBase.setLatestUpdate(dateFormat.format(date));
        		
        		// Add inputPanel and button1 to pane presenting the input GUI
        		JPanel inpuPanel = dataBase.getInputPanel();
        		inpuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        		JButton button1 = dataBase.getButton1();
        		button1.setAlignmentX(Component.CENTER_ALIGNMENT);

        		dataBase.getStartPanel().add(inpuPanel);
        		dataBase.getStartPanel().add(button1);
			}
		});
        
        // Build second menu in the menu bar
        menu = new JMenu("Edit");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription("This menu does nothing");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Clear", KeyEvent.VK_T);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menu.add(menuItem);

        return menuBar;
	}
		
    public Container createContentPane() {
        
    	// Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        // Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        // Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }
    
    public void createTab(JFrame frame, DataBase dataBase) {
    	
    	JTabbedPane tabbedPane = new JTabbedPane();
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		tabbedPane.add("Tab1", startPanel);
		frame.getContentPane().add(tabbedPane);
		
		// Store to dataBase
		dataBase.setTabbedPane(tabbedPane);
		dataBase.setStartPanel(startPanel);

    }
    
    public void createActions(DataBase dataBase) {
    	
    	// Create and set up the window (viewer)
        JFrame matrixFrame = new JFrame("Input Field");
        matrixFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up content pane
        Container pane = new Container();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        matrixFrame.setContentPane(pane);

    	JPanel matrixPanel = dataBase.getMatrixPanel();
    	matrixPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    	JButton button1 = dataBase.getButton1();

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Parse the text to an integer
				int keys = Integer.parseInt(dataBase.getKeyField().getText());
				int locks = Integer.parseInt(dataBase.getLockField().getText());
			      
				// Store to dataBase
				dataBase.setKeyAmount(keys);
				dataBase.setLockAmount(locks);
			       
				ArrayList<String> lockText = new ArrayList<String>();
				lockText.add("      ");
			        
				// Define labels for the locks
	      		for(int i = 0; i < locks; i++) {
	      			String lock = "";
      				if(i < 9) {
      					lock = "L" + "0" + String.valueOf(i + 1);
      				} else {
      					lock = "L" + String.valueOf(i + 1);
      				}
      				lockText.add(lock);
	      		}
    
	      		ArrayList<String> keyText = new ArrayList<String>();
	      		String key = "";
			        
	      		// Define labels for the keys
	      		for(int i = 0; i < keys; i++) { 
	      			if(i < 9) {
	      				key = "K" + "0" + String.valueOf(i + 1);
	      			} else {
	      				key = "K" + String.valueOf(i + 1);
	      			}
	      			keyText.add(key);
	      		}

	      		Box inputBox = Box.createVerticalBox();
					
	      		Box line = Box.createHorizontalBox();
	      		JLabel inputLabel = new JLabel();
	
	      		// Add first row with lock labels to inputPane
	      		for(int i = 0; i < lockText.size(); i++) {
	      			inputLabel = new JLabel(lockText.get(i) + "\n");
	      			line.add(inputLabel);
	      		}
					
	      		inputBox.add(line);
					
	      		// Add remaining rows with keys labels and check boxes for the Input Matrix
	      		JCheckBox newBox;
	      		ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();
					
	      		for(int i = 0; i < keyText.size(); i++) {
	      			inputLabel = new JLabel(keyText.get(i));
	      			line = Box.createHorizontalBox();
	      			line.add(inputLabel);
	      			for(int j = 1; j < lockText.size(); j++) {
	      				newBox = new JCheckBox();
	      				line.add(newBox);
	      				checkBoxes.add(newBox);
	      			}
	      			inputBox.add(line);
	      		}
	      		matrixPanel.add(inputBox);
	      		dataBase.setCheckBoxes(checkBoxes);
			      
	      		JButton button2 = dataBase.getButton2();
	      		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
			      
	      		pane.add(matrixPanel);
	      		matrixPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	      		pane.add(button2);
	      		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
			      
	      		// Display the window.
	      		matrixFrame.setSize(locks * 40, keys * 45);
	      		matrixFrame.setVisible(true);
			      
	      		// Store to dataBase
	      		dataBase.setMatrixPanel(matrixPanel);
	      		dataBase.setLockText(lockText);
	      		dataBase.setKeyText(keyText);
			}
		});
		
	
		
		JButton button2 = dataBase.getButton2();
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> inputMatrix = new ArrayList<String>();
				ArrayList<JCheckBox> checkBoxes = dataBase.getCheckBoxes();

		        // Parse the text to an integer
		        String inputLine = "";

				for (JCheckBox checkBox : checkBoxes) {
					
					if (checkBox.isSelected()) {
						inputLine += "1";
			        } else {
			        	inputLine += "0";
			        }
					
					if(inputLine.length() == dataBase.getLockAmount()) {
						inputMatrix.add(inputLine);
						inputLine = "";
					}
				}

				LockSystem lockSystem = new LockSystem();
		        InputMatrix input = new InputMatrix();
		        
		        // SAVING POINT - store inputMatrix in database - lockSystem
		        lockSystem.setInput(inputMatrix);
		        lockSystem.setDataBase(dataBase);
		        
		        input.defineLockSystem(inputMatrix, lockSystem);
		        
		        //lockSystem.getInfoDeveloper();
		      	lockSystem.getInfoUser();
		      	
		      	// Print result
		      	dataBase.printResult();
				}
		});
    }
        
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
    	
    	// Create a database for this solution
    	DataBase dataBase = new DataBase();
    	
    	// Create all components needed for this GUI
    	new CreateComponents(dataBase);
        
    	// Create and set up the window (viewer)
        JFrame frame = new JFrame("Lock System Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the menuBar and content pane
        UnLockerMain newSolution = new UnLockerMain();
        Container pane = newSolution.createContentPane();
        JMenuBar menuBar = newSolution.createMenuBar(frame, dataBase);

        frame.setJMenuBar(menuBar);        
        frame.setContentPane(pane);
        
        newSolution.createTab(frame, dataBase);
        
        newSolution.createActions(dataBase);

        // Display the window.
        frame.setSize(273, 200);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        
    	// Schedule a job for the event-dispatching thread:
        // Creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
