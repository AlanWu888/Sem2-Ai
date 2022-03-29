package skelton_ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
	static int NUMB_OF_EPOCHS = 9000; 
	public static void main(String[] args) throws IOException {
		NeuralNetwork neuralNetwork = new NeuralNetwork(4);	// construct a network based on the length of our data set
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		List<Double> rmse = new ArrayList<Double>();
		double[] result = new double[data_sets.TRAINING_DATA.length];
		
		boolean flag = true;
		while (flag) {
			System.out.println("> What do you want to do (run, train, exit) ?");
			String command = bufferedReader.readLine();
			switch (command) {
				case "run":
					// double[] result = new double[data_sets.TRAINING_DATA.length];
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
																.fProp(data_sets.TRAINING_DATA[i][0])
																.getLayers()[2].getNeurons()[0]
																.getOutput());
					
					excel_handler.writeResults(result);	// write to the excel file called "results"
					System.out.println("[results ready]");
					break;
					
				case "run_validation":
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
						.fProp(data_sets.TRAINING_DATA[i][0])
						.getLayers()[2].getNeurons()[0]
						.getOutput());
					
					excel_handler.writeValidation(runners.validate(result));	// write to the excel file called "validation"
					System.out.println("[results ready]");
					break;
					
				case "run_test":
					IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> result[i] = neuralNetwork
					.fProp(data_sets.TRAINING_DATA[i][0])
					.getLayers()[2].getNeurons()[0]
					.getOutput());
				
					excel_handler.writeTest(runners.test(result));	// write to the excel file called "test"
					System.out.println("[results ready]");
					break;
					
				case "train":
					double[] result_rmse = new double[data_sets.TRAINING_DATA.length];
					IntStream.range(0,  NUMB_OF_EPOCHS).forEach(i -> {
						System.out.print("[epoch "+i+"]		");
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(j -> neuralNetwork
																.fProp(data_sets.TRAINING_DATA[j][0])
																.backpropError(data_sets.TRAINING_DATA[j][1][0]));
						IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(k -> result_rmse[k] = neuralNetwork
								.getLayers()[2].getNeurons()[0]
								.getOutput());
						System.out.println("Error: " + NeuralNetwork.calcRMSE(result_rmse));
						rmse.add(NeuralNetwork.calcRMSE(result_rmse));
					});
					excel_handler.writeErrors(rmse);	
					System.out.println("[done training]");
					break;
					
				case "exit":
					flag = false;
					break;
			}
		}
		System.exit(0);
	}
	
	/*
	static void printResult(double[] result) {
		System.out.println("   Date    |    Flow    | Target Result |  Result    ");
		System.out.println("-------------------------------------------------------------");
		IntStream.range(0, data_sets.TRAINING_DATA.length).forEach(i -> {
			IntStream.range(0, data_sets.TRAINING_DATA[0][0].length).forEach(j -> System.out.print("    "+ data_sets.TRAINING_DATA[i][0][j] + "      |  "));
			System.out.print("    "+ data_sets.TRAINING_DATA[i][1][0] + "      |  " + String.format("%.5f", result[i]) + "  \n");
		});
	}
	*/
}

