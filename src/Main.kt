fun main(args: Array<String>) {
    if (args[0].equals("s")) {
        val server = Server(8101)
        server.startServer()
    }
    else if (args[0].equals("c")) {
        val client = Client("localhost", 8101)
        client.startClient()
    }
}