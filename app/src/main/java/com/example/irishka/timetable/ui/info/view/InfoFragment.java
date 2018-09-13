package com.example.irishka.timetable.ui.info.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.irishka.timetable.BuildConfig;
import com.example.irishka.timetable.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.DaggerFragment;

public class InfoFragment extends Fragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.btn_home)
    ImageButton homeBtn;

    @BindView(R.id.tvVersion)
    TextView versionTv;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, v);

        toolbarTitle.setText("О приложении");

        homeBtn.setOnClickListener(view -> getActivity().onBackPressed());

        String versionInfo = getResources().getString(R.string.about_version, BuildConfig.VERSION_NAME);

        versionTv.setText(versionInfo);

        return v;
    }
}
