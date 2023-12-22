package com.example.uas_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_android.databinding.ActivityMain3Binding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.firestore.toObjects

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private val firestore = FirebaseFirestore.getInstance()
    private val movieCollection = firestore.collection("movie")
    private var updateId = ""
    private val movieListLiveData: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>()
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = binding.recyclerViewadmin
        recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MoviesAdapter { item ->
            updateId = item.id
            val intentToEdit_Movie_Activity = Intent(this@MainActivity3, Edit_Movie_Activity::class.java)

            intentToEdit_Movie_Activity.putExtra("UPDATE_ID", updateId)
            intentToEdit_Movie_Activity.putExtra("TITLE", item.title)
            intentToEdit_Movie_Activity.putExtra("DESCRIPTION", item.Description)
            intentToEdit_Movie_Activity.putExtra("IMAGE", item.image)

            startActivity(intentToEdit_Movie_Activity)
        }

        recyclerView.adapter = movieAdapter

        with(binding){
            fab.setOnClickListener{
                val  intentToAdd_movie_Activity = Intent(this@MainActivity3, Add_movie_Activity::class.java)
                startActivity(intentToAdd_movie_Activity)
            }
        }
        getAllMovie()
        observeMovie()
    }
    private fun getAllMovie(){
        observeMovieChanges()
    }

    private fun observeMovie(){
        movieListLiveData.observe(this) { movies ->
            movieAdapter.submitList(movies)
        }
    }
    private fun observeMovieChanges(){
        movieCollection.addSnapshotListener{snapshot, error->
            if (error != null){
                Log.d("MainActivity3", "Error listening for budget changes: ", error)
                return@addSnapshotListener
            }
            val movie = snapshot?.toObjects(Movie::class.java)
            if (movie != null){
                movieListLiveData.postValue(movie)
            }
        }
    }
}