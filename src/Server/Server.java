package Server;

import java.io.*;
import java.net.*;


public class Server
{

    static boolean doesStringContainChar(String str, char c)
    {
        char[] arr = str.toCharArray();
        for(int i = 0; i < str.length(); i++) if(c == arr[i]) return true;
        return false;
    }

    public static void main(String[] arg)
    {
        String vowels = "eyuioa";
        String nonVowels = "qwrtpsdfghjklzxcvbnm";
        ServerSocket serverSocket = null;
        Socket clientAccepted     = null;
        ObjectInputStream  sois   = null;
        ObjectOutputStream soos   = null;
        try
        {
            serverSocket = new ServerSocket(2525);
            while(true) {
                clientAccepted = serverSocket.accept();
                while (clientAccepted.isConnected()) {
                    sois = new ObjectInputStream(clientAccepted.getInputStream());
                    soos = new ObjectOutputStream(clientAccepted.getOutputStream());
                    String clientMessageRecieved = (String) sois.readObject();
                    if(clientMessageRecieved.equals("EXIT")) break;
                    char[] arr;
                    int vowelsCount;
                    int nonVowelsCount;
                    while (true) {
                        vowelsCount = 0;
                        nonVowelsCount = 0;
                        System.out.println("Server recieved: '" + clientMessageRecieved + "'");
                        arr = clientMessageRecieved.toLowerCase().toCharArray();
                        for (int i = 0; i < clientMessageRecieved.length(); i++) {
                            if (doesStringContainChar(vowels, arr[i])) vowelsCount++;
                            else if (doesStringContainChar(nonVowels, arr[i])) nonVowelsCount++;
                        }
                        clientMessageRecieved = "Vowels: " + vowelsCount + ", consonants: " + nonVowelsCount;
                        soos.writeObject(clientMessageRecieved);
                        clientMessageRecieved = (String) sois.readObject();
                    }
                }
            }
        }
        catch(Exception e)
        {

        }
        finally
        {
            try
            {
                sois.close();
                soos.close();
                clientAccepted.close();
                serverSocket.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
