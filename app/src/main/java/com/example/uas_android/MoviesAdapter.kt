package com.example.uas_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter(private val onItemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var movies: List<Movie> = emptyList()

    fun submitList(newList: List<Movie>) {
        movies = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
//        Toast.makeText(, movie.title, Toast.LENGTH_SHORT).show()
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Bind your views here
        private val titleTextView: TextView = itemView.findViewById(R.id.texttitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.Descview)
        private val imageView: ImageView = itemView.findViewById(R.id.imageview_rev) // Change TextView to ImageView

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            descriptionTextView.text = movie.Description
            // Bind other views as needed for the movie item

            Glide.with(itemView)
                .load(movie.image)
                .into(imageView)
        }
    }

}