package com.generals.todoapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.generals.todoapp.model.bean.ChildTask
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
    private val _livedataChildTask : MutableLiveData<List<ChildTask>> = MutableLiveData()

    val livedataParentTask : LiveData<List<ParentTask>> get() = _livedataParentTask
    val livedataIsChanged : LiveData<Int> get() = _livedataIsChanged
    val livedataChildTask : LiveData<List<ChildTask>> get() = _livedataChildTask

    fun insertParentTask(parentTask: ParentTask) {
        val disposable = HomeRepository.insertParentTask(parentTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it.toInt())
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
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
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
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
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
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
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
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

    fun insertChildTask(childTask: ChildTask) {
        val disposable = HomeRepository.insertChildTask(childTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it.toInt())
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun updateChildTask(childTask: ChildTask) {
        val disposable = HomeRepository.updateChildTask(childTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun finishChildTask(taskId: Int) {
        val disposable = HomeRepository.finishChildTask(taskId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun deleteChildTask(childTask: ChildTask) {
        val disposable = HomeRepository.deleteChildTask(childTask)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun loadAllChildTask(parentId: Int, userId: Int) {
        val disposable = HomeRepository.loadAllChildTask(parentId, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( { childTask ->
                _livedataChildTask.postValue(childTask)
            } , {
                _livedataChildTask.postValue(listOf())
            })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}