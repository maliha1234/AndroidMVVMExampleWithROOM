package com.example.demoLogin.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.demoLogin.api.LoginApi;
import com.example.demoLogin.data.model.LoggedInUser;
import com.example.demoLogin.service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;


    private LoginApi loginApi;
    final LoginUser result = new LoginUser();
    private final static MutableLiveData<LoginUser> loginResult = new MutableLiveData<>();

    public LiveData<LoginUser> getLoginResult() {
        return loginResult;
    }

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;



    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        loginApi = LoginService.createService(LoginApi.class);
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
    public LoginUser getResult() {
        return result;
    }

    public void login(LoginUser loginUser) {
        // handle login

        //result.setJob(loginUser.getJob());
       // result.setName(loginUser.getName());


        loginApi.createPost(loginUser).enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                // this method is called when we get response from our api.
                if (response.isSuccessful()){
                    Log.d("response", "onResponse: "+ response.body().getName());

                    result.setName(response.body().getJob());
                    result.setJob(response.body().getJob());
                    Log.d("responsehhhh", "onResponse: "+ result.getName());
                    loginResult.setValue(result);


                }

            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {



            }
        });

    }
}