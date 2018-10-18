import java.net.ServerSocket

class Server(val port: Int) {

    var clientSockets = mutableSetOf<ServerClientConnection>()

    fun startServer() {
        try {
            val socket = ServerSocket(port)

            while (true) {
                val clientSocket = socket.accept()
                println("Creating client socket connection")
                val clientSocketConnection = ServerClientConnection(clientSocket, this)
                clientSockets.add(clientSocketConnection)
                Thread(clientSocketConnection).start()
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun removeClientSocket(clientSocket: ServerClientConnection) {
        synchronized(clientSockets) {
            if (clientSockets.contains(clientSocket))
                clientSockets.remove(clientSocket)
        }
    }

    fun notifyOtherSockets(clientSocket: ServerClientConnection, message: String) {
        synchronized(clientSockets) {
            clientSockets.forEach {
                if (it != clientSocket)
                    it.setMessage(message)
            }
        }
    }
}