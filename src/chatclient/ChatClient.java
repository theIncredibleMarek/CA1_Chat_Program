package chatclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;

public class ChatClient extends Thread
{

    private Socket socket;
    private int port;
    private String username;
    private InetAddress serverAddress;
    private Scanner input;
    private PrintWriter output;
    private List<MessageListener> listeners = new ArrayList();
    private static Properties properties;

    public ChatClient()
    {
        properties = Utils.initProperties("server.properties");
    }

    public void connect(String address, int port, String username) throws UnknownHostException, IOException
    {
        this.username = username;
        this.port = port;
        serverAddress = InetAddress.getByName(address);
        socket = new Socket(serverAddress, port);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);  //Set to true, to get auto flush behaviour
        send(ProtocolStrings.CONNECT + ProtocolStrings.DIVIDER + username);
        if (getState().toString().equals("RUNNABLE") || getState().toString().equals("NEW"))
        {
            start();
        }
    }

    public void send(String msg)
    {
        output.println(msg);
    }

    public void closeTheConnection() throws IOException
    {
        output.println(ProtocolStrings.STOP);
        stop();
    }

    @Override
    public void run()
    {
        String msg = input.nextLine();
        while (!msg.equals(ProtocolStrings.STOP))
        {
            notifyListeners(msg);
            msg = input.nextLine();
        }
        notifyListeners(msg);

        try
        {
            socket.close();
        } catch (IOException ex)
        {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerMessageListener(MessageListener l)
    {
        listeners.add(l);
    }

    public void unRegisterMessageListener(MessageListener l) throws IOException
    {
        listeners.remove(l);
    }

    private void notifyListeners(String msg)
    {
        for (MessageListener l : listeners)
        {
            l.messageArrived(msg);
        }
    }

    public String getDefaultIP()
    {
        return properties.getProperty("serverHostname");
    }

    public int getDefaultPort()
    {
        return Integer.parseInt(properties.getProperty("chatProgramPort"));
    }
}
