package com.cmccx.moge.presentation.view.quiz

import android.content.Context
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.cmccx.moge.R
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.data.remote.model.QuizComment
import com.cmccx.moge.databinding.ItemCommentChildBinding
import com.cmccx.moge.databinding.ItemCommentParentBinding
import com.cmccx.moge.presentation.viewmodel.QuizViewModel


class QuizCommentAdapter(
    private val context: Context,
    private var comments: List<QuizComment>,
    private val viewModel: QuizViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                popupMenus(it, position)
            }
        }

        private fun popupMenus(view: View, position: Int) {
            val popupMenus = PopupMenu(context, view)
            popupMenus.inflate(R.menu.comment_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.comment_modify -> {
                        Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.comment_delete -> {
                        Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()
                        viewModel.deleteQuizComment(
                            jwt = getJwt(context),
                            boardIdx = viewModel.userBoard.value!!,
                            commentIdx = viewModel.comments.value!![position].commentIdx
                        )
                        viewModel.getQuizComments(viewModel.userBoard.value!!)
                        notifyDataSetChanged()
                        true
                    }
                    R.id.comment_child -> {
                        Toast.makeText(context, "대댓글", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true

            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
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
                popupMenus(it, position)
            }
        }

        private fun popupMenus(view: View, position: Int) {
            val popupMenus = PopupMenu(context, view)
            popupMenus.inflate(R.menu.comment_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.comment_modify -> {
                        Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.comment_delete -> {
                        Toast.makeText(context, "삭제", Toast.LENGTH_SHORT).show()
                        viewModel.deleteQuizComment(
                            jwt = getJwt(context),
                            boardIdx = viewModel.userBoard.value!!,
                            commentIdx = viewModel.comments.value!![position].commentIdx
                        )
                        viewModel.getQuizComments(viewModel.userBoard.value!!)
                        notifyDataSetChanged()
                        true
                    }
                    R.id.comment_child -> {
                        Toast.makeText(context, "대댓글", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true

            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

    }
}