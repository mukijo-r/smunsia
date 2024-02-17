package com.example.smunsia1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostinganRepository {
    private val database = FirebaseDatabase.getInstance()
    private val postinganRef = database.getReference("postingan")

    private val _postingans = MutableLiveData<List<Postingan>>()
    val postingans: LiveData<List<Postingan>> get() = _postingans

    init {
        fetchPostingans()
    }

    private fun fetchPostingans() {
        postinganRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val postingansList: MutableList<Postingan> = mutableListOf()

                for (postSnapshot in snapshot.children) {
                    val postingan = postSnapshot.getValue(Postingan::class.java)
                    postingan?.let {
                        postingansList.add(it)
                    }
                }

                // Sort the list based on timestamp in descending order
                val sortedPostingans = postingansList.sortedByDescending { it.timestamp }

                _postingans.value = sortedPostingans
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here if needed
            }
        })
    }

    fun getPostingansByUsername(username: String): LiveData<List<Postingan>> {
        val postingansLiveData = MutableLiveData<List<Postingan>>()

        postinganRef.orderByChild("username").equalTo(username)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val postingansList: MutableList<Postingan> = mutableListOf()

                    for (postSnapshot in snapshot.children) {
                        val postingan = postSnapshot.getValue(Postingan::class.java)
                        postingan?.let {
                            postingansList.add(it)
                        }
                    }
                    postingansLiveData.value = postingansList
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error here if needed
                }
            })

        return postingansLiveData
    }


}