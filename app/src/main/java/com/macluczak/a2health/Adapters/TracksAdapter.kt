package com.macluczak.a2health.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.macluczak.a2health.R
import com.macluczak.a2health.databinding.HolderTracksBinding

class TracksAdapter(val tracks: List<Track>, private val trackInterface: TrackInterface): RecyclerView.Adapter<TracksAdapter.TracksViewHolder>(){
    inner class TracksViewHolder(val binding: HolderTracksBinding, trackInterface: TrackInterface): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {

        return TracksViewHolder(HolderTracksBinding.inflate(LayoutInflater.from(parent.context), parent, false), trackInterface)
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
                .load(track.locationView)
                .fallback(R.drawable.ic_baseline_image_24)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_hide_image_24)
                .centerCrop()
                .into(trackImage)

            trackTitle.text = track.title
            trackLong.text = track.distance
            trackStart.text = track.startAdress



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