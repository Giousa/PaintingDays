package com.zmm.paintingdays.mvp.presenter.contract;


import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.mvp.view.BaseView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface UserContract {


    interface IUserModel{

        Observable<BaseBean<UserBean>> uploadIcon(String id, String username,MultipartBody.Part file);

        Observable<BaseBean<UserBean>> findUserById(String id);

        Observable<BaseBean<String>> deleteUserById(String id);

        Observable<BaseBean<UserBean>> updateUserBean(UserBean userBean);



    }


    interface UserView extends BaseView {

        void updateSuccess(UserBean userBean);

        void updateFailure();

        void deleteSuccess();
        
    }

}
