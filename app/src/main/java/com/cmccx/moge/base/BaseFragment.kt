package com.cmccx.moge.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    // fragment 이동하는 메서드
    // 파라메타 형태 : R.id.action_현재fragment_to_목표fragment
    fun moveFragment(resId: Int) {
        findNavController().navigate(resId)
    }

    // fragment 뒤로 이동하는 메서드
    fun backFragment() {
        findNavController().navigateUp()
    }

    fun showDialog(message: String) {
        /** !!!임시!!!
         *  향후 getString(R.string.???)를 활용하여 변경할 것
         *  */
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("제목")
            .setMessage(message)
            .setNegativeButton("취소") { _, _ ->
            }
            .setPositiveButton("ok") { _, _ ->
            }
            .show()
    }

    // 키보드 숨김 메서드
    fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

}