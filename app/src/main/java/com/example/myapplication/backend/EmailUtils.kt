package com.example.myapplication.backend

import jakarta.mail.*
import java.util.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.time.LocalDateTime


data class Email(
    val from: String,
    val to: List<String>,
    val subject: String,
    val body: String,
    val sentDate: String ,
    val receivedDate: String
)

object EmailUtil {

    fun sendEmail(host: String, port: String, username: String, password: String, mail: Email) {
        val properties = Properties().apply {
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true") // If required
        }

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication() = PasswordAuthentication(username, password)
        })

        try {
            val mimeMessage = MimeMessage(session).apply {
                setFrom(InternetAddress(username))

                for(recipient in mail.to){
                    addRecipient(Message.RecipientType.TO, InternetAddress(recipient))
                }

                this.subject = subject
                setText(mail.body)
            }

            Transport.send(mimeMessage)

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

    fun fetchEmails(host: String, username: String, password: String) : List<Email> {

        val fetchedInbox : MutableList<Email> = mutableListOf()

        val properties = Properties().apply {
            put("mail.store.protocol", "imaps")
        }

        val session = Session.getInstance(properties, null)
        val store = session.getStore("imaps")
        store.connect(host, username, password)

        val inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)

        val messages = inbox.messages
        for (message in messages) {

            val recipients = message.getRecipients(Message.RecipientType.TO)
            val toList = mutableListOf<String>()
            if (recipients != null) {
                for (recipient in recipients) {
                    toList.add(recipient.toString())
                }
            }

            var tmpEmail = Email(
                message.from.toString(),
                toList,
                message.subject,
                message.content.toString(),
                "",
                message.receivedDate.toString()
                )

            fetchedInbox.add(tmpEmail)
        }

        return fetchedInbox
    }
}
