package com.example.driving_car_project.ui.exam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.R
import com.example.driving_car_project.data.model.Exam
import okhttp3.internal.notify

class ExamAdapter(
    private var exams: List<Exam>,
    private val onItemClick: (Exam) -> Unit
) : RecyclerView.Adapter<ExamAdapter.ExamViewHolder>(){

    inner class ExamViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle : TextView = itemView.findViewById(R.id.tv_exam_title)
        private val tvInfo: TextView = itemView.findViewById(R.id.tv_exam_info)

        fun bind(item: Exam) {
            tvTitle.text = item.title

            val numQuestions = item.questionIds.size
            val timeMinutes = numQuestions / 2 // 30s/cau
            tvInfo.text = "$numQuestions câu - $timeMinutes phút"

            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exam, parent, false)
        return ExamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exams.size
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.bind(exams[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<Exam>) {
        exams = newList
        notifyDataSetChanged()
    }
}