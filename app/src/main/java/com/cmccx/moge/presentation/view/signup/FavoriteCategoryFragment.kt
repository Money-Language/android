package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentFavoriteCategoryBinding

class FavoriteCategoryFragment : BaseFragment<FragmentFavoriteCategoryBinding>(FragmentFavoriteCategoryBinding::bind, R.layout.fragment_favorite_category), View.OnClickListener {

    private var favoriteCategory: MutableList<Int> = mutableListOf()   // defalut값은 null, 건너뛰기 가능

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickEvent()

        binding.favoriteCategoryBackIv.setOnClickListener {
            backFragment()
        }

        binding.favoriteCategoryNextSelectBtn.setOnClickListener {
            moveFragment(R.id.action_favoriteCategoryFragment_to_finishSignupFragment)
        }
    }

    private fun setClickEvent() {
        binding.favoriteCategoryNewBtn.setOnClickListener(this)
        binding.favoriteCategorySpellingBtn.setOnClickListener(this)
        binding.favoriteCategoryNonsenseBtn.setOnClickListener(this)
        binding.favoriteCategoryIdiomBtn.setOnClickListener(this)
        binding.favoriteCategoryWordBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.favorite_category_new_btn -> saveCategory(view, 1)
            R.id.favorite_category_spelling_btn -> saveCategory(view, 2)
            R.id.favorite_category_nonsense_btn -> saveCategory(view, 3)
            R.id.favorite_category_idiom_btn -> saveCategory(view, 4)
            R.id.favorite_category_word_btn -> saveCategory(view, 5)
        }
    }

    private fun saveCategory(view: View, category: Int) {
        if(favoriteCategory.size < 3) {
            view.isSelected = !view.isSelected

            setCheckButton(view.isSelected, category)

            if(view.isSelected) favoriteCategory.add(category)
            else favoriteCategory.remove(category)

        } else if (favoriteCategory.size == 3 && view.isSelected) {
            favoriteCategory.remove(category)
            view.isSelected = false

            setCheckButton(view.isSelected, category)
        } else {
            // TODO 에러 메세지 출력 (카테고리 선택 3개 다 함 or 다 안함)
        }

        checkValidCategory()
    }

    private fun setCheckButton(isSelected: Boolean, category: Int) {
        if (isSelected) {
            when (category) {
                1 -> {
                    binding.favoriteCategoryNewIv.visibility = View.VISIBLE
                    binding.favoriteCategoryNewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                2 -> {
                    binding.favoriteCategorySpellingIv.visibility = View.VISIBLE
                    binding.favoriteCategorySpellingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                3 -> {
                    binding.favoriteCategoryNonsenseIv.visibility = View.VISIBLE
                    binding.favoriteCategoryNonsenseTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                4 -> {
                    binding.favoriteCategoryIdiomIv.visibility = View.VISIBLE
                    binding.favoriteCategoryIdiomTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                5 -> {
                    binding.favoriteCategoryWordIv.visibility = View.VISIBLE
                    binding.favoriteCategoryWordTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        } else {
            when (category) {
                1 -> {
                    binding.favoriteCategoryNewIv.visibility = View.GONE
                    binding.favoriteCategoryNewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                2 -> {
                    binding.favoriteCategorySpellingIv.visibility = View.GONE
                    binding.favoriteCategorySpellingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                3 -> {
                    binding.favoriteCategoryNonsenseIv.visibility = View.GONE
                    binding.favoriteCategoryNonsenseTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                4 -> {
                    binding.favoriteCategoryIdiomIv.visibility = View.GONE
                    binding.favoriteCategoryIdiomTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                5 -> {
                    binding.favoriteCategoryWordIv.visibility = View.GONE
                    binding.favoriteCategoryWordTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
            }
        }
    }

    private fun checkValidCategory() {
        if (favoriteCategory.size == 3) {
            binding.favoriteCategoryNextUnselectBtn.visibility = View.GONE
            binding.favoriteCategoryNextSelectBtn.visibility = View.VISIBLE
        } else {
            binding.favoriteCategoryNextUnselectBtn.visibility = View.VISIBLE
            binding.favoriteCategoryNextSelectBtn.visibility = View.GONE
        }
    }


    /*private var categoryList = arrayListOf<String>()
    private var favoriteCategoryList = arrayListOf<Int>()
    private var adapter = FavoriteCategorySelectAdapter(categoryList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initAdapter()
    }

    private fun initList() {
        categoryList.add(getString(R.string.category_new))
        categoryList.add(getString(R.string.category_spelling))
        categoryList.add(getString(R.string.category_nonsense))
        categoryList.add(getString(R.string.category_idiom))
        categoryList.add(getString(R.string.category_word))
    }

    private fun initAdapter() {
        // 관심있는 키워드
        adapter.setCategoryClickListener(object: FavoriteCategorySelectAdapter.CategoryClickListener {
            override fun onItemClick(categoryList: String, isClicked: Boolean) {
                Log.d("click", isClicked.toString())
                if (isClicked) {
                    when(categoryList) {
                        "신조어" -> favoriteCategoryList.add(1)
                        "맞춤법" -> favoriteCategoryList.add(2)
                        "넌센스" -> favoriteCategoryList.add(3)
                        "사자성어" -> favoriteCategoryList.add(4)
                        "단어 의미" -> favoriteCategoryList.add(5)
                    }
                } else {
                    when(categoryList) {
                        "신조어" -> favoriteCategoryList.remove(1)
                        "맞춤법" -> favoriteCategoryList.remove(2)
                        "넌센스" -> favoriteCategoryList.remove(3)
                        "사자성어" -> favoriteCategoryList.remove(4)
                        "단어 의미" -> favoriteCategoryList.remove(5)
                    }
                }
            }
        })

        binding.favoriteCategoryRv.adapter = adapter
        binding.favoriteCategoryRv.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
    }*/
}