package com.example.driving_car_project.question

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.model.Question

class QuestionAdapter(
    private var questions: List<Question>,
    private val onItemClick: (Question) -> Unit
) :RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        private val imgIcon: ImageView = itemView.findViewById(R.id.img_icon)
        private val tvNumber: TextView = itemView.findViewById(R.id.tv_question_number)
        private val tvContent: TextView = itemView.findViewById(R.id.tv_question_content)

        fun bind(item: Question, position: Int){
            tvNumber.text = "Câu ${position + 1}"
            tvContent.text = item.question
            imgIcon.setImageResource(R.drawable.ic_question)

            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position], position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Question>) {
        questions = newList
        notifyDataSetChanged()
    }
}