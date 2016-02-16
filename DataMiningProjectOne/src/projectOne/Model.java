package projectOne;

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
		if(Double.parseDouble(p.weight) > 170.0){
			discrimVirus = discrimVirus * heavyVirusLike;
			discrimNotVirus = discrimNotVirus * heavyNotVirusLike;
		}
		else{
			discrimVirus = discrimVirus * lightVirusLike;
			discrimNotVirus = discrimNotVirus * lightNotVirusLike;
		}
		
		if(discrimVirus > discrimNotVirus){
			return "Y";
		}
		else{
			return "N";
		}

	}

}
