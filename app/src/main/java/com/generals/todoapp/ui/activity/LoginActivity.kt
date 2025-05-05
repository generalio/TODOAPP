package com.generals.todoapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentTransaction
import com.generals.todoapp.R
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.ui.custom.CustomDialog
import com.generals.todoapp.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : BaseActivity() {

    private lateinit var mEtAccount: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mTilAccount: TextInputLayout
    private lateinit var mTilPassword: TextInputLayout
    private lateinit var mBtnLogin: Button
    private lateinit var mBtnSign: Button

    private lateinit var signView: View
    private lateinit var mEtSignUsername: EditText
    private lateinit var mEtSignPassword: EditText
    private lateinit var mEtSignRePassword: EditText

    private val viewmodel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initEvent()

    }

    private fun initView() {
        mEtAccount = findViewById(R.id.et_login_account)
        mEtPassword = findViewById(R.id.et_login_password)
        mTilAccount = findViewById(R.id.til_login_account)
        mTilPassword = findViewById(R.id.til_login_password)
        mBtnLogin = findViewById(R.id.btn_login_login)
        mBtnSign = findViewById(R.id.btn_login_sign)
    }

    private fun initEvent() {
        val sharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val getUsername = sharedPreferences.getString("username","").toString()
        val getPassword = sharedPreferences.getString("password","").toString()
        if(getUsername != "" && getPassword != "") {
            viewmodel.login(getUsername, getPassword)
        }
        mEtAccount.addTextChangedListener {
            if (mEtAccount.text.toString() != "") {
                mTilAccount.isErrorEnabled = false
            }
        }
        mEtPassword.addTextChangedListener {
            if (mEtPassword.text.toString() != "") {
                mTilPassword.isErrorEnabled = false
            }
        }
        mBtnLogin.setOnClickListener {
            if (mEtAccount.text.toString() == "") {
                mTilAccount.setError("账户名不能为空")
            } else {
                if (mEtPassword.text.toString() == "") {
                    mTilPassword.setError("密码不能为空")
                } else {
                    val username = mEtAccount.text.toString()
                    val password = mEtPassword.text.toString()
                    viewmodel.login(username, password)
                }
            }
        }
        viewmodel.livedataLoginId.observe(this) {id ->
            if(id == -1) {
                "账号/密码错误".showDialog("登录失败!") {
                    clearInput()
                }
            } else {
                "登录成功".showToast()
                sharedPreferences.edit {
                    putString("username",mEtAccount.text.toString())
                    putString("password",mEtPassword.text.toString())
                }
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("userId", id)
                startActivity(intent)
            }
        }
        mBtnSign.setOnClickListener {
            sign()
        }
    }

    @SuppressLint("InflateParams")
    private fun sign() {
        signView = LayoutInflater.from(this).inflate(R.layout.layout_sign, null)!!
        val dialog = CustomDialog()
            .newInstance()
            .setDialogHeight(1000)
            .setCustomView(signView)
        val ft: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE) //设置过渡动画
        dialog.show(ft, "DialogMore") //开启bottomSheetDialog

        mEtSignUsername = signView.findViewById(R.id.et_sign_account)
        mEtSignPassword = signView.findViewById(R.id.et_sign_password)
        mEtSignRePassword = signView.findViewById(R.id.et_sign_rePassword)
        val mTilUsername : TextInputLayout= signView.findViewById(R.id.til_sign_account)
        val mTilPassword : TextInputLayout = signView.findViewById(R.id.til_sign_password)
        val mTilRePassword : TextInputLayout = signView.findViewById(R.id.til_sign_rePassword)
        val mBtnSign : Button= signView.findViewById(R.id.btn_sign_sign)
        signView.findViewById<ImageView>(R.id.iv_sign_close).setOnClickListener {
            dialog.dismiss()
        }

        mEtSignUsername.addTextChangedListener {
            mTilUsername.isErrorEnabled = false
        }
        mEtSignPassword.addTextChangedListener {
            mTilPassword.isErrorEnabled = false
        }
        mEtSignRePassword.addTextChangedListener {
            mTilRePassword.isErrorEnabled = false
        }
        var username = mEtSignUsername.text.toString()
        var password = mEtSignPassword.text.toString()
        mBtnSign.setOnClickListener {
            username = mEtSignUsername.text.toString()
            password = mEtSignPassword.text.toString()
            val rePassword = mEtSignRePassword.text.toString()
            if (username == "") {
                mTilUsername.setError("用户名不能为空!")
            } else {
                if (password == "") {
                    mTilPassword.setError("密码不能为空!")
                } else {
                    if (rePassword == "") {
                        mTilRePassword.setError("请再次输入密码!")
                    } else {
                        if (password != rePassword) {
                            mTilRePassword.setError("两次密码输入不相同!")
                        } else {
                            viewmodel.sign(username)
                        }
                    }
                }
            }
        }
        viewmodel.livedataIsSign.observe(this) { isSign ->
            if(isSign) {
                "账号已经注册".showDialog("注册失败") {
                    mEtSignUsername.setText("")
                    mEtSignPassword.setText("")
                    mEtSignRePassword.setText("")
                }
            } else {
                viewmodel.insertUser(User(username, password))
            }
        }
        viewmodel.livedataUserId.observe(this) {
            "注册成功,请返回登录!".showDialog("") {
                mEtAccount.setText(mEtSignUsername.text)
                mEtPassword.setText("")
                signView.visibility = View.GONE
            }
        }
    }

    //清除输入框的数据
    private fun clearInput() {
        mEtAccount.setText("")
        mEtPassword.setText("")
    }

}