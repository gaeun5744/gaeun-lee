package org.android.go.sopt.remote.remoteData.datasource

import org.android.go.sopt.remote.domain.MainPageRepo
import org.android.go.sopt.remote.remoteData.model.ResponseListUsersDto
import org.android.go.sopt.remote.service.MainPageService
import retrofit2.Response

class MainPageDataSource(private val apiService:MainPageService):MainPageRepo {
    override suspend fun getUserList(): Response<ResponseListUsersDto> {
        return apiService.getListUsers()
    }
}