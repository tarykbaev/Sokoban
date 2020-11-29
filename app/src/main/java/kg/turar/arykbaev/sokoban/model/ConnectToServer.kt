package kg.turar.arykbaev.sokoban.model

import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class ConnectToServer : Thread {
    private val message: String
    private var letter: String

    constructor(message: String) {
        this.message = message
        letter = ""
    }

    fun go() {
        start()
        try {
            join(2000)
        } catch (ie: InterruptedException) {
            println(ie)
        }
    }

    override fun run() {
        send(message)
    }

    private fun send(number: String) {
        try {
            println("Start send.")
            val socket = Socket("localhost", 8080)
            val outputStream = ObjectOutputStream(socket.getOutputStream())
            val inputStream = ObjectInputStream(socket.getInputStream())

            outputStream.writeUTF(number)
            outputStream.flush()

            println("End send.")

            val line: String = inputStream.readUTF()

            letter = line

            println(line)
            println("--------------------------")

            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getLetter(): String {
        return letter
    }
}