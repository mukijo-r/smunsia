package com.example.smunsia1.ui

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smunsia1.Postingan
import com.example.smunsia1.PostinganRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User

class AuthViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    fun setLoggedInUser(username: String) {
        _username.value = username
    }

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    fun setSelectedImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }
}

class PostinganViewModel : ViewModel() {
    private val repository = PostinganRepository()
    val postingans = repository.postingans

    fun getPostingansByUsername(username: String): LiveData<List<Postingan>> {
        return repository.getPostingansByUsername(username)
    }
}

class UserViewModel : ViewModel() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
    val usersData = mutableStateOf<List<User>>(emptyList())

    init {
        fetchUsersData()
    }

    private fun fetchUsersData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = mutableListOf<User>()

                for (userSnapshot in dataSnapshot.children) {
                    val username = userSnapshot.child("username").getValue(String::class.java)
                    val email = userSnapshot.child("email").getValue(String::class.java)
                    val password = userSnapshot.child("password").getValue(String::class.java)

                    val user = User(username)
                    users.add(user)
                }

                usersData.value = users
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle kesalahan jika terjadi
            }
        })
    }
}

