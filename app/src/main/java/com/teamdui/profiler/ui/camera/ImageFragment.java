package com.teamdui.profiler.ui.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.FragmentImageBinding;

public class ImageFragment extends Fragment {

    ImageView imageView;
    TextView textView;
    private FragmentImageBinding binding;
    private ImageViewModel imageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        imageViewModel =
                new ViewModelProvider(this).get(ImageViewModel.class);
        binding = FragmentImageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageView = binding.hgview;
        textView = binding.textView;

        byte[] bytes = getArguments().getByteArray("img");

        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bmp);
        textView.setText("Food Name");

        return root;
    }
}