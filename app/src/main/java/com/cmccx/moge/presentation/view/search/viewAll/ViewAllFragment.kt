package com.cmccx.moge.presentation.view.search.viewAll

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.QuizLatestService
import com.cmccx.moge.data.remote.api.QuizOrderService
import com.cmccx.moge.data.remote.api.QuizOrderView
import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.databinding.FragmentViewAllBinding

class ViewAllFragment : BaseFragment<FragmentViewAllBinding>(FragmentViewAllBinding::bind, R.layout.fragment_view_all),
    QuizOrderView {

    private lateinit var viewAllAdapter: ViewAllAdapter
    private val args: ViewAllFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.viewAllHeaderBackIv.setOnClickListener {
            backFragment()
        }
    }

    private fun initView() {
        binding.viewAllHeaderCateTv.text = args.category

        // 퀴즈 보드
        getQuizTen()
    }

    @SuppressLint("ResourceAsColor")
    private fun getQuizTen() {
        // 카테고리 클릭할때마다 어뎁터 갱신해주기
        viewAllAdapter = ViewAllAdapter(requireContext(), args.category)
        binding.viewAllBoardsRv.adapter = viewAllAdapter
        binding.viewAllBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        val quizLatestService = QuizLatestService(this)
        val quizOrderService = QuizOrderService(this)

        // 처음에는 최신순
        quizLatestService.getQuizLatestResult(args.jwt, args.categoryIdx)

        val grayLight = requireContext().getColor(R.color.gray_light)
        val grayDark = requireContext().getColor(R.color.gray_dark)

        // 필터링 - 최신순, 조회순, 인기순
        // 최신순
        binding.viewAllBoardsLatestTv.setOnClickListener {
            quizLatestService.getQuizLatestResult(args.jwt, args.categoryIdx)

            // 텍스트 색상 변화
            binding.viewAllBoardsLatestTv.setTextColor(grayDark)
            binding.viewAllBoardsViewsTv.setTextColor(grayLight)
            binding.viewAllBoardsLikesTv.setTextColor(grayLight)
        }
        // 조회순
        binding.viewAllBoardsViewsTv.setOnClickListener {
            quizOrderService.getQuizViewOrderResult(args.jwt, args.categoryIdx, "view")

            binding.viewAllBoardsLatestTv.setTextColor(grayLight)
            binding.viewAllBoardsViewsTv.setTextColor(grayDark)
            binding.viewAllBoardsLikesTv.setTextColor(grayLight)
        }
        // 인기순
        binding.viewAllBoardsLikesTv.setOnClickListener {
            quizOrderService.getQuizLikeOrderResult(args.jwt, args.categoryIdx, "like")

            binding.viewAllBoardsLatestTv.setTextColor(grayLight)
            binding.viewAllBoardsViewsTv.setTextColor(grayLight)
            binding.viewAllBoardsLikesTv.setTextColor(grayDark)
        }

        // 퀴즈 클릭 인터페이스
        clickViewAll()
    }

    override fun onGetQuizOrderResultSuccess(result: ArrayList<Board>) {
        viewAllAdapter.addViewAll(result)
    }

    override fun onGetQuizOrderResultFailure(message: String) {
        Log.d(ContentValues.TAG, "퀴즈 최신순 조회 실패 - $message")
    }

    private fun clickViewAll() {
        viewAllAdapter.setViewAllClickListener(object: ViewAllAdapter.ViewAllClickListener{
            override fun onItemClick(board: Board) {
                // TODO 퀴즈 상세보기로 넘어가기
            }
        })
    }

}