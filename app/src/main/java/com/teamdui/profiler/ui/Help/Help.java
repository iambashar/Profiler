package com.teamdui.profiler.ui.Help;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.teamdui.profiler.databinding.HelpFragmentBinding;

public class Help extends Fragment {

    private HelpViewModel mViewModel;
    private HelpFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(HelpViewModel.class);

        binding = HelpFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String textView1 = binding.helptext1.getText().toString();
        String textView2 = binding.helptext2.getText().toString();
        String textView3 = binding.helptext3.getText().toString();
        String textView4 = binding.helptext4.getText().toString();
        String textView5 = binding.helptext5.getText().toString();
        String textView6 = binding.helptext6.getText().toString();

        SpannableString spannableString1 = new SpannableString(textView1);
        SpannableString spannableString2 = new SpannableString(textView2);
        SpannableString spannableString3 = new SpannableString(textView3);
        SpannableString spannableString4 = new SpannableString(textView4);
        SpannableString spannableString5 = new SpannableString(textView5);
        SpannableString spannableString6 = new SpannableString(textView6);

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        spannableString1.setSpan(boldSpan, 31, 43, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString2.setSpan(boldSpan, 118, 124, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString3.setSpan(boldSpan, 28, 40, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString4.setSpan(boldSpan, 53, 66, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString5.setSpan(boldSpan, 40, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString6.setSpan(boldSpan, 44, 51, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        binding.helptext1.setText(spannableString1);
        binding.helptext2.setText(spannableString2);
        binding.helptext3.setText(spannableString3);
        binding.helptext4.setText(spannableString4);
        binding.helptext5.setText(spannableString5);
        binding.helptext6.setText(spannableString6);

        return root;
    }

}