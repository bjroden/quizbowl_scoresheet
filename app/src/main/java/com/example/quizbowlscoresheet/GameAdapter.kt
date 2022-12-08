package com.example.quizbowlscoresheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game

class GameAdapter (
    private val gameSelected:(game: Game)->Unit
) : ListAdapter<Game, GameAdapter.GameViewHolder>(GameComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, gameSelected)
        holder.itemView.tag = current.id
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game, gameSelected: (game: Game) -> Unit) {

        }

        companion object {
            fun create(parent: ViewGroup): GameViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_game, parent, false)
                return GameViewHolder(view)
            }
        }
    }

    class GameComparator : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }

}