package com.macluczak.a2health.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.HolderModeBinding
import com.macluczak.a2health.databinding.HolderTracksBinding

class TracksAdapterMode(val tracks: List<Track>, private val trackInterface: TrackInterface): RecyclerView.Adapter<TracksAdapterMode.TracksViewHolder>(){
    inner class TracksViewHolder(val binding: HolderModeBinding, trackInterface: TrackInterface): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {

        return TracksViewHolder(HolderModeBinding.inflate(LayoutInflater.from(parent.context), parent, false), trackInterface)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = tracks[position]

        holder.itemView.setOnClickListener{
            trackInterface.onClick(track.id.toInt())
        }
        holder.itemView.setOnLongClickListener {
            trackInterface.onLongClick(track.id.toInt())
            return@setOnLongClickListener true
        }

        holder.binding.apply {
            Glide.with(holder.itemView)
                .load("https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg")
//            .load("https://maps.googleapis.com/maps/api/streetview?size=600x300&location=46.414382,10.013988&heading=151.78&pitch=-0.76&key=${key}")
                .fallback(R.drawable.ic_baseline_image_24)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_hide_image_24)
                .centerCrop()
                .into(trackImage)

            trackTitle.text = track.title



        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    interface TrackInterface{
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }

}