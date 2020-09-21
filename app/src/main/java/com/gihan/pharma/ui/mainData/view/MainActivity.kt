package com.gihan.pharma.ui.mainData.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gihan.pharma.R
import com.gihan.pharma.model.Post
import com.gihan.pharma.ui.mainData.adapter.ListenerAdapter
import com.gihan.pharma.ui.mainData.adapter.PostAdapter
import com.gihan.pharma.ui.mainData.viewModel.MainViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), ListenerDialog, ListenerAdapter {

    private val viewModel by inject<MainViewModel>()
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListenerForViewModel()
        initUi()
        getData()
        initSearch()
    }

    private fun initUi() {
        initAdapter()
        fab.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            val newFragment = DialogOption(this)
            newFragment.show(ft, "dialog")

        }
    }

    private fun initSearch() {
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                viewModel.search(et_search.text.toString())
            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }

    private fun initAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(arrayListOf(), this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

    }

    private fun initListenerForViewModel() {

        viewModel.getSavePost().observe(this, Observer {
            it?.let { resource ->
                getData()
                Toast.makeText(this, resource, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getPosts().observe(this, Observer {
            it?.let { posts ->

                notifyAdapter(posts)
            }
        })
        viewModel.getDeletePost().observe(this, Observer {
            it?.let { isDeleted ->
                if (isDeleted) {
                    getData()
                }
            }
        })

    }

    override fun save(post: Post) {
        viewModel.savePostData(post)
    }

    private fun getData() {
        viewModel.getPostsData()
    }

    override fun delete(post: Post) {
        viewModel.deletePostData(post)
    }

    private fun notifyAdapter(posts: List<Post>) {
        adapter.apply {

            addPost(posts)
            notifyDataSetChanged()
        }
    }
}
