package ml.assignment.spam.techincal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

import ml.assignment.spam.application.ApplicationData;

import org.apache.commons.io.FileUtils;

public class DataReader {
	
	public static Map<String,Map<String,Long>> getDataBasedOnClassification(String folderPath) throws IOException
	{
		Map<String,Map<String,Long>> dataBasedOnClassification= new HashMap<String,Map<String,Long>>();
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		//File file =null;
		String classificationName=null;
		File[] listOfClassificationFile=null;
		File classificationFileDirectory=null;
		 String fileContent=null;
		HashMap<String,Long> vocabMap = null;
		long docCountPerClassification=0;
		//int vocabCountPerClassification=0;
		String[] wordList=null;
		for (File file : listOfFiles) {
		  if (file.isDirectory()) 
		  {
			  classificationName = file.getName();
			  vocabMap = new HashMap<String,Long>();
			  docCountPerClassification=0;
			 // vocabCountPerClassification=0;
			  listOfClassificationFile = file.listFiles();
			  for(File classificationFile : listOfClassificationFile )
			  {
				  if (classificationFile.isFile() && classificationFile.getName().endsWith(".txt"))
				  {
					  docCountPerClassification=docCountPerClassification+1;
					  fileContent= FileUtils.readFileToString(classificationFile);
					  //fileContent = fileContent.replaceAll("[^\\dA-Za-z ]", "");
					  wordList = fileContent.split("\\s+");
					  for(String tempWord : wordList)
					  {
						  if(vocabMap.get(tempWord.toLowerCase())!=null)
						  {
							  vocabMap.put(tempWord.toLowerCase(), vocabMap.get(tempWord.toLowerCase())+1) ;
						  }
						  else
						  {
							  vocabMap.put(tempWord.toLowerCase(),1L);
						  }
						  if(ApplicationData.totalVocabData.get(tempWord.toLowerCase())!=null)
						  {
							  ApplicationData.totalVocabData.put(tempWord.toLowerCase(), ApplicationData.totalVocabData.get(tempWord.toLowerCase())+1) ;
						  }
						  else
						  {
							  ApplicationData.totalVocabData.put(tempWord.toLowerCase(),1L);
						  }
					  }
				  }
			  }
			  ApplicationData.docCountByClassification.put(classificationName.toLowerCase(),docCountPerClassification);
			  ApplicationData.vocabCountByClassification.put(classificationName.toLowerCase(),Long.valueOf(vocabMap.size()));
			  dataBasedOnClassification.put(classificationName.toLowerCase(), vocabMap);
		  }
		}
		
		return dataBasedOnClassification;
	}
	
	public static Map<String,HashMap<String,Map<String,Long>>> getTestDataBasedOnClassification(String folderPath) throws IOException
	{
		Map<String,HashMap<String,Map<String,Long>>> dataBasedOnClassification= new HashMap<String,HashMap<String,Map<String,Long>>>();
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();
		//File file =null;
		String classificationName=null;
		File[] listOfClassificationFile=null;
		File classificationFileDirectory=null;
		 String fileContent=null;
		HashMap<String,Long> vocabMap = null;
	//	int docCountPerClassification=0;
		HashMap<String,Map<String,Long>> docMap=null;
		//int vocabCountPerClassification=0;
		String[] wordList=null;
		for (File file : listOfFiles) {
		  if (file.isDirectory()) 
		  {
			  classificationName = file.getName();
			 
			 //docCountPerClassification=0;
			 // vocabCountPerClassification=0;
			  listOfClassificationFile = file.listFiles();
			  docMap = new HashMap<String,Map<String,Long>>();
			  for(File classificationFile : listOfClassificationFile )
			  {
				  if (classificationFile.isFile() && classificationFile.getName().endsWith(".txt"))
				  {
					  vocabMap = new HashMap<String,Long>();
					  fileContent= FileUtils.readFileToString(classificationFile);
					 // fileContent = fileContent.replaceAll("[^\\dA-Za-z ]", "");
					  wordList = fileContent.split("\\s+");
					  for(String tempWord : wordList)
					  {
						  if(vocabMap.get(tempWord.toLowerCase())!=null)
						  {
							  vocabMap.put(tempWord.toLowerCase(), vocabMap.get(tempWord.toLowerCase())+1) ;
						  }
						  else
						  {
							  vocabMap.put(tempWord.toLowerCase(),1L);
						  }
					  }
					  docMap.put(classificationFile.getName(),vocabMap);
				  }
				  
			  }

			  dataBasedOnClassification.put(classificationName.toLowerCase(), docMap);
		  }
		}
		
		return dataBasedOnClassification;
	}
	
	public static TreeSet<String> getStopWordsSetWithTree(String fileName)
	{
		TreeSet<String> stopSet = new TreeSet<String>();
		 BufferedReader br = null;
		 String line = null;
		 String token = ",";
		 
			try {
		 
				Map<String, String> maps = new HashMap<String, String>();
		 
				br = new BufferedReader(new FileReader(fileName));
				ArrayList<String> row = null;
				String[] valueList=null;
				while ((line = br.readLine()) != null) {
		 
					// use comma as separator
					valueList = line.split(token);
					for(String value: valueList)
						stopSet.add(value.toLowerCase());
				}
		 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			return stopSet;
	}
	
	public static HashSet<String> getStopWordsSet(String fileName)
	{
		HashSet<String> stopSet = new HashSet<String>();
		 BufferedReader br = null;
		 String line = null;
		 String token = ",";
		 
			try {
		 
				Map<String, String> maps = new HashMap<String, String>();
		 
				br = new BufferedReader(new FileReader(fileName));
				ArrayList<String> row = null;
				String[] valueList=null;
				while ((line = br.readLine()) != null) {
		 
					// use comma as separator
					valueList = line.split(token);
					for(String value: valueList)
						stopSet.add(value.toLowerCase());
				}
		 
		 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			return stopSet;
	}
	
	public static void main(String args[]) throws IOException
	{
		/*Map<String,Map<String,Long>> testData = getDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\testFolder\\train");
		Map<String,HashMap<String,Map<String,Long>>> dataBasedOnClassification= getTestDataBasedOnClassification("C:\\Users\\SRPOP\\Desktop\\Gogate\\testFolder\\train");
		System.out.println("ApplicationData.docCountByClassification : " + ApplicationData.docCountByClassification);
		System.out.println("ApplicationData.vocabCountByClassification : " + ApplicationData.vocabCountByClassification);
		System.out.println(dataBasedOnClassification);
		HashSet<String> stopWords = getStopWordsSet("C:\\Users\\SRPOP\\Desktop\\Gogate\\HW2\\stopword.csv");
			System.out.println(stopWords.size());*/
		String reg = "^[a-zA-Z]*$";
		System.out.println("acs".matches(reg));
		
	}

}
