package chatserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;

public class ChatServer
{

    private static ChatServer instance;
    private static boolean keepRunning = true;
    private static ServerSocket serverSocket;
    private static final Properties properties = Utils.initProperties("server.properties");
    private static HashMap<String, ClientHandler> clientHandlers;

    public static ChatServer getInstance()
    {
        if (instance == null)
        {
            instance = new ChatServer();
        }
        return instance;
    }

    public static void removeHandler(ClientHandler ch)
    {
        clientHandlers.remove(ch.getClientName());
    }

    public static void addHandler(ClientHandler ch)
    {
        clientHandlers.put(ch.getClientName(), ch);
        System.out.println(getNbOfConnectedUsers());
    }

    public static void stopServer()
    {
        keepRunning = false;
    }

    public static void send(String msg, String[] recipients)
    {
        String[] msgParts = msg.split("\\#");
        //1. Find out what kind of message the server is sending back and build the text 
        String command = msgParts[0];
        switch (command)
        {
            case ProtocolStrings.ONLINE:
                //write back the list of users send("ONLINE#Marek,Smara","*")
                //server should only send back the list of users when somebody is online
                if (clientHandlers.size() > 0)
                {
                    //In case that the client wants to connect, the server must send back ONLINE and list of connected users
                    msg = msg + ProtocolStrings.DIVIDER;
                    for (Map.Entry<String, ClientHandler> ch : clientHandlers.entrySet())
                    {
                        String name = ch.getKey();
                        msg = msg + name + ",";
                    }
                    //At the end the last person will have a comma after his name so we just have to remove it
                    msg = msg.substring(0, msg.length() - 1);
                    Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Server sends: " + msg);
                }
                break;
            case ProtocolStrings.MESSAGE:
                // in this case the message is built in the client handler so no additions from the server
                Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Server sends: " + msg);
                break;
            case ProtocolStrings.CLOSE:
                //send a close message back 
                msg = msg + ProtocolStrings.DIVIDER;
                Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Server sends: " + msg);
                break;
            default:
                break;
        }

        //2.notify the recipients of the message
        boolean msgToEverybody = false;
        for (String s : recipients)
        {
            if (s.equals("*"))
            {
                msgToEverybody = true;
                break;
            }
        }
        if (msgToEverybody)
        {
            for (Map.Entry<String, ClientHandler> ch : clientHandlers.entrySet())
            {
                if (command.equals(ProtocolStrings.MESSAGE))
                {
                    if (!ch.getKey().equals(msgParts[1]))
                    {
                        ch.getValue().send(msg);
                    }
                } else
                {
                    ch.getValue().send(msg);
                }
            }
        } else
        {
            for (String s : recipients)
            {
                for (Map.Entry<String, ClientHandler> ch : clientHandlers.entrySet())
                {
                    if (msgParts.length > 1)
                    {
                        //send the message but not to the sender itself
                        if (ch.getKey().equals(s) && !ch.getKey().equals(msgParts[1]))
                        {
                            ch.getValue().send(msg);
                        }
                    } else
                    {
                        //in case that the message is CLOSE it will not have the receivers
                        if (ch.getKey().equals(s))
                        {
                            ch.getValue().send(msg);
                        }
                    }
                }
            }
        }
    }

    public static int getNbOfConnectedUsers()
    {
        return clientHandlers.size();
    }

    public static String[] getConnectedUsers()
    {
        String[] connectedUsers = new String[clientHandlers.size()];
        int i = 0;
        for (Map.Entry<String, ClientHandler> ch : clientHandlers.entrySet())
        {
            connectedUsers[i] = ch.getKey();
            i++;
        }
        return connectedUsers;
    }

    public void start()
    {
        int port = Integer.parseInt(properties.getProperty("chatProgramPort"));
        String ip = properties.getProperty("serverIp");
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile, ChatServer.class.getName());
        Logger logger = Utils.getLogger(logFile, ChatServer.class.getName());
        Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Chat server started,\n"
                + "ip: " + ip + ", listening on port: " + port + ".");
        clientHandlers = new HashMap<String, ClientHandler>()
        {
        };
        try
        {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            do
            {
                Socket socket = serverSocket.accept(); //Important Blocking call
                Logger.getLogger(ChatServer.class.getName()).log(Level.INFO, "Connected to a client");
                ClientHandler ch = new ClientHandler(socket);
//                ch.setClientName("TestShit");
//                addHandler(ch);
                ch.start();
            } while (keepRunning);
        } catch (IOException ex)
        {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            Utils.closeLogger(ChatServer.class.getName());
        }
    }
}
