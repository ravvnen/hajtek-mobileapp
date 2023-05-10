package com.example.myapplication.backend

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.*

object FirestoreUtils {

    data class FireStoreUser(
        val firstName : String = "",
        val lastName : String = "",
        val emailAddress : String = "",
        val password : String = "",
        val smtpHost : String = "",
        val smtpPort : String = "",
        val imapHost : String = "",
        val imapPort : String = ""
    )

    suspend fun fetchUserData(currentUser: FirebaseUser) : FireStoreUser = suspendCoroutine { continuation ->

        //get uid
        val uid = currentUser.uid

        //create firestore instance
        val fireDB = Firebase.firestore

        //get the collection
        val userRef = fireDB.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val activeUser = document.toObject<FireStoreUser>()
                    if (activeUser != null) {
                        continuation.resume(activeUser)
                    } else {
                        continuation.resumeWithException(Exception("Failed to fetch account"))
                    }
                } else {
                    continuation.resumeWithException(Exception("Failed to fetch account"))
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }



    suspend fun writeUserData(context: Context, newUser : FirebaseUser) = withContext(Dispatchers.IO){
        //add new document
        val uid = newUser.uid
        val fireDB = Firebase.firestore
        val newDoc = fireDB.collection("users")
            .document(uid)
            .set(newUser)
            .addOnCompleteListener{
                Toast.makeText(context, "Account information updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(context, "Account information failed to update", Toast.LENGTH_SHORT).show()
            }
    }

    suspend fun updateUserData(context : Context, newUser: FirebaseUser, updatedData : FireStoreUser) = withContext(Dispatchers.IO){

        val uid = newUser.uid
        val fireDB = Firebase.firestore

        val updates = hashMapOf<String, Any>(
            "FirstName" to updatedData.firstName,
            "LastName" to updatedData.lastName,
            "emailAddress" to updatedData.emailAddress,
            "password" to updatedData.password,
            "smtpHost" to updatedData.smtpHost,
            "smtpPort" to updatedData.smtpPort,
            "imapHost" to updatedData.imapHost,
            "imapPort" to updatedData.imapPort
        )

        val updateUser = fireDB.collection("users")
            .document(uid)
            .update(updates)
            .addOnCompleteListener{
                Toast.makeText(context, "Account information updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(context, "Account information failed to update", Toast.LENGTH_SHORT).show()
            }
    }

}