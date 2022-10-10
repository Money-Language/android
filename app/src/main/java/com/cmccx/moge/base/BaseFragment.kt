package com.cmccx.moge.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.cmccx.moge.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.System.exit

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

}