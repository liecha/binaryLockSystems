import java.util.ArrayList;

public class inputMatrixMain {

	public static void main(String[] args) {
		LockSystem lockSystem = new LockSystem();
		InputMatrix input = new InputMatrix();
		
		ArrayList<String> inputMatrix = new ArrayList<String>();
		inputMatrix = input.reader(lockSystem);
		
		input.defineLockSystem(inputMatrix, lockSystem);
		
		//lockSystem.getInfoDeveloper();
		lockSystem.getInfoUser();
	}

}
