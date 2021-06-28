package com.teamdui.profiler.ui.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentDashboardBinding;
import com.teamdui.profiler.databinding.FragmentImageBinding;
import com.teamdui.profiler.ui.dashboard.DashboardViewModel;

public class ImageFragment extends Fragment {

    ImageView imageView;
    TextView textView;
    private FragmentImageBinding binding;
    private ImageViewModel imageViewModel;
    public String s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageViewModel =
                new ViewModelProvider(this).get(ImageViewModel.class);
        binding = FragmentImageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//
        imageView = binding.hgview;
        textView = binding.textView;

        byte[] bytes = this.getArguments().getByteArray("img");
//        String st = getArguments().getString("img");
        System.out.println("Here 6890");

//        byte[] bytes = {-1, 3, 2, 3};

        //Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
 //       imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageView.getWidth(), imageView.getHeight(), false));
        //textView.setText("Food Name");
        imageView.animate();
        //imageView.setImageBitmap(bmp);
        return inflater.inflate(R.layout.fragment_image, container, false);
    }


    public void onAttach() {
        s = "OnAttach";
    }

    public void onCreate() {
        s = "On Create";
    }

    public void onViewCreated() {
        s = "On View Created";
    }

    public void onActivityCreated() {
        s = "On Activity Created";
    }


    @Override
    public void onStart() {
        super.onStart();
        s = "On Start";
    }

    @Override
    public void onResume() {
        super.onResume();
        s = "On Resume";
    }

    @Override
    public void onPause() {
        super.onPause();
        s = "On Pause";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        s = "on Destroy View";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        s = "on destroy";

    }

    @Override
    public void onDetach() {
        super.onDetach();
        s = "On Detach";
    }
}