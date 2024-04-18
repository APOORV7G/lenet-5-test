import java.io.IOException;
import java.util.Random;


public class conv extends readImg {
	
	public static double[][][][] initializeWeights(int kernel, int outc) {
	    Random random = new Random();
	    double[][][][] weights = new double[kernel][kernel][1][outc]; // Assuming only one input channel for grayscale images
	    double scaleFactor = Math.sqrt(2.0 / (kernel * kernel));

	    // Initialize weights with random values from a normal distribution
	    for (int i = 0; i < kernel; i++) {
	        for (int j = 0; j < kernel; j++) {
	            for (int l = 0; l < outc; l++) {
	                weights[i][j][0][l] = random.nextGaussian() * scaleFactor;
	            }
	        }
	    }

	    return weights;
	}


    // Convolution function
	public static double[][][] convolution(double[][][] input, double bias) {
	    int kernel = 5; // Example kernel size
	    int outc = 6;   // Number of output channels (or feature maps)
	    double[][][][] filters = initializeWeights(kernel, outc); // Initialize weights for each output channel
	    int inputHeight = input[0].length;
	    int inputWidth = input[0][0].length;

	    // Calculate output size (assuming same padding and stride)
	    int outputHeight = inputHeight - kernel + 1;
	    int outputWidth = inputWidth - kernel + 1;

	    // Create output matrix
	    double[][][] output = new double[outc][outputHeight][outputWidth];

	    // Convolution operation for each filter (output channel)
	    for (int f = 0; f < outc; f++) {
	        for (int i = 0; i < outputHeight; i++) {
	            for (int j = 0; j < outputWidth; j++) {
	                double sum = 0;
	                for (int k = 0; k < kernel; k++) {
	                    for (int l = 0; l < kernel; l++) {
	                        // Ensure that the filter stays within the bounds of the input matrix
	                        if (i + k < inputHeight && j + l < inputWidth) {
	                        	//number of feature maps 
	                        	for(int m =0; m<input.length;m++) {
	                        		// Perform convolution operation by element-wise multiplication and summation
		                            sum += input[m][i + k][j + l] * filters[k][l][0][f];
	                        	}
	                            
	                        }
	                    }
	                }
	                // Add bias and assign the result to the corresponding output position
	                output[f][i][j] = sum + bias;
	            }
	        }
	    }

	    return output;
	}


    public static void main(String[] args) throws IOException {
        // Define input, filters, and bias
    	int[][] intInput = readImage("out/t10k-images.idx3-ubyte");
    	double[][][] input = new double[1][intInput.length][intInput[0].length]; // Initialize input with appropriate dimensions
    	for (int i = 0; i < intInput.length; i++) {
    	    for (int j = 0; j < intInput[i].length; j++) {
    	        input[0][i][j] = (double) intInput[i][j];
    	    }
    	}
    	

    	double bias = 0;

    	// Perform first convolution
    	double[][][] output1 = convolution(input, bias);

    	// Perform second convolution on the output of the first convolution
    	double[][][] output = convolution(output1, bias);


        // Print output
        for (int f = 0; f < output.length; f++) {
            System.out.println("Output for filter " + (f + 1) + ":");
            for (int i = 0; i < output[f].length; i++) {
                for (int j = 0; j < output[f][0].length; j++) {
                    System.out.print(output[f][i][j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
