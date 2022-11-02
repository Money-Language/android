package com.cmccx.moge.presentation.view.signup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmccx.moge.R
import com.cmccx.moge.base.BaseFragment
import com.cmccx.moge.data.remote.api.CategoryService
import com.cmccx.moge.data.remote.api.CategoryView
import com.cmccx.moge.data.remote.model.Category
import com.cmccx.moge.databinding.FragmentFavoriteCategoryBinding

class FavoriteCategoryFragment : BaseFragment<FragmentFavoriteCategoryBinding>(FragmentFavoriteCategoryBinding::bind, R.layout.fragment_favorite_category), View.OnClickListener, CategoryView {
    private var favoriteCategory: MutableList<String> = mutableListOf()   // defalut값은 null, 건너뛰기 가능

    // 이전 Fragment에서 넘겨받은 인자들
    private val args: FavoriteCategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("args.jwt", args.jwt)
        Log.d("args.userIdx", args.userIdx.toString())

        setClickEvent()

        binding.favoriteCategoryBackIv.setOnClickListener {
            backFragment()
        }

        binding.favoriteCategoryNextSelectBtn.setOnClickListener {
            CategoryService(this).getCategory(args.jwt, args.userIdx, Category(favoriteCategory))
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
            R.id.favorite_category_new_btn -> saveCategory(view, "1")
            R.id.favorite_category_spelling_btn -> saveCategory(view, "2")
            R.id.favorite_category_nonsense_btn -> saveCategory(view, "3")
            R.id.favorite_category_idiom_btn -> saveCategory(view, "4")
            R.id.favorite_category_word_btn -> saveCategory(view, "5")
        }
    }

    private fun saveCategory(view: View, category: String) {
        if(favoriteCategory.size < 3) {
            view.isSelected = !view.isSelected

            setCheckButton(view.isSelected, category)

            if(view.isSelected) favoriteCategory.add(category)
            else favoriteCategory.remove(category)

        } else if (favoriteCategory.size == 3 && view.isSelected) {
            favoriteCategory.remove(category)
            view.isSelected = false

            setCheckButton(view.isSelected, category)
        }
        Log.d("category", favoriteCategory.toString())
        checkValidCategory()
    }

    private fun setCheckButton(isSelected: Boolean, category: String) {
        if (isSelected) {
            when (category) {
                "1" -> {
                    binding.favoriteCategoryNewIv.visibility = View.VISIBLE
                    binding.favoriteCategoryNewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                "2" -> {
                    binding.favoriteCategorySpellingIv.visibility = View.VISIBLE
                    binding.favoriteCategorySpellingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                "3" -> {
                    binding.favoriteCategoryNonsenseIv.visibility = View.VISIBLE
                    binding.favoriteCategoryNonsenseTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                "4" -> {
                    binding.favoriteCategoryIdiomIv.visibility = View.VISIBLE
                    binding.favoriteCategoryIdiomTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                "5" -> {
                    binding.favoriteCategoryWordIv.visibility = View.VISIBLE
                    binding.favoriteCategoryWordTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
            }
        } else {
            when (category) {
                "1" -> {
                    binding.favoriteCategoryNewIv.visibility = View.GONE
                    binding.favoriteCategoryNewTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                "2" -> {
                    binding.favoriteCategorySpellingIv.visibility = View.GONE
                    binding.favoriteCategorySpellingTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                "3" -> {
                    binding.favoriteCategoryNonsenseIv.visibility = View.GONE
                    binding.favoriteCategoryNonsenseTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                "4" -> {
                    binding.favoriteCategoryIdiomIv.visibility = View.GONE
                    binding.favoriteCategoryIdiomTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_brown))
                }
                "5" -> {
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

    override fun onGetCategoryResultSuccess() {
        val action = FavoriteCategoryFragmentDirections.actionFavoriteCategoryFragmentToFinishSignupFragment(args.nickname)
        findNavController().navigate(action)
    }

    override fun onGetCategoryResultFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}