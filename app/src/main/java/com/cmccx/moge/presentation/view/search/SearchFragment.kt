package com.cmccx.moge.presentation.view.search

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.base.getJwt
import com.cmccx.moge.base.getUserIdx
import com.cmccx.moge.data.remote.api.*
import com.cmccx.moge.data.remote.model.Board
import com.cmccx.moge.data.remote.model.QuizBoard
import com.cmccx.moge.data.remote.model.Search
import com.cmccx.moge.databinding.FragmentSearchBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.home.HomeFragmentDirections
import com.cmccx.moge.presentation.viewmodel.PointViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), TopView, TopLike, QuizOrderView {
    private lateinit var owner: MainOwner
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jwt = getJwt(requireContext())

        initView()

        binding.searchViewMainCl.setOnClickListener {
            moveFragment(R.id.action_searchFragment_to_searchResultFragment)
        }
    }

    private fun initView() {
        // ????????? ?????? ?????????
        owner.setFloatingBtnVisible(true)

        // ?????? ?????????
        binding.point = pointViewModel
        pointViewModel.getPoint(getJwt(this.requireContext()), getUserIdx(this.requireContext()))

        // ?????????, ????????? top 10
        getTopView()

        // ????????????
        getCategory()

        // ?????? ??????
        getQuizTen(category)
    }

    private fun getTopView() {
        // ?????????
        viewTopTenAdapter = ViewTopTenAdapter(requireContext())
        binding.searchViewToptenRv.adapter = viewTopTenAdapter
        binding.searchViewToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val topViewService = TopViewService(this)
        topViewService.getTopViewResult()

        clickTopView()


        // ?????????
        likeTopTenAdapter = LikeTopTenAdapter(requireContext())
        binding.searchLikeToptenRv.adapter = likeTopTenAdapter
        binding.searchLikeToptenRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val topLikeService = TopLikeService(this)
        topLikeService.getTopLikeResult()

        clickTopLike()
    }

    override fun onGetTopViewResultSuccess(result: ArrayList<Search>) {
        viewTopTenAdapter.addViewTopTen(result)
    }

    override fun onGetTopViewResultFailure(message: String) {
        Log.d(TAG, "????????? top 10 ?????? - $message")
    }

    private fun clickTopView() {
        viewTopTenAdapter.setViewTopTenClickListener(object: ViewTopTenAdapter.ViewTopTenClickListener{
            override fun onItemClick(topTen: Search) {
                val curItem = QuizBoard(topTen.boardIdx, topTen.nickname, topTen.profileImage, topTen.elapsedTime, topTen.title, topTen.quizCount.toString(), topTen.viewCount.toString(), topTen.likeCount.toString())
                val action = SearchFragmentDirections.actionSearchFragmentToQuizFragment(curItem)
                findNavController().navigate(action)
            }
        })
    }

    override fun onGetTopLikeResultSuccess(result: ArrayList<Search>) {
        likeTopTenAdapter.addLikeTopTen(result)
    }

    override fun onGetTopLikeResultFailure(message: String) {
        Log.d(TAG, "????????? top 10 ?????? - $message")
    }

    private fun clickTopLike() {
        likeTopTenAdapter.setLikeTopTenClickListener(object: LikeTopTenAdapter.LikeTopTenClickListener{
            override fun onItemClick(topTen: Search) {
                val curItem = QuizBoard(topTen.boardIdx, topTen.nickname, topTen.profileImage, topTen.elapsedTime, topTen.title, topTen.quizCount.toString(), topTen.viewCount.toString(), topTen.likeCount.toString())
                val action = SearchFragmentDirections.actionSearchFragmentToQuizFragment(curItem)
                findNavController().navigate(action)
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

        // ?????? ???????????? ?????? - ?????????
        binding.searchQuizCateHeaderTv.text = getString(R.string.category_new)
        category = getString(R.string.category_new)

        // ???????????? ?????? ??? ?????? ??????????????? ??????
        clickCategory()
    }

    private fun clickCategory() {
        categoryAdapter.setCategoryClickListener(object: CategoryAdapter.CategoryClickListener{
            override fun onItemClick(selectCategory: String) {
                // ?????? ???????????? ?????? ????????????
                binding.searchQuizCateHeaderTv.text = selectCategory

                // ?????? ?????????????????? ????????????
                getQuizTen(selectCategory)
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun getQuizTen(category: String) {
        getCategoryIdx(category)

        // ???????????? ?????????????????? ????????? ???????????????
        quizTenAdapter = QuizTenAdapter(requireContext(), category)
        binding.searchQuizBoardsRv.adapter = quizTenAdapter
        binding.searchQuizBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        quizLatestService = QuizLatestService(this)
        quizOrderService = QuizOrderService(this)

        val grayLight = requireContext().getColor(R.color.gray_light)
        val grayDark = requireContext().getColor(R.color.gray_dark)

        // ????????? - ?????????, ?????????, ?????????
        initOrder(grayLight, grayDark)
        getOrderBoard(grayLight, grayDark)

        // ?????? ?????? ???????????????
        clickQuizTen()

        // ???????????? ?????? ??? ?????? ?????? ?????? ?????????
        binding.searchAllViewBtn.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToViewAllFragment(jwt, categoryIdx, category)
            findNavController().navigate(action)
        }
    }

    override fun onGetQuizOrderResultSuccess(result: ArrayList<Board>) {
        quizTenAdapter.addQuizTen(result)
    }

    override fun onGetQuizOrderResultFailure(message: String) {
        Log.d(TAG, "?????? ????????? ?????? ?????? - $message")
    }

    private fun getCategoryIdx(category: String) {
        when (category) {
            "?????????" -> { categoryIdx = 1 }
            "?????????" -> { categoryIdx = 2 }
            "?????????" -> { categoryIdx = 3 }
            "?????? ??????" -> { categoryIdx = 4 }
            "????????????" -> { categoryIdx = 5 }
        }
    }

    private fun initOrder(grayLight: Int, grayDark: Int) {
        // ???????????? ?????????
        quizLatestService.getQuizLatestResult(jwt, categoryIdx)

        binding.searchQuizBoardsLatestTv.setTextColor(grayDark)
        binding.searchQuizBoardsViewsTv.setTextColor(grayLight)
        binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
    }

    private fun getOrderBoard(grayLight: Int, grayDark: Int) {
        // ?????????
        binding.searchQuizBoardsLatestTv.setOnClickListener {
            quizLatestService.getQuizLatestResult(jwt, categoryIdx)

            // ????????? ?????? ??????
            binding.searchQuizBoardsLatestTv.setTextColor(grayDark)
            binding.searchQuizBoardsViewsTv.setTextColor(grayLight)
            binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
        }
        // ?????????
        binding.searchQuizBoardsViewsTv.setOnClickListener {
            quizOrderService.getQuizViewOrderResult(jwt, categoryIdx, "view")

            binding.searchQuizBoardsLatestTv.setTextColor(grayLight)
            binding.searchQuizBoardsViewsTv.setTextColor(grayDark)
            binding.searchQuizBoardsLikesTv.setTextColor(grayLight)
        }
        // ?????????
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
                val curItem = QuizBoard(board.boardIdx, board.nickname, board.profileImage, board.elapsedTime, board.title, board.quizCount.toString(), board.viewCount.toString(), board.likeCount.toString())
                val action = SearchFragmentDirections.actionSearchFragmentToQuizFragment(curItem)
                findNavController().navigate(action)
            }
        })
    }
}