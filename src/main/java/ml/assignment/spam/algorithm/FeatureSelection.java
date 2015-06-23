package ml.assignment.spam.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import ml.assignment.spam.utility.Utils;

public class FeatureSelection {
	
	public static HashMap<String,ArrayList<Long>> prepareDataForAttributeMutualInformation(Map<String,Long> totalVocab, Map<String,HashMap<String,Map<String,Long>>> classificationData)
	{
		HashMap<String,ArrayList<Long>> mutualInformationList = new HashMap<String,ArrayList<Long>>();
		ArrayList<Long> tempList=null;
		long presentCount=0;
		long totalCount=0;
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
		for(String attribute : totalVocab.keySet())
		{
			tempList = new ArrayList<Long>();
			for(String classificationName : classificationData.keySet())
			{
				presentCount=0;
				totalCount=0;
				classificationData1 = classificationData.get(classificationName);
				for(String docName : classificationData1.keySet())
				{
					totalCount = totalCount+1;
					docData1 = classificationData1.get(docName);
					if(docData1.containsKey(attribute))
						presentCount=presentCount+1;	
				}
				tempList.add(presentCount);
				tempList.add(totalCount-presentCount);
			}
			mutualInformationList.put(attribute, tempList);
		}
		
		return mutualInformationList;
		
	}
	
	public static HashMap<String,HashMap<String,ArrayList<Long>>> prepareDataForAttributeMutualInformation2(Map<String,Long> totalVocab, Map<String,HashMap<String,Map<String,Long>>> classificationData)
	{
		 HashMap<String,HashMap<String,ArrayList<Long>>> mutualInfoByClassification = new  HashMap<String,HashMap<String,ArrayList<Long>>>();
		HashMap<String,ArrayList<Long>> mutualInformationList = new HashMap<String,ArrayList<Long>>();
		ArrayList<Long> tempList=null;
		long presentCount=0;
		long totalCount=0;
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
	
			
			for(String classificationName : classificationData.keySet())
			{
				for(String attribute : totalVocab.keySet())
				{
					tempList = new ArrayList<Long>();
					presentCount=0;
					totalCount=0;
					classificationData1 = classificationData.get(classificationName);
					for(String docName : classificationData1.keySet())
					{
						totalCount = totalCount+1;
						docData1 = classificationData1.get(docName);
						if(docData1.containsKey(attribute))
							presentCount=presentCount+1;	
					}
					tempList.add(presentCount);
					tempList.add(totalCount-presentCount);

					if(mutualInfoByClassification.get(classificationName)!=null)
					{
						mutualInfoByClassification.get(classificationName).put(attribute, tempList);
					}
					else
					{
						mutualInformationList = new HashMap<String,ArrayList<Long>>();
						mutualInformationList.put(attribute, tempList);
						mutualInfoByClassification.put(classificationName, mutualInformationList);
					}
				}
				
		}
		
		return mutualInfoByClassification;
		
	}
	
	public static HashMap<String,TreeMap<Double,ArrayList<String>>>  getAttributeMutualInformation2(HashMap<String,HashMap<String,ArrayList<Long>>> mutualInformationList)
	{
		HashMap<String,TreeMap<Double,ArrayList<String>>>  mutualInformationDataByClassification = new  HashMap<String,TreeMap<Double,ArrayList<String>>>();
		TreeMap<Double,ArrayList<String>> attributeMutualInfo = new TreeMap<Double,ArrayList<String>> ();
		HashMap<String,ArrayList<Long>> attributeInfo = null;
		ArrayList<Long> tempList= null;
		ArrayList<String> attributeList= null;
		double miValue=0.0;
		long N11 =0;
		long N10= 0;
		double term=0.0;
		for(String classificationName : mutualInformationList.keySet())
		{
			attributeMutualInfo = new TreeMap<Double,ArrayList<String>> ();
			attributeInfo = mutualInformationList.get(classificationName);
			for(String attribute :  attributeInfo.keySet())
			{
				tempList = attributeInfo.get(attribute);
				term = tempList.get(0)/Double.valueOf(tempList.get(0)+ tempList.get(1));
				term = term * Utils.logBase2(term);
				if(attributeMutualInfo.get(term)!=null)
				{
					attributeMutualInfo.get(term).add(attribute);
				}
				else
				{
					attributeList = new ArrayList<String>();
					attributeList.add(attribute);
					attributeMutualInfo.put(term, attributeList);
				}
			}
			mutualInformationDataByClassification.put(classificationName, attributeMutualInfo);
		}
		return mutualInformationDataByClassification;
	}
	
	public static TreeMap<Double,ArrayList<String>>  getAttributeMutualInformation(HashMap<String,ArrayList<Long>> mutualInformationData)
	{
		TreeMap<Double,ArrayList<String>> attributeMutualInfo = new TreeMap<Double,ArrayList<String>> ();
		ArrayList<Long> tempList= null;
		ArrayList<String> attributeList= null;
		double miValue=0.0;
		long N11 =0;
		long N10= 0;
		long N01= 0;
		long N00 = 0;
		long totalN = 0;
		double term1=0.0;
		for(String attribute : mutualInformationData.keySet())
		{
			tempList = mutualInformationData.get(attribute);
			N11 =tempList.get(0);
			N10= tempList.get(1);
			N01= tempList.get(2);
			N00 = tempList.get(3);
			totalN = N11 + N10 + N01 + N00;
			term1 = ( N11/(Double.valueOf(totalN)) ) * Utils.logBase2( (N11*totalN)  /  (Double.valueOf( (N11+N10) * (N11+N01) )  )  );
			term1 = term1 +  ( N01/(Double.valueOf(totalN)) )* Utils.logBase2( (N01*totalN)  /  (Double.valueOf( (N01+N00) * (N01+N11) )  )  );
			term1 = term1 +  ( N10/(Double.valueOf(totalN)) )* Utils.logBase2( (N10*totalN)  /  (Double.valueOf( (N10+N00) * (N10+N11) )  )  );
			term1 = term1 +  ( N00/(Double.valueOf(totalN)) )* Utils.logBase2( (N00*totalN)  /  (Double.valueOf( (N00+N01) * (N00+N10) )  )  );
			
			if(attributeMutualInfo.get(term1)!=null)
			{
				attributeMutualInfo.get(term1).add(attribute);
			}
			else
			{
				attributeList = new ArrayList<String>();
				attributeList.add(attribute);
				attributeMutualInfo.put(term1, attributeList);
			}
		
			
		}
		return attributeMutualInfo;
	}
	
	public static ArrayList<String> getBestKFeature(TreeMap<Double,ArrayList<String>> attibuteMutualInfo, int k)
	{
		ArrayList<String> attributeBestList= new ArrayList<String>();
		boolean exitFlag=false;
		for(Double bestValue : attibuteMutualInfo.descendingKeySet() )
		{
			for(String attribute : attibuteMutualInfo.get(bestValue))
			{
				attributeBestList.add(attribute);
				if(attributeBestList.size()==k)
				{
					exitFlag=true;
					break;
				}
			}
			
			if(exitFlag)
				break;
		}
		return attributeBestList;
	}
	
	public static HashSet<String> getBestKFeature2(HashMap<String,TreeMap<Double,ArrayList<String>>> attibuteMutualInfoByClassification, int k)
	{
		HashSet<String> attributeBestList= new HashSet<String>();
		boolean exitFlag=false;
		
		TreeSet<String> classSet = new TreeSet<String>();
		classSet.addAll(attibuteMutualInfoByClassification.keySet());
		int firstListCount = k/(classSet.size());
		int round=1;
		for(String class1 : classSet)
		{
			for(Double bestValue : attibuteMutualInfoByClassification.get(class1).descendingKeySet() )
			{
				for(String attribute : attibuteMutualInfoByClassification.get(class1).get(bestValue))
				{
					attributeBestList.add(attribute);
					if(attributeBestList.size()>=(firstListCount*round))
					{
						exitFlag=true;
						break;
					}
				}
				
				if(exitFlag)
					break;
			}
			round= round +1;
		}
		
		
		
		return attributeBestList;
	}
	
	public static ArrayList<String> getBestKFeatureMain(Map<String,Long> totalVocab, Map<String,HashMap<String,Map<String,Long>>> classificationData,int k)
	{
		HashMap<String,ArrayList<Long>> mutualInformationList = prepareDataForAttributeMutualInformation(totalVocab,classificationData);
		TreeMap<Double,ArrayList<String>> attributeMutualInfo = getAttributeMutualInformation(mutualInformationList);
		return getBestKFeature(attributeMutualInfo,k);
	}
	
	public static HashSet<String> getBestKFeatureMain2(Map<String,Long> totalVocab, Map<String,HashMap<String,Map<String,Long>>> classificationData,int k)
	{
		HashMap<String,HashMap<String,ArrayList<Long>>>  mutualInformationList = prepareDataForAttributeMutualInformation2(totalVocab,classificationData);
		HashMap<String,TreeMap<Double,ArrayList<String>>> attributeMutualInfo = getAttributeMutualInformation2(mutualInformationList);
		return getBestKFeature2(attributeMutualInfo,k);
	}
}
