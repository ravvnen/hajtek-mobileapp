package com.example.myapplication.backend

import android.widget.Toast
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import jakarta.mail.search.FlagTerm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
    val id : String,
    val from: String,
    val to: List<String>,
    val subject: String,
    val body: String,
    val sentDate: String,
    val receivedDate: String,
    val isRead : Boolean
)

object EmailUtil {

    val mailFrom : String = "testemailappuser@gmail.com"
    val password : String = "ffabdfjtpgsqtlht"


    /**
     * Sends a mail from the specified account
     * @param host
     * @param port
     * @param username
     * @param password
     * @param mail
     */
    suspend fun sendEmail(host: String, port: String, username: String, password: String, mail: Email) = withContext(Dispatchers.IO) {
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

    /**
     * Fetches the email from an smtp server
     * @param host
     * @param username
     * @param password
     * @return List<Email>
     */
    suspend fun fetchInbox(host: String, username: String, password: String) : List<Email> = withContext(Dispatchers.IO){

        val fetchedInbox : MutableList<Email> = mutableListOf()

        val properties = Properties().apply {
            put("mail.store.protocol", "imaps")
        }

        val session = Session.getInstance(properties, null)
        val store = session.getStore("imaps")
        store.connect(host, username, password)

        val inbox = store.getFolder("INBOX")

        if (inbox.exists()) {
            inbox.open(Folder.READ_ONLY)
        } else {
            throw Exception("Inbox is empty")
        }


        val messages = inbox.messages

        for (message in messages) {

            val recipients = message.getRecipients(TO)
            val toList = mutableListOf<String>()
            if (recipients != null) {
                for (recipient in recipients) {
                    toList.add(recipient.toString())
                }
            }

            var tmpEmail = Email(
                message.messageNumber.toString(),
                message.from[0].toString(),
                toList,
                message.subject ?: "empty",
                message.content.toString(),
                "",
                message.receivedDate.toString(),
                message.flags.contains(Flags.Flag.SEEN)
            )

            fetchedInbox.add(tmpEmail)
        }
        fetchedInbox
    }

    suspend fun fetchSpamEmails(host: String, port: String, username: String, password: String): List<Email> = withContext(Dispatchers.IO) {

        val properties = Properties().apply {
            put("mail.store.protocol", "imaps")
        }

        val session = Session.getInstance(properties, null)
        val store = session.getStore("imaps")
        store.connect(host, username, password)

        val spam = store.getFolder("[Gmail]/Spam")
        if (spam.exists()) {
            spam.open(Folder.READ_ONLY)
        } else {
            throw Exception("Spam is empty")
        }

        val messages = spam.messages

        val emails = mutableListOf<Email>()
        messages.forEach { message ->
            val id = message.messageNumber.toString()
            val from = message.from?.get(0)?.toString() ?: ""
            val to = (message.getRecipients(Message.RecipientType.TO) ?: arrayOf()).map { it.toString() }
            val subject = message.subject ?: ""
            val body = message.content?.toString() ?: ""
            val sentDate = message.sentDate?.toString() ?: ""
            val receivedDate = message.receivedDate?.toString() ?: ""
            val isRead = message.isSet(Flags.Flag.SEEN)

            val email = Email(id, from, to, subject, body, sentDate, receivedDate, isRead)
            emails.add(email)
        }

        spam.close(false)
        store.close()

        emails.toList()
    }

    suspend fun fetchTrashEmails(host: String, port: String, username: String, password: String) : List<Email> = withContext(Dispatchers.IO){
        val properties = Properties().apply {
            put("mail.store.protocol", "imaps")
        }

        val session = Session.getInstance(properties, null)
        val store = session.getStore("imaps")
        store.connect(host, username, password)


        val trashFolder = store.getFolder("[Gmail]/Papirkurv")
        if (trashFolder.exists()) {
            trashFolder.open(Folder.READ_ONLY)
        } else {
            throw Exception("Trash is empty")
        }

        val messages = trashFolder.messages

        val emails = mutableListOf<Email>()
        messages.forEach { message ->
            val id = message.messageNumber.toString()
            val from = message.from?.get(0)?.toString() ?: ""
            val to = (message.getRecipients(Message.RecipientType.TO) ?: arrayOf()).map { it.toString() }
            val subject = message.subject ?: ""
            val body = message.content?.toString() ?: ""
            val sentDate = message.sentDate?.toString() ?: ""
            val receivedDate = message.receivedDate?.toString() ?: ""
            val isRead = message.isSet(Flags.Flag.SEEN)

            val email = Email(id, from, to, subject, body, sentDate, receivedDate, isRead)
            emails.add(email)
        }

        trashFolder.close(false)
        store.close()

        emails.toList()
    }

    suspend fun deleteMailByID(host: String, username: String, password: String, messageNumber : String, mailList : MutableList<Email>) = withContext(Dispatchers.IO){
        try {
            val props = Properties()
            props["mail.store.protocol"] = "imaps"
            props["mail.imaps.host"] = host
            props["mail.imaps.port"] = "993"
            props["mail.imaps.starttls.enable"] = "true"

            val session = Session.getDefaultInstance(props, null)
            val store = session.getStore("imaps")
            store.connect(host, username, password)

            // Find the Trash folder
            val trashFolder = store.getFolder("[Gmail]/Papirkurv")
            if (!trashFolder.exists()) {
                throw Exception("Trash doesn't exist")
            }
            trashFolder.open(Folder.READ_WRITE)

            // Get the message by ID
            val message = trashFolder.getMessage(messageNumber.toInt())

            // Set the message's Deleted flag to true
            message.setFlag(Flags.Flag.DELETED, true)

            // Close the folder and expunge deleted messages to actually delete the email
            trashFolder.close(true)

            mailList.find{ it.id == messageNumber}?.let{ mailList.remove(it) }

        } catch (e: Exception) {
            // An exception occurred, return an error code or throw it
            throw Exception("Unable to delete email. It might already be deleted")
        }
    }

}
