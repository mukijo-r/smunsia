package com.example.smunsia1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val usersList = mutableListOf<User>()

                for (postSnapshot in snapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)
                    user?.let {
                        usersList.add(it)
                    }
                }

                _users.value = usersList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }

    fun updateUserProfile(username: String, newUsername: String, newEmail: String) {
        // Cari data pengguna berdasarkan username
        userRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    // Dapatkan ID pengguna
                    val userId = userSnapshot.key ?: ""

                    // Buat objek User dan update nilai username dan email
                    val updatedUser = User(newUsername, newEmail)
                    userRef.child(userId).setValue(updatedUser)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}

