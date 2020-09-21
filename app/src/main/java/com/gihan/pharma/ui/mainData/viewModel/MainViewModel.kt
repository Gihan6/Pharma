package com.gihan.pharma.ui.mainData.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gihan.pharma.model.Post
import com.gihan.pharma.repo.MainRepository
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private lateinit var database: DatabaseReference


    private val posts = MutableLiveData<List<Post>>()
    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    fun getPostsData() {

        val data = ArrayList<Post>()
        database = Firebase.database.reference.child("posts")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {

                    val id = postSnapshot.child("id").getValue(String::class.java).toString()
                    val title =
                        postSnapshot.child("title").getValue(String::class.java).toString()
                    val description =
                        postSnapshot.child("description").getValue(String::class.java)
                            .toString()

                    data.add(Post(id, title, description))
                }
                posts.postValue(data)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                // ...
            }
        })


    }

    private val savePost = MutableLiveData<String>()
    fun getSavePost(): LiveData<String> {
        return savePost
    }

    fun savePostData(post: Post) {
        viewModelScope.launch {
            if (post.title.isEmpty() || post.description.isEmpty()) {
                savePost.postValue("Complete Data Please")
            } else {
                database = Firebase.database.reference
                val key = database.child("posts").push().key.toString()
                post.id = key
                database.child("posts").child(key).setValue(post)
                    .addOnSuccessListener {
                        // Write was successful!
                        savePost.postValue("Success")

                    }.addOnFailureListener {
                        // Write failed
                        savePost.postValue(it.toString())
                    }
            }
        }


    }

    private val deletePost = MutableLiveData<Boolean>()
    fun getDeletePost(): LiveData<Boolean> {
        return deletePost
    }

    fun deletePostData(post: Post) {
        viewModelScope.launch {

            database = Firebase.database.reference.child("posts").child(post.id)
            database.setValue(null)
            deletePost.postValue(true)
        }


    }

    fun search(key: String) {
        val data = ArrayList<Post>()
        database = Firebase.database.reference

        val query: Query = database.child("posts").orderByChild("title").equalTo(key)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (postSnapshot in dataSnapshot.children) {

                        val id = postSnapshot.child("id").getValue(String::class.java).toString()
                        val title =
                            postSnapshot.child("title").getValue(String::class.java).toString()
                        val description =
                            postSnapshot.child("description").getValue(String::class.java)
                                .toString()

                        data.add(Post(id, title, description))
                    }
//                    posts.postValue(data)
                }
                posts.postValue(data)


            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}