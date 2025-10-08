package com.example.driving_car_project.exam

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.driving_car_project.model.AnswerOption
import com.example.driving_car_project.core.resources.R
import com.example.driving_car_project.core.resources.databinding.ItemAnswerOptionBinding

class ExamDetailAdapter(
    private var options: List<AnswerOption>,
    private val onOptionSelected: (AnswerOption) -> Unit
) : RecyclerView.Adapter<ExamDetailAdapter.AnswerOptionViewHolder>() {

    var isReviewMode: Boolean = false
    private var selectedLabel: String? = null
    private var showResult: Boolean = false

    inner class AnswerOptionViewHolder(
        private val binding: ItemAnswerOptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun bind(option: AnswerOption) {
            binding.apply {
                tvOptionLabel.text = option.label
                tvOptionText.text = option.text

                // reset trạng thái
                rootOption.isSelected = false
                rootOption.setBackgroundResource(R.drawable.selector_option_state)
                imgResult.visibility = View.GONE
                tvSuggest.visibility = View.GONE
                imgChecked.visibility = View.GONE

                if (showResult || isReviewMode) {
                    when {
                        // Chọn đúng
                        option.label == selectedLabel && option.isCorrect -> {
                            rootOption.setBackgroundResource(R.drawable.bg_option_correct)
                            imgResult.visibility = View.VISIBLE
                            imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                            option.suggest?.let {
                                tvSuggest.visibility = View.VISIBLE
                                tvSuggest.text = "Giải thích: $it"
                            }
                        }

                        // Chọn sai
                        option.label == selectedLabel && !option.isCorrect -> {
                            rootOption.setBackgroundResource(R.drawable.bg_option_wrong)
                            imgResult.visibility = View.VISIBLE
                            imgResult.setImageResource(android.R.drawable.ic_delete)
                        }

                        // Không chọn gì nhưng có đáp án đúng
                        selectedLabel.isNullOrEmpty() && option.isCorrect -> {
                            rootOption.setBackgroundResource(R.drawable.bg_option_correct)
                            imgResult.visibility = View.VISIBLE
                            imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                            option.suggest?.let {
                                tvSuggest.visibility = View.VISIBLE
                                tvSuggest.text = "Giải thích: $it"
                            }
                        }

                        // Highlight đáp án đúng khi chọn sai
                        option.isCorrect -> {
                            rootOption.setBackgroundResource(R.drawable.bg_option_correct)
                            imgResult.visibility = View.VISIBLE
                            imgResult.setImageResource(android.R.drawable.checkbox_on_background)
                            option.suggest?.let {
                                tvSuggest.visibility = View.VISIBLE
                                tvSuggest.text = "Giải thích: $it"
                            }
                        }
                    }
                } else {
                    // Đang làm bài thi
                    if (option.label == selectedLabel) {
                        rootOption.isSelected = true
                        imgChecked.visibility = View.VISIBLE
                    }
                }

                // Xử lý click chọn đáp án
                rootOption.setOnClickListener {
                    if (!showResult && !isReviewMode) {
                        selectedLabel = option.label
                        onOptionSelected(option)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerOptionViewHolder {
        val binding = ItemAnswerOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerOptionViewHolder(binding)
    }

    override fun getItemCount(): Int = options.size

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