package mathAlgorithm;

import graphicalUI.DataBase;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

public class LockSystem {
	
	// Database variables
	private DataBase dataBase;
	private ArrayList<String> inputMatrix;
	private ArrayList<String> partialOrderKeys_matrix;
	private int masterKey = 0;
	private ArrayList<String> partialOrderLocks_matrix;
	private ArrayList<Integer> comparableKeyValues;
	private ArrayList<Integer> comparableLockValues;
	private int generalLock = 0;
	private int keyAmount;
	private int lockAmount;
	private int positions;
	private ArrayList<Integer> atomBase;
	private ArrayList<Integer> atoms;
	private ArrayList<Integer> lockOrder;
	private ArrayList<Integer> uniqueLockOrder;
	private int chainAmount;
	private ArrayList<String> binaryKeys;
	private ArrayList<Integer> locksBaseTen;
	private ArrayList<Integer> sortedLocksBaseTen;
	private ArrayList<String> locksBaseTwo;
	private ArrayList<String> hexadecimalKeys;
	private ArrayList<String> hexadecimalLocks;
	Chain object = new Chain();
	
	// Getters and setters for variables in database
	public void setDataBase(DataBase guiInput) {
		dataBase = guiInput;
	}
	
	public void setInput(ArrayList<String> input) {
		inputMatrix = input;
	}
	
	public void setPartialOrderKeys_matrix(ArrayList<String> input) {
		partialOrderKeys_matrix = input;
	}
	
	public ArrayList<String> getPartialOrderKeys_matrix() {
		return partialOrderKeys_matrix;
	}
	
	public void setMasterKey(int n) {
		masterKey = n;
	}
	
	public int getMasterKey() {
		return masterKey;
	}

	public void setPartialOrderLocks_matrix(ArrayList<String> input) {
		partialOrderLocks_matrix = input;
	}
	
	public void setComparableKeyValues(ArrayList<Integer> input) {
		comparableKeyValues = input;
	}
	
	public ArrayList<Integer> getComparableKeyValues() {
		return comparableKeyValues;
	}

	public void setComparableLockValues(ArrayList<Integer> input) {
		comparableLockValues = input;
	}
	
	public ArrayList<Integer> getComparableLockValues() {
		return comparableLockValues;
	}
	
	public void setGeneralLock(int n) {
		generalLock = n;
	}
	
	public int getGeneralLock() {
		return generalLock;
	}
	
	public void setKeyAmount(int n) {
		keyAmount = n;
	}
	
	public void setLockAmount(int n) {
		lockAmount = n;
	}

	public void setPositions(int n) {
		positions = n;
	}
	
	public int getPositions() {
		return positions;
	}
	
	public void setAtomBase(ArrayList<Integer> input) {
		atomBase = input;
	}
	
	public ArrayList<Integer> getAtomBase() {
		return atomBase;
	}

	public void setAtoms(ArrayList<Integer> input) {
		atoms = input;
	}
	
	public ArrayList<Integer> getAtoms() {
		return atoms;
	}
	
	public ArrayList<Integer> getLockOrder() {
		return lockOrder;
	}
	
	public void setLockOrder(ArrayList<Integer> order) {
		lockOrder = order;
	}
	
	public ArrayList<Integer> getUniqueLockOrder() {
		return uniqueLockOrder;
	}
	
	public void setUniqueLockOrder(ArrayList<Integer> order) {
		uniqueLockOrder = order;
	}
	
	public void setChainAmount(int n) {
		chainAmount = n;
	}
	
	public int getChainAmount() {
		return chainAmount;
	}
	
	public ArrayList<Integer> getLocksBaseTen() {
		return locksBaseTen;
	}
	
	public void setLocksBaseTen(ArrayList<Integer> finalResult) {
		locksBaseTen = finalResult;
	}
	
	public void setSortedLocksBaseTen(ArrayList<Integer> finalResult) {
		sortedLocksBaseTen = finalResult;
	}
	
	public void setBinaryLocks(ArrayList<String> finalResult) {
		locksBaseTwo = finalResult;
	}
	
	public ArrayList<String> getBinaryLocks() {
		return locksBaseTwo;
	}
	
	public void setBinaryKeys(ArrayList<String> finalResult) {
		binaryKeys = finalResult;
	}
	
	public ArrayList<String> getBinaryKeys() {
		return binaryKeys;
	}
	
	public void passChains(Chain newObject) {
		object = newObject;
	}
	
	public void printFinalChains() {
		int length = object.getChainListSize();
		
		for(int i = 0; i < length; i ++) {
			if(i < 9) {
				System.out.println("Chain " + (i + 1) + ":-------------------------- " + object.getSortedChain(i));
			} else {
				System.out.println("Chain " + (i + 1) + ":------------------------- " + object.getSortedChain(i));
			}
			
		}
	}
	
	// PRINTER - printer for developer
	public void getInfoDeveloper() {
				
		// Define the keys from the lock solution
		defineKeys(partialOrderKeys_matrix, locksBaseTwo);
		keysToHexadecimal();
		locksToHexadecimal();
		
		System.out.println("--------------------------SYSTEM INFORMATION---------------------------");
		System.out.println("                              (DEVELOPER)");
		System.out.println();
		System.out.println("Input matrix:--------------------- " + inputMatrix);
		System.out.println("Partially ordered set of keys:---- " + partialOrderKeys_matrix);
		System.out.println("Partially ordered set of locks:--- " + partialOrderLocks_matrix);
		System.out.println("Master key:----------------------- " + masterKey + " (1 = yes, 0 = no)");
		System.out.println("General lock:--------------------- " + generalLock + " (1 = yes, 0 = no)");
		System.out.println("Comparable lock values:----------- " + comparableLockValues);
		printFinalChains();
		System.out.println("Lock index order:----------------- " + lockOrder);
		System.out.println("Atom base:------------------------ " + atomBase);
		System.out.println("Atoms:---------------------------- " + atoms);
		System.out.println("Result (base 10):----------------- " + locksBaseTen);
		System.out.println("Sorted result (base 10):---------- " + sortedLocksBaseTen);
		System.out.println("Positions:------------------------ " + positions);
		System.out.println("Amount of keys:------------------- " + keyAmount);
		System.out.println("Amount of locks:------------------ " + lockAmount);
		System.out.println("Locks:---------------------------- " + locksBaseTwo);
		if(positions > 6) {
			System.out.println("Locks (hexadecimal):--------------- " + hexadecimalLocks);
		}
		System.out.println("Keys (binary):-------------------- " + binaryKeys);
		if(positions > 6) {
			System.out.println("Keys (hexadecimal):--------------- " + hexadecimalKeys);
		}
		
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
	}
	
	// PRINTER - printer for user
	public void getInfoUser() {

		// Define the keys from the lock solution
		defineKeys(partialOrderKeys_matrix, locksBaseTwo);
				
		System.out.println("--------------------------SYSTEM INFORMATION---------------------------");
		System.out.println("                                (USER)");
		System.out.println();
		System.out.println("Input matrix:--------------------- " + inputMatrix);
		System.out.println("Positions:------------------------ " + positions);
		System.out.println("Amount of keys:------------------- " + keyAmount);
		System.out.println("Amount of locks:------------------ " + lockAmount);
		System.out.println("Locks:---------------------------- " + locksBaseTwo);
		System.out.println("Keys:----------------------------- " + binaryKeys);
		if(positions > 6) {
			System.out.println("Keys (hexadecimal):--------------- " + hexadecimalKeys);
		}
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
	}
	
	/* METHOD - createGUIPresentation*/
	// ALGORITHM Define the components needed to present the solution for users on Unlocker
	public void createGUIPresentation() {
		
		// Add label for input matrix + the input matrix to a box
		// Send box to dataBase and add to presentation frame
		Box labelBox = Box.createVerticalBox();
		JLabel dateLabel = new JLabel("       Date:-------------- ");
		JLabel nameLabel = new JLabel("       File:--------------- ");
		JLabel positionLabel = new JLabel("       Positions:---------- ");
		JLabel keyLabel = new JLabel("       Amount of keys:----- ");
		JLabel lockLabel = new JLabel("       Amount of locks:---- ");
		
		labelBox.add(dateLabel);
		labelBox.add(nameLabel);
		labelBox.add(positionLabel);
		labelBox.add(keyLabel);
		labelBox.add(lockLabel);
		
		Box matrixBox = Box.createVerticalBox();

		for(int i = 0; i < inputMatrix.size(); i++) {
			matrixBox.add(new JLabel(inputMatrix.get(i)));
		}
		
		// Store components to dataBase
		dataBase.setLabelBox(labelBox);
		dataBase.setPositions(positions);
		dataBase.setMatrixBox(matrixBox);	
	}
		
	/* METHOD - defineKeys*/
	// ALGORITHM 3.10 Define the keys
	public void defineKeys(ArrayList<String> keyMapList, ArrayList<String> binaryLocks) {
		
		ArrayList<Integer> pickLock = new ArrayList<Integer>();
		ArrayList<Integer> indexWithOne = new ArrayList<Integer>();
		ArrayList<String> keyList = new ArrayList<String>();
		
		int tempPositions = 0;
		
		// Part I. Defines which locks a key should access
		for(int i = 0; i < keyMapList.size(); i++) {
			String keyMap = keyMapList.get(i);
			for(int j = 0; j < keyMap.length(); j++) {
				if(keyMap.charAt(j) == '1') {
					pickLock.add(j);
				}
			}

			ArrayList<Integer> key = new ArrayList<Integer>();

			if(positions > 6) {
				tempPositions = 16;
			} else {
				tempPositions = positions;
			}
			
			for(int m = 0; m < tempPositions; m++) {
				key.add(0);
			}
			
			// Part II. Picks out locks defined in part I.
			for(int j = 0; j < pickLock.size(); j++) {
				String lock = binaryLocks.get(pickLock.get(j));

				for(int k = 0; k < lock.length(); k++) {
					if(lock.charAt(k) == '1') {
						indexWithOne.add(k);
					}
				}
			}
			
			Collections.sort(indexWithOne);
			
			// Part III. Defines which indexes in the locks containing a one
			// Each one in the lock is reflected to the key.
			for(int l = 0; l < indexWithOne.size(); l++) {
				key.set(indexWithOne.get(l),1);
			}
			
			String binaryKey = "";
			for(int n = 0; n < key.size(); n++) {
				if(key.get(n) == 1) {
					binaryKey += 1;
				} else {
					binaryKey += 0;
				}
			}
			
			keyList.add(binaryKey);
			indexWithOne.clear();
			key.clear();
			pickLock.clear();
			
		}

		// IF system has a master key --> add the master key at index 0 in the resulting array
		int master = 1;
		
		if(masterKey == 1) {
			for(int t = 0; t < tempPositions; t++) {
				master *= 2;
			}
			master -= 1;
			String binaryMaster = Integer.toBinaryString(master);
			keyList.add(0, binaryMaster);
		}
		keyList.remove(0);
		binaryKeys = keyList;
		dataBase.setKeySolution(binaryKeys);
		dataBase.setLockSolution(binaryLocks);
		
		// Create component for the GUI presentation
		createGUIPresentation();
		if(positions > 6) {
			keysToHexadecimal();
		}
	}
	
	/* METHOD - keysToHexadecimal*/
	// ALGORITHM 3.13a Convert keys from base 2 to hexadecimal
	public void keysToHexadecimal() {
		ArrayList<String> hexKeys = new ArrayList<String>();
		if(positions > 6) {
			for(int i = 0; i < binaryKeys.size(); i++) {
				String key = binaryKeys.get(i);
				int decimal = Integer.parseInt(key, 2);
				String hexStr = Integer.toString(decimal, 16);
				hexStr = hexStr.toUpperCase();
				hexKeys.add(hexStr);
			}	
		}
		hexadecimalKeys = hexKeys;
		dataBase.setKeySolution(hexadecimalKeys);
	}
	
	/* METHOD - locksToHexadecimal*/
	// ALGORITHM 3.13a Convert locks from base 2 to hexadecimal
	public void locksToHexadecimal() {
		ArrayList<String> hexLocks = new ArrayList<String>();
		if(positions > 6) {
			for(int i = 0; i < locksBaseTwo.size(); i++) {
				String key = locksBaseTwo.get(i);
				int decimal = Integer.parseInt(key, 2);
				String hexStr = Integer.toString(decimal, 16);
				hexStr = hexStr.toUpperCase();
				hexLocks.add(hexStr);
			}	
		}
		hexadecimalLocks = hexLocks;
	}
}

