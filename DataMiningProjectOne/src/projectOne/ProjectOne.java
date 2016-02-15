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
		
		double virusCount = 0;
		double femaleVirusCount = 0;
		double maleVirusCount = 0;
		double femaleCount = 0;
		double maleCount = 0;
		double positiveCount = 0;
		double positiveVirusCount = 0;
		double heavyVirusCount = 0;
		double heavyCount = 0;
		
		int total;
		
		for(total = 0; total < trainArray.size(); total++){
			Patient current = trainArray.get(total);
			if(current.hasVirus.equals("Y")){
				virusCount++;
				if(current.gender.equals("female")){
					femaleVirusCount++;
				}
				if(current.gender.equals("male")){
					maleVirusCount++;
				}
				if(current.bloodType.contains("+")){
					positiveVirusCount++;
				}
				if(Double.parseDouble(current.weight) > 170.0){
					heavyVirusCount++;
				}	
			}
			if(current.gender.equals("female")){
				femaleCount++;
			}
			if(current.bloodType.contains("+")){
				positiveCount++;
			}
			if(Double.parseDouble(current.weight) > 170.0){
				heavyCount++;
			}
			
		}
		
		double virusPrior = virusCount/total;
		double virusNotPrior = 1.0 - virusPrior;
		
		double femaleNotVirusCount = femaleCount - femaleVirusCount;
		double femaleNotVirusLike = (femaleNotVirusCount/total)/(virusNotPrior);
		double femaleVirusLike = (femaleVirusCount/total)/(virusPrior);
		
		maleCount = total - femaleCount;
		
		double maleNotVirusCount = maleCount - maleVirusCount;
		double maleNotVirusLike = (maleNotVirusCount/total)/(virusNotPrior);
		double maleVirusLike = (maleVirusCount/total)/(virusPrior);
		
		double positiveNotVirusCount = positiveCount - positiveVirusCount;
		double positiveNotVirusLike = (positiveNotVirusCount/total)/(virusNotPrior);
		double negativeNotVirusLike = 1 - positiveNotVirusLike;
		
		double positiveVirusLike = (positiveVirusCount/total)/(virusPrior);
		double negativeVirusLike = 1 - positiveVirusLike;
		
		double heavyVirusLike = (heavyVirusCount/total)/(virusPrior);
		double lightVirusLike = 1 - heavyVirusLike;
		
		double heavyNotVirusCount = heavyCount - heavyVirusCount;
		double heavyNotVirusLike = (heavyNotVirusCount/total)/(virusNotPrior);
		double lightNotVirusLike = 1 - heavyNotVirusLike;
		
		
		System.out.println("Prior for virus: " + virusPrior);
		System.out.println("Prior for not virus: " + virusNotPrior);
		
		System.out.println("Likelihood for female given not virus: " + femaleNotVirusLike);
		System.out.println("Likelihood for male given not virus: " + maleNotVirusLike);
		
		System.out.println("Likelihood for female given virus: " + femaleVirusLike);		
		System.out.println("Likelihood for male given virus: " + maleVirusLike);
		
		System.out.println("Likelihood for blood positive given not virus: " + positiveNotVirusLike);
		System.out.println("Likelihood for blood negative given not virus: " + negativeNotVirusLike);
		
		System.out.println("Likelihood for blood positive given virus: " + positiveVirusLike);
		System.out.println("Likelihood for blood negative given virus: " + negativeVirusLike);
		
		System.out.println("Likelihood for weight > 170.0 given not virus: " + heavyNotVirusLike);
		System.out.println("Likelihood for weight <= 170.0 given not virus: " + lightNotVirusLike);
		
		System.out.println("Likelihood for weight > 170.0 given virus: " + heavyVirusLike);
		System.out.println("Likelihood for weight <= 170.0 given virus: " + lightVirusLike);
		
		
	}

}
