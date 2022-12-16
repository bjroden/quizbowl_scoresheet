package com.example.quizbowlscoresheet

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameWithTeams
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class GameWithTeamAdapter (
    private val gameSelected:(game: Game)->Unit
) : ListAdapter<GameWithTeams, GameWithTeamAdapter.GameViewHolder>(GameWithTeamComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, gameSelected)
        holder.itemView.tag = current.game.id
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var gameLayout: LinearLayout = itemView.findViewById(R.id.gameLayout)
        val team1Score: TextInputEditText = itemView.findViewById(R.id.loadGameTeam1Score)
        val team2Score: TextInputEditText = itemView.findViewById(R.id.loadGameTeam2Score)
        val team1Name: MaterialTextView = itemView.findViewById(R.id.TeamAName)
        val team2Name: MaterialTextView = itemView.findViewById(R.id.TeamBName)
        val gameName: MaterialTextView = itemView.findViewById(R.id.gameName)

        fun bind(gameWithTeams: GameWithTeams, gameSelected: (game: Game) -> Unit) {
            team1Score.setText(gameWithTeams.game.team1SavedScore.toString())
            team2Score.setText(gameWithTeams.game.team2SavedScore.toString())
            team1Name.setText(gameWithTeams.team1?.name ?: "Team 1 Not found")
            team2Name.setText(gameWithTeams.team2?.name ?: "Team 2 Not found")
            val date = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(gameWithTeams.game.createdAt)
            gameName.text = "Game at $date"
            gameName.setOnClickListener { gameSelected(gameWithTeams.game) }
            gameLayout.setOnClickListener { gameSelected(gameWithTeams.game) }
            team1Score.setOnClickListener { gameSelected(gameWithTeams.game) }
            team2Score.setOnClickListener { gameSelected(gameWithTeams.game) }
        }

        companion object {
            fun create(parent: ViewGroup): GameViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_game, parent, false)
                return GameViewHolder(view)
            }
        }
    }

    class GameWithTeamComparator : DiffUtil.ItemCallback<GameWithTeams>() {
        override fun areItemsTheSame(oldItem: GameWithTeams, newItem: GameWithTeams): Boolean {
            return oldItem.game.id == newItem.game.id
        }

        override fun areContentsTheSame(oldItem: GameWithTeams, newItem: GameWithTeams): Boolean {
            return oldItem == newItem
        }
    }

}