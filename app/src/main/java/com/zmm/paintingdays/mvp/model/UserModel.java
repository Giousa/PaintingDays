package com.zmm.paintingdays.mvp.model;


import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.http.ApiService;
import com.zmm.paintingdays.mvp.presenter.contract.UserContract;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public class UserModel implements UserContract.IUserModel {

    private ApiService mApiService;

    public UserModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<UserBean>> uploadIcon(String id,String username, MultipartBody.Part file) {
        return mApiService.uploadIcon(id,username,file);
    }

    @Override
    public Observable<BaseBean<UserBean>> findUserById(String id) {
        return mApiService.findUserById(id);
    }

    @Override
    public Observable<BaseBean<String>> deleteUserById(String id) {
        return mApiService.deleteUserById(id);
    }

    @Override
    public Observable<BaseBean<UserBean>> updateUserBean(UserBean userBean) {
        return mApiService.updateUserBean(userBean);
    }
}
