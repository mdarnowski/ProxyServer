HTTPS PROXY SERVER

    -- Main
        Program expects an integer between 1 and 65535.
	    Input must match the patter for the program to proceed.

    -- Server
        Server creates new ServerSocket with the integer taken as a parameter that represents TCP port.
        ServerSocket will wait for requests to come in over the network.

        * acceptConnections()
            While the program is running Server accepts new socket connections,
            passes them to Handler and creates new threads to handle.
            Server accepts new connections while old ones are being handled.
        * run()
            Checks user input to turn off the program.

    -- Handler
        Object responsible for servicing HTTP "GET" requests from the client and handling this requests.

        * run()
            Newly created BufferReader reads data (request) from the client.
            To obtain string URL - request is parsed (request type and everything after the next space is removed).

        * sendToClient()
            If data is not an image, HTTP connection to this URL is created.
            Program retrieves a URL Connection, gets an input stream from the connection
	    and reads from a stream.
            The program writes the required information to a client by the output stream,
            flushes the output stream forcing any buffered output bytes to be written out and closes the stream.
            If data is an image (verified by URL extension) data is written to a client by BufferedImage -
            BufferedImage represents image data that is present in memory.




    HOW TO USE
        - compile and run main
        - set the value of the port before surfing
        - enter 'menu' - to show menu
        - enter 'time' - to show time
        - enter 'q'  - to turn off the program
