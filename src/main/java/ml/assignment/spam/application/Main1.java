package ml.assignment.spam.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import ml.assignment.spam.algorithm.FeatureSelection;
import ml.assignment.spam.algorithm.LogisticRegression;
import ml.assignment.spam.algorithm.NaiveBayes;
import ml.assignment.spam.techincal.DataReader;
import ml.assignment.spam.utility.RemoveUtils;
import ml.assignment.spam.utility.Utils;
import ml.assignment.spam.utility.WeightUtils;

public class Main1 {
	
	public static void main(String[] args) throws IOException {
		
	/*	String dataSet=args[0];
		double lapalaceSmooth = Double.valueOf(args[1]);
		int featureSelectionParamer = Integer.valueOf(args[2]);
		double learningRate = Double.valueOf(args[3]);
		double lambda=Double.valueOf(args[4]);
		int noOfIteration=Integer.valueOf(args[5]);*/
		
		double lapalaceSmooth = 1;
		double learningRate = 0.001;
		double lambda=2;
		int noOfIteration=1500;
		int featureSelectionParamer = 100;
		String dataSet="Data";
		
		
		
		Map<String,HashMap<String,Map<String,Long>>> trainingDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\train");
		//Map<String,HashMap<String,Map<String,Long>>> trainingDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("Data" + File.separator + dataSet+File.separator + "train");
		
		// logistic regression input data
		ArrayList<Map<String,HashMap<String,Map<String,Long>>>>  splitData =  RemoveUtils.splitTrainingSet(trainingDataPerDocWithClassificationAsKey1);
		Map<String,HashMap<String,Map<String,Long>>> reducedTrainingDataPerDocWithClassificationAsKey1 = splitData.get(1);
		Map<String,HashMap<String,Map<String,Long>>> reducedValidationDataPerDocWithClassificationAsKey1 = splitData.get(0);
		
		Map<String,Map<String,Long>> trainingDataWithClassification1 = DataReader.getDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\train");
		//Map<String,Map<String,Long>> trainingDataWithClassification1 = DataReader.getDataBasedOnClassification("Data" + File.separator + dataSet+File.separator + "train");
		
		Map<String,HashMap<String,Object>> trainedNBData1 = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,ApplicationData.totalVocabData,lapalaceSmooth);

		Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\test");
		//Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("Data" + File.separator + dataSet+File.separator + "test");
		
		
		//NB accuracy without removing stopwords
		double accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBData1);
		System.out.println("Total Accuracy without removing stopWords : "+accuracy );
		
		
		//Map<String,HashMap<String,Map<String,Long>>> trainDataPerDocWithClassificationAsKey = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\train");
		
		//Stop words logic
		HashSet<String> stopWords = DataReader.getStopWordsSet("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\stopword.csv");
		//HashSet<String> stopWords = DataReader.getStopWordsSet("stopword.csv");
		Map<String,Long>   reducedTotalVocabData = RemoveUtils.removeStopWordsFromTotalVocabReturnNewMap(ApplicationData.totalVocabData, stopWords);
		Map<String,HashMap<String,Object>> trainedNBDataWithoutStopWord = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabData,lapalaceSmooth);
		
		//NB accuracy with removing stopwords
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with removing stopWords : "+accuracy );
		
		//NB by removing special character
		Map<String,Long>   reducedTotalVocabData1 = RemoveUtils.removeSpecialCharacterFromTotalVocabAndReturnNewMap(ApplicationData.totalVocabData);
		trainedNBDataWithoutStopWord =  NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabData1,lapalaceSmooth);
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with removing special character : "+accuracy );
		
		Map<String,Long>   reducedTotalVocabDataTemp=null;
		//NB by  removing special character and stopwords
		reducedTotalVocabDataTemp =  RemoveUtils.removeSpecialCharacterFromTotalVocabAndReturnNewMap(reducedTotalVocabData);
		trainedNBDataWithoutStopWord =  NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabDataTemp,lapalaceSmooth);
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with removing special character and stopwords : "+accuracy );
		
		//NB by removing special character and stopwords and Words which appear less than 5 times
		Map<String,Long> reducedTotalVocabDataTemp12= RemoveUtils.removeLessFrequentWordFromTotalVocabAndReturnNewMap(reducedTotalVocabDataTemp,4);
		trainedNBDataWithoutStopWord =  NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabDataTemp12,lapalaceSmooth);
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with removing special character , stopwords and less frequent word(<5) : "+accuracy );
		
		
		// feature Selection with Mutual Information
		ArrayList<String> bestKFeatures = FeatureSelection.getBestKFeatureMain(reducedTotalVocabData,trainingDataPerDocWithClassificationAsKey1,100);
		Map<String,Long> reducedTotalVocabDataTemp21 = RemoveUtils.createNewVocabWithKFeatures(ApplicationData.totalVocabData,bestKFeatures);
		trainedNBDataWithoutStopWord = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabDataTemp21,lapalaceSmooth);
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with MutuaInfor : "+accuracy );
		
		
		// feature Selection with Mutual Information
		HashSet<String> bestKFeatures2 = FeatureSelection.getBestKFeatureMain2(reducedTotalVocabData,trainingDataPerDocWithClassificationAsKey1,100);
		Map<String,Long> reducedTotalVocabDataTemp22 = RemoveUtils.createNewVocabWithKFeatures(ApplicationData.totalVocabData,bestKFeatures2);
		trainedNBDataWithoutStopWord = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabDataTemp22,lapalaceSmooth);
		accuracy = checkAccuracyWithNB(testDataPerDocWithClassificationAsKey1,trainedNBDataWithoutStopWord);
		System.out.println("Total Accuracy with MutuaInformation modification : "+accuracy );
		
		
		
		// logistic regression
	//	 accuracy = performLR(reducedTrainingDataPerDocWithClassificationAsKey1,reducedValidationDataPerDocWithClassificationAsKey1
	//			 ,testDataPerDocWithClassificationAsKey1,ApplicationData.totalVocabData,learningRate,lambda,noOfIteration);
	//	System.out.println("Accuracy with Test Data For Logistic Regression : " + accuracy);
		
		// logistic regression with k Best Feature 
		/*accuracy = performLR(reducedTrainingDataPerDocWithClassificationAsKey1,reducedValidationDataPerDocWithClassificationAsKey1
				 ,testDataPerDocWithClassificationAsKey1,reducedTotalVocabDataTemp21,learningRate,lambda,noOfIteration);
		System.out.println("Accuracy with Test Data For Logistic Regression with K Best Feature : " + accuracy);
		
		// logistic regression with removing stopwords and special character 
		accuracy = performLR(reducedTrainingDataPerDocWithClassificationAsKey1,reducedValidationDataPerDocWithClassificationAsKey1
				 ,testDataPerDocWithClassificationAsKey1,reducedTotalVocabDataTemp,learningRate,lambda,noOfIteration);
		System.out.println("Accuracy with Test Data For Logistic Regression by removing stop words and special character : " + accuracy);*/

		
	}
	
	private static double checkAccuracyWithNB(Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1,Map<String,HashMap<String,Object>> trainedNBData1 )
	{
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
		Map<String,Long> extractData1 = null;
		String calculatedClass1=null;
		HashMap<String,Double> classificationScore1=null;
		long successCount1 =0l;
		long totalCount1=0l;
		long totalSuccess =0l;
		long totalTotal=0l;
		for(String classificationName1 : testDataPerDocWithClassificationAsKey1.keySet())
		{
			successCount1 =0l;
			totalCount1=0l;
			classificationData1 = testDataPerDocWithClassificationAsKey1.get(classificationName1);
			for(String docName : classificationData1.keySet())
			{
				totalCount1 = totalCount1 + 1;
				docData1 = classificationData1.get(docName);
				//System.out.println(" docData : "+ docData1);
				extractData1 = Utils.extractTokensFromDocWithVocab(docData1,ApplicationData.totalVocabData);
				//System.out.println(" extractData : "+ extractData1);
				classificationScore1 = NaiveBayes.applyMultinomialNBForEachDoc(trainedNBData1, extractData1);
				//System.out.println(" classificationScore : "+ classificationScore1);
				calculatedClass1 = Utils.getClassificationWithMostProbability(classificationScore1);
				//System.out.println(" calculatedClass : "+ calculatedClass1);
				if(calculatedClass1.equalsIgnoreCase(classificationName1))
				{
					successCount1 = successCount1 + 1;
				}
			}
			totalSuccess = totalSuccess +successCount1;
			totalTotal = totalTotal + totalCount1;
			System.out.println("Accuracy with "+ classificationName1 + " : " + successCount1 + " : " + Double.valueOf(totalCount1));
			System.out.println("Accuracy with "+ classificationName1 + " : " +(100* successCount1)/(Double.valueOf(totalCount1)));
		}
		
		return (100* totalSuccess)/(Double.valueOf(totalTotal));
		
	}
	
	private static double checkAccuracy(Map<String,HashMap<String,Map<String,Long>>> testData , HashMap<String,Double> currentWeightVector)
	{
		long totalTotal=0;
		long totalSuccess=0;

		long successCount1=0;
		long totalCount1=0;
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
		String calculatedClass1=null;
		for(String classificationName1 : testData.keySet())
		{
			successCount1 =0l;
			totalCount1=0l;
			classificationData1 = testData.get(classificationName1);
			for(String docName : classificationData1.keySet())
			{
				totalCount1 = totalCount1 + 1;
				docData1 = classificationData1.get(docName);
				 
				double  weightFrequencyProduct = LogisticRegression.calculateWeightFrequencyProductForAccuracy(docData1, currentWeightVector);
				calculatedClass1 = weightFrequencyProduct>0?"ham":"spam";
				
				if(calculatedClass1.equalsIgnoreCase(classificationName1))
				{
					successCount1 = successCount1 + 1;
				}
			}
			totalTotal = totalTotal + totalCount1;
			totalSuccess = totalSuccess + successCount1;
			//System.out.println("Test Accuracy with " + classificationName1 + " : " + successCount1/(Double.valueOf(totalCount1)));	
		}
		return 100*totalSuccess/(Double.valueOf(totalTotal));
	}
	
	private static double performLR(Map<String,HashMap<String,Map<String,Long>>> reducedTrainingDataPerDocWithClassificationAsKey1, 
			Map<String,HashMap<String,Map<String,Long>>> reducedValidationDataPerDocWithClassificationAsKey1,
			Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1,
			Map<String,Long> totalVocab, double learningRate, double lambda, int noOfIteration)
	{
		Double weightVectorError = null; 
		HashMap<String,Double> weightVector = WeightUtils.initializeWeightVector(totalVocab);
		//HashMap<String,Double> weightVector = WeightUtils.initializeWeightVector(reducedTotalVocabDataTemp);
		double modifiedWeight=0.0;
		double secondSummationTerm=0.0;
		HashMap<String,Double> currentWeightVector = null;
		HashMap<String,Double> previousWeightVector = weightVector;
		double featureFrequency =0.0;
		double classificationValue =0.0;
		HashMap<String,Double> docCondtionalProbability = null;
		double trainAccuracy=0.0;
		double validationAccuracy=0.0;
		double testAccuracy=0.0;
		double miniumAccuracyDiff=100.0;
		
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
		HashMap<String,Double> bestWeightVector = null;
		
		for(int i=0; i<noOfIteration; i++)
		{
			currentWeightVector = new HashMap<String,Double>();
			
			docCondtionalProbability = new HashMap<String,Double>();
			for(String classificationName1 : reducedTrainingDataPerDocWithClassificationAsKey1.keySet())
			{
				classificationData1 = reducedTrainingDataPerDocWithClassificationAsKey1.get(classificationName1);
				for(String docName : classificationData1.keySet())
				{
					docData1 = classificationData1.get(docName);
					docCondtionalProbability.put(docName,LogisticRegression.calculateWeightFrequencyProduct(docData1, previousWeightVector));
				}
			}
			
			//System.out.println(docCondtionalProbability);
			
			for(String weightKey : previousWeightVector.keySet())
			{
				modifiedWeight = previousWeightVector.get(weightKey);
				secondSummationTerm=0.0;
				//second summation term logic
				for(String classificationName1 : reducedTrainingDataPerDocWithClassificationAsKey1.keySet())
				{
					classificationData1 = reducedTrainingDataPerDocWithClassificationAsKey1.get(classificationName1);
					for(String docName : classificationData1.keySet())
					{
						docData1 = classificationData1.get(docName);
						featureFrequency = docData1.get(weightKey)!=null?docData1.get(weightKey):0.0;
						classificationValue = classificationName1.equalsIgnoreCase("ham")?1.0:0.0;
						//System.out.println(" doc nam : " + docName + " : " + docCondtionalProbability.get(docName));
						secondSummationTerm = secondSummationTerm + featureFrequency*(classificationValue - docCondtionalProbability.get(docName));
					}
				}
			   // end summation logic
				
				modifiedWeight = modifiedWeight + (learningRate * secondSummationTerm) - (learningRate*lambda*modifiedWeight);
				currentWeightVector.put(weightKey, modifiedWeight);
			}
			
			weightVectorError = Utils.weightChangeErrror(previousWeightVector,currentWeightVector);		
			previousWeightVector = currentWeightVector;
			
			trainAccuracy = checkAccuracy(reducedTrainingDataPerDocWithClassificationAsKey1,currentWeightVector);
			
			if(trainAccuracy==100.0)
			{
				validationAccuracy =  checkAccuracy(reducedValidationDataPerDocWithClassificationAsKey1,currentWeightVector);
				if(trainAccuracy - validationAccuracy <= miniumAccuracyDiff)
				{
					miniumAccuracyDiff = trainAccuracy - validationAccuracy;
					bestWeightVector = currentWeightVector;
				}			
			}
			
			
			
		}
		if(bestWeightVector==null)
			bestWeightVector= currentWeightVector;
		
		testAccuracy = checkAccuracy(testDataPerDocWithClassificationAsKey1,bestWeightVector);
		return testAccuracy;
	}
}
