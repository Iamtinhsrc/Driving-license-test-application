package com.example.driving_car_project.ui.exam

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

class ExamDetailAdapter(
    private var options: List<AnswerOption>,
    private val onOptionSelected: (AnswerOption) -> Unit
) : RecyclerView.Adapter<ExamDetailAdapter.AnswerOptionViewHolder>(){

    var isReviewMode: Boolean = false
    private var selectedLabel: String? = null
    private var showResult: Boolean = false

    inner class AnswerOptionViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val root: LinearLayout = itemView.findViewById(R.id.root_option)
        private val tvLabel: TextView = itemView.findViewById(R.id.tv_option_label)
        private val tvText: TextView = itemView.findViewById(R.id.tv_option_text)
        private val imgResult: ImageView = itemView.findViewById(R.id.img_result)
        private val tvSuggest: TextView = itemView.findViewById(R.id.tv_suggest)

        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: AnswerOption) {
            tvLabel.text = option.label
            tvText.text = option.text

            // reset trạng thái
            root.isSelected = false
            root.setBackgroundResource(R.drawable.selector_option_state)
            imgResult.visibility = View.GONE
            tvSuggest.visibility = View.GONE

            if (showResult || isReviewMode) {
                when {
                    // Chọn đúng
                    option.label == selectedLabel && option.isCorrect -> {
                        root.setBackgroundResource(R.drawable.bg_option_correct)
                        imgResult.visibility = View.VISIBLE
                        imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                        option.suggest?.let {
                            tvSuggest.visibility = View.VISIBLE
                            tvSuggest.text = "Giải thích: $it"
                        }
                    }

                    // Chọn sai
                    option.label == selectedLabel && !option.isCorrect -> {
                        root.setBackgroundResource(R.drawable.bg_option_wrong)
                        imgResult.visibility = View.VISIBLE
                        imgResult.setImageResource(android.R.drawable.ic_delete)
                    }

                    // Người dùng không chọn gì
                    selectedLabel.isNullOrEmpty() && option.isCorrect -> {
                        root.setBackgroundResource(R.drawable.bg_option_correct)
                        imgResult.visibility = View.VISIBLE
                        imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                        option.suggest?.let {
                            tvSuggest.visibility = View.VISIBLE
                            tvSuggest.text = "Giải thích: $it"
                        }
                    }

                    // Người dùng chọn sai đáp án khác → vẫn highlight đáp án đúng
                    option.isCorrect -> {
                        root.setBackgroundResource(R.drawable.bg_option_correct)
                        imgResult.visibility = View.VISIBLE
                        imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                        option.suggest?.let {
                            tvSuggest.visibility = View.VISIBLE
                            tvSuggest.text = "Giải thích: $it"
                        }
                    }
                }
            }
            else {
                // Trạng thái đang làm bài thi
                if (option.label == selectedLabel) {
                    root.isSelected = true
                }
            }

            // Chỉ cho chọn khi đang làm bài (chưa nộp & chưa review)
            root.setOnClickListener {
                if (!showResult && !isReviewMode) {
                    selectedLabel = option.label
                    onOptionSelected(option)
                    notifyDataSetChanged()
                }
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerOptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_answer_option, parent, false)
        return AnswerOptionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: AnswerOptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    fun updateOption(newList: List<AnswerOption>) {
        options = newList
        selectedLabel = null
        showResult = false
        notifyDataSetChanged()
    }

    fun setSelected(label: String?) {
        selectedLabel = label
        notifyDataSetChanged()
    }

    fun showResult() {
        showResult = true
        notifyDataSetChanged()
    }
}