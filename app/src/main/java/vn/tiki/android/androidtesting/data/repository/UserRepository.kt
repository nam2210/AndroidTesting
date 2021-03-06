package vn.tiki.android.androidtesting.data.repository

import android.arch.lifecycle.LiveData
import androidx.lifecycle.switchMap
import vn.tiki.android.androidtesting.data.AppExecutors
import vn.tiki.android.androidtesting.data.NetworkBoundResource
import vn.tiki.android.androidtesting.data.local.LocalStorage
import vn.tiki.android.androidtesting.data.model.Resource
import vn.tiki.android.androidtesting.data.model.User
import vn.tiki.android.androidtesting.data.remote.ApiResponse
import vn.tiki.android.androidtesting.data.remote.ApiServices

class UserRepository(
  private val appExecutors: AppExecutors,
  private val apiServices: ApiServices,
  private val localStorage: LocalStorage
) {

  fun getAccessToken(): LiveData<String> {
    return localStorage.getAccessToken()
  }

  fun login(username: String, password: String): LiveData<Resource<String>> {
    return object : NetworkBoundResource<String, String>(appExecutors) {
      override fun saveCallResult(item: String) {
        localStorage.setAccessToken(item)
      }

      override fun createCall(): LiveData<ApiResponse<String>> {
        return apiServices.login(username, password)
      }
    }.asLiveData()
  }

  fun getUser(): LiveData<Resource<User>> {
    return localStorage.getAccessToken()
      .switchMap { token ->
        object : NetworkBoundResource<User, User>(appExecutors) {
          override fun createCall(): LiveData<ApiResponse<User>> {
            return apiServices.getUser(token)
          }
        }.asLiveData()
      }
  }
}
