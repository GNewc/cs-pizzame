package com.newcomb.pizzame.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.newcomb.pizzame.model.PizzaOption;

/**
 * ViewModel implementation for our currently selected pizza joint
 */

public class PizzaDetailViewModel extends ViewModel {
    private MutableLiveData<PizzaOption> selected = new MutableLiveData<>();

    public void select(PizzaOption option) {
        selected.setValue(option);
    }

    public LiveData<PizzaOption> getSelected() {
        return selected;
    }
}
