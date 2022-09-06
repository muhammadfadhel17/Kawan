package com.fadhel.kawan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener

class ForgotPassword2Activity : AppCompatActivity() {

    companion object {
        private const val TEST_VERIFY_CODE = "123456"
    }

    private val linearLayoutRoot: LinearLayout by lazy {
        findViewById(R.id.linerLayoutRoot)
    }

    private val edtCode1: EditText by lazy {
        findViewById(R.id.edtCode1)
    }

    private val edtCode2: EditText by lazy {
        findViewById(R.id.edtCode2)
    }

    private val edtCode3: EditText by lazy {
        findViewById(R.id.edtCode3)
    }

    private val edtCode4: EditText by lazy {
        findViewById(R.id.edtCode4)
    }

    private val edtCode5: EditText by lazy {
        findViewById(R.id.edtCode5)
    }

    private val edtCode6: EditText by lazy {
        findViewById(R.id.edtCode6)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password2)

        setListener()

        initFocus()
    }

    private fun setListener() {
        linearLayoutRoot.setOnClickListener {
            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(linearLayoutRoot.windowToken, 0)
        }

        setTextChangeListener(fromEditText = edtCode1, targetEditText = edtCode2)
        setTextChangeListener(fromEditText = edtCode2, targetEditText = edtCode3)
        setTextChangeListener(fromEditText = edtCode3, targetEditText = edtCode4)
        setTextChangeListener(fromEditText = edtCode4, targetEditText = edtCode5)
        setTextChangeListener(fromEditText = edtCode5, targetEditText = edtCode6)
        setTextChangeListener(fromEditText = edtCode6, done = {
            verifyOTPCode()
        })

        setKeyListener(fromEditText = edtCode2, backToEditText = edtCode1)
        setKeyListener(fromEditText = edtCode3, backToEditText = edtCode2)
        setKeyListener(fromEditText = edtCode4, backToEditText = edtCode3)
        setKeyListener(fromEditText = edtCode5, backToEditText = edtCode4)
        setKeyListener(fromEditText = edtCode6, backToEditText = edtCode5)
    }

    private fun initFocus() {
        edtCode1.isEnabled = true

        edtCode1.postDelayed({
            edtCode1.requestFocus()
            val inpitMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inpitMethodManager.showSoftInput(edtCode1, InputMethodManager.SHOW_FORCED)
        },500)
    }

    private fun reset() {

        edtCode1.isEnabled = false
        edtCode2.isEnabled = false
        edtCode3.isEnabled = false
        edtCode4.isEnabled = false
        edtCode5.isEnabled = false
        edtCode6.isEnabled = false

        edtCode1.setText("")
        edtCode2.setText("")
        edtCode3.setText("")
        edtCode4.setText("")
        edtCode5.setText("")
        edtCode6.setText("")

        initFocus()
    }

    private fun setTextChangeListener(
        fromEditText: EditText,
        targetEditText: EditText? = null,
        done: (() -> Unit)? = null
    ) {
        fromEditText.addTextChangedListener {
            it?.let { string ->
                if (string.isNotEmpty()) {

                    targetEditText?.let { editText ->

                        editText.isEnabled = true
                        editText.requestFocus()

                    } ?: run {
                        done ?.let { done ->
                            done()
                        }
                    }
                    fromEditText.clearFocus()
                    fromEditText.isEnabled = false
                }
            }
        }
    }

    private fun setKeyListener(fromEditText: EditText, backToEditText: EditText) {
        fromEditText.setOnKeyListener { _,_, event ->

            if (null != event && KeyEvent.KEYCODE_DEL == event.keyCode) {
                backToEditText.isEnabled = true
                backToEditText.requestFocus()
                backToEditText.setText("")

                fromEditText.clearFocus()
                fromEditText.isEnabled = false
            }

            false
        }
    }

    private fun verifyOTPCode() {
        val otpCode = "${edtCode1.text.toString().trim()}" +
                "${edtCode2.text.toString().trim()}" +
                "${edtCode3.text.toString().trim()}" +
                "${edtCode4.text.toString().trim()}" +
                "${edtCode5.text.toString().trim()}" +
                "${edtCode6.text.toString().trim()}"
        if (6 != otpCode.length) {
            return
        }

        if (otpCode == TEST_VERIFY_CODE) {

            Toast.makeText(this, "Verify Kode Sukses", Toast.LENGTH_LONG).show()

            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(linearLayoutRoot.windowToken, 0)

            return
        }

        Toast.makeText(this,"error Code, Silahkan Input Lagi", Toast.LENGTH_SHORT).show()
        reset()
    }
}