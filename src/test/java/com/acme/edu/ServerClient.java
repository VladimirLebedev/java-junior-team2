package com.acme.edu;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple Server.
 */
public class ServerClient
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ServerClient(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ServerClient.class );
    }

    /**
     * Rigourous Client :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
