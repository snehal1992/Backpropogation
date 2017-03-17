
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
	
	public static void main(String[] args) throws IOException{
		ArrayList<ArrayList<Double>> standardDataSet=new ArrayList<ArrayList<Double>>();
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/adult.txt"
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/adult1.csv"
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/housing.data"
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/housing1.csv"
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/iris.data"
		//"C:/Indrajit/Workspace/NeuralNet/src/default_package/iris1.csv"
		Instances instance = new Instances();
		String answer="";
		do{
			Scanner scan = new Scanner(System.in); //scanner for input
			System.out.print("Do you want to read dataset?(y/n): ");
			answer=scan.next();
		System.out.println();
			if(answer.equals("y")){
				instance.readData();
		}
		System.out.println();
	    }while(answer.equals("y"));
		Scanner scan = new Scanner(System.in); //scanner for input
		int inputNodes =0;
		int outputNodes =0;
		int hiddenNodes  =0 ;
		System.out.print("Enter the standardized dataset location:");
		String fileLocation=scan.nextLine();
		if(fileLocation.contains("adult1.csv")) {
			inputNodes =  14;
		    outputNodes = 1;
		    hiddenNodes = 3;
		} else if (fileLocation.contains("iris1.csv")) {
			inputNodes =  4;
		    outputNodes = 2;
		    hiddenNodes = 2;
		} else if(fileLocation.contains("housing.csv")) {
			inputNodes =  13;
		    outputNodes = 4;
		    hiddenNodes = 3;
		}
		standardDataSet=instance.getStandardizedData(fileLocation);
		//instance.printStandardizedData(standardDataSet);
		Backpropogation bp = new Backpropogation();
		bp.backpropogation(standardDataSet, 0.9 , inputNodes  , outputNodes ,  hiddenNodes);
	}
}
