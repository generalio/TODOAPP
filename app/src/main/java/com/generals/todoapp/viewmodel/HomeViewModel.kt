package com.generals.todoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.ParentTask
import com.generals.todoapp.model.repository.HomeRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @Desc : Home的Viewmodel
 * @Author : zzx
 * @Date : 2025/5/1 21:02
 */

class HomeViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _livedataParentTask : MutableLiveData<List<ParentTask>> = MutableLiveData()
    private val _livedataIsChanged : MutableLiveData<Int> = MutableLiveData()

    val livedataParentTask : LiveData<List<ParentTask>> get() = _livedataParentTask
    val livedataIsChanged : LiveData<Int> get() = _livedataIsChanged

    fun insertParentTask(parentTask: ParentTask) {
        val disposable = HomeRepository.insertParentTask(parentTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it.toInt())
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun updateParentTask(parentTask: ParentTask) {
        val disposable = HomeRepository.updateParentTask(parentTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun finishParentTask(taskId: Int) {
        val disposable = HomeRepository.finishParentTask(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun changeExpandStatus(taskId: Int) {
        val disposable = HomeRepository.changeExpandStatus(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun deleteParentTask(parentTask: ParentTask) {
        val disposable = HomeRepository.deleteParentTask(parentTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun loadAllParentTask(userId: Int) {
        val disposable = HomeRepository.loadAllParentTask(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { parentTaskList ->
                _livedataParentTask.postValue(parentTaskList)
            }, { _ ->
              _livedataParentTask.postValue(listOf())
            } )
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}