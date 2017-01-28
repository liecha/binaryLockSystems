package mathAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class InputMatrix {
	
	/* METHOD - reader */
	// ALGORITHM 3.1
	// Method read the file and store the wanted data - storeInput - in a ArrayList
	public ArrayList<String> reader(LockSystem myLockSystem) {
		
		ArrayList<String> storeInput = new ArrayList<String>();
	
		// Create an object representing a file
		// input1 - house four apartment, two utility rooms NO one general lock
		// input2 - example from thesis
		try {
			File file = new File("/Users/Liecha/Documents/programming/MAM290/binaryLockSystem/src/input1.rtf");
			
			// Create a scanner that read from the file
			Scanner fileScan = new Scanner(file);
			int wordCount = 0;
			
			// Read from the file - one word at the time
			while(fileScan.hasNext()) {
				String binaryString = fileScan.next();
				if(wordCount > 7) {
					
					// Cleaning the string from unwanted characters \ and }
					binaryString = binaryString.replace("\\", "");
					binaryString = binaryString.replace("}", "");
					storeInput.add(binaryString);
				}
				wordCount++;
			}
			fileScan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// SAVING POINT - store inputMatrix in database - myLockSystem
		myLockSystem.setInput(storeInput);

		return storeInput;
	}
	
	/* METHOD - defineLockSystem */
	// ALGORITHM 3.2 Defines all required parameters to define the lock system. 
	// The final result is a set of locks which fulfill requirements in the inputMatrix.
	// This result is used to define the keys (Algorithm 2.12 defineKeys)
	public void defineLockSystem(ArrayList<String> inputMatrix, LockSystem lockSystem) {
		
		// unit - 0 = input matrix, 1 = keys, 2 = locks
		int unit = 0;
		
		// Each row (key) is stored as a binary string in partialOrderKeys_matrix
		// The matrix express the partial ordered set of keys
		unit = 1;
		ArrayList<String> partialOrderKeys_matrix = new ArrayList<String>();
		partialOrderKeys_matrix = sort(sort(inputMatrix, unit, lockSystem), unit, lockSystem);
		
		// Each row is stored in reversed base ten - the comparable values
		ArrayList<Integer> comparableKeyValues = new ArrayList<Integer>();
		comparableKeyValues = comparableElements(partialOrderKeys_matrix, unit, lockSystem);
		
		// If the system contain a master key the masterKey = 1
		// else masterKey = 0
		masterKey(partialOrderKeys_matrix.get(0).length(), comparableKeyValues, lockSystem);

		// Each column (lock) is stored as a binary string in partialOrderLocks_matrix
		// The matrix express the partial ordered set of locks
		unit = 2;
		ArrayList<String> partialOrderLocks_matrix = new ArrayList<String>();
		partialOrderLocks_matrix = sort(partialOrderKeys_matrix, unit, lockSystem);


		// Each column is stored in reversed base ten - the comparable values
		ArrayList<Integer> comparableLockValues = new ArrayList<Integer>();
		comparableLockValues = comparableElements(partialOrderLocks_matrix, unit, lockSystem);
		
		// If the system contain a general lock the generalLock = 1
		// else generalLock = 0
		generalLock(comparableLockValues, lockSystem);

		// Defines lock chains with the comparable values
        		Chain chains = new Chain();
		chains.detector(comparableLockValues); // (STORE POINT in algorithm detector)
		
		// The original order among the locks - given by the input matrix - is defined
		findLockOrder(lockSystem.getComparableLockValues(), chains, lockSystem);
		
		// The chain priority is defined
		chains.chainPriority(chains, lockSystem);

		// The atom base is defined
		atomBase(lockSystem);
		
		// The lock solution is defined
		chains.defineLocks(chains, lockSystem);
		
		// The required amount of positions is defined
		definePositions(lockSystem);
		
		// If the result has more than five positions the resulting key are presented in hexadecimals
		int positions = lockSystem.getPositions();		
		if(positions < 6) {
			cleanLocks(lockSystem);
		}
	}
	
	/* METHOD - sort */
	// ALGORITHM 3.3 Transform input data, calculate the row value and sort the matrix in descending order
	public ArrayList<String> sort(ArrayList<String> input, int unit, LockSystem lockSystem) {
		
		int size = input.get(0).length();
		
		// Transforming each column in the input matrix - each column is express in rows
		ArrayList<String> transMatrix = new ArrayList<String>(Collections.nCopies(size, ""));
		
		for(int i = 0; i < input.size(); i++) {
			String selectedString = input.get(i);
			for(int j = 0; j < size; j++) {
				String selected = selectedString.substring(j,j+1);
				transMatrix.set(j, transMatrix.get(j) + selected);
			}
		}
		
		// Calculate the value of each row
		List<Integer> binaryToInt = new ArrayList<Integer>(Collections.nCopies(size, 0));
		binaryToInt = binaryToInt(transMatrix, input.size());
		
		// Copy binaryToInt to an array that could be sorted
		// These two arrays could be compared to make sure that the locks
		// get the right position after they are converted back to binary strings
		List<Integer> sortedBinaryToInt = new ArrayList<Integer>(binaryToInt);
		Collections.copy(sortedBinaryToInt, binaryToInt);
		Collections.sort(sortedBinaryToInt, Collections.reverseOrder());
		
		// Find the order - sort input matrix
		ArrayList<String> finalResult = new ArrayList<String>();
		for(int i = 0; i < transMatrix.size(); i++) {
			int findPosition = sortedBinaryToInt.get(i);
			
			if(i < (transMatrix.size() - 1) && findPosition == sortedBinaryToInt.get(i + 1)) {
				sortedBinaryToInt.remove(i);
			}
			
			// Compare transSummary and sortedSummary to find the column order 
			for(int j = 0; j < transMatrix.size(); j++) {
				if(findPosition == binaryToInt.get(j)) {
					finalResult.add(transMatrix.get(j));
				}
			}
			if(finalResult.size() == size) {
				break;
			}
		}

		// If unit == 1 --> the result is for the key solution
		if(unit == 1) {
			// SAVING POINT - store key parameters in database - myLockSystem
			lockSystem.setPartialOrderKeys_matrix(finalResult);
			lockSystem.setKeyAmount(finalResult.size());
		}
		
		// If unit == 2 --> the result is for the lock solution
		if(unit == 2) {
			// SAVING POINT - store lock parameters in database - myLockSystem
			lockSystem.setPartialOrderLocks_matrix(finalResult);
			lockSystem.setLockAmount(finalResult.size());
		}
		return finalResult;
	}
	
	/* METHOD - comparableElements */
	// ALGORITHM 3.4 Calculate the comparable row value for each binary string in input array
	public ArrayList<Integer> comparableElements(ArrayList<String> inputMatrix, int unit, LockSystem lockSystem) {
		
		ArrayList<Integer> lockValues = new ArrayList<Integer>();
		
		// Each row in the input is given an reversed base ten value
		// Each one represent 1, 2, 4, 8, 16, ... in reversed order compared to the original expression
		// where each on represent ..., 16, 8, 4, 2, 1
		for(int i = 0; i < inputMatrix.size(); i++) {
			int lockValue = 0;
			int adder = 1;
			String row = inputMatrix.get(i);
			for(int j = 0; j < row.length(); j++) {
				if(row.charAt(j) == '1') {
					lockValue += adder;
				}
				adder *= 2;
			}
			lockValues.add(lockValue);
		}
		
		// If unit == 1 --> the result is for the key solution
		if(unit == 1) {
			lockSystem.setComparableKeyValues(lockValues);
		}
		
		// If unit == 2 --> the result is for the lock solution
		if(unit == 2) {
			ArrayList<Integer> comparableLockValues_originalOrder = new ArrayList<Integer>(lockValues);
			lockSystem.setComparableLockValues(comparableLockValues_originalOrder);
		}
		
		return lockValues;
	}
	
	/* METHOD - masterKey */
	// ALGORITHM 3.5 Define if a system has a master key
	public void masterKey(int length, ArrayList<Integer> comparableValues, LockSystem lockSystem) {
		int master = 1;
		
		for(int i = 0; i < length; i++) {
			master *= 2;
		}
		
		master -= 1;

		if(comparableValues.get(0) == master) {
			lockSystem.setMasterKey(1);
			comparableValues.remove(0);
		}
	}
	
	/* METHOD - generalLock */
	// ALGORITHM 3.6 Define if a system has a general lock
	public void generalLock(ArrayList<Integer> comparableValues, LockSystem lockSystem) {
		int master = 0;

		if(comparableValues.get(comparableValues.size() - 1) == master) {
			lockSystem.setGeneralLock(1);
			comparableValues.remove(comparableValues.get(comparableValues.size() - 1));
		} else {
			master = 1;
		}
		
		if(master == 0) {
			ArrayList<String> keys = new ArrayList<String>();
			keys = lockSystem.getPartialOrderKeys_matrix();
			
			for(int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				key = removeCharAt(key, (key.length() - 1));
				keys.set(i, key);
			}
			lockSystem.setPartialOrderKeys_matrix(keys);
			masterKey(keys.get(0).length(), lockSystem.getComparableKeyValues(), lockSystem);
		}
	}
	
	public static String removeCharAt(String s, int pos) {
	      return s.substring(0, pos) + s.substring(pos + 1);
	   }
	
	/* METHOD - findLockOrder */
	// ALGORITHM 3.7 Defines the original order among the locks
	public void findLockOrder(ArrayList<Integer> original, Chain chains, LockSystem lockSystem) {
		ArrayList<Integer> lockOrder = new ArrayList<Integer>();
		ArrayList<Integer> locksUnOrderedList = new ArrayList<Integer>();
		locksUnOrderedList = chains.getUnOrderedChains();
		
		// Sorted lock list is compared to original lock list to recreate the lock
		// order given in the input matrix
		for(int m = 0; m < locksUnOrderedList.size(); m++) {
			int testDigit = locksUnOrderedList.get(m);
			for(int l = 0; l < original.size(); l++) {
				if(testDigit == original.get(l)) {
					lockOrder.add(l);
				}
			}
		}
		lockSystem.setLockOrder(lockOrder);
	}
	
	/* METHOD - atomBase*/
	// ALGORITHM 3.8 Build the ground elements - atoms - to express the locks
	public void atomBase(LockSystem lockSystem) {
		
		int positions = 16;
		
		ArrayList<Integer> atomBase = new ArrayList<Integer>();
		
		// Define n (= positions) atoms - locks containing one 1
		for(int i = positions - 1; i >= 0; i--) {
			int atom = (int) Math.pow(2, i);
			atomBase.add(atom);
		}
		
		lockSystem.setAtomBase(atomBase);
	}
	
	/* METHOD - definePositions*/
	// ALGORITHM 3.9 Define required amount of positions in locks and keys
	public void definePositions(LockSystem lockSystem) {
		
		int maxPosition = 0;
		
		ArrayList<String> lockList = new ArrayList<String>();
		lockList = lockSystem.getBinaryLocks();
		
		for(int i = 0; i < lockList.size(); i++) {
			String lock = lockList.get(i);
			for(int j = 0; j < lock.length(); j++) {
				if(lock.charAt(j) == '1') {
					if(j + 1 > maxPosition) {
						maxPosition = j + 1;
					}
				}
			}
		}
		// STORE POINT - store positions do database - lockSystem
		lockSystem.setPositions(maxPosition);
	}
	
	/* METHOD - cleanLocks*/
	// ALGORITHM 3.9a Clean the locks from unwanted zeroes
	public void cleanLocks(LockSystem lockSystem) {
		
		int positions = lockSystem.getPositions();
		
		ArrayList<String> lockList = new ArrayList<String>();
		lockList = lockSystem.getBinaryLocks();
		
		ArrayList<String> cleanLockList = new ArrayList<String>();
		
		for(int i = 0; i < lockList.size(); i++) {
			String lock = lockList.get(i);
			String cleanLock = "";
			for(int j = 0; j < lock.length(); j++) {
				cleanLock = lock.substring(0,positions);
			}
			cleanLockList.add(cleanLock);
		}
		lockSystem.setBinaryLocks(cleanLockList);
	}
	
	/* METHOD - intToBinary*/
	// ALGORITHM 3.12a Convert elements from integer to binary string
	public ArrayList<String> intToBinary(ArrayList<Integer> locksBaseTen) {
		
		ArrayList<String> locksBaseTwo = new ArrayList<String>();
		
		// Converting each integer to binary string
		for(int i = 0; i < locksBaseTen.size(); i++) {	
			String binaryString = Integer.toBinaryString(locksBaseTen.get(i));
			locksBaseTwo.add(binaryString);		
		}
		
		int maxLength = locksBaseTwo.get(0).length();
			
		for(int i = 1; i < locksBaseTwo.size(); i++) {
			if(locksBaseTwo.get(i).length() > maxLength) {
				maxLength = locksBaseTwo.get(i).length();
			}
		}
		
		for(int i = 0; i < locksBaseTwo.size(); i++) {
			String binaryString = locksBaseTwo.get(i);
			int currentLength = locksBaseTwo.get(i).length();
			if(currentLength < maxLength) {
				int difference = maxLength - currentLength;
				String adder = "";
				for(int j = 0; j < difference; j++) {
					adder += '0';
				}
				binaryString = adder + binaryString;
			}
			locksBaseTwo.set(i, binaryString);
		}
		return locksBaseTwo;
	}
	
	/* METHOD - binaryToInt*/
	// ALGORITHM 3.12b Convert elements from binary string to integer
	public ArrayList<Integer> binaryToInt(ArrayList<String> input, int length) {
		
		ArrayList<Integer> binaryToInt = new ArrayList<Integer>(Collections.nCopies(input.size(), 0));
		
		// Converting each binary string to integers
		for(int i = 0; i < input.size(); i++) {
			int colValue = 0;
			String currentRow = input.get(i);
			int binaryValue = (int) Math.pow(2, length);
			
			for(int j = 0; j < currentRow.length(); j++) {
				
				// Select a column - step 1, select a row - step 2
				char binaryPosition = currentRow.charAt(j);
			
				// Divide binaryValue by two (16,8,4,2,1)
				binaryValue /= 2;
				
				if(binaryPosition == '1' || binaryPosition == '0') {
					int binaryInt = Character.getNumericValue(binaryPosition) * binaryValue;
					
					// Adding each binary value to get the column sum
					colValue += binaryInt;
				}
			}
			binaryToInt.set(i, colValue);
		}
		return binaryToInt;
	}

}
