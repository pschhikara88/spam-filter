package ml.assignment.spam.algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class LogisticRegression {
	
	public static double caluculateWeightSecondSummationTerm(Map<String,Long> docData, String classification, String weightKey, HashMap<String,Double> weightVector)
	{
		double featureFrequency = docData.get(weightKey)!=null?docData.get(weightKey):0.0;
		double classificationValue = classification.equalsIgnoreCase("ham")?1.0:0.0;
		
		double tempWeightFrequencyProduct = calculateWeightFrequencyProduct(docData,weightVector);
		
		
		return 0.0;
		//return featureFrequency*(classificationValue - probabilityOfOneByX);
	}
	
	public static double calculateWeightFrequencyProduct(Map<String,Long> docData,HashMap<String,Double> weightVector)
	{
		//System.out.println(weightVector);
		
		//System.out.println("This goes to out.txt");
	//	printTreeTraverse(root,0);
		//printTreeTraverse(root,0,null, null);
	
		double tempWeightFrequencyProduct = 0.0;
		for(String docFeature : docData.keySet())
		{
			//System.out.println(docFeature);
			tempWeightFrequencyProduct = tempWeightFrequencyProduct + docData.get(docFeature) * (weightVector.get(docFeature)!=null?weightVector.get(docFeature):0.0);
			//System.out.println(docFeature + " = " + docData.get(docFeature) + " = " +  (weightVector.get(docFeature)!=null?weightVector.get(docFeature):0.0) + "  " + tempWeightFrequencyProduct);
		}
		//System.out.println(weightVector.get("w0"));
		tempWeightFrequencyProduct = tempWeightFrequencyProduct + weightVector.get("w0");
		//System.out.println(" temp : " + tempWeightFrequencyProduct);
		if(tempWeightFrequencyProduct>500.0)
			return 1.0;
		double probabilityOfOneByX = Math.exp(tempWeightFrequencyProduct)/Double.valueOf(1+ Math.exp(tempWeightFrequencyProduct));
		//System.out.println("probabilityOfOneByX : "+ probabilityOfOneByX);
		return probabilityOfOneByX;
		
	}
	
	public static double calculateWeightFrequencyProductForAccuracy(Map<String,Long> docData,HashMap<String,Double> weightVector)
	{
		//System.out.println(weightVector);
		double tempWeightFrequencyProduct = 0.0;
		for(String docFeature : docData.keySet())
		{
			//System.out.println(docFeature);
			tempWeightFrequencyProduct = tempWeightFrequencyProduct + docData.get(docFeature) * (weightVector.get(docFeature)!=null?weightVector.get(docFeature):0.0);
		}
		tempWeightFrequencyProduct = tempWeightFrequencyProduct + weightVector.get("w0");
		
		return tempWeightFrequencyProduct;
	}

}
