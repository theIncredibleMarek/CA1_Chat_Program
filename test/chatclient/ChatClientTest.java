/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import chatserver.ChatServer;
import java.io.IOException;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Utils;

/**
 *
 * @author Marek FURAK
 */
public class ChatClientTest
{

    ChatClient Marek;
    ChatClient Cristi;
    ChatClient Smara;
    long threadSleep = 1500;
    private static final Properties properties = Utils.initProperties("server.properties");
    int port = Integer.parseInt(properties.getProperty("chatProgramPort"));
    String ip = properties.getProperty("serverIp");
    String message, message2;
    Thread t;

    public ChatClientTest()
    {
        Marek = new ChatClient();
        Cristi = new ChatClient();
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp() throws IOException, InterruptedException
    {
        final ChatServer server = ChatServer.getInstance();
        t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                server.start();
            }
        });
        t.start();
        Marek.connect(ip, port, "Marek");
        Thread.sleep(threadSleep);
        Cristi.connect(ip, port, "Cristi");
        Thread.sleep(threadSleep);
        System.out.println("Set up - DONE");

    }

    @After
    public void tearDown() throws IOException, InterruptedException
    {
        
        Marek.closeTheConnection();
        Thread.sleep(threadSleep);
        Cristi.closeTheConnection();
        Thread.sleep(threadSleep);
        ChatServer.stopServer();
        t.stop();
        Thread.sleep(threadSleep);
        System.out.println("Tear down - DONE");
    }

    /**
     * Test of connect method, of class ChatClient.
     */
    @Test
    public void testConnect() throws Exception
    {
        System.out.println("Test CONNECT");
        Smara = new ChatClient();

        Smara.registerMessageListener(new MessageListener()
        {
            @Override
            public void messageArrived(String data)
            {
                message = data;
                System.out.println("Received message " + message);
            }
        });
        Smara.connect(ip, port, "Smara");
        Thread.sleep(100);
        assertEquals("ONLINE#Smara,Cristi,Marek", message);
        Smara.closeTheConnection();
        System.out.println("Test CONNECT - DONE");
    }

    /**
     * Test of send method, of class ChatClient.
     */
    @Test
    public void testSendOne() throws InterruptedException
    {
        System.out.println("Test Send ONE");
        Cristi.registerMessageListener(new MessageListener()
        {

            @Override
            public void messageArrived(String data)
            {
                message = data;
                System.out.println("Received message " + message);
            }
        });

        Marek.send("SEND#Cristi#Hey");
        Thread.sleep(threadSleep);
        assertEquals("MESSAGE#Marek#Hey", message);
        System.out.println("Test Send ONE - DONE");
    }

    /**
     * Test of closeTheConnection method, of class ChatClient.
     */
    @Test
    public void testSendAll() throws Exception
    {
        System.out.println("Test Send ALL");

//        Cristi.registerMessageListener(new MessageListener()
//        {
//            @Override
//            public void messageArrived(String data)
//            {
//                message = data;
//                System.out.println("Cristi received message " + message);
//            }
//        });
//
//        Smara = new ChatClient();
//        Smara.registerMessageListener(new MessageListener()
//        {
//            @Override
//            public void messageArrived(String data)
//            {
//                message2 = data;
//                System.out.println("Smara received message " + message2);
//            }
//        });
//        Smara.connect(ip, port, "Smara");
//        Thread.sleep(100);
//        Marek.send("SEND#*#Hey");
//        Thread.sleep(threadSleep);
//        assertEquals("MESSAGE#Marek#Hey", message);
//        assertEquals("MESSAGE#Marek#Hey", message2);
//        Smara.closeTheConnection();
        System.out.println("Test Send ALL - DONE");
    }

    /**
     * Test of run method, of class ChatClient.
     */
    @Test
    public void testDisconnect() throws IOException, InterruptedException
    {
        System.out.println("Test DISCONNECT");
        Smara = new ChatClient();

        Smara.registerMessageListener(new MessageListener()
        {
            @Override
            public void messageArrived(String data)
            {
                message = data;
                System.out.println("Received message " + message);
            }
        });
        Smara.connect(ip, port, "Smara");
        Smara.closeTheConnection();
        Thread.sleep(threadSleep);
        assertEquals("CLOSE#", message);
        System.out.println("Test DISCONNECT - DONE");
    }

}
