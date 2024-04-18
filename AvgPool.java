

public class AvgPool extends conv {
    private int poolSize;
    private int stride;

    public AvgPool(int poolSize, int stride) {
        this.poolSize = poolSize;
        this.stride = stride;
    }

    // Average pooling operation
    public double[][][] averagePooling(double[][][] input) {
        int depth = input.length;
        int inputHeight = input[0].length;
        int inputWidth = input[0][0].length;

        // Calculate output size
        int outputHeight = (inputHeight - poolSize) / stride + 1;
        int outputWidth = (inputWidth - poolSize) / stride + 1;

        // Create output matrix
        double[][][] output = new double[depth][outputHeight][outputWidth];

        // Average pooling operation
        for (int d = 0; d < depth; d++) {
            for (int i = 0; i < outputHeight; i++) {
                for (int j = 0; j < outputWidth; j++) {
                    // Calculate the average within the pooling region
                    double sum = 0;
                    for (int m = 0; m < poolSize; m++) {
                        for (int n = 0; n < poolSize; n++) {
                            sum += input[d][i * stride + m][j * stride + n];
                        }
                    }
                    output[d][i][j] = sum / (poolSize * poolSize);
                }
            }
        }

        return output;
    }

}
