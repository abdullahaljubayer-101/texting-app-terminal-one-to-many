# 👥 Texting App (Terminal, One-to-Many)

![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Sockets](https://img.shields.io/badge/Networking-TCP%20Sockets-blueviolet?style=flat-square)
![Multithreading](https://img.shields.io/badge/Concurrency-Multithreading-orange?style=flat-square)

A **group chat server in the terminal**, built with **Java sockets and multithreading**. Any number of users can join with a username, and every message is broadcast to everyone else in the room. 🗣️🗣️🗣️

This one builds on my [one-to-one chat](https://github.com/abdullahaljubayer-101/texting-app-terminal-one-to-one). The big step up was handling **many clients at once**, with one thread per client.

---

## ✨ Features

- 👥 **Unlimited clients** can join the same chat
- 🏷️ Pick a **username** when you join
- 📢 Messages are **broadcast** to everyone else
- 🔔 Join and leave notices like `SERVER: Jubayer has Entered the Chat!`
- 🧵 **One thread per client** on the server, so no one blocks anyone else
- 📦 Zero dependencies, just the JDK

## 🧠 How it works

```text
              ┌──────────────────────────┐
 Client A ──► │                          │ ──► Client B
 Client B ──► │  Server (port 1234)      │ ──► Client C
 Client C ──► │  └─ ClientHandler × N    │ ──► Client A
              └──────────────────────────┘
```

- `Server.java` accepts connections on port **1234** and starts a new `ClientHandler` thread for each one.
- `ClientHandler.java` keeps a shared list of all connected clients and **broadcasts** each incoming message to everyone except the sender.
- `Client.java` asks for your username, then listens and sends on separate threads.

## 🚀 Run it

```bash
cd src
javac *.java
```

**Terminal 1 (server):**

```bash
java Server
```

**Terminal 2, 3, 4… (clients):**

```bash
java Client
```

Enter a username in each client and start chatting. 🎉

## 📁 Project structure

```text
src/
├── Server.java         # Accepts connections, one thread per client
├── ClientHandler.java  # Handles one client + broadcasts messages
└── Client.java         # Username, send and receive
```

## 🔗 Related projects

- 🖥️ [Texting App: Terminal, One-to-One](https://github.com/abdullahaljubayer-101/texting-app-terminal-one-to-one)
- 🪟 [Texting App: GUI, One-to-One (JavaFX)](https://github.com/abdullahaljubayer-101/texting-app-gui-one-to-one)

---

Made by **Abdullah Al Jubayer** · [GitHub](https://github.com/abdullahaljubayer-101) · [Portfolio](https://abdullahaljubayer.com)
