package com.example.myapplication.backend

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes.GMAIL_SEND
import jakarta.mail.*
import jakarta.mail.Message.RecipientType.TO
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Paths
import java.util.*
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import org.apache.commons.codec.binary.Base64


data class Email(
    val from: String,
    val to: List<String>,
    val subject: String,
    val body: String,
    val sentDate: String,
    val receivedDate: String,
)

object EmailUtil {

    val mailFrom : String = "testemailappuser@gmail.com"
    val password : String = "ffabdfjtpgsqtlht"

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

    suspend fun fetchEmails(host: String, username: String, password: String) : List<Email> = withContext(Dispatchers.IO){

        val fetchedInbox : MutableList<Email> = mutableListOf()

        val properties = Properties().apply {
            put("mail.store.protocol", "imaps")
        }

        val session = Session.getInstance(properties, null)
        val store = session.getStore("imaps")
        store.connect(host, username, password)

        val inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_ONLY)

        val messages = inbox.getMessages(5, 10)
        //val messages = inbox.messages
        for (message in messages) {

            val recipients = message.getRecipients(TO)
            val toList = mutableListOf<String>()
            if (recipients != null) {
                for (recipient in recipients) {
                    toList.add(recipient.toString())
                }
            }

            var tmpEmail = Email(
                message.from[0].toString(),
                toList,
                message.subject,
                message.content.toString(),
                "",
                message.receivedDate.toString()
                )

            fetchedInbox.add(tmpEmail)
        }
        fetchedInbox
    }

}
