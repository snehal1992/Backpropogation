

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Instances {
	public void readData() throws IOException {
		
		String[] row;
		ArrayList<Object> rowAttributes;
		ArrayList<Object> rowTempAttributes;
		ArrayList<ArrayList<Object>> attributes = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> standardizedAttributes = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> standardizedTempAttributes = new ArrayList<ArrayList<Object>>();
		
		Scanner sc=new Scanner(System.in);
		String ipFileName,opFileName,intermediateOutput;
		System.out.print("Enter the input file location:");
		ipFileName=sc.nextLine();
		System.out.println();
		System.out.print("Enter the output file location:");
		opFileName=sc.nextLine();
		File inputSet=new File(ipFileName);
		sc = new Scanner(inputSet);
		
		/*Read adult dataset*/
		if(ipFileName.contains("adult"))
		{
			for(int i=0;sc.hasNext();i++){
				rowAttributes=new ArrayList<Object>();
				String line=sc.nextLine();
				if(!line.contains("?")){
					row = line.split(",\\s+");
					for(int j=0;j<row.length;j++){
						if(isInteger(row[j]))
							rowAttributes.add(Integer.parseInt(row[j]));
						else if(isDouble(row[j]))
							rowAttributes.add(Double.parseDouble(row[j]));
						else 
							rowAttributes.add(row[j]);
					}
					attributes.add(rowAttributes);
				}
			}
		}
		
		/*Read iris dataset*/
		if(ipFileName.contains("iris"))
		{
			for(int i=0;sc.hasNext();i++){
				rowAttributes=new ArrayList<Object>();
				String line=sc.nextLine();
				if(!line.contains("?")){
					row = line.split(",");
					for(int j=0;j<row.length;j++){
						if(isInteger(row[j]))
							rowAttributes.add(Integer.parseInt(row[j]));
						else if(isDouble(row[j]))
							rowAttributes.add(Double.parseDouble(row[j]));
						else 
							rowAttributes.add(row[j]);
					}
					attributes.add(rowAttributes);
				}
			}
		}
		
		/*Read housing dataset*/
		if(ipFileName.contains("housing"))
		{
			for(int i=0;sc.hasNext();i++){
				rowAttributes=new ArrayList<Object>();
				String line=sc.nextLine();
				if(!line.contains("?")){
					if(!(line.charAt(0)==' ')){
						row = line.split("\\s+");
						if(isInteger(row[0]))
							rowAttributes.add(Integer.parseInt(row[0]));
						else if(isDouble(row[0]))
							rowAttributes.add(Double.parseDouble(row[0]));
						else 
							rowAttributes.add(row[0]);
						for(int j=1;j<row.length;j++){
							if(isInteger(row[j]))
								rowAttributes.add(Integer.parseInt(row[j]));
							else if(isDouble(row[j]))
								rowAttributes.add(Double.parseDouble(row[j]));
							else 
								rowAttributes.add(row[j]);
						}
						attributes.add(rowAttributes);
					}else{
						row = line.split("\\s+");
						for(int j=1;j<row.length;j++){
							if(isInteger(row[j]))
								rowAttributes.add(Integer.parseInt(row[j]));
							else if(isDouble(row[j]))
								rowAttributes.add(Double.parseDouble(row[j]));
							else 
								rowAttributes.add(row[j]);
						}
						attributes.add(rowAttributes);
					}
				}
			}
		}
		
		//Standardized features
		standardizedAttributes=standardizeData(attributes);
		
		//For converting housing output feature based on numeric categories
		if(ipFileName.contains("housing")){
			standardizedTempAttributes=getAttributeTranspose(standardizedAttributes);
			int lastCol=standardizedTempAttributes.size()-1;
			rowTempAttributes=standardizedTempAttributes.get(lastCol);
			standardizedTempAttributes.set(lastCol, changeCategory(rowTempAttributes));
			standardizedAttributes=getAttributeTranspose(standardizedTempAttributes);
		}

		//Writing the final output
		File outputSet=new File(opFileName);
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputSet));
		for(int i=0;i<standardizedAttributes.size();i++){
			writer.write(String.valueOf(standardizedAttributes.get(i).get(0)));
			for (int j = 1; j < standardizedAttributes.get(i).size(); j++) {
				writer.write(","+String.valueOf(standardizedAttributes.get(i).get(j)));
			}
			writer.newLine();
		}
		writer.flush();
		writer.close();
	}	
	
	//For converting housing output feature based on numeric categories
	private ArrayList<Object> changeCategory(ArrayList<Object> rowTempAttributes) {
		for(int i=0;i<rowTempAttributes.size();i++){
			double temp=(double) rowTempAttributes.get(i);
			if(temp >= - 2.0 && temp <= -1.5)
				rowTempAttributes.set(i, 0);
			else if(temp > -1.5  && temp <= -1.0)
				rowTempAttributes.set(i, 1);
			else if(temp > -1.0 && temp <= -0.5)
				rowTempAttributes.set(i, 2);
			else if(temp > -0.5 && temp <= 0.0)
				rowTempAttributes.set(i, 3);
			else if(temp > 0.0 && temp <= 0.5)
				rowTempAttributes.set(i, 4);
			else if(temp > 0.5 && temp <= 1.0)
				rowTempAttributes.set(i, 5);
			else if(temp > 1.0 && temp <= 1.5)
				rowTempAttributes.set(i, 6);
			else if(temp > 1.5 && temp <= 2.0)
				rowTempAttributes.set(i, 7);
			else if(temp > 2.0 && temp <= 2.5)
				rowTempAttributes.set(i, 8);
			else
				rowTempAttributes.set(i, 9);
		}
		return rowTempAttributes;
	}
	
	public ArrayList<ArrayList<Object>> getAttributeTranspose(ArrayList<ArrayList<Object>> attributes){
		ArrayList<ArrayList<Object>> transpose = new ArrayList<ArrayList<Object>>();
		int N = attributes.get(0).size();
		for (int i = 0; i < N; i++) {
			ArrayList<Object> col = new ArrayList<Object>();
			for (ArrayList<Object> row : attributes) {
				col.add(row.get(i));
			}
			transpose.add(col);
		}
		return transpose;
	}	
	
	//Get the standardized data
	public ArrayList<ArrayList<Object>> standardizeData(ArrayList<ArrayList<Object>> attributes){
		ArrayList<ArrayList<Object>> transpose = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> attributesNew = new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		transpose = getAttributeTranspose(attributes);
		for(int i=0;i<transpose.size();i++){
			ArrayList<Object> row= new ArrayList<Object>();
			if(transpose.get(i).get(0).getClass().equals(Integer.class)){
				row=standardizeInteger(transpose.get(i));
				attributesNew.add(row);
			}
			else if(transpose.get(i).get(0).getClass().equals(Double.class)){
				row=standardizeDouble(transpose.get(i));
				attributesNew.add(row);				
			}
			else{
				row=convertString(transpose.get(i));
				attributesNew.add(row);
			} 

		}
		result=getAttributeTranspose(attributesNew);
		return result;
	}
	
	//Standardize Integer columns
	private ArrayList<Object> standardizeInteger(ArrayList<Object> intList) {
		int num=intList.size();
		double sum=0;
		double temp0=0;
		double temp1=0;
		double sd=0;
		double mean=0;
		for(int i=0;i<intList.size();i++){
			sum += (double)((Integer) intList.get(i));
		}
		mean = sum/num;
		for(int i=0;i<intList.size();i++){
			temp0 += Math.pow((double)((Integer) intList.get(i))-mean,2);
		}		
		sd=Math.pow(temp0/num, 0.5);
		for(int i=0;i<intList.size();i++){
			temp1=((double)((Integer) intList.get(i))-mean)/sd;
			intList.set(i, (Object)(temp1));
		}		
		return intList;
	}
	
	//Standardize Double columns
	private ArrayList<Object> standardizeDouble(ArrayList<Object> doubleList) {
		int num=doubleList.size();
		double sum=0;
		double temp0=0;
		double temp1=0;
		double sd=0;
		double mean=0;
		for(int i=0;i<doubleList.size();i++){
			sum += (double)doubleList.get(i);
		}
		mean = sum/num;
		for(int i=0;i<doubleList.size();i++){
			temp0 += Math.pow((double)doubleList.get(i)-mean,2);
		}		
		sd=Math.pow(temp0/num, 0.5);
		for(int i=0;i<doubleList.size();i++){
			temp1=((double)doubleList.get(i)-mean)/sd;
			doubleList.set(i, (Object)(temp1));
		}		
		return doubleList;
	}
	
	//Converting String columns to numeric categorical columns
	private ArrayList<Object> convertString(ArrayList<Object> stringList) {
		int category=0;
		Set<String> uniqueSet = new LinkedHashSet<>();
		ArrayList<Object> result= new ArrayList<Object>();
		for(int i=0;i<stringList.size();i++){
			uniqueSet.add((String)stringList.get(i));
		}
		HashMap<Integer, String> uniqueMap= new HashMap<Integer, String>();
		for (String element : uniqueSet) {
			category++;
			uniqueMap.put(category, element);
		}
		for(int i=0;i<stringList.size();i++){
			for (Map.Entry<Integer, String> entry : uniqueMap.entrySet()){
				if(entry.getValue().equals((String)stringList.get(i))){
					int tempCat=entry.getKey()-1;
					result.add(tempCat);
				}
			}
		}		
		return result;
	}
	
	void printData(ArrayList<ArrayList<Object>> attributes){
		for(int i=0;i<attributes.size();i++){
			for (int j = 0; j < attributes.get(i).size(); j++) {
				System.out.print(attributes.get(i).get(j)+" ");	
			}
			System.out.println();
		}
	}
	public boolean isInteger( String input )
	{
		try
		{
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
	public boolean isDouble( String input )
	{
		try
		{
			Double.parseDouble( input );
			return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}

	public ArrayList<ArrayList<Double>> getStandardizedData(String fileLocation) throws FileNotFoundException {
		String[] row;
		ArrayList<ArrayList<Double>> result=new ArrayList<ArrayList<Double>>();
		ArrayList<Double> rowAttributes;
		File inputSet=new File(fileLocation);
		Scanner sc = new Scanner(inputSet);
		for(int i=0;sc.hasNext();i++){
			rowAttributes=new ArrayList<Double>();
			String line=sc.nextLine();
			if(!line.contains("?")){
				row = line.split(",");
				for(int j=0;j<row.length;j++){
						rowAttributes.add(Double.parseDouble(row[j]));
				}
				result.add(rowAttributes);
			}
		}
		return result;
	}
	void printStandardizedData(ArrayList<ArrayList<Double>> attributes){
		for(int i=0;i<attributes.size();i++){
			for (int j = 0; j < attributes.get(i).size(); j++) {
				System.out.print(attributes.get(i).get(j)+" ");	
			}
			System.out.println();
		}
	}

}
