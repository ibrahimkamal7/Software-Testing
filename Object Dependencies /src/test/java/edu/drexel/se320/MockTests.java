package edu.drexel.se320;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import java.io.IOException;

public class MockTests {

    public MockTests() {}

    /*
    Test that if the attempt to connectTo(...) the server fails, the client code calls no further methods on the connection

    sc.connectTo(anyString()) returns false and then a call is being made to requestFile() method of the client and then verify() is being
    used to check whether any other methods on the connection are being called or not
    */

    @Test
    public void testNoFurtherCallsOnConnectionFailure() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(false);

        String s = c.requestFile("Dummy", "Dummy");

        verify(sc, never()).requestFileContents(anyString());
        verify(sc, never()).read();
        verify(sc, never()).moreBytes();
        verify(sc, never()).closeConnection();
    }

    /*
       Test that if the connection succeeds but the file name is invalid, the client code calls no further methods on the connection
       except closeConnection. That is, the client code is expected to call closeConnection, but should not call other methods after
       it is known the file name is invalid.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns false and then a call is being made to
       requestFile() method of the client and then verify() is being used to check whether any other methods on the connection are being called or not

       This test fails as according to the code given in Client.java, the closeConnection() method is being called only if the connection is successful,
       the filename is valid, and it is being completely read
    */

    @Test
    public void testNoFurtherCallsForInvalidFile() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(false);

        String s = c.requestFile("Dummy", "Dummy");

        verify(sc, never()).read();
        verify(sc, never()).moreBytes();
        verify(sc, times(1)).closeConnection();
    }

    /*
       Test that if the connection succeeds and the file is valid and non-empty, that the connection asks for at least some part of the file.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns true, true, and false
       and then a call is being made to requestFile() method of the client and then verify() is being used to check whether
       read() is being called or not
    */

    @Test
    public void testAskForSomePartIfFileIsValid() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, false);

        String s = c.requestFile("DUMMY", "DUMMY");

        verify(sc, atLeastOnce()).read();
        verify(sc, times(2)).read();
    }

    /*
       Test that if the connection succeeds and the file is valid but empty, the client returns an empty string.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns false
       and then a call is being made to requestFile() method of the client and then assertEquals() is being used to check whether
       requestFile() returns "" or not
    */

    @Test
    public void testReturnEmptyStringIfFileIsEmpty() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(false);

        String s = c.requestFile("DUMMY", "DUMMY");
        assertEquals(s, "");
    }

    /*
       Test that if the client successfully reads part of a file, and then an IOException occurs before the file is
       fully read (i.e., moreBytes() has not returned false), the client still returns null to indicate an error,
       rather than returning a partial result.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns true, true, and false,
       sc.read() returns "override", throw an IOException during the second call and then a call is being made to requestFile() method of the client and then assertNull()
       is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testClientReturnNullIfIOExceptionOccursWhileReading() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, false);
        when(sc.read()).thenReturn("override").thenThrow(new IOException());

        String s = c.requestFile("DUMMY", "DUMMY");
        assertNull(s);
    }

    /*
       Test that if the initial server connection succeeds, then if a IOException occurs while retrieving the file (requesting, or reading bytes, either one)
       the client still explicitly closes the server connection.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(), sc.read() or sc.moreBytes() throw an IOException
       and then a call is being made to requestFile() method of the client and then verify() is used to check whether the
       closeConnection() method is being called or not

       This test fails as according to the code given in Client.java, the closeConnection() method is being called only if the connection is successful,
       the filename is valid, and it is being completely read
    */

    @Test
    public void testCloseConnectionCallOnIOException() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        //if IOException occurs in moreBytes()
        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true).thenThrow(new IOException());
        when(sc.read()).thenReturn("override", "hello");

        String s = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).closeConnection();
        assertNull(s);

        //if IOException occurs in requestFileContents()
        sc = mock(ServerConnection.class);
        c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenThrow(new IOException());

        s = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).closeConnection();
        assertNull(s);

        //if IOException occurs in read()
        sc = mock(ServerConnection.class);
        c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        when(sc.read()).thenThrow(new IOException());

        s = c.requestFile("DUMMY", "DUMMY");

        verify(sc, times(1)).closeConnection();
        assertNull(s);
    }

    /*
       Test that the client simply returns unmodified the contents if it reads a file from the server starting with “override”,
       i.e., it doesn't interpret a prefix of “override” as a trigger for some weird other behavior.

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns true, true, and false,
       sc.read() returns "override", "World" and then a call is being made to requestFile() method of the client and then assertEquals()
       is being used to check whether requestFile() returns "overrideWorld" or not
    */

    @Test
    public void testClientWorksCorrectlyIfItFindsOverrideWhileReading() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, true, false);
        when(sc.read()).thenReturn("override", "World", "!");

        String s = c.requestFile("DUMMY", "DUMMY");
        assertEquals(s, "overrideWorld!");
    }

    /*
       If the server returns the file in three pieces (i.e., two calls to read() must be executed), the client concatenates them in the correct order).

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns true, true, true, and false,
       sc.read() returns "Hello", "World", "!" and then a call is being made to requestFile() method of the client and then assertEquals()
       is being used to check whether requestFile() returns "HelloWorld!" or not
    */

    @Test
    public void testMultipleCallsToRead() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, true, false);
        when(sc.read()).thenReturn("Hello", "World", "!");

        String s = c.requestFile("DUMMY", "DUMMY");
        assertEquals(s, "HelloWorld!");
    }

    /*
       If read() ever returns null, the client treats this as the empty string (as opposed to appending “null” to the file contents read thus far,
       which is the default if you simply append null).

       sc.connectTo(anyString()) returns true, sc.requestFileContents(anyString()) returns true, sc.moreBytes() returns true, true, and false,
       sc.read() returns "Hello", null and then a call is being made to requestFile() method of the client and then assertEquals()
       is being used to check whether requestFile() returns "Hello" or not
    */

    @Test
    public void testClientHandleNullCorrectlyWhileReading() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, false);
        when(sc.read()).thenReturn("Hello").thenReturn(null);

        String s = c.requestFile("DUMMY", "DUMMY");
        assertEquals(s, "Hello");
    }

    /*
       Test that if the connectTo() fails the first time it is executed with an IOException, the client returns null.

       sc.connectTo(anyString()) throws an IOException then assertNull() is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testIOExceptionForConnectTo() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        doThrow(new IOException()).when(sc).connectTo(anyString());

        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }

    /*
       Test that if the requestFileContents() fails the first time it is executed with an IOException, the client returns null.

       sc.requestFileContents(anyString()) throws an IOException then assertNull() is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testIOExceptionForRequestFileContents() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        doThrow(new IOException()).when(sc).requestFileContents(anyString());

        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }

    /*
       Test that if the read() fails the first time it is executed with an IOException, the client returns null.

       sc.read() throws an IOException then assertNull() is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testIOExceptionForRead() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, false);
        doThrow(new IOException()).when(sc).read();

        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }

    /*
       Test that if the moreBytes() fails the first time it is executed with an IOException, the client returns null.

       sc.moreBytes() throws an IOException then assertNull() is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testIOExceptionForMoreBytes() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        doThrow(new IOException()).when(sc).moreBytes();

        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }

    /*
       Test that if the closeConnection() fails the first time it is executed with an IOException, the client returns null.

       sc.closeConnection() throws an IOException then assertNull() is being used to check whether requestFile() returns null or not
    */

    @Test
    public void testIOExceptionForCloseConnection() throws IOException {
        ServerConnection sc = mock(ServerConnection.class);
        Client c = new Client(sc);

        when(sc.connectTo(anyString())).thenReturn(true);
        when(sc.requestFileContents(anyString())).thenReturn(true);
        when(sc.moreBytes()).thenReturn(true, true, false);
        when(sc.read()).thenReturn("Hello", "World");
        doThrow(new IOException()).when(sc).closeConnection();

        assertNull(c.requestFile("DUMMY", "DUMMY"));
    }
}
