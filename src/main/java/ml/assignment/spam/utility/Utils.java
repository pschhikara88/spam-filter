package ml.assignment.spam.utility;

import java.util.HashMap;
import java.util.Map;

import ml.assignment.spam.application.ApplicationData;

public class Utils {

	public static double logBase2(double value)
	{
		return Math.log(value)/Math.log(2);
	}
	
	public static long vocabFrequencyCount(Map<String,Long> classificationVocabData)
	{
		long totalVocab=0;
		for(String classifier : classificationVocabData.keySet())
		{
			totalVocab =totalVocab + classificationVocabData.get(classifier);
		}
		return totalVocab;
	}
	
	public static Map<String,Long> extractTokensFromDocWithVocab(Map<String,Long> docVocab,Map<String,Long> totalVocab)
	{
		Map<String,Long> docVocabPresentInVocab = new HashMap<String,Long>();
		for(String word : docVocab.keySet())
		{
			if(totalVocab.containsKey(word))
				docVocabPresentInVocab.put(word, docVocab.get(word));
		}
		return docVocabPresentInVocab;
	}
	
	public static String getClassificationWithMostProbability(HashMap<String,Double> classifierTotalScore)
	{
		Double maxClassScore = null;
		String maxClass = null;
		Double tempClassScore=null;
		for(String tempClass : classifierTotalScore.keySet())
		{
			tempClassScore = classifierTotalScore.get(tempClass);
			if(maxClassScore==null)
			{	
				maxClassScore = tempClassScore;
				maxClass = tempClass;	
			}
			else
			{
				if(tempClassScore>=maxClassScore)
				{
					maxClassScore = tempClassScore; 
					maxClass = tempClass;
				}
			}
		}
		return maxClass;
	}
	
	public static double weightChangeErrror(HashMap<String,Double> previousWeightVector,HashMap<String,Double> currentWeightVector)
	{
		double tempValue=0.0;
		for(String feature : previousWeightVector.keySet())
		{
			tempValue = tempValue + Math.pow(previousWeightVector.get(feature) - currentWeightVector.get(feature),2);
		}
		return tempValue;
	}
}
