package com.example.driving_car_project.question

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.driving_car_project.model.Question
import com.example.driving_car_project.util.toAnswerOptions

class QuestionPageAdapter(
    private var questions: List<Question>
) : RecyclerView.Adapter<QuestionPageAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIndex: TextView = itemView.findViewById(R.id.tv_question_index)
        val tvDetail: TextView = itemView.findViewById(R.id.tv_question_detail)
        val imgQuestion: ImageView = itemView.findViewById(R.id.img_question)
        val rvAnswers: RecyclerView = itemView.findViewById(R.id.rv_answers)
        val btnCheck: Button = itemView.findViewById(R.id.btn_check_answer)
        val llResult: LinearLayout = itemView.findViewById(R.id.ll_result)
        val tvAnswerResult: TextView = itemView.findViewById(R.id.tv_answer_result)
        val tvExplanation: TextView = itemView.findViewById(R.id.tv_explanation)
        val btnPrev: Button = itemView.findViewById(R.id.btn_prev_question)
        val btnNext: Button = itemView.findViewById(R.id.btn_next_question)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_question_page, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val q = questions[position]

        holder.tvIndex.text = "Câu ${position + 1}/${questions.size}"
        holder.tvDetail.text = q.question

        // Load ảnh
        val imageUrl = q.image.img1 ?: q.image.img2 ?: q.image.img3 ?: q.image.img4
        if (!imageUrl.isNullOrEmpty()) {
            holder.imgQuestion.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .error(R.drawable.ic_question)
                .into(holder.imgQuestion)
        } else {
            holder.imgQuestion.visibility = View.GONE
        }

        // Adapter cho danh sách đáp án
        val answerAdapter = QuestionDetailAdapter(q.toAnswerOptions()) { /* handle click */ }
        holder.rvAnswers.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.rvAnswers.adapter = answerAdapter

        // Reset UI
        holder.llResult.visibility = View.GONE

        // Check Answer
        holder.btnCheck.setOnClickListener {
            val selected = answerAdapter.getSelectedOption()
            if (selected == null) {
                holder.btnCheck.context?.let { context ->
                    Toast.makeText(
                        context,
                        context.getString(R.string.toast_select_answer),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@setOnClickListener
            }


            answerAdapter.checkAnswer()
            val correct = answerAdapter.getCorrectOption()
            if (selected.isCorrect) {
                holder.tvAnswerResult.text = "Bạn chọn đúng!"
            } else {
                holder.tvAnswerResult.text = "Sai, đáp án đúng: ${correct?.label}"
            }
            holder.tvExplanation.text = correct?.suggest ?: "Không có giải thích"
            holder.llResult.visibility = View.VISIBLE
        }

        holder.btnPrev.setOnClickListener {
            if (position > 0) {
                (holder.itemView.parent as? RecyclerView)?.smoothScrollToPosition(position - 1)
            }
        }
        holder.btnNext.setOnClickListener {
            if (position < itemCount - 1) {
                (holder.itemView.parent as? RecyclerView)?.smoothScrollToPosition(position + 1)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: List<Question>) {
        questions = newList
        notifyDataSetChanged()
    }
}
