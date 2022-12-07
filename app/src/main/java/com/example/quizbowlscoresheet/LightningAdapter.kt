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
import com.example.quizbowlscoresheet.database.models.LightningAnswer
import com.example.quizbowlscoresheet.database.models.LightningQuestion

class LightningAdapter (
    private val questionAnswered:(lightningQuestion: LightningQuestion)->Unit
) : ListAdapter<LightningQuestion, LightningAdapter.LightningViewHolder>(LightningComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightningViewHolder {
        return LightningViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: LightningViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, questionAnswered)
        holder.itemView.tag = current.questionNumber
    }

    class LightningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val lightningNumberView: TextView = itemView.findViewById(R.id.lightningNum)
        private val radioGroup: RadioGroup = itemView.findViewById(R.id.lightningRadioGroup)
        private val correctButton: RadioButton = itemView.findViewById(R.id.CorrectButton)
        private val incorrectButton: RadioButton = itemView.findViewById(R.id.IncorrectButton)
        private val missedButton: RadioButton = itemView.findViewById(R.id.MissedButton)

        fun bind(lightningQuestion: LightningQuestion, questionAnswered:(lightningQuestion: LightningQuestion)->Unit) {
            lightningNumberView.text = lightningQuestion.questionNumber.toString()
            when(lightningQuestion.answer){
                LightningAnswer.CORRECT -> correctButton.isChecked = true
                LightningAnswer.BOUNCED_BACK -> incorrectButton.isChecked = true
                LightningAnswer.INCORRECT -> missedButton.isChecked = true
                LightningAnswer.STALLED -> missedButton.isChecked = true
            }
            radioGroup.setOnClickListener{
                questionAnswered(lightningQuestion)
            }
        }

        companion object {
            fun create(parent: ViewGroup): LightningViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_lightning, parent, false)
                return LightningViewHolder(view)
            }
        }
    }

    class LightningComparator : DiffUtil.ItemCallback<LightningQuestion>() {
        override fun areItemsTheSame(oldItem: LightningQuestion, newItem: LightningQuestion): Boolean {
            return oldItem.questionNumber == newItem.questionNumber
        }

        override fun areContentsTheSame(oldItem: LightningQuestion, newItem: LightningQuestion): Boolean {
            return oldItem == newItem
        }
    }

}