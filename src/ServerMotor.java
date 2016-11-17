import java.net.*;


/**
 * Created by andre on 20-10-2016.
 */

//Message type should 127.0.0.1 5001 "Ola mundo"

public class ServerMotor {
    final int SERVERPORT = 6001;
    public static Dados dados = new Dados();


    public void motorServidor() throws Exception
    {
        ServerSocket welcomeSocket = new ServerSocket(SERVERPORT);

        while(true)
        {
            Socket connectionSocket = welcomeSocket.accept();
            Runnable runnable = new ClienteHandler(connectionSocket, dados);
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }
}
