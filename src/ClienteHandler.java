/**
 * Created by andre on 20-10-2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;



class SendImage implements Runnable
{
    Socket socketToReceive;

    @Override
    public void run() {

    }
}

public class ClienteHandler implements Runnable
{

    private final Socket socket;
    String thisUserName;
    String thisIp;
    int thisPort;
    Dados dados;

    public ClienteHandler(Socket socket, Dados dados) {
        this.socket = socket;
        this.dados = dados;
    }

    public void run() {
        String clienteSentence = null;
        PrintWriter writeToCliente = null;

        BufferedReader inFromClient = null;
        String[] args = null;
        String[] getMessage;

        try {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writeToCliente = new PrintWriter(socket.getOutputStream());
            clienteSentence = inFromClient.readLine();

            //User login: userName:Password:ip:port

            args = clienteSentence.split(":");
            /*if(Dados.alreadyLogged(args[0]))
            {
                //Fazer todas as verificações
            }*/


            System.out.println("Received: " + clienteSentence + " ---from ip:" + socket.getInetAddress() + ":   port: " + socket.getPort());

            System.out.println("User: " + args[0] + " Password: " + args[1]);
            clienteSentence = null;
            clienteSentence = "AllUsers";
            for(NewUser u : ServerMotor.dados.allUsers)
            {
                clienteSentence += ":" + u.getUserName();
            }
            writeToCliente.println(clienteSentence);
            writeToCliente.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
        }


        //Falta verificar o id e a password

        assert args != null;
        ServerMotor.dados.allUsers.add(new NewUser(args[0], args[1], socket.getInetAddress().toString(), socket.getPort(), socket));
        thisUserName = args[0];
        thisIp = socket.getInetAddress().toString();
        thisPort = socket.getPort();


        for (NewUser a : ServerMotor.dados.allUsers) {
            try {
                writeToCliente = new PrintWriter(a.userSocket.getOutputStream());
                writeToCliente.println("NewUser:" + thisUserName);
                writeToCliente.flush();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        //dados.allUsers.add(new NewUser(args[0], args[1], socket.getInetAddress().toString(), socket.getPort()));


        do{
            try {
                inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writeToCliente = new PrintWriter(socket.getOutputStream());
                clienteSentence = inFromClient.readLine();
                if(clienteSentence == null){
                    ServerMotor.dados.removeUser(this.thisUserName);
                    break;
                }


                if(clienteSentence.equals("<IsAnyoneThere?>"))
                {
                    clienteSentence = "AllUsers";
                    for(NewUser u : ServerMotor.dados.allUsers) {
                        clienteSentence += ":" + u.getUserName();
                    }
                    writeToCliente.println(clienteSentence);
                    writeToCliente.flush();
                    continue;
                }

                /*As mensagens devem ser do tipo: M:Name:Message sending a message
                *                                 I:Name:image sending a image*/

                getMessage = clienteSentence.split(":");
                System.out.println("[INFO] - Sending to... " + getMessage[0]);

                System.out.println("[INFO] - RECEIVED: " + clienteSentence + " ---from ip:" + socket.getInetAddress() + ":   port: " + socket.getPort());

                boolean flag = true;
                if(!thisUserName.equals(getMessage[0])) { //Verifica se não está a enviar a mensagem a si mesmo
                    for (NewUser a : ServerMotor.dados.allUsers) {
                        if (a.getUserName().equals(getMessage[0])) {
                            writeToCliente = new PrintWriter(a.userSocket.getOutputStream());
                            writeToCliente.println(thisUserName+ ":" + getMessage[1]);
                            writeToCliente.flush();
                            flag = false;
                            break;
                        }
                    }

                    if (flag) {
                        writeToCliente = new PrintWriter(socket.getOutputStream());
                        writeToCliente.println("<CLIENT_NOT_FOUND>");
                        writeToCliente.flush();
                    } else {
                        System.out.println("[INFO] - Mensagem enviada com sucesso");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        while(!Objects.equals(clienteSentence, "Quit Connection") && !(clienteSentence == null));
        for(NewUser a : ServerMotor.dados.allUsers)
        {
            if(!a.userName.equals(this.thisUserName)) {
                try {
                    writeToCliente = new PrintWriter(a.userSocket.getOutputStream());
                    System.out.println("[INFO] - Vou avisar que o cliente [" + this.thisUserName +"] saiu" );
                    writeToCliente.println("<EXIT_CLIENTE>:" + this.thisUserName);
                    writeToCliente.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerMotor.dados.removeUser(this.thisUserName);
        System.out.println("[THREAD] - Exit: " + this.thisUserName);
    }
}
