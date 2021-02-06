package com.ride

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubscribeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BPFragment : BottomSheetDialogFragment() {
    private var btBookNow: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bp_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btBookNow = view.findViewById(R.id.bookNow)

        setUpViews()
    }

    private fun setUpViews() {

        btBookNow!!.setOnClickListener {
            dismissAllowingStateLoss()
            mListener?.onItemClick("1 Scotter Booked")

        }

//        txt_share.setOnClickListener {
//            dismissAllowingStateLoss()
//            mListener?.onItemClick("Share")
//        }
    }

    private var mListener: ItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context as ItemClickListener
        } else {
            throw RuntimeException(
                context.toString()
                    .toString() + " must implement ItemClickListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    interface ItemClickListener {
        fun onItemClick(item: String)
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): BPFragment {
            val fragment = BPFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}