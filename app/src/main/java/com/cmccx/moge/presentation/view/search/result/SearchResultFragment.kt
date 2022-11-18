package com.cmccx.moge.presentation.view.search.result

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.KeywordView
import com.cmccx.moge.data.remote.api.SearchKeywordService
import com.cmccx.moge.data.remote.api.QueryView
import com.cmccx.moge.data.remote.api.SearchService
import com.cmccx.moge.data.remote.model.Keyword
import com.cmccx.moge.data.remote.model.QuizBoard
import com.cmccx.moge.data.remote.model.Search
import com.cmccx.moge.databinding.FragmentSearchResultBinding
import com.cmccx.moge.presentation.view.MainOwner
import com.cmccx.moge.presentation.view.home.HomeFragmentDirections
import com.cmccx.moge.presentation.view.search.LikeTopTenAdapter

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result), KeywordView, QueryView {
    private lateinit var keywordAdapter: SearchKeywordAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter

    private lateinit var owner: MainOwner

    override fun onAttach(context: Context) {
        super.onAttach(context)
        owner = context as MainOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        binding.resultSearchViewBackIv.setOnClickListener {
            backFragment()
        }

        binding.resultSearchViewSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {   // 검색 버튼 클릭했을 때
                getSearchResult(query)
                hideKeyboard()
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean { // 텍스트가 바뀌는 순간순간
                return false
            }
        })
    }

    private fun initView() {
        // 플로팅 버튼 사라지기
        owner.setFloatingBtnVisible(false)

        // 추천 키워드 view 보여주기
        binding.resultSearchKeywordCl.visibility = View.VISIBLE
        binding.resultSearchNothingTv.visibility = View.GONE
        binding.resultSearchBoardsCl.visibility = View.GONE

        // 추천 키워드 검색
        getKeyword()

        // SearchView 포커싱 맞추기
        binding.resultSearchViewSv.isIconified = false
        // 키보드 올라오기
        binding.resultSearchViewSv.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) showKeyboard(view)
        }
    }

    // 추천 키워드 조회
    private fun getKeyword() {
        keywordAdapter = SearchKeywordAdapter()
        binding.resultSearchKeywordRv.adapter = keywordAdapter
        binding.resultSearchKeywordRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)

        val searchKeywordService = SearchKeywordService(this)
        searchKeywordService.getKeywordResult()
    }

    // 추천 키워드 조회 성공
    override fun onGetKeywordResultSuccess(result: ArrayList<Keyword>) {
        keywordAdapter.addKeywordResult(result)
    }

    // 추천 키워드 조회 실패
    override fun onGetKeywordResultFailure(message: String) {
        Log.d(ContentValues.TAG, "키워드 조회 실패 - $message")
    }

    // 검색 결과 조회
    private fun getSearchResult(query: String?) {
        // 검색 결과 view 보여주기
        binding.resultSearchKeywordCl.visibility = View.GONE
        binding.resultSearchNothingTv.visibility = View.GONE
        binding.resultSearchBoardsCl.visibility = View.VISIBLE

        searchResultAdapter = SearchResultAdapter(requireContext())
        binding.resultSearchBoardsRv.adapter = searchResultAdapter
        binding.resultSearchBoardsRv.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)

        val searchResultService = SearchService(this)
        searchResultService.getSearchResult(query)

        clickSearchResult()
    }

    // 검색 결과 조회 성공
    override fun onGetSearchResultSuccess(result: ArrayList<Search>?) {
        Log.d(TAG, "result - $result")
        if(result.isNullOrEmpty()) {
            binding.resultSearchNothingTv.visibility = View.VISIBLE
        } else {
            binding.resultSearchNothingTv.visibility = View.GONE
            searchResultAdapter.addSearchResult(result)
        }
    }

    // 검색 결과 조회 실패
    override fun onGetSearchResultFailure(code: Int, message: String) {
        if(code == 2032) Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        Log.d(ContentValues.TAG, "검색 결과 조회 실패 - $message")
    }

    private fun clickSearchResult() {
        searchResultAdapter.setSearchResultClickListener(object: SearchResultAdapter.SearchResultClickListener{
            override fun onItemClick(search: Search) {
                val curItem = QuizBoard(search.boardIdx, search.nickname, search.profileImage, search.elapsedTime, search.title, search.quizCount.toString(), search.viewCount.toString(), search.likeCount.toString())
                val action = HomeFragmentDirections.actionHomeFragmentToQuizFragment(curItem)
                findNavController().navigate(action)
            }
        })
    }
}