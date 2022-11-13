package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.api.*
import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.data.remote.model.TopTen
import com.cmccx.moge.databinding.FragmentSearchBinding
import com.cmccx.moge.presentation.viewmodel.PointViewModel
import kotlin.properties.Delegates

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), TopView, TopLike, QuizOrderView {
    private lateinit var viewTopTenAdapter: ViewTopTenAdapter
    private lateinit var likeTopTenAdapter: LikeTopTenAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var quizTenAdapter: QuizTenAdapter

    private lateinit var quizLatestService: QuizLatestService
    private lateinit var quizOrderService: QuizOrderService

    private var jwt : String = ""
    private val cateList = arrayListOf<String>()
    private var category : String = ""
    private var categoryIdx: Int = 1

    private val pointViewModel: PointViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jwt = getJwt(requireContext())

        initView()
    }

    private fun initView() {
        // 상단 포인트
        binding.point = pointViewModel
        pointViewModel.getPoint(getJwt(this.requireContext()), getUserIdx(this.requireContext()))

        // 조회수, 좋아요 top 10
        getTopView()

        // 카테고리
        getCategory()

        // 퀴즈 보드
        getQuizTen(category)
    }

    private fun getTopView() {
        // 조회수
        viewTopTenAdapter = ViewTopTenAdapter(requireContext())
        binding.searchViewToptenRv.adapter = viewTopTenAdapter
        binding.searchViewToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val topViewService = TopViewService(this)
        topViewService.getTopViewResult()

        clickTopView()


        // 좋아요
        likeTopTenAdapter = LikeTopTenAdapter(requireContext())
        binding.searchLikeToptenRv.adapter = likeTopTenAdapter
        binding.searchLikeToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val topLikeService = TopLikeService(this)
        topLikeService.getTopLikeResult()

        clickTopLike()
    }

    override fun onGetTopViewResultSuccess(result: ArrayList<TopTen>) {
        viewTopTenAdapter.addViewTopTen(result)
    }

    override fun onGetTopViewResultFailure(message: String) {
        Log.d(TAG, "조회순 top 10 실패 - $message")
    }

    private fun clickTopView() {
        viewTopTenAdapter.setViewTopTenClickListener(object: ViewTopTenAdapter.ViewTopTenClickListener{
            override fun onItemClick(topTen: TopTen) {
                // TODO 퀴즈 상세보기로 넘어가기
            }
        })
    }

    override fun onGetTopLikeResultSuccess(result: ArrayList<TopTen>) {
        likeTopTenAdapter.addLikeTopTen(result)
    }

    override fun onGetTopLikeResultFailure(message: String) {
        Log.d(TAG, "좋아요 top 10 실패 - $message")
    }

    private fun clickTopLike() {
        likeTopTenAdapter.setLikeTopTenClickListener(object: LikeTopTenAdapter.LikeTopTenClickListener{
            override fun onItemClick(topTen: TopTen) {
                // TODO 퀴즈 상세보기로 넘어가기
            }
        })
    }

    private fun getCategory() {
        cateList.add(getString(R.string.category_new))
        cateList.add(getString(R.string.category_spelling))
        cateList.add(getString(R.string.category_nonsense))
        cateList.add(getString(R.string.category_idiom))
        cateList.add(getString(R.string.category_word))

        categoryAdapter = CategoryAdapter(requireContext(), cateList)
        binding.searchQuizCateHeaderRv.adapter = categoryAdapter
        binding.searchQuizCateHeaderRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 처음 카테고리 설정 - 신조어
        binding.searchQuizCateHeaderTv.text = getString(R.string.category_new)
        category = getString(R.string.category_new)

        // 카테고리 선택 시 클릭 인터페이스 설정
        clickCategory()
    }

    private fun clickCategory() {
        categoryAdapter.setCategoryClickListener(object: CategoryAdapter.CategoryClickListener{
            override fun onItemClick(selectCategory: String) {
                // 하단 카테고리 이름 변경하기
                binding.searchQuizCateHeaderTv.text = selectCategory

                // 하단 리사이클러뷰 변경하기
                getQuizTen(selectCategory)
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun getQuizTen(category: String) {
        getCategoryIdx(category)

        // 카테고리 클릭할때마다 어뎁터 갱신해주기
        quizTenAdapter = QuizTenAdapter(requireContext(), category)
        binding.searchQuizBoardsRv.adapter = quizTenAdapter
        binding.searchQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        quizLatestService = QuizLatestService(this)
        quizOrderService = QuizOrderService(this)

        val grayLight = requireContext().getColor(R.color.gray_light)
        val grayDark = requireContext().getColor(R.color.gray_dark)

        // 필터링 - 최신순, 조회순, 인기순
        initOrder(grayLight, grayDark)
        getOrderBoard(grayLight, grayDark)

        // 퀴즈 클릭 인터페이스
        clickQuizTen()

        // 모두보기 클릭 시 전체 화면 뷰로 넘어감
        binding.searchAllViewBtn.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToViewAllFragment(jwt, categoryIdx, category)
            findNavController().navigate(action)
        }
    }

    override fun onGetQuizOrderResultSuccess(result: ArrayList<Board>) {
        quizTenAdapter.addQuizTen(result)
    }

    override fun onGetQuizOrderResultFailure(message: String) {
        Log.d(TAG, "퀴즈 최신순 조회 실패 - $message")
    }

    private fun getCategoryIdx(category: String) {
        when (category) {
            "신조어" -> { categoryIdx = 1 }
            "맞춤법" -> { categoryIdx = 2 }
            "넌센스" -> { categoryIdx = 3 }
            "단어 의미" -> { categoryIdx = 4 }
            "사자성어" -> { categoryIdx = 5 }
        }
    }

    private fun initOrder(grayLight: Int, grayDark: Int) {
        // 처음에는 최신순
        quizLatestService.getQuizLatestResult(jwt, categoryIdx)

        binding.searchQuizBoardsLatestTv.setTextColor(grayDark)
        binding.searchQuizBoardsViewsTv.setTextColor(grayLight)
        binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
    }

    private fun getOrderBoard(grayLight: Int, grayDark: Int) {
        // 최신순
        binding.searchQuizBoardsLatestTv.setOnClickListener {
            quizLatestService.getQuizLatestResult(jwt, categoryIdx)

            // 텍스트 색상 변화
            binding.searchQuizBoardsLatestTv.setTextColor(grayDark)
            binding.searchQuizBoardsViewsTv.setTextColor(grayLight)
            binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
        }
        // 조회순
        binding.searchQuizBoardsViewsTv.setOnClickListener {
            quizOrderService.getQuizViewOrderResult(jwt, categoryIdx, "view")

            binding.searchQuizBoardsLatestTv.setTextColor(grayLight)
            binding.searchQuizBoardsViewsTv.setTextColor(grayDark)
            binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
        }
        // 인기순
        binding.searchQuizBoardsLikesTv.setOnClickListener {
            quizOrderService.getQuizLikeOrderResult(jwt, categoryIdx, "like")

            binding.searchQuizBoardsLatestTv.setTextColor(grayLight)
            binding.searchQuizBoardsViewsTv.setTextColor(grayLight)
            binding.searchQuizBoardsLikesTv.setTextColor(grayDark)
        }
    }

    private fun clickQuizTen() {
        quizTenAdapter.setQuizTenClickListener(object: QuizTenAdapter.QuizTenClickListener{
            override fun onItemClick(board: Board) {
                // TODO 퀴즈 상세보기로 넘어가기
            }
        })
    }
}