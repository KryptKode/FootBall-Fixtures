package com.kryptkode.footballfixtures.app.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class InfoDialog : DialogFragment() {
    private var title: String? = null
    private var message: String? = null
    private var buttonText: String? = null
    private var neutralButtonText: String? = null
    private var hasNeutralButton: Boolean? = false
    private var listener: InfoListener? = null
    private var neutralListener: NeutralListener? = null

    companion object {
        private const val TITLE = "title"
        private const val MESSAGE = "message"
        private const val BUTTON_TEXT = "button_text"
        private const val NEUTRAL_BUTTON = "neutral_button"
        private const val NEUTRAL_BUTTON_TEXT = "neutral_button_text"

        @JvmStatic
        fun newInstance(
            title: String?,
            message: String?,
            buttonText: String? = null,
            listener: InfoListener? = null,
            hasNeutralButton: Boolean = false,
            neutralButtonText: String? = null,
            neutralListener: NeutralListener? = null
        ): InfoDialog {
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putString(MESSAGE, message)
            bundle.putString(BUTTON_TEXT, buttonText)
            bundle.putString(NEUTRAL_BUTTON_TEXT, neutralButtonText)
            bundle.putBoolean(NEUTRAL_BUTTON, hasNeutralButton)
            val fragment = InfoDialog()
            fragment.arguments = bundle
            fragment.listener = listener
            fragment.neutralListener = neutralListener
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString(TITLE)
        message = arguments?.getString(MESSAGE)
        buttonText = arguments?.getString(BUTTON_TEXT)
        neutralButtonText = arguments?.getString(NEUTRAL_BUTTON_TEXT)
        hasNeutralButton = arguments?.getBoolean(NEUTRAL_BUTTON)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(buttonText) { _, _ ->
                listener?.onConfirm()
            }
        if (hasNeutralButton == true) {
            builder.setNegativeButton(neutralButtonText) { _, _ ->
                neutralListener?.onNeutralClick()
            }
        }
        return builder.create()
    }


    interface InfoListener {
        fun onConfirm()
    }

    interface NeutralListener {
        fun onNeutralClick()
    }

}