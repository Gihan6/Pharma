package com.gihan.pharma.ui.mainData.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.gihan.pharma.R
import com.gihan.pharma.model.Post
import kotlinx.android.synthetic.main.dialog_add_post.*


class DialogOption(private val action: ListenerDialog) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_post, null, false)
        setupView(view)
        return view
    }

    private fun setupView(view: View) {
        val title = view.findViewById(R.id.et_title) as EditText
        val description = view.findViewById(R.id.et_description) as EditText
        val save = view.findViewById(R.id.btn_save) as Button

        save.setOnClickListener {
            action.save(Post("",title.text.toString(), description.text.toString()))
            dismiss()
        }
    }


}
