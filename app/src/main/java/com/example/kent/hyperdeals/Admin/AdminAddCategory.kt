package com.example.kent.hyperdeals.Admin

import android.content.Intent
import android.support.v4.app.Fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kent.hyperdeals.R
import kotlinx.android.synthetic.main.admin_add_category.*

class AdminAddCategory: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.admin_add_category,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adminaddcategorybutton.setOnClickListener{
            val intent = Intent (this.context,AddCategoryInput::class.java)
            startActivity(intent)
        }

    }
}