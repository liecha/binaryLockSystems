package mathAlgorithm;

import java.util.ArrayList;
import java.util.Collections;

public class Chain extends InputMatrix {
	
	private ArrayList<Integer> lockChain;
	private ArrayList<Integer> sortedChains;
	private ArrayList<Integer> locks;
	private ArrayList<Chain> chainList = new ArrayList<Chain>();
	private ArrayList<Integer> uniqueLocks = new ArrayList<Integer>();
	private ArrayList<Chain> sortedChainList = new ArrayList<Chain>();
	private ArrayList<Chain> lockList = new ArrayList<Chain>();
	private ArrayList<Integer> unOrderedChains = new ArrayList<Integer>();
	
	
	public void setUniqueLocks(ArrayList<Integer> uniqueLockList) {
		 uniqueLocks = uniqueLockList;
	}
	
	public ArrayList<Integer> getUniqueLocks() {
		return uniqueLocks;
	}
	
	public ArrayList<Integer> getChain(int position) {
		ArrayList<Integer> chain = new ArrayList<Integer>();
		chain = chainList.get(position).lockChain;
		return chain;
	}
	
	public ArrayList<Integer> getSortedChain(int position) {
		ArrayList<Integer> chain = new ArrayList<Integer>();
		chain = sortedChainList.get(position).sortedChains;
		return chain;
	}
	
	public void clearChain() {
		lockChain.clear();
	}
	
	public void printChain() {
		System.out.println(lockChain);
	}
	
	public int getChainListSize() {
		return chainList.size();
	}
	
	public int getLockListSize() {
		return lockList.size();
	}
	
	public void addChain(ArrayList<Integer> newChain) {
		Chain newObject = new Chain();
		newObject.lockChain = newChain;
		chainList.add(newObject);
	}
	
	public void addSortedChain(ArrayList<Integer> newChain) {
		Chain newObject = new Chain();
		newObject.sortedChains = newChain;
		sortedChainList.add(newObject);
	}
	
	public ArrayList<Chain> getSortedChainList() {
		return sortedChainList;
	}
	
	public void addLock(ArrayList<Integer> newLocks) {
		Chain newLockChain = new Chain();
		newLockChain.locks = newLocks;
		lockList.add(newLockChain);
	}
	
	public void chainAmount() {
		System.out.println("The amount od chains are: " + chainList.size());
	}
	
	public ArrayList<Integer> getUnOrderedChains() {
		return unOrderedChains;
	}
	
	public void printLocks() {
		int length = lockList.size();
		
		for(int i = 0; i < length; i ++) {
			System.out.println("LOCK " + (i + 1) + " : " + lockList.get(i).locks);
		}
	}
	
	public void printChains() {
		int length = chainList.size();
		
		for(int i = 0; i < length; i ++) {
			System.out.println("CHAIN " + (i + 1) + " : " + chainList.get(i).lockChain);
		}
	}
	
	public void printSortedChains() {
		int length = sortedChainList.size();
		
		for(int i = 0; i < length; i ++) {
			System.out.println("SORTED CHAIN " + (i + 1) + " : " + sortedChainList.get(i).sortedChains);
		}
	}
	
	/* METHOD - detector*/
	// ALGORITHM 3.4a Defines chains with comparable locks and anti-chains without comparable locks
	public void detector(ArrayList<Integer> lockValues) {
		ArrayList<Integer> chain = new ArrayList<Integer>();
		int min = 0;
		int counter = 0;
		int lockAmount = lockValues.size();
		
		// All comparable locks are detected and stored in a chain
		// All anti-chains in the total set of locks are defined
		for(int i = 0; i < lockValues.size(); i++) {
			int max = lockValues.get(0);
			
			// Define all comparable lock in the range max to min
			chain = defineChain(max, min, lockValues);
			
			for(int t = 0; t < chain.size(); t++) {
				int partOfChain = chain.get(t);
				for(int q = 0; q < lockValues.size(); q++) {
					if(partOfChain == lockValues.get(q)) {
						lockValues.remove(q);
					}
				}
				
			}
			
			for(int k = 0; k < chain.size(); k++) {
				unOrderedChains.add(chain.get(k));
			}
			
			addChain(chain);
			
			for(int j = 0; j < chain.size(); j++) {
				counter++;
			}
			
			// Delete all locks which is part of a chain.
			// Remaining locks will be anti-chains to previous defined locks.
			lockValues = cleanArray(lockValues, chain);
			
			if(counter == lockAmount) {
				break;
			}
			min = max;	
		}
		
		// Unique locks - locks only opened by one key - is not part of a chain
		// These locks are added to the final result
		if(lockValues.size() > 0) {
			setUniqueLocks(lockValues);
			for(int i = 0; i < lockValues.size(); i++) {
				ArrayList<Integer> uniqueLockArray = new ArrayList<Integer>();
				int uniqueLock = lockValues.get(i);
				uniqueLockArray.add(uniqueLock);
				addChain(uniqueLockArray);
				unOrderedChains.add(uniqueLock);
			}
		}
	}
	
	/* METHOD - defineChain*/
	// ALGORITHM 3.4b - Define all comparable lock in the range max to min
	public ArrayList<Integer> defineChain(int max, int min, ArrayList<Integer> lockValues) {
		
		ArrayList<Integer> newChain = new ArrayList<Integer>();
		int lockAmount = lockValues.size();
		
		for(int j = 0; j < lockAmount; j++) {
			int test = lockValues.get(j);
			if(max >= test && test > min) {	
				newChain.add(test);
			}
		}
		
		return newChain;
	}
	
	/* METHOD - cleanArray*/
	// ALGORITHM 3.4c - Delete all locks which is part of a chain. 
	// Remaining locks will be anti-chains to previous defined locks.
	public ArrayList<Integer> cleanArray(ArrayList<Integer> lockValues, ArrayList<Integer> chain) {
		for(int i = 0; i < chain.size(); i++) {
			int testValue = chain.get(i);
			for(int j = 0; j < lockValues.size(); j++) {
				if(testValue == lockValues.get(j)) {
					lockValues.remove(j);
				}
			}
		}
		return lockValues;
	}
	
	/* METHOD - chainPriority*/
	// ALGORITHM 3.7a Sorting the chains in descending order by length
	public void chainPriority(Chain chains, LockSystem lockSystem) {
		ArrayList<Integer> chainSizes = new ArrayList<Integer>();
		
		for(int i = 0; i < chains.getChainListSize(); i++) {
			int chainLength = chains.getChain(i).size();
			chainSizes.add(chainLength);
		}
		
		ArrayList<Integer> sortedChainSizes = new ArrayList<Integer>(chainSizes);
		Collections.copy(sortedChainSizes, chainSizes);
		Collections.sort(sortedChainSizes, Collections.reverseOrder());
		ArrayList<Integer> cleanChainSizes = new ArrayList<Integer>();
		cleanChainSizes.add(sortedChainSizes.get(0));
		
		int counter = 0;
		
		for(int i = 0; i < sortedChainSizes.size(); i++) {
			int test = sortedChainSizes.get(i);
			for(int j = 0; j < cleanChainSizes.size(); j++) {
				if(test == cleanChainSizes.get(j)) {
					counter++;	
				}
			}
			if(counter == 0) {
				cleanChainSizes.add(test);
			}
			if(counter > 0) {
				counter = 0;
			}
		}
		
		sortedChainSizes = cleanChainSizes;
		
		// Find the order - sort input matrix
		ArrayList<Integer> finalResult = new ArrayList<Integer>();
		for(int i = 0; i < sortedChainSizes.size(); i++) {
			int findPosition = sortedChainSizes.get(i);
			
			// Compare transSummary and sortedSummary to find the column order 
			for(int j = 0; j < chains.getChainListSize(); j++) {
				if(findPosition == chainSizes.get(j)) {
					finalResult = chains.getChain(j);
					addSortedChain(finalResult);
				}
			}
		}
		
		lockSystem.passChains(chains);
	
		ArrayList<Integer> chain = new ArrayList<Integer>();
		ArrayList<Integer> originalMap = new ArrayList<Integer>();
		ArrayList<Integer> sortedMap = new ArrayList<Integer>();
		ArrayList<Integer> lockOrder = new ArrayList<Integer>();
		ArrayList<Integer> sortedLockOrder = new ArrayList<Integer>();
		
		// Create a map to the original order of the locks
		for(int i = 0; i < chains.getChainListSize(); i++) {
			chain = chains.getChain(i);
			for(int j = 0; j < chain.size(); j++) {
				originalMap.add(chain.get(j));
			}
		}
	
		for(int i = 0; i < chains.getChainListSize(); i++) {
			chain = chains.getSortedChain(i);
			for(int j = 0; j < chain.size(); j++) {
				sortedMap.add(chain.get(j));
			}
		}
		
		lockOrder = lockSystem.getLockOrder();
		
		for(int i = 0; i < lockOrder.size(); i++) {
			sortedLockOrder.add(0);
		}
		 
		for(int i = 0; i < originalMap.size(); i++) {
			 int find = originalMap.get(i);
			 for(int j = 0; j < originalMap.size(); j++) {
				 if(find == sortedMap.get(j)) {
					 sortedLockOrder.set(j, lockOrder.get(i));
				 }
			 }
		 }
		 lockSystem.setLockOrder(sortedLockOrder); 
		 lockSystem.setChainAmount(chains.getChainListSize());
	}
	
	/* METHOD - defineLocks*/
	// ALGORITHM 3.8a Expressing the lock solutions - a set of locks fulfilling requirements in the given input matrix
	public void defineLocks(Chain object, LockSystem lockSystem) {
		ArrayList<Integer> atomBase = new ArrayList<Integer>();
		atomBase = lockSystem.getAtomBase();
		
		ArrayList<Integer> locks = new ArrayList<Integer>();
		ArrayList<Integer> finalResult = new ArrayList<Integer>();
		ArrayList<Integer> atoms = new ArrayList<Integer>();
		ArrayList<Integer> chain = new ArrayList<Integer>();
		
		int lastPositionedChain = 0;
		
		if(object.getUniqueLocks().size() != 0) {
			lastPositionedChain = object.getChainListSize() - object.getUniqueLocks().size();
		} else {
			lastPositionedChain = 2;
		}
		
		for(int j = 0; j < lastPositionedChain; j++) {
			chain = object.getSortedChain(j); // [3,1,2], [12,4,8]
		
			locks.add(atomBase.get(j));
			atoms.add(atomBase.get(j));
			finalResult.add(atomBase.get(j));
			
			int previousElement = chain.get(0);

			// Each lock gets a value depending on which chain it belongs to
			// IF chain(i) > chain(i + 1) --> add all previous defined lock values
			// IF chain(i) < chain(i + 1) --> add lock atom with path(i)
			for(int i = 0; i < chain.size() - 1; i++) {
				int lock = 0;
				int thisElement = chain.get(i + 1);
			
				if(thisElement < previousElement) {
					lock = locks.get(i) + atomBase.get(i + j + 2);
					locks.add(lock);
					finalResult.add(lock);
					previousElement = thisElement;
				}
				
				if(thisElement > previousElement) {
					int position = (j + i);
					while(thisElement > previousElement) {
						position--;
						previousElement = chain	.get(position);
					}
					lock = locks.get(position) + atomBase.get(i + j + 2);
					locks.add(lock);
					finalResult.add(lock);
					previousElement = thisElement;
				}
			}
			
			intToBinary(locks);
			
			ArrayList<Integer> store = new ArrayList<Integer>(locks);
			addLock(store);
			locks.clear();
		}
		
		// Unique locks
		if(lastPositionedChain < lockSystem.getChainAmount()) {
			int uniqueAtom = atoms.get(atoms.size() - 1) / 2;
			atoms.add(uniqueAtom);
			int position = 0;
			for(int i = lastPositionedChain; i < lockSystem.getChainAmount(); i++) {
				int uniqueLock = atomBase.get(position) + uniqueAtom;
				position++;
				finalResult.add(uniqueLock);
			}
		}
		
		lockSystem.setAtoms(atoms);
		lockSystem.setLocksBaseTen(finalResult);
		
		recreateOriginalLockOrder(lockSystem);
	}
	
	/* METHOD - originalLockOrder*/
	// ALGORITHM 3.1x Expressing the lock solutions - a set of locks fulfilling requirements in the given input matrix
	public void recreateOriginalLockOrder(LockSystem lockSystem) {
	
	ArrayList<Integer> lockOrder = new ArrayList<Integer>();
	lockOrder = lockSystem.getLockOrder();

	ArrayList<Integer> sortedResult = new ArrayList<Integer>(lockOrder);	

	// Recreate the original order among the locks given in the input matrix
	for(int k = 0; k < lockSystem.getLocksBaseTen().size(); k++) {
		int position = lockOrder.get(k);
		int element = lockSystem.getLocksBaseTen().get(k);
		sortedResult.set(position,element);
	}

	// IF system has a general lock --> add 0 at index 0 in the resulting array
	int generalLock = lockSystem.getGeneralLock();
	if(generalLock == 1) {
		sortedResult.add(0);
	}
	
	lockSystem.setSortedLocksBaseTen(sortedResult);
	
	// Convert lock with base ten to binary strings
	ArrayList<String> binaryLocks = new ArrayList<String>();
	binaryLocks = intToBinary(sortedResult);
	lockSystem.setBinaryLocks(binaryLocks);
	
	}
}
