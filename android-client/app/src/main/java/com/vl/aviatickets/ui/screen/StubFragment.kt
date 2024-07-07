package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vl.aviatickets.databinding.FragmentStubBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StubFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentStubBinding.inflate(inflater, container, false).root
}