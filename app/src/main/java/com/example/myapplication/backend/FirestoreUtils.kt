package com.example.myapplication.backend

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

object FirestoreUtils {

    data class FireStoreUser(
        val FirstName : String = "",
        val LastName : String = "",
        val emailAddress : String = "",
        val smtpHost : String = "",
        val smtpPort : String = "",
        val imapHost : String = "",
        val imapPort : String = ""
    )

    suspend fun fetchUserData(context: CoroutineContext, currentUser: FirebaseUser) = withContext(Dispatchers.IO){

        //user to be returned
        var activeUser : FireStoreUser = FireStoreUser()

        //get uid
        val uid = currentUser.uid

        val fireDB = Firebase.firestore

        val userRef = fireDB.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
            if (document != null) {
                activeUser = document.toObject<FireStoreUser>()!!
            }else
            {
                throw Exception("Failed to fetch account")
            }
        }

        activeUser
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
            "FirstName" to updatedData.FirstName,
            "LastName" to updatedData.LastName,
            "emailAddress" to updatedData.emailAddress,
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