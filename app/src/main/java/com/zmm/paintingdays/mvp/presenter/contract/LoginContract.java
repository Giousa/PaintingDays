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
public interface LoginContract {


    interface ILoginModel{
        Observable<BaseBean<UserBean>> login(String username, String password);

    }


    interface LoginView extends BaseView {

        void loginSuccess(UserBean userBean);
    }

}
