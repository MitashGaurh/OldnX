package com.oldandx.oldnx.api;

import android.arch.lifecycle.LiveData;

import com.oldandx.oldnx.vo.User;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mitash Gaurh on 4/18/2018.
 */
public interface OldnXService {

    @GET("users/{login}")
    LiveData<ApiResponse<User>> getUser(@Path("login") String login);

}
