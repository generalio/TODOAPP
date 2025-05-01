package com.generals.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.model.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @Desc : 类的描述
 * @Author : zzx
 * @Date : 2025/5/1 16:35
 */

class MainViewModel : ViewModel() {

    private val _livedataUser: MutableLiveData<User> = MutableLiveData()

    val livedataUser : LiveData<User> get() = _livedataUser

    private val compositeDisposable = CompositeDisposable()

    fun setUser(userId: Int) {
        val disposable = MainRepository.getUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { user ->
                _livedataUser.postValue(user)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

}