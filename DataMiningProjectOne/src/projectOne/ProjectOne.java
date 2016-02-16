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
		
		Model model = new Model();
		
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

		int total;
		
		for(total = 0; total < trainArray.size(); total++){
			Patient current = trainArray.get(total);
			if(current.hasVirus.equals("Y")){
				model.virusCount++;
				if(current.gender.equals("female")){
					model.femaleVirusCount++;
				}
				if(current.gender.equals("male")){
					model.maleVirusCount++;
				}
				if(current.bloodType.contains("+")){
					model.positiveVirusCount++;
				}
				if(Double.parseDouble(current.weight) > 170.0){
					model.heavyVirusCount++;
				}	
			}
			if(current.gender.equals("female")){
				model.femaleCount++;
			}
			if(current.bloodType.contains("+")){
				model.positiveCount++;
			}
			if(Double.parseDouble(current.weight) > 170.0){
				model.heavyCount++;
			}
			
		}
		model.total = total;
		model.buildModel();
				
		System.out.println("Prior for virus: " + model.virusPrior);
		System.out.println("Prior for not virus: " + model.virusNotPrior);
		
		System.out.println("Likelihood for female given not virus: " + model.femaleNotVirusLike);
		System.out.println("Likelihood for male given not virus: " + model.maleNotVirusLike);
		
		System.out.println("Likelihood for female given virus: " + model.femaleVirusLike);		
		System.out.println("Likelihood for male given virus: " + model.maleVirusLike);
		
		System.out.println("Likelihood for blood positive given not virus: " + model.positiveNotVirusLike);
		System.out.println("Likelihood for blood negative given not virus: " + model.negativeNotVirusLike);
		
		System.out.println("Likelihood for blood positive given virus: " + model.positiveVirusLike);
		System.out.println("Likelihood for blood negative given virus: " + model.negativeVirusLike);
		
		System.out.println("Likelihood for weight > 170.0 given not virus: " + model.heavyNotVirusLike);
		System.out.println("Likelihood for weight <= 170.0 given not virus: " + model.lightNotVirusLike);
		
		System.out.println("Likelihood for weight > 170.0 given virus: " + model.heavyVirusLike);
		System.out.println("Likelihood for weight <= 170.0 given virus: " + model.lightVirusLike);
		
		int predYactualY = 0;
		int predYactualN = 0;
		int predNactualN = 0;
		int predNactualY = 0;	
		
		for(int i = 0; i < testArray.size(); i++){
			Patient current = testArray.get(i);
			
			String result = model.discriminator(current);			

			if(current.hasVirus.equals(result)){
				if(current.hasVirus.equals("Y")){
					predYactualY++;
				}
				else{
					predNactualN++;
				}
			}
			else if(current.hasVirus.equals("Y")){
				predNactualY++;
			}
			else{
				predYactualN++;
			}
					
			System.out.println(i + " " + current.hasVirus + " " + result);
		}
		
		
		System.out.println("\nConfusion Matrix");
		System.out.println("	Y	N\nY	" + predYactualY + "	" + predNactualY +  "\n\nN	" + predYactualN + "	" + predNactualN);		
	}
}
