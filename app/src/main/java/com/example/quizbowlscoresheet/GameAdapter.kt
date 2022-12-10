package com.example.quizbowlscoresheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.google.android.material.textfield.TextInputEditText

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
        var gameLayout: LinearLayout = itemView.findViewById(R.id.gameLayout)
        val team1Score: TextInputEditText = itemView.findViewById(R.id.loadGameTeam1Score)
        val team2Score: TextInputEditText = itemView.findViewById(R.id.loadGameTeam2Score)

        fun bind(game: Game, gameSelected: (game: Game) -> Unit) {
            team1Score.setText(game.team1SavedScore.toString())
            team2Score.setText(game.team2SavedScore.toString())
            gameLayout.setOnClickListener {
                gameSelected(game)
            }
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