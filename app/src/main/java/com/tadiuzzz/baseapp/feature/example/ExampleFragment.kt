package com.tadiuzzz.baseapp.feature.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tadiuzzz.baseapp.App.Companion.appComponent
import com.tadiuzzz.baseapp.R
import com.tadiuzzz.baseapp.databinding.ExampleFragmentBinding
import com.tadiuzzz.baseapp.di.ViewModelFactory
import javax.inject.Inject

class ExampleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: ExampleFragmentBinding

    private val viewModel: ExampleViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ExampleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.example_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    /**
     * ViewModel передается в binding только после внедрения зависимостей
     *
     * @param savedInstanceState бандл
     */

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        binding.viewmodel = viewModel
        super.onActivityCreated(savedInstanceState)
    }

}
