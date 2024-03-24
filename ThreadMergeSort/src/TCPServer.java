import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private static ExecutorService threadPool;
    public static void main(String[] args)  throws IOException,InterruptedException  {
        ServerSocket serverSocket = new ServerSocket(6789);
        System.out.println("Servidor iniciado.. ");
        threadPool = Executors.newFixedThreadPool(10);

        while(true){
            System.out.println("Esperando conexión...");
            final Socket sc = serverSocket.accept(); //aceptar cliente
            new Thread(()->{
                try {
                    PrintWriter out;
                    BufferedReader in;
                    out = new PrintWriter(sc.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(sc.getInputStream()));

                    String mensajeCliente = in.readLine();
                    String listaNum = mensajeCliente;
                    System.out.println("Lista de numeros recibido: " + mensajeCliente);
                    // le decimos al cliente que espre
                    out.println(" Espere un momento....");
                    //recibimos la resp del cliente

                    mensajeCliente = in.readLine();
                    System.out.println(mensajeCliente);
                    System.out.println("Creando Threads....");
                    // ponemos a esperar al cliente

                    String[] list = listaNum.split(" ");
                    double[] numberList = new double[list.length];

                    for(int i=0;i<list.length;i++){
                        numberList[i] = Double.parseDouble(list[i]);
                        System.out.println("Numero : "+numberList[i]);
                    }
                    System.out.println("Creando Threads....");
                    SorterThread sorter = new SorterThread(numberList,sc);
                    Thread thread = new Thread(sorter);
                    thread.start();
                    threadPool.execute(thread);
                    Thread.sleep(6000);
                    // le respondemos al cliente
                    out.println(" Lista ha sido ordenada....");

                    sc.close();

                } catch (Exception e) {
                    // TODO: handle exception
                }

            }).start();
        }

        //serverSocket.close();

    }
}