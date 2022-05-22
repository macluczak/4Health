package com.macluczak.a2health.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macluczak.a2health.TrackDetails
import com.macluczak.a2health.databinding.HolderTracksBinding

class TracksAdapter(val tracks: List<Track>): RecyclerView.Adapter<TracksAdapter.TracksViewHolder>(){
    inner class TracksViewHolder(val binding: HolderTracksBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        return TracksViewHolder(HolderTracksBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = tracks[position]

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, TrackDetails::class.java)
            intent.putExtra("id", track.id)
            holder.itemView.getContext().startActivity(intent)
        }

        holder.binding.apply {

            trackTitle.text = track.title
            trackFav.isChecked = track.fav

        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}