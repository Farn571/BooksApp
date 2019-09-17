package com.farn.booksapp.ui


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.farn.booksapp.R
import com.farn.booksapp.models.Volume


class VolumesListAdapter(onVolumeClickListener: OnVolumeClickListener) : RecyclerView.Adapter<VolumesListAdapter.VolumesViewHolder>() {

    private var volumes = ArrayList<Volume>()
    private var volumeClickListener = onVolumeClickListener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolumesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.volume_item, parent, false)
        return VolumesViewHolder(view, volumeClickListener)
    }

    override fun onBindViewHolder(holder: VolumesViewHolder, position: Int) {
        holder.bind(volumes[position])
    }

    override fun getItemCount(): Int {
        return volumes.size
    }

    fun setData(list: List<Volume>) {
        volumes.clear()
        volumes.addAll(list)
        notifyDataSetChanged()
    }

    inner class VolumesViewHolder(private val view: View, private val onVolumeClickListener: OnVolumeClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var volume: Volume
        private val tvBookTitle = view.findViewById<TextView>(R.id.tvBookTitle)
        private val tvBookDate = view.findViewById<TextView>(R.id.tvBookDate)
        private val imgBookImage = view.findViewById<ImageView>(R.id.imgBookImage)
        private var volumeClickListener : OnVolumeClickListener

        init {
            volumeClickListener = onVolumeClickListener
            view.setOnClickListener(this)
        }

        fun bind(volume: Volume) {
            this.volume = volume
            tvBookTitle.text = volume.title
            tvBookDate.text = volume.publishedDate
            volume.imageLinks?.thumbnail?.let {
                Glide.with(view.context)
                    .load(volume.imageLinks.thumbnail)
                    .fitCenter()
                    .placeholder(R.color.placeholderColor)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgBookImage)
            }
        }

        override fun onClick(v: View?) {
           volumeClickListener.onVolumeClick(volume)
        }
    }

    interface OnVolumeClickListener {
        fun onVolumeClick(volume: Volume)
    }



}



