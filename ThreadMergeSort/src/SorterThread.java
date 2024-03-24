import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SorterThread implements Runnable{
    private Socket clientSocket;
    private double[] numbers;
    private double[] resultArray;
    public SorterThread(double[] nums,Socket socket){
        this.numbers = nums;
        this.clientSocket = socket;
    }
    public double[] getSortedNumbers(){
        return this.resultArray;
    }
    @Override
    public void run() {
        System.out.println("Inicia el Ordenamiento");
        resultArray = mergeSort(numbers);
        try {
            sendMessageToClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessageToClient() throws IOException {
        PrintWriter out;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        String msj = "";
        for(int i=0; i<resultArray.length;i++){
            msj += resultArray[i]+" ";
        }
        out.println(msj);
        clientSocket.close();
    }
    public static double[] mergeSort(double[] arr) {
        if (arr.length <= 1) {
            return null;
        }

        int middle = arr.length / 2;
        double[] left = new double[middle];
        double[] right = new double[arr.length - middle];

        // Copiar elementos a los subarreglos izquierdo y derecho
        for (int i = 0; i < middle; i++) {
            left[i] = arr[i];
        }
        for (int i = middle; i < arr.length; i++) {
            right[i - middle] = arr[i];
        }

        mergeSort(left);
        mergeSort(right);
        double[] result = new double[arr.length];
        result = merge(arr, left, right);
        return result;
    }
    public static double[] merge(double[] arr, double[] left, double[] right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
        return arr;
    }
}
