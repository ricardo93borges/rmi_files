# rmi_files
Academic project to exercise programming with sockets and Java RMI

## TODO

- [x] Main class that receive params to indicate 
  - [x] If the program is a server or a client
  - [x] Host
- [x] Server class
  - [x] Receive resources list from clients
  - [x] Keep clients resources in a data structure
  - [x] Send resources list to clients
  - [x] Send resources location to a client
  - [x] Remove client from list if did not receive a request after 10 seconds
- [x] Client class
  - [x] Receive param to indicate files dir
  - [x] Read/Write files
  - [x] Send/receive files
  - [x] Send resources list on register
  - [x] Every 5 seconds:
    - [x] Request resources list
    - [x] Request a resource location to server
    - [x] Request a resource to another client