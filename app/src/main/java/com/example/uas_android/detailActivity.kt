package com.example.uas_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.uas_android.databinding.ActivityDetailBinding

class detailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intentData = intent
        if (intentData != null) {
            val id = intentData.getStringExtra("UPDATE_ID") ?: ""
            val movieTitle = intentData .getStringExtra("TITLE")
            val movieDescription = intentData .getStringExtra("DESCRIPTION")
            val movieImage = intentData .getStringExtra("IMAGE")

            binding.juduldetail.text = movieTitle
            binding.descdetail.text = movieDescription

            Glide.with(this)
                .load(movieImage)
                .into(binding.imageview2)

        }
    }
}