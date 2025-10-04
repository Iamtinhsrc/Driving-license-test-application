package com.example.driving_car_project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.feature.history.R
import com.example.driving_car_project.model.HistoryWithExam
import java.text.SimpleDateFormat
import java.util.Locale

class ExamHistoryAdapter(
    private var items: List<HistoryWithExam>,
    private val onHistoryClick: (HistoryWithExam) -> Unit
) : RecyclerView.Adapter<ExamHistoryAdapter.HistoryViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    inner class HistoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tv_history_date)
        private val tvBadge: TextView = itemView.findViewById(R.id.tv_pass_badge)
        private val tvExamTitle: TextView = itemView.findViewById(R.id.tv_exam_title)
        private val tvScore: TextView = itemView.findViewById(R.id.tv_history_score)
        private val tvDuration: TextView = itemView.findViewById(R.id.tv_history_duration)

        fun bind(item: HistoryWithExam) {
            val history = item.history
            val exam = item.exam

            tvDate.text = dateFormat.format(history.takenAt)

            if(history.passed) {
                tvBadge.text = itemView.context.getString(R.string.badge_pass)
                tvBadge.setBackgroundResource(R.drawable.bg_badge_pass)
            } else {
                tvBadge.text = itemView.context.getString(R.string.badge_fail)
                tvBadge.setBackgroundResource(R.drawable.bg_badge_fail)
            }

            // Ten de thi
            tvExamTitle.text = exam?.title ?: itemView.context.getString(R.string.default_tv_exam_title)


            // Diem so
            tvScore.text = "Đúng ${history.score}/${history.total}"

            // 🔥 Format duration
            val minutes = history.durationSeconds / 60
            val seconds = history.durationSeconds % 60
            tvDuration.text = String.format("Thời gian: %02d:%02d", minutes, seconds)

            itemView.setOnClickListener {
                onHistoryClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newItems: List<HistoryWithExam>){
        items = newItems
        notifyDataSetChanged()
    }
}