import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ServerClientConnection(val clientSocket: Socket, val server: Server) : Runnable {

    var messageQueue: MutableList<String> = mutableListOf()

    override fun run() {
        println("ServerClient thread started")

        val out = PrintWriter(clientSocket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

        try {
            var inputLine: String?
            do {
                if (input.ready()) {
                    inputLine = input.readLine()
                    if (inputLine == null)
                        break
                    println(inputLine)
                     // notify the other clients that client sent something
                    if (inputLine.isNotEmpty())
                        server.notifyOtherSockets(this, inputLine)
                }

                // Notify the client of messages sent by the server
                synchronized(messageQueue) {
                    // Send all the messages and clear them out
                    messageQueue.forEach { out.println(it) }
                    messageQueue.clear()
                }

                // Sleep so we aren't using up a huge amount of CPU
                Thread.sleep(500)
            } while (true)
        } catch (e : Exception) {
            println(e.message)
        }
        // close connections and streams
        println("Closing Client Connection")
        out.close()
        input.close()
        clientSocket.close()
        server.removeClientSocket(this)
    }

    fun setMessage(message: String) {
        synchronized(messageQueue) {
            messageQueue.add(message)
        }
    }
}