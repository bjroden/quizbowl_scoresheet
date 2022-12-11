package com.example.quizbowlscoresheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizbowlscoresheet.database.models.BonusCategory
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup

class BonusAdapter(
    private val bonusAnswered:(bonusCategory:BonusCategory)->Unit
) : ListAdapter<BonusCategory, BonusAdapter.BonusViewHolder>(BonusAdapter.BonusComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BonusViewHolder {
        return BonusViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: BonusViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, bonusAnswered)
        holder.itemView.tag = current.categoryInfo.id
    }

    class BonusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bonusButtonGroup: MaterialButtonToggleGroup = itemView.findViewById(R.id.bonusButtonGroup)
        private val bonus1: MaterialButton = itemView.findViewById(R.id.bonus1)
        private val bonus2: MaterialButton = itemView.findViewById(R.id.bonus2)
        private val bonus3: MaterialButton = itemView.findViewById(R.id.bonus3)
        private val bonus4: MaterialButton = itemView.findViewById(R.id.bonus4)
        private val bonusCategoryNum: TextView = itemView.findViewById(R.id.bonusCategoryNum)

        fun bind(bonusCategory: BonusCategory, bonusAnswered:(bonusCategory:BonusCategory)->Unit) {
            bonusCategoryNum.text = bonusCategory.categoryInfo.categoryNumber.toString()
            bonusButtonGroup.clearOnButtonCheckedListeners()
            for(question in bonusCategory.bonusQuestions) {
                when(question.questionNumber) {
                    1 -> bonus1.isChecked = question.answered
                    2 -> bonus2.isChecked = question.answered
                    3 -> bonus3.isChecked = question.answered
                    4 -> bonus4.isChecked = question.answered
                }
            }
            bonusButtonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                val newBonus = when (checkedId) {
                    R.id.bonus1 -> bonusCategory.bonusQuestions[0].copy(answered = isChecked)
                    R.id.bonus2 -> bonusCategory.bonusQuestions[1].copy(answered = isChecked)
                    R.id.bonus3 -> bonusCategory.bonusQuestions[2].copy(answered = isChecked)
                    R.id.bonus4 -> bonusCategory.bonusQuestions[3].copy(answered = isChecked)
                    else -> null
                }
                newBonus?.let { bonusAnswered(bonusCategory) }
            }


        }

        companion object {
            fun create(parent: ViewGroup): BonusViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_bonus, parent, false)
                return BonusViewHolder(view)
            }
        }
    }

    class BonusComparator : DiffUtil.ItemCallback<BonusCategory>() {
        override fun areItemsTheSame(oldItem: BonusCategory, newItem: BonusCategory): Boolean {
            return oldItem.categoryInfo.id == newItem.categoryInfo.id
        }

        override fun areContentsTheSame(oldItem: BonusCategory, newItem: BonusCategory): Boolean {
            return oldItem == newItem
        }
    }
}