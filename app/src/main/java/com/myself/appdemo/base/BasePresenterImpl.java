package com.myself.appdemo.base;

import com.myself.mylibrary.mvp.base.IInteractor;
import com.myself.mylibrary.mvp.base.IView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by riven_chris on 16/7/6.
 */
public abstract class BasePresenterImpl<V extends IView, I extends IInteractor> {

    protected V mView;
    protected I mInteractor;
    protected CompositeSubscription subscriptions;

    public BasePresenterImpl(V view) {
        mView = view;
        mInteractor = createInteractor();
        subscriptions = new CompositeSubscription();
    }

    protected abstract I createInteractor();

    public void unSubscribe() {
        mInteractor.unSubscribe();
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
    }

    public void onDestroy() {
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
        if (mInteractor != null) {
            mInteractor.onDestroy();
            mInteractor = null;
        }
        mView = null;
    }
}
