package com.zmm.paintingdays.rx.subscriber;

import com.zmm.paintingdays.rx.RxErrorHandler;
import com.zmm.paintingdays.rx.exception.BaseException;

import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:上午11:48
 */

public abstract class ErrorHandlerSubscriber<T> extends DefaultSubscriber<T> {


    protected RxErrorHandler mRxErrorHandler;

    public ErrorHandlerSubscriber(){

        mRxErrorHandler = new RxErrorHandler();

    }

    @Override
    public void onError(Throwable e) {

        BaseException baseException =  mRxErrorHandler.handlerError(e);

        if(baseException == null){
            mRxErrorHandler.showErrorMessage(new BaseException(404,"服务器繁忙，请稍后再试"));
            e.printStackTrace();
        } else {
            mRxErrorHandler.showErrorMessage(baseException);
        }

    }


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
