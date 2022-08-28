package com.macluczak.a2health.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.macluczak.a2health.databinding.HodlerStatsBinding
import com.macluczak.a2health.databinding.HolderTracksBinding

class TrackStatsAdapter(val stats: List<TrackStats>): RecyclerView.Adapter<TrackStatsAdapter.StatsViewHolder>() {
    inner class StatsViewHolder (val binding: HodlerStatsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        return StatsViewHolder(HodlerStatsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val trackstats = stats[position]

        holder.binding.apply {

            runTime.text = trackstats.runTime
            runDay.text = trackstats.runDay
            runDate.text = trackstats.runDate
            user.text = trackstats.user


        }
    }

    override fun getItemCount(): Int {
        return stats.size
    }
}