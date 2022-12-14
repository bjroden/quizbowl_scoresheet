package com.example.quizbowlscoresheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.TeamAnswered
import com.example.quizbowlscoresheet.database.models.Tossup
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class TossupAdapter (
    private val questionAnswered:(tossup:Tossup)->Unit
) : ListAdapter<Tossup, TossupAdapter.TossupViewHolder>(TossupComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TossupViewHolder {
        return TossupViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TossupViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, questionAnswered)
        holder.itemView.tag = current.id
    }

    class TossupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tossupNumberView: TextView = itemView.findViewById(R.id.tossupNum)
        private val radioGroup: MaterialButtonToggleGroup = itemView.findViewById(R.id.tossupButtonGroup)
        private val team1Button: MaterialButton = itemView.findViewById(R.id.Team1Button)
        private val team2Button: MaterialButton = itemView.findViewById(R.id.Team2Button)
        private val missedButton: MaterialButton = itemView.findViewById(R.id.MissedButton)

        fun bind(tossup: Tossup, questionAnswered:(tossup:Tossup)->Unit) {
            tossupNumberView.text = tossup.questionNumber.toString()
            radioGroup.clearOnButtonCheckedListeners()
            when(tossup.team){
                TeamAnswered.TEAM1 -> team1Button.isChecked = true
                TeamAnswered.TEAM2 -> team2Button.isChecked = true
                TeamAnswered.NONE -> missedButton.isChecked = true
            }
            radioGroup.addOnButtonCheckedListener { group, _, _ ->
                val newTossup = when (group.checkedButtonId) {
                    R.id.Team1Button -> tossup.copy(team = TeamAnswered.TEAM1)
                    R.id.Team2Button -> tossup.copy(team = TeamAnswered.TEAM2)
                    R.id.MissedButton -> tossup.copy(team = TeamAnswered.NONE)
                    else -> null
                }
                newTossup?.let { questionAnswered(it) }
            }
        }

        companion object {
            fun create(parent: ViewGroup): TossupViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_tossup, parent, false)
                return TossupViewHolder(view)
            }
        }
    }

    class TossupComparator : DiffUtil.ItemCallback<Tossup>() {
        override fun areItemsTheSame(oldItem: Tossup, newItem: Tossup): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tossup, newItem: Tossup): Boolean {
            return oldItem == newItem
        }
    }

}