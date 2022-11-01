package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.databinding.FragmentFavoriteCategoryBinding

class FavoriteCategoryFragment : BaseFragment<FragmentFavoriteCategoryBinding>(FragmentFavoriteCategoryBinding::bind, R.layout.fragment_favorite_category), View.OnClickListener {

    private var favoriteCategory: MutableList<Int> = mutableListOf()   // defalut값은 null, 건너뛰기 가능

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: FavoriteCategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickEvent()

        binding.favoriteCategoryBackIv.setOnClickListener {
            backFragment()
        }

        binding.favoriteCategoryNextSelectBtn.setOnClickListener {
            val action = FavoriteCategoryFragmentDirections.actionFavoriteCategoryFragmentToFinishSignupFragment(args.flag, args.contract1, args.contract2, args.contract3, args.contract4, args.email, args.password, args.rePassword, args.nickname)
            findNavController().navigate(action)
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
}