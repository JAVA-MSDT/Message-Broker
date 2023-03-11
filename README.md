### Task ***gRPC part*** 
#### Description

* Create a simple gRPC server application that listens on port 8080 for a request like this (feel free to add more fields)
 { "message": "Ping" }
* And responses with something like this (feel free to add more fields)
 { "message": "Pong" }
* Create a simple Java gRPC client application that sends a request to the simple server (share the same proto file), gets a response, and prints it.
* Create a simple gRPC client application in any other language you like (or ask a trainer/lector for a Python example) that sends a request to the simple server,  gets a response, and prints it. Use the same proto file.
* Start a server and try to communicate with it using the clients you just wrote.

#### Solution
##### Project Structure
  * It's a multi module project (Client to call the Server and proto to generate the proto stubs for the communication between Client and Server).

#### To run the App
   * From the root where is the pom parent file exist, run this maven command mvn clean install.
   * Rnu the Server project first from the MainServer main.
   * Run the Client project second from the MainClinet file.
   * Check the console you should find message displayed in the server console in this pattern 'INFO: Response Message:: Pong'
#### Run only server
   * If you want to run the server only witghout the client,  you can download [BloomRPC](https://github.com/bloomrpc/bloomrpc/releases)
   * Then run the BloomRPC locally, and make sure that you use localhost:port-of-server
   * from BloomRPC select the proto file from the proto module, then run
   
   

  
