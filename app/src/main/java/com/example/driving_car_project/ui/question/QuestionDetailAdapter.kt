package com.example.driving_car_project.ui.question

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.R
import com.example.driving_car_project.data.model.AnswerOption

class QuestionDetailAdapter(
    private var answers: List<AnswerOption>,
    private val onOptionClick: (AnswerOption) -> Unit
) : RecyclerView.Adapter<QuestionDetailAdapter.AnswerViewHolder>(){
    private var selectedLabel: String? = null // Chon A/B/C/D
    private var isChecked: Boolean = false

    inner class AnswerViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val root: LinearLayout = itemView.findViewById(R.id.root_option)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_option_label)
        private val tvText: TextView = itemView.findViewById(R.id.tv_option_text)
        private val imgResult: ImageView = itemView.findViewById(R.id.img_result)
        private val tvSuggest: TextView = itemView.findViewById(R.id.tv_suggest)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: AnswerOption){
            tvLabel.text = option.label
            tvText.text = option.text

            //resetUI
            root.isSelected = (selectedLabel == option.label)
            imgResult.visibility = View.GONE
            tvSuggest.visibility = View.GONE

            if(isChecked){
                if(option.isCorrect){
                    imgResult.visibility = View.VISIBLE
                    imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                    tvSuggest.visibility = View.VISIBLE
                    tvSuggest.text = option.suggest ?: "Đáp án đúng"
                } else if (selectedLabel == option.label) {
                    imgResult.visibility = View.VISIBLE
                    imgResult.setImageResource(android.R.drawable.ic_delete)
                }
            }

            // Event Click
            itemView.setOnClickListener {
                selectedLabel = option.label
                notifyDataSetChanged()
                onOptionClick(option)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_answer_option, parent, false)
        return AnswerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return answers.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(answers[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newOptions: List<AnswerOption>) {
        answers = newOptions
        selectedLabel = null
        isChecked = false
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun checkAnswer(){
        isChecked = true
        notifyDataSetChanged()
    }

    fun getSelectedOption(): AnswerOption? {
        return answers.find { it.label == selectedLabel }
    }

    fun getCorrectOption(): AnswerOption? {
        return answers.find { it.isCorrect }
    }

}