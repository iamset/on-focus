package com.focusg.focusgroup.presentation.focus_groups

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.focusg.focusgroup.R
import android.view.MenuInflater









class FocusGroupListFragment : Fragment() {
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_focus_group_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.action_create).isVisible = true
        menu.findItem(R.id.action_logout).isVisible = true
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun showUpButton(){
        toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    }


}