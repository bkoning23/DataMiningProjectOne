/*
 * Brendan Koning
 * CIS 335
 * Naive Bayes Classifier
 * Model.java
 */

package projectOne;

import java.util.ArrayList;
import java.util.List;

public class Model {

	double virusCount = 0;
	double femaleVirusCount = 0;
	double maleVirusCount = 0;
	double femaleCount = 0;
	double maleCount = 0;
	double positiveCount = 0;
	double positiveVirusCount = 0;
	double heavyVirusCount = 0;
	double heavyCount = 0;
	double total = 0;
	
	double virusWeightTotal = 0;
	double notVirusWeightTotal = 0;
	
	double virusPrior;
	double femaleNotVirusCount;
	double femaleNotVirusLike;
	double femaleVirusLike;
	double maleNotVirusCount;
	double maleNotVirusLike;
	double virusNotPrior;
	double maleVirusLike;
	double positiveNotVirusCount;
	double positiveNotVirusLike;
	double negativeNotVirusLike;
	double positiveVirusLike;
	double negativeVirusLike;
	double lightVirusLike;
	double heavyVirusLike;
	double heavyNotVirusCount;
	double heavyNotVirusLike;
	double lightNotVirusLike;
	
	double virusWeightMean;
	double notVirusWeightMean;
	
	List<Double> virusWeights = new ArrayList<Double>();
	List<Double> notVirusWeights = new ArrayList<Double>();
	
	double virusWeightSD = 0;
	double notVirusWeightSD  = 0;
	
	
	public void buildModel(){
		virusPrior = virusCount/total;
		virusNotPrior = 1.0 - virusPrior;
		
		femaleNotVirusCount = femaleCount - femaleVirusCount;
		femaleNotVirusLike = (femaleNotVirusCount/total)/(virusNotPrior);
		femaleVirusLike = (femaleVirusCount/total)/(virusPrior);
		
		maleCount = total - femaleCount;
		
		maleNotVirusCount = maleCount - maleVirusCount;
		maleNotVirusLike = (maleNotVirusCount/total)/(virusNotPrior);
		maleVirusLike = (maleVirusCount/total)/(virusPrior);
		
		positiveNotVirusCount = positiveCount - positiveVirusCount;
		positiveNotVirusLike = (positiveNotVirusCount/total)/(virusNotPrior);
		negativeNotVirusLike = 1 - positiveNotVirusLike;
		
		positiveVirusLike = (positiveVirusCount/total)/(virusPrior);
		negativeVirusLike = 1 - positiveVirusLike;
		
		heavyVirusLike = (heavyVirusCount/total)/(virusPrior);
		lightVirusLike = 1 - heavyVirusLike;
		
		heavyNotVirusCount = heavyCount - heavyVirusCount;
		heavyNotVirusLike = (heavyNotVirusCount/total)/(virusNotPrior);
		lightNotVirusLike = 1 - heavyNotVirusLike;
		
		virusWeightMean = virusWeightTotal/virusCount;
		notVirusWeightMean = notVirusWeightTotal/(total - virusCount);	
		
		
		int i = 0;
		double varianceTotal = 0;
		for(i = 0; i < virusWeights.size(); i++){
			double temp = virusWeights.get(i) - virusWeightMean;
			temp = temp * temp;
			varianceTotal = varianceTotal + temp;
		}
		virusWeightSD = Math.sqrt(varianceTotal/virusCount);
		
		varianceTotal = 0;
		for(i = 0; i < notVirusWeights.size(); i++){
			double temp = notVirusWeights.get(i) - notVirusWeightMean;
			temp = temp * temp;
			varianceTotal = varianceTotal + temp;
		}
		notVirusWeightSD = Math.sqrt(varianceTotal/(total-virusCount));
		
		
	}
	
	public String discriminator(Patient p){
		double discrimVirus = virusPrior;
		double discrimNotVirus = virusNotPrior;
				
		if(p.gender.equals("male")){
			discrimVirus = discrimVirus * maleVirusLike;
			discrimNotVirus = discrimNotVirus * maleNotVirusLike;
		}
		else{
			discrimVirus = discrimVirus * femaleVirusLike;
			discrimNotVirus = discrimNotVirus * femaleNotVirusLike;
		}
		if(p.bloodType.contains("+")){
			discrimVirus = discrimVirus * positiveVirusLike;
			discrimNotVirus = discrimNotVirus * positiveNotVirusLike;
		}
		else{
			discrimVirus = discrimVirus * negativeVirusLike;
			discrimNotVirus = discrimNotVirus * negativeNotVirusLike;
		}

		double virusWeightNormalEstimate = calculateNormalEstimate(Double.parseDouble(p.weight), virusWeightMean, virusWeightSD);
		discrimVirus = discrimVirus * virusWeightNormalEstimate;
		
		double notVirusWeightNormalEstimate = calculateNormalEstimate(Double.parseDouble(p.weight), notVirusWeightMean, notVirusWeightSD);
		discrimNotVirus = discrimNotVirus * notVirusWeightNormalEstimate;
		
		if(discrimVirus > discrimNotVirus){
			return "Y";
		}
		else{
			return "N";
		}

	}
	
	public void increaseWeightTotal(double weight, String virus){
		if(virus.equals("Y")){
			virusWeightTotal = virusWeightTotal + weight;
		}
		else{
			notVirusWeightTotal = notVirusWeightTotal + weight;
		}
	}
	
	private double calculateNormalEstimate(double weight, double mean, double standardDev){
		double firstHalf = 1/(Math.sqrt(2* Math.PI * standardDev * standardDev));
		double expon = (Math.pow((mean - weight), 2))/(2*Math.pow(standardDev, 2));
		double secondHalf = Math.pow(Math.E, expon);
		
		return firstHalf * secondHalf;
	}
	

}
