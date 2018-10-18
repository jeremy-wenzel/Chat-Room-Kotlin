import java.util.*

class Message(val name: String, val date: Date, val message: String) {
    override fun toString(): String {
        return "${date} ${name}: ${message}"
    }
}