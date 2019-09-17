package com.farn.booksapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.farn.booksapp.R
import com.farn.booksapp.models.Volume

class VolumeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volume_detail)
        val volumeDetail = intent.getSerializableExtra("volumeDetail") as Volume
        val detailTitle = findViewById<TextView>(R.id.tvBookDetailsTitle)
        val detailDate = findViewById<TextView>(R.id.tvBookDetailsDate)
        val detailDescription = findViewById<TextView>(R.id.tvBookDetailsDescription)
        val detailImage = findViewById<ImageView>(R.id.imgBookDetailImage)

        detailTitle.text = volumeDetail.title
        detailDate.text = volumeDetail.publishedDate
        detailDescription.text = volumeDetail.description

        volumeDetail.imageLinks?.let {
            Glide.with(this)
                .load(volumeDetail.imageLinks.thumbnail)
                .fitCenter()
                .placeholder(R.color.placeholderColor)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(detailImage)
        }
    }

}
