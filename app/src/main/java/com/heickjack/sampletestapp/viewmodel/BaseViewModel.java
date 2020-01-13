package com.heickjack.sampletestapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.heickjack.sampletestapp.model.base.BaseResponse;

public class BaseViewModel extends ViewModel {

    protected MutableLiveData<BaseResponse> serverErrorMutableLiveData = new MutableLiveData<>();
    protected MutableLiveData<Throwable> throwableMutableLiveData = new MutableLiveData<>();

    public interface Listener {
        void onError(BaseResponse serverError);

        void onThrowable(Throwable throwable);
    }

    public MutableLiveData<BaseResponse> onError() {
        return serverErrorMutableLiveData;
    }

    public MutableLiveData<Throwable> onThrowable() {
        return throwableMutableLiveData;
    }
}
