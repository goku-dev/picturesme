package com.tapi.picturesme.functions.m001home.screen

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapi.picturesme.activity.HomeActivity
import com.tapi.picturesme.core.database.DownLoadPhoto
import com.tapi.picturesme.core.server.ApiService
import com.tapi.picturesme.functions.m001home.PhotoItemView
import kotlinx.coroutines.launch

open class HomeViewModel : ViewModel() {
    val TAG = HomeViewModel::class.java

    private var _images: MutableLiveData<List<PhotoItemView>> = MutableLiveData()
    val images: LiveData<List<PhotoItemView>>
        get() = _images

    private var currentPage = 1

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun getListPhoto(): LiveData<List<PhotoItemView>>? {
        try {
            Log.d("TAG", "loadMore: API getListPhoto calling....... ")
            viewModelScope.launch {
                _loading.value = true
                _images.postValue(ApiService.retrofitService.getPictures(page = currentPage).map {
                    Log.d("TAG", "comfirmPhoto: $it")
                    if (DownLoadPhoto().isDownloaded(it)) {
                        PhotoItemView(it, true)

                    } else {
                        PhotoItemView(it, false)
                    }
                })
                _loading.value = false
            }
        } catch (e: Exception) {
            return null
        }
        return images
    }


    fun getListPhotoByPage(page: Int): LiveData<List<PhotoItemView>>? {
        try {
            Log.d("TAG", "loadMore : API getListPhotoByPage calling....... ")

            viewModelScope.launch {
                _loading.value = true
                _images.postValue(ApiService.retrofitService.getPictures(page = page).map {

                    Log.d("TAG", "comfirmPhoto: $it")
                    if (DownLoadPhoto().isDownloaded(it)) {
                        PhotoItemView(it, true)

                    } else {
                        PhotoItemView(it, false)
                    }
                })
                _loading.value = false
            }
        } catch (e: Exception) {
            return null
        }
        return images
    }


    fun getIsloading(): LiveData<Boolean> {
        return loading
    }

    fun loadMore(): Int {
        currentPage++
        try {
            viewModelScope.launch {

                callGetPhotoToLoadMore(currentPage)

            }
        } catch (e: Exception) {
   e.printStackTrace()
        }

        return currentPage

    }

    suspend fun callGetPhotoToLoadMore(currentPage: Int): LiveData<List<PhotoItemView>>? {
        Log.d("TAG", "loadMore: API loadMore calling....... ")
        _loading.value = true
        val moreList = ApiService.retrofitService.getPictures(page = currentPage).map {
            if (DownLoadPhoto().isDownloaded(it)) {
                PhotoItemView(it, true)

            } else {
                PhotoItemView(it, false)
            }
        }
        val currList = _images.value?.toMutableList()
        currList?.let {
            currList.addAll(moreList)
            _images.value = currList
        }

        _loading.value = false
        return _images
    }

}




