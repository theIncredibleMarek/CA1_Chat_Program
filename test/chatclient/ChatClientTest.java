/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Marek FURAK
 */
public class ChatClientTest
{
    
    public ChatClientTest()
    {
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
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of connect method, of class ChatClient.
     */
    @Test
    public void testConnect() throws Exception
    {
        System.out.println("connect");
        String address = "";
        int port = 0;
        String username = "";
        ChatClient instance = new ChatClient();
        instance.connect(address, port, username);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class ChatClient.
     */
    @Test
    public void testSend()
    {
        System.out.println("send");
        String msg = "";
        ChatClient instance = new ChatClient();
        instance.send(msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeTheConnection method, of class ChatClient.
     */
    @Test
    public void testCloseTheConnection() throws Exception
    {
        System.out.println("closeTheConnection");
        ChatClient instance = new ChatClient();
        instance.closeTheConnection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class ChatClient.
     */
    @Test
    public void testRun()
    {
        System.out.println("run");
        ChatClient instance = new ChatClient();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerEchoListener method, of class ChatClient.
     */
    @Test
    public void testRegisterEchoListener()
    {
        System.out.println("registerEchoListener");
        MessageListener l = null;
        ChatClient instance = new ChatClient();
        instance.registerEchoListener(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unRegisterEchoListener method, of class ChatClient.
     */
    @Test
    public void testUnRegisterEchoListener()
    {
        System.out.println("unRegisterEchoListener");
        MessageListener l = null;
        ChatClient instance = new ChatClient();
        instance.unRegisterEchoListener(l);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class ChatClient.
     */
    @Test
    public void testMain()
    {
        System.out.println("main");
        String[] args = null;
        ChatClient.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
