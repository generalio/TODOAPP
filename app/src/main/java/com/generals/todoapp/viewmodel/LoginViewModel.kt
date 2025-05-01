package com.generals.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.model.repository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/1 15:12
 */

class LoginViewModel : ViewModel() {

    private val _livedataUserId : MutableLiveData<Int> = MutableLiveData()
    private val _livedataLoginId : MutableLiveData<Int> = MutableLiveData()
    private val _livedataIsSign : MutableLiveData<Boolean> = MutableLiveData()

    val livedataUserId : LiveData<Int> get() = _livedataUserId
    val livedataLoginId : LiveData<Int> get() = _livedataLoginId
    val livedataIsSign : LiveData<Boolean> get() = _livedataIsSign

    private val compositeDisposable = CompositeDisposable()

    fun sign(username: String) {
        val disposable = LoginRepository.checkSign(username)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .doOnSuccess { mark ->
                _livedataIsSign.postValue(mark)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun login(username: String, password: String) {
        val disposable = LoginRepository.checkUser(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _livedataLoginId.postValue(user.id)
            }, { throwable ->
                _livedataLoginId.postValue(-1)
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${throwable.stackTrace}")
            })
        compositeDisposable.add(disposable)
    }

    fun insertUser(user: User) {
        val disposable = LoginRepository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { userId ->
                _livedataUserId.postValue(userId.toInt())
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}