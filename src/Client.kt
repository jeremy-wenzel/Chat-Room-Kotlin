import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Client(val hostName: String, val port: Int) {

    fun startClient() {
        println("Starting connection to server ${hostName} on port ${port}")
        val socket = Socket(hostName, port)
        val out = PrintWriter(socket.getOutputStream(), true)
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val userInput = BufferedReader(InputStreamReader(System.`in`))

        var str: String?
        do {
            // Read user input
            if (userInput.ready()) {
                str = userInput.readLine()
                if (str == null)
                    break
                if (str == "free at last")
                    break
                // Send user input
                out.println(str)
            }

            // Read input from server
            if (input.ready()) {
                str = input.readLine()
                if (str == null)
                    break
                // Print it if it is not empty
                if (str.isNotEmpty())
                    println(str)
            }

            // Sleep so we aren't using up a huge amount of CPU
            Thread.sleep(500)
        } while (true)

        out.close()
        input.close()
        socket.close()
        println("Closing connection to server")
    }
}