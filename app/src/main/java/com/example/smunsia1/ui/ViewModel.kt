package com.example.smunsia1.ui

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smunsia1.Group
import com.example.smunsia1.Postingan
import com.example.smunsia1.PostinganRepository
import com.example.smunsia1.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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
        viewModelScope.launch {
            try {
                // Menggunakan withContext(Dispatchers.IO) untuk menjalankan operasi di thread IO
                val fetchedGroups = withContext(Dispatchers.IO) {
                    fetchGroupsFromFirebase()
                }

                _groups.value = fetchedGroups
            } catch (e: Exception) {
                // Handle error, misalnya log atau tampilkan pesan ke pengguna
                e.printStackTrace()
            }
        }
    }

    // Fungsi untuk mendapatkan daftar grup dari Firebase Realtime Database
    private suspend fun fetchGroupsFromFirebase(): List<Group> {
        return withContext(Dispatchers.IO) {
            return@withContext suspendCoroutine<List<Group>> { continuation ->
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val groups = mutableListOf<Group>()

                        for (groupSnapshot in snapshot.children) {
                            val groupId = groupSnapshot.key ?: ""
                            val groupName = groupSnapshot.child("groupName").getValue(String::class.java) ?: ""
                            val description = groupSnapshot.child("description").getValue(String::class.java) ?: ""

                            val group = Group(groupId, groupName, description)
                            groups.add(group)
                        }

                        continuation.resume(groups)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        continuation.resumeWithException(error.toException())
                    }
                })
            }
        }
    }
}


class UserViewModel : ViewModel() {
    private val repository = UserRepository()
    val users = repository.users

}

