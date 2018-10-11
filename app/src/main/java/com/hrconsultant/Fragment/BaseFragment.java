package com.hrconsultant.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrconsultant.utils.Utils;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(this.getFragmentLayout(), container, false);
       // ButterKnife.bind(this, v);
        this.onFragmentReady();
        return v;
    }

    abstract public int getFragmentLayout();
    abstract public void onFragmentReady();

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Utils.hideSoftKeyboard(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.hideSoftKeyboard(getActivity());
    }

}