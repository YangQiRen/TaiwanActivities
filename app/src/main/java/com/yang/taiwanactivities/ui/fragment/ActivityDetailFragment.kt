package com.yang.taiwanactivities.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.squareup.picasso.Picasso
import com.yang.taiwanactivities.R
import com.yang.taiwanactivities.data.factory.MainFactory
import com.yang.taiwanactivities.data.repository.MainRepository
import com.yang.taiwanactivities.databinding.FragmentActivityDetailBinding
import com.yang.taiwanactivities.ui.viewmodel.MainViewModel
import com.yang.taiwanactivities.util.formatToServerDateTimeDefaults

class ActivityDetailFragment : Fragment() {

    private lateinit var mainFactory: MainFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var binding: FragmentActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainRepository = MainRepository()
        mainFactory = MainFactory(mainRepository)
        mainViewModel =
            ViewModelProvider(requireActivity(), mainFactory).get(MainViewModel::class.java)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentActivityDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver() {
        mainViewModel.selectedInfo.observe(viewLifecycleOwner, { info ->
            binding.ivPicture.transitionName = info.Id

            Picasso.get().load(info.Picture1).error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).fit().centerCrop().into(binding.ivPicture)
            val strTime =
                info.Start.formatToServerDateTimeDefaults() + "~" + info.End.formatToServerDateTimeDefaults()
            binding.tvName.text = info.Name
            binding.tvTime.text = strTime
            binding.tvAddr.text = info.Location + " " + info.Add
            binding.tvDescrption.text = info.Description
        })
    }
}