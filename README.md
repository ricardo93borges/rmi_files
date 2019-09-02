# rmi_files
Academic project to exercise programming with sockets and Java RMI

## TODO

- [x] Main class that receive params to indicate 
  - [x] If the program is a server or a client
  - [x] Host
- [ ] Server class
  - [ ] Receive resources list from clients
  - [ ] Keep clients resources in a data structure
  - [ ] Send resources list to clients
  - [ ] Send resources location to a client
  - [ ] Remove client from list if did not receive a request after 10 seconds
- [ ] Client class
  - [x] Receive param to indicate files dir
  - [ ] Read/Write files
  - [ ] Send/receive files
  - [ ] Send resources list on register
  - [ ] Every 5 seconds:
    - [ ] Request resources list
    - [ ] Request a resource location to server
    - [ ] Request a resource to another client
- 