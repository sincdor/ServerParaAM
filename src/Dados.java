import java.util.ArrayList;

/**
 * Created by andre on 21-10-2016.
 */
public class Dados{
    public static ArrayList<NewUser> allUsers;

    public Dados() {
        allUsers = new ArrayList<>();
    }

    public static boolean alreadyLogged(String userName) {
        for(NewUser a : allUsers)
            if(a.userName.equals(userName) && a.isUserLogged())
                return true;
        return false;
    }

    public static int findIndexUser(String userName) {
        for(int i = 0; i < allUsers.size(); i++)
            if(allUsers.get(i).userName.equals(userName))
                return i;
        return -1;
    }

    public static void removeUser(String userName)
    {
        int index;
        if((index = findIndexUser(userName)) >= 0)
            allUsers.remove(index);
    }


    //Caso existe algum user com o
    public boolean existUser(String nome)
    {
        for(NewUser u : allUsers){
            if(u.getUserName().equals(nome))
                return true;
        }
        return false;
    }


}
