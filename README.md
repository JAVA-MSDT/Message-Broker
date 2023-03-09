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
##### Requierd Softwares
  * Download the latest version of [protoc compiler package](https://github.com/protocolbuffers/protobuf/releases) as zip file
  * Download the latest version of [Protoc Gen GRPC Java](https://mvnrepository.com/artifact/io.grpc/protoc-gen-grpc-java) as executable file
  * Set the protoc compiler in the PATH environment variable for your system for me i unziped the protoc compiler package under C:\Users\Public\Tools\grpc\

#### To run the App
##### Manual
   * Run protoc command by providing the following "Sett them as environment variable in your sustem to avoid typo issues"
     * Plugin.EXE path.                        PROTO_PLUGIN_PATH
     * Source folder to pick the proto files.  PROTO_SRC_FOLDER
     * Destination folder to generate classes  PROTO_OUTPUT_FOLDER
     * ***example*** ' protoc --proto_path=%PROTO_SRC_FOLDER% --plugin=protoc-gen-grpc-java=%PROTO_PLUGIN_PATH% --grpc-java_out=%PROTO_OUTPUT_FOLDER% --java_out=%PROTO_OUTPUT_FOLDER% %PROTO_SRC_FOLDER%/message.proto '
    ![Output build manually in CMD](https://myoctocat.com/assets/images/base-octocat.svg)

  