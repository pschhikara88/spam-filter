package ml.assignment.spam.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import ml.assignment.spam.algorithm.LogisticRegression;
import ml.assignment.spam.algorithm.NaiveBayes;
import ml.assignment.spam.techincal.DataReader;
import ml.assignment.spam.utility.Utils;
import ml.assignment.spam.utility.WeightUtils;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		/*Map<String,Map<String,Long>> trainingDataWithClassification = DataReader.getDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\testFolder\\train");
		System.out.println(" trainingDataWithClassification : "+ trainingDataWithClassification);
		Map<String,HashMap<String,Object>> trainedNBData = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification);
		System.out.println(" trainedNBData : "+ trainedNBData);
		
		Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\testFolder\\train");
		System.out.println(" testDataPerDocWithClassificationAsKey : "+ testDataPerDocWithClassificationAsKey);
		
		
		HashMap<String,Map<String,Long>> classificationData = null;
		Map<String,Long> docData = null;
		Map<String,Long> extractData = null;
		String calculatedClass=null;
		HashMap<String,Double> classificationScore=null;
		long successCount =0l;
		long totalCount=0l;
		for(String classificationName : testDataPerDocWithClassificationAsKey.keySet())
		{
			successCount =0l;
			totalCount=0l;
			classificationData = testDataPerDocWithClassificationAsKey.get(classificationName);
			for(String docName : classificationData.keySet())
			{
				totalCount = totalCount + 1;
				docData = classificationData.get(docName);
				System.out.println(" docData : "+ docData);
				extractData = Utils.extractTokensFromDocWithVocab(docData);
				System.out.println(" extractData : "+ extractData);
				classificationScore = NaiveBayes.applyMultinomialNBForEachDoc(trainedNBData, extractData);
				System.out.println(" classificationScore : "+ classificationScore);
				calculatedClass = Utils.getClassificationWithMostProbability(classificationScore);
				System.out.println(" calculatedClass : "+ calculatedClass);
				if(calculatedClass.equalsIgnoreCase(classificationName))
				{
					successCount = successCount + 1;
				}
			}
			System.out.println("Accuracy with "+ classificationName + " : " + successCount/(Double.valueOf(totalCount)));
		}*/
		
		
		Map<String,Map<String,Long>> trainingDataWithClassification1 = DataReader.getDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW22\\train");
		//System.out.println(" trainingDataWithClassification : "+ trainingDataWithClassification);
		Map<String,HashMap<String,Object>> trainedNBData1 = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,ApplicationData.totalVocabData,1);
		//System.out.println(" trainedNBData : "+ trainedNBData);
		
		Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW22\\test");
		//System.out.println(" testDataPerDocWithClassificationAsKey : "+ testDataPerDocWithClassificationAsKey);
		
		
		HashMap<String,Map<String,Long>> classificationData1 = null;
		Map<String,Long> docData1 = null;
		Map<String,Long> extractData1 = null;
		String calculatedClass1=null;
		HashMap<String,Double> classificationScore1=null;
		long successCount1 =0l;
		long totalCount1=0l;
		/*for(String classificationName1 : testDataPerDocWithClassificationAsKey1.keySet())
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
			System.out.println("Accuracy with "+ classificationName1 + " : " + successCount1 + " : " + Double.valueOf(totalCount1));
			System.out.println("Accuracy with "+ classificationName1 + " : " + successCount1/(Double.valueOf(totalCount1)));
		}
		
		
		System.out.println("test vocab : " + ApplicationData.totalVocabData.containsKey("hanover"));
		HashSet<String> stopWords = DataReader.getStopWordsSet("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\stopword.csv");
		
		//Map<String,Map<String,Long>> trainingDataWithClassificationWithoutStopWord = RemoveUtils.removeStopWordsAndReturnNewMap(trainingDataWithClassification1, stopWords);
		//System.out.println(" trainingDataWithClassification : "+ trainingDataWithClassification);
		Map<String,HashMap<String,Map<String,Long>>> trainDataPerDocWithClassificationAsKey = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\train");
		
		//Map<String,Long>  reducedTotalVocabData = RemoveUtils.createNewVocabWithKFeatures(ApplicationData.totalVocabData,);
		Map<String,Long>  reducedTotalVocabData = RemoveUtils.removeSpecialCharacterFromTotalVocabAndReturnNewMap(ApplicationData.totalVocabData);
		 reducedTotalVocabData = RemoveUtils.removeStopWordsFromTotalVocabReturnNewMap(reducedTotalVocabData, stopWords);
		//ArrayList<String> bestKFeatures = FeatureSelection.getBestKFeatureMain(reducedTotalVocabData,trainDataPerDocWithClassificationAsKey,200);
		 //HashSet<String> bestKFeatures = FeatureSelection.getBestKFeatureMain2(reducedTotalVocabData,trainDataPerDocWithClassificationAsKey,100);
	     //reducedTotalVocabData = RemoveUtils.createNewVocabWithKFeatures(ApplicationData.totalVocabData,bestKFeatures);
		Map<String,HashMap<String,Object>> trainedNBDataWithoutStopWord = NaiveBayes.trainMultinomialBayes(trainingDataWithClassification1,reducedTotalVocabData);
		//System.out.println(" trainedNBData : "+ trainedNBData);
		
		//Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKey1 = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\test");
		//System.out.println(" testDataPerDocWithClassificationAsKey : "+ testDataPerDocWithClassificationAsKey);
	
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
				extractData1 = Utils.extractTokensFromDocWithVocab(docData1,reducedTotalVocabData);
				//System.out.println(" extractData : "+ extractData1);
				classificationScore1 = NaiveBayes.applyMultinomialNBForEachDoc(trainedNBDataWithoutStopWord, extractData1);
				//System.out.println(" classificationScore : "+ classificationScore1);
				calculatedClass1 = Utils.getClassificationWithMostProbability(classificationScore1);
				//System.out.println(" calculatedClass : "+ calculatedClass1);
				if(calculatedClass1.equalsIgnoreCase(classificationName1))
				{
					successCount1 = successCount1 + 1;
				}
			}
			System.out.println("Accuracy with "+ classificationName1 + " : " + successCount1 + " : " + Double.valueOf(totalCount1));
			System.out.println("StopWords Accuracy with "+ classificationName1 + " : " + successCount1/(Double.valueOf(totalCount1)));
		}
		*/
		
		
		//logistic regression
		Double weightVectorError = null; 
		double learningRate = 0.001;
		double lambda=1;
		HashMap<String,Double> weightVector = WeightUtils.initializeWeightVector(ApplicationData.totalVocabData);
		
		Map<String,HashMap<String,Map<String,Long>>> testDataPerDocWithClassificationAsKeyLR = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW22\\train");
		Map<String,HashMap<String,Map<String,Long>>> validationDataPerDocWithClassificationAsKeyLR = DataReader.getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW22\\validation");
		 // randomized weight vector initialization for which total vocab required
		double modifiedWeight=0.0;
		double secondSummationTerm=0.0;
		double thirdTerm=0.0;
		double weightMagnitude=0.0;
		HashMap<String,Double> currentWeightVector = null;
		HashMap<String,Double> previousWeightVector = weightVector;
		double featureFrequency =0.0;
		double classificationValue =0.0;
		HashMap<String,Double> docCondtionalProbability = null;
		double trainAccuracy=0.0;
		double validationAccuracy=0.0;
		double testAccuracy=0.0;
		System.out.println("This goes to the console");
		PrintStream console = System.out;

		//File file = new File("Data" + File.separator+ dataSet + File.separator + "information-tree.txt");
		File file = new File("C:/Users/SRPOP/Desktop/Gogate/HW2/"+lambda+"-"+ learningRate+".txt");
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		for(int i=0; i<600; i++)
		{
			
			//thirdTerm=0.0;
			
			//weightMagnitude = WeightUtils.getWeightMagnitude(weightVector);
			currentWeightVector = new HashMap<String,Double>();
			
			docCondtionalProbability = new HashMap<String,Double>();
			for(String classificationName1 : testDataPerDocWithClassificationAsKeyLR.keySet())
			{
				classificationData1 = testDataPerDocWithClassificationAsKeyLR.get(classificationName1);
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
				for(String classificationName1 : testDataPerDocWithClassificationAsKeyLR.keySet())
				{
					classificationData1 = testDataPerDocWithClassificationAsKeyLR.get(classificationName1);
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
			
			trainAccuracy = checkAccuracy(testDataPerDocWithClassificationAsKeyLR,currentWeightVector);
			testAccuracy = checkAccuracy(testDataPerDocWithClassificationAsKey1,currentWeightVector);
			
			System.out.println(weightVectorError+","+trainAccuracy+","+testAccuracy);
			
			
			
			/*successCount1 =0l;
			totalCount1=0l;
			for(String classificationName1 : testDataPerDocWithClassificationAsKeyLR.keySet())
			{
				
				classificationData1 = testDataPerDocWithClassificationAsKeyLR.get(classificationName1);
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
				
			}
			
			trainAccuracy = 100*successCount1/(Double.valueOf(totalCount1));
			
			if(trainAccuracy==100.0)
			{
				System.out.println(" i : "+ i + " error : "+weightVectorError +" Accuracy : "+100*successCount1/(Double.valueOf(totalCount1)));
				successCount1 =0l;
				totalCount1=0l;
				for(String classificationName1 : validationDataPerDocWithClassificationAsKeyLR.keySet())
				{
					
					classificationData1 = validationDataPerDocWithClassificationAsKeyLR.get(classificationName1);
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
					
				}
				validationAccuracy = 100*successCount1/(Double.valueOf(totalCount1));
				System.out.println("Validation  i : "+ i + " error : "+weightVectorError +" Accuracy : "+validationAccuracy);
				if(trainAccuracy - validationAccuracy<=5.0)
				{
					long totalTotal=0;
					long totalSuccess=0;
					for(String classificationName1 : testDataPerDocWithClassificationAsKey1.keySet())
					{
						successCount1 =0l;
						totalCount1=0l;
						classificationData1 = testDataPerDocWithClassificationAsKey1.get(classificationName1);
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
						System.out.println("Test Accuracy with " + classificationName1 + " : " + successCount1/(Double.valueOf(totalCount1)));	
					}
					System.out.println("Test Accuracy ------- : " + totalSuccess/(Double.valueOf(totalTotal)));
				}
			}*/
				
			
			/*if(successCount1/(Double.valueOf(totalCount1))>=1.0)
			{
				break;
			}*/
			
			
			
		}
		System.setOut(console);
		
		
		
		
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
		return totalSuccess/(Double.valueOf(totalTotal));
	}

}
