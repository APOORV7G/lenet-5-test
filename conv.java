import java.io.IOException;
import java.util.Random;


public class conv extends readImg {
	
    public static double[][][][] initializeWeights(int kernel, int inc, int outc) {
        Random random = new Random();
        double[][][][] weights = new double[kernel][kernel][inc][outc];
        double scaleFactor = Math.sqrt(2.0 / (kernel * kernel * inc));

        // Initialize weights with random values from a normal distribution
        for (int i = 0; i < kernel; i++) {
            for (int j = 0; j < kernel; j++) {
                for (int k = 0; k < inc; k++) {
                    for (int l = 0; l < outc; l++) {
                        weights[i][j][k][l] = random.nextGaussian() * scaleFactor;
                    }
                }
            }
        }

        return weights;
    }

    // Convolution function
    public static double[][][] convolution(double[][] input, double bias) {
    	int kernel = 3; // Example kernel size
        int inc = 2;    // Example number of input channels
        int outc = 2;
    	double [][][][] filters = initializeWeights(kernel,inc,outc);
        int inputHeight = input.length;
        int inputWidth = input[0].length;
 


        // Calculate output size
        int outputHeight = inputHeight - kernel + 1;
        int outputWidth = inputWidth - kernel + 1;

        // Create output matrix
        double[][][] output = new double[outc][outputHeight][outputWidth];

        // Convolution operation for each filter
        for (int f = 0; f < outc; f++) {
        	for(int inputf = 0; inputf<inc;inputf++) {
                for (int i = 0; i < outputHeight; i++) {
                    for (int j = 0; j < outputWidth; j++) {
                        double sum = 0;
                        for (int k = 0; k < kernel; k++) {
                            for (int l = 0; l < kernel; l++) {
                                // Ensure that the filter stays within the bounds of the input matrix
                                if (i + k < inputHeight && j + l < inputWidth) {
                                    sum += input[i + k][j + l] * filters[k][l][inputf][f];
                                }
                            }
                        }
                        output[f][i][j] = sum + bias;
                    }
                }
        	}

        }

        return output;
    }

    public static void main(String[] args) throws IOException {
//    	int kernel = 3; // Example kernel size
//        int inc = 2;    // Example number of input channels
//        int outc = 2;   // Example number of output channels
//
//        // Initialize weights for convolutional layer
//        double[][][][] weights = initializeWeights(kernel, inc, outc);
//
//     // Print the initialized weights (optional)
//        for (int l = 0; l < outc; l++) {
//            System.out.println("Weights for output channel " + l + ":");
//            for (int k = 0; k < inc; k++) {
//                System.out.println("Weights for input channel " + k + ":");
//                for (int i = 0; i < kernel; i++) {
//                    for (int j = 0; j < kernel; j++) {
//                        System.out.print(weights[i][j][k][l] + "\t");
//                    }
//                    System.out.println();
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }


    
        // Example usage
        // Define input, filters, and bias

        int[][] intInput = readImage("out/t10k-images.idx3-ubyte");
        double[][] input = new double[intInput.length][intInput[0].length];
        for (int i = 0; i < intInput.length; i++) {
            for (int j = 0; j < intInput[i].length; j++) {
                input[i][j] = (double) intInput[i][j];
            }
        }
        double bias = 0;

        // Perform convolution
        double[][][] output = convolution(input, bias);

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
