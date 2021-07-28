package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.databinding.FragmentActivityDetailBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel
import com.yang.taiwanactivities.util.formatToServerDateTimeDefaults

class ActivityDetailFragment : Fragment(R.layout.fragment_activity_detail) {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private var _binding: FragmentActivityDetailBinding? = null
    private val binding: FragmentActivityDetailBinding get() = _binding!!
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        _binding = FragmentActivityDetailBinding.bind(view)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel =
            ViewModelProvider(requireActivity(), mainFactory).get(MainViewModel::class.java)

        initObserver()
    }

    private fun initObserver() {
        mainViewModel.selectInfoEvent.observe(viewLifecycleOwner, { info ->
            Picasso.get().load(info.Picture1).into(binding.ivPicture, object : Callback{
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }

            })
            val strTime =
                info.Start.formatToServerDateTimeDefaults() + "~" + info.End.formatToServerDateTimeDefaults()
            binding.tvName.text = info.Name
            binding.tvTime.text = strTime
            binding.tvAddr.text = info.Location + " " + info.Add
            binding.tvDescrption.text = info.Description
        })
        mainViewModel.selectedPosition.observe(viewLifecycleOwner, {
            position = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}