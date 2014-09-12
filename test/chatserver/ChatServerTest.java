/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

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
public class ChatServerTest
{
    
    public ChatServerTest()
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
     * Test of getInstance method, of class ChatServer.
     */
    @Test
    public void testGetInstance()
    {
        System.out.println("getInstance");
        ChatServer expResult = null;
        ChatServer result = ChatServer.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeHandler method, of class ChatServer.
     */
    @Test
    public void testRemoveHandler()
    {
        System.out.println("removeHandler");
        ClientHandler ch = null;
        ChatServer.removeHandler(ch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addHandler method, of class ChatServer.
     */
    @Test
    public void testAddHandler()
    {
        System.out.println("addHandler");
        ClientHandler ch = null;
        ChatServer.addHandler(ch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopServer method, of class ChatServer.
     */
    @Test
    public void testStopServer()
    {
        System.out.println("stopServer");
        ChatServer.stopServer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of send method, of class ChatServer.
     */
    @Test
    public void testSend()
    {
        System.out.println("send");
        String msg = "";
        String[] recipients = null;
        ChatServer.send(msg, recipients);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNbOfConnectedUsers method, of class ChatServer.
     */
    @Test
    public void testGetNbOfConnectedUsers()
    {
        System.out.println("getNbOfConnectedUsers");
        int expResult = 0;
        int result = ChatServer.getNbOfConnectedUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of start method, of class ChatServer.
     */
    @Test
    public void testStart()
    {
        System.out.println("start");
        ChatServer instance = new ChatServer();
        instance.start();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
