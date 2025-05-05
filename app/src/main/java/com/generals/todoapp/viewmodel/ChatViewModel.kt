package com.generals.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.ChatResponse
import com.generals.todoapp.model.repository.ChatRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @Desc : Chat的ViewModel
 * @Author : zzx
 * @Date : 2025/5/3 00:09
 */

class ChatViewModel : ViewModel() {

    private val _livedataChatResponse : MutableLiveData<ChatResponse> = MutableLiveData()

    val livedataChatResponse : LiveData<ChatResponse> get() = _livedataChatResponse

    private val compositeDisposable = CompositeDisposable()

    fun chat(message: String) {
        val disposable = ChatRepository.chat(message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { response ->
                _livedataChatResponse.postValue(response)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
                _livedataChatResponse.postValue(ChatResponse("请求失败",502,"","",""))
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}