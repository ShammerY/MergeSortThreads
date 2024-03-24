import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) throws IOException,  InterruptedException{
        //String serverHostname = args[0]; //127.0.0.1
        String serverHostname = "localhost"; //127.0.0.1

        Socket socket = new Socket(serverHostname, 6789);
        PrintWriter out;
        BufferedReader in;
        BufferedReader reader;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        reader = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(socket.getOutputStream(), true);
        //1 mensajeñb
        print("\n Ingrese los numeros separados por espacios: \n" +
                "EJEMPLO :\n" +
                "3 6 1 8 4 9 2");

        String message = reader.readLine();
        out.println(message);

        String respuestaServidor = in.readLine();
        System.out.println("Respuesta del servidor: " + respuestaServidor);
        //2 mensaje
        out.println("Estoy esperando...");

        //Arreglo Ordenado
        respuestaServidor = in.readLine();
        System.out.println("Lista Ordenada : " + respuestaServidor);

        socket.close();
    }
    private static void print(Object o){System.out.println(o);}
}