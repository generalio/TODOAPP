package com.generals.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.User
import com.generals.todoapp.model.repository.CoinRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @Desc : Coin的ViewModel层
 * @Author : zzx
 * @Date : 2025/5/4 13:49
 */

class CoinViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _livedataUpdate: MutableLiveData<Int> = MutableLiveData()
    private val _livedataUser: MutableLiveData<User> = MutableLiveData()

    val livedataUpdate : LiveData<Int> get() = _livedataUpdate
    val livedataUser : LiveData<User> get() = _livedataUser

    fun updateCoin(userId: Int, coin: Int) {
        val disposable = CoinRepository.updateCoin(userId, coin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataUpdate.postValue(it)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun getUser(userId: Int) {
        val disposable = CoinRepository.getUser(userId)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}