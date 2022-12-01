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
        private val radioGroup: RadioGroup = itemView.findViewById(R.id.tossupRadioGroup)
        private val team1Button: RadioButton = itemView.findViewById(R.id.Team1Button)
        private val team2Button: RadioButton = itemView.findViewById(R.id.Team2Button)
        private val missedButton: RadioButton = itemView.findViewById(R.id.MissedButton)

        fun bind(tossup: Tossup, questionAnswered:(tossup:Tossup)->Unit) {
            tossupNumberView.text = tossup.questionNumber.toString()
            when(tossup.team){
                TeamAnswered.TEAM1 -> team1Button.isChecked = true
                TeamAnswered.TEAM2 -> team2Button.isChecked = true
                TeamAnswered.NONE -> missedButton.isChecked = true
            }
            radioGroup.setOnClickListener{
                questionAnswered(tossup)
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