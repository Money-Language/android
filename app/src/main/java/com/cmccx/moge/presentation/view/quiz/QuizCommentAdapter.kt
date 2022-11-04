package com.cmccx.moge.presentation.view.quiz

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.data.remote.model.QuizComment
import com.cmccx.moge.databinding.ItemCommentChildBinding
import com.cmccx.moge.databinding.ItemCommentParentBinding


class QuizCommentAdapter(
    private var comments: List<QuizComment>,
    private var optionsMenuClickListener: OptionsMoreClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OptionsMoreClickListener {
        fun onOptionsMoreClicked(view: View, position: Int)
    }

    object CommentViewType {
        const val PARENT = 0
        const val CHILD = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == CommentViewType.PARENT) {
            return ParentCommentViewHolder(
                ItemCommentParentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return ChildCommentViewHolder(
            ItemCommentChildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (comments[position].parentIdx) {
            CommentViewType.PARENT -> {
                (holder as ParentCommentViewHolder).bind(comments[position], position)
            }
            CommentViewType.CHILD -> {
                (holder as ChildCommentViewHolder).bind(comments[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun getItemViewType(position: Int): Int {
        return comments[position].parentIdx
    }

    fun setData(newData: List<QuizComment>) {
        comments = newData
        notifyDataSetChanged()
    }

    inner class ParentCommentViewHolder(binding: ItemCommentParentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val nickName = binding.itemCommentParentNicknameTv
        private val time = binding.itemCommentParentTimeTv
        private val content = binding.itemCommentParentContentTv
        private val like = binding.itemCommentParentFavContentTv
        private val more = binding.itemCommentParentMoreIconIv

        fun bind(item: QuizComment, position: Int) {
            nickName.text = item.nickname
            time.text = item.elapsedTime
            content.text = item.content
            like.text = item.commentLike.toString()

            more.setOnClickListener {
                optionsMenuClickListener.onOptionsMoreClicked(it, adapterPosition)
            }
        }

    }

    inner class ChildCommentViewHolder(binding: ItemCommentChildBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val nickName = binding.itemCommentChildNicknameTv
        private val time = binding.itemCommentChildTimeTv
        private val content = binding.itemCommentChildContentTv
        private val like = binding.itemCommentChildFavContentTv
        private val more = binding.itemCommentChildMoreIconIv

        fun bind(item: QuizComment, position: Int) {
            nickName.text = item.nickname
            time.text = item.elapsedTime
            content.text = item.content
            like.text = item.commentLike.toString()

            more.setOnClickListener {
                optionsMenuClickListener.onOptionsMoreClicked(it, adapterPosition)
            }
        }

    }
}