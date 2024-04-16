import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class readImg {
    public static int [][] readImage (String filePath) throws IOException {
//        String filePath = "out/t10k-images.idx3-ubyte";

        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            // Read magic number
            int magicNumber = dis.readInt();
            if (magicNumber != 2051) {
//                System.err.println("Invalid magic number, not a MNIST image file");
            }

            // Read number of images
            int numImages = dis.readInt();

            // Read number of rows
            int numRows = dis.readInt();

            // Read number of columns
            int numCols = dis.readInt();
            int[][] arr= new int[numRows][numCols];
//            System.out.printf("Magic number: %d, Number of images: %d, Image dimensions: %d x %d\n", magicNumber, numImages, numRows, numCols);

            // Read image data
            for (int i = 0; i < 1; i++) {
//                System.out.println("\nReading image #" + (i + 1));
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols; col++) {
                        arr[row][col] = dis.readUnsignedByte();
                        //System.out.print( + " ");
                    }
                    System.out.println();
                }
            }
            return arr;
        }
    }
}