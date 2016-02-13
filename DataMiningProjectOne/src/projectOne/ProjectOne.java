package projectOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectOne {

	
	
	public static void main(String[] args) throws IOException {
		
		List<Patient> trainArray = new ArrayList<Patient>();
		List<Patient> testArray = new ArrayList<Patient>();		
		
		String line;
		
		try(BufferedReader br = new BufferedReader(new FileReader("proj1train.txt"))){
			while((line = br.readLine()) != null){
				String[] data = line.split(",");
				Patient temp = new Patient(data[0], data[1], data[2], data[3], data[4]);
				trainArray.add(temp);
			}
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader("proj1test.txt"))){
			while((line = br.readLine()) != null){
				String[] data = line.split(",");
				Patient temp = new Patient(data[0], data[1], data[2], data[3], data[4]);
				testArray.add(temp);
			}
		}
		
		

		
		
	}

}
