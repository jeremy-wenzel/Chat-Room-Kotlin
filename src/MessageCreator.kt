import java.util.*

class MessageCreator(val name: String) {

    fun generateMessage(message: String): Message {
        return Message(name, Date(), message)
    }
}