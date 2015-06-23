package ml.assignment.spam.algorithm;

import java.util.HashMap;
import java.util.Map;

import ml.assignment.spam.application.ApplicationData;
import ml.assignment.spam.utility.Utils;

public class NaiveBayes {
	
	public static Map<String,HashMap<String,Object>> trainMultinomialBayes(Map<String,Map<String,Long>> classificationData,Map<String,Long>  totalVocabData,double laplaceCorrection)
	{
		Map<String,HashMap<String,Object>> trainedNBData = new HashMap<String,HashMap<String,Object>>();
		long vocabCount = totalVocabData.size();
		
		long totalExampleCount = Utils.vocabFrequencyCount(ApplicationData.docCountByClassification);
		
		Long docsCount=null;
		Double prior=null;
		Map<String,Long> classifierAttributeFrequency=null;
		Long tempWordFrequency=null;
		Double tempConditionalProbability=null;
		HashMap<String,Object> classifierData=null;
		HashMap<String,Double> wordProbabilityCondition=null;
		for(String classificationName : classificationData.keySet())
		{
			classifierData = new HashMap<String,Object>();
			trainedNBData.put(classificationName, classifierData);
			classifierAttributeFrequency =  classificationData.get(classificationName);
			docsCount = ApplicationData.docCountByClassification.get(classificationName);
			
			long totalFrequencyCount = Utils.vocabFrequencyCount(classifierAttributeFrequency);
			
			
			
			if(totalExampleCount>0 && docsCount!=null && docsCount>0)
				prior = docsCount/Double.valueOf(totalExampleCount);
			
			wordProbabilityCondition =  new HashMap<String,Double>();
			for(String currentAttribute : totalVocabData.keySet())
			{
				
				tempWordFrequency = classifierAttributeFrequency.get(currentAttribute);
				if(tempWordFrequency!=null)
				{
				//	tempConditionalProbability = (tempWordFrequency+1)/Double.valueOf(classifierAttributeFrequency.size()+vocabCount);
					tempConditionalProbability = (tempWordFrequency+laplaceCorrection)/Double.valueOf(totalFrequencyCount+(laplaceCorrection*vocabCount));
				}
				else
				{
					//tempConditionalProbability = 1/Double.valueOf(classifierAttributeFrequency.size()+vocabCount);
					tempConditionalProbability =laplaceCorrection/Double.valueOf(totalFrequencyCount+(laplaceCorrection*vocabCount));
				}
				wordProbabilityCondition.put(currentAttribute, tempConditionalProbability);
				
			}
			
			trainedNBData.get(classificationName).put("PRIOR",prior);
			trainedNBData.get(classificationName).put("CONDITIONAL-PROBABILITY",wordProbabilityCondition);
			
		}
		
		return trainedNBData;
	}
	
	public static HashMap<String,Double> applyMultinomialNBForEachDoc(Map<String,HashMap<String,Object>> trainedNBDataMap, Map<String,Long> docData)
	{
		HashMap<String,Double> classifierScore = new HashMap<String,Double>();
		HashMap<String,Object> classifierTrainedData = null;
		Double prior=null;
		HashMap<String,Double> wordProbabilityCondition=null;
		double tempScore=0.0;
		double tempValue=0.0;
		//long frequencyData=0;
		for(String classifierName : trainedNBDataMap.keySet())
		{
			prior = (Double)trainedNBDataMap.get(classifierName).get("PRIOR");
			wordProbabilityCondition = (HashMap<String,Double>)trainedNBDataMap.get(classifierName).get("CONDITIONAL-PROBABILITY");
			tempScore = Math.log(prior);
			for(String attributeName : docData.keySet())
			{
				tempValue = wordProbabilityCondition.get(attributeName)==null?0.0:Math.log(wordProbabilityCondition.get(attributeName));
				//tempValue =  tempValue * docData.get(attributeName);
				tempScore = tempScore + tempValue;
			}
			classifierScore.put(classifierName, tempScore);
		}
		return classifierScore;
	}

}
