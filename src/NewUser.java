import java.net.Socket;

/**
 * Created by andre on 21-10-2016.
 */
public class NewUser {
    String userName;
    String password;
    String ip;
    int clientPort;
    protected boolean userLogged;
    Socket userSocket;

    public boolean isUserLogged() {
        return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
        this.userLogged = userLogged;
    }

    public NewUser(String userName, String password, String ip, int clientPort, Socket socket) {
        this.userName = userName;
        this.password = password;
        this.ip = ip;
        this.clientPort = clientPort;
        userLogged = true;
        userSocket = socket;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
