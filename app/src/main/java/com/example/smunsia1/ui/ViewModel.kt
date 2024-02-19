package com.example.smunsia1.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.smunsia1.Postingan
import com.example.smunsia1.PostinganRepository
import com.example.smunsia1.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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

class GroupListViewModel : ViewModel() {

    private val _groups = MutableLiveData<List<Group>>()
    val groups: LiveData<List<Group>> get() = _groups

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("groups")

    init {
        // Panggil fungsi ini untuk mengambil daftar grup saat ViewModel diinisialisasi
        fetchGroups()
    }

    // Fungsi ini akan dipanggil untuk mengambil daftar grup dari Firebase Realtime Database
    fun fetchGroups() {
        val groupsList = mutableListOf<Group>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (groupSnapshot in snapshot.children) {
                    val groupId = groupSnapshot.key ?: ""
                    val groupName = groupSnapshot.child("groupName").getValue(String::class.java) ?: ""
                    val description = groupSnapshot.child("description").getValue(String::class.java) ?: ""

                    val group = Group(groupId, groupName, description)
                    groupsList.add(group)
                }

                _groups.value = groupsList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error, misalnya log atau tampilkan pesan ke pengguna
                error.toException().printStackTrace()
            }
        })
    }
}

data class Group(
    val groupId: String,
    val groupName: String,
    val description: String
)

class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    val users = repository.users

}

