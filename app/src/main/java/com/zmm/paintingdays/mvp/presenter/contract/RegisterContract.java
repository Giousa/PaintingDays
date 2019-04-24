package com.zmm.paintingdays.mvp.presenter.contract;


import com.zmm.paintingdays.bean.BaseBean;
import com.zmm.paintingdays.bean.UserBean;
import com.zmm.paintingdays.mvp.view.BaseView;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/10/31
 * Email:65489469@qq.com
 */
public interface RegisterContract {


    interface IRegisterModel{

        Observable<BaseBean<String>> getVerifyCode(String username);

        Observable<BaseBean<UserBean>> register(String username, String password, String code);

        Observable<BaseBean<String>> modifyPassword(String username, String oldPassword, String newPassword);

        Observable<BaseBean<String>> resetPassword(String username, String newPassword, String code);


    }


    interface RegisterView extends BaseView {

        void getVerifyCode(String msg);

        void registerSuccess(UserBean userBean);

        void modifyPasswordSuccess(String msg);
    }

}
