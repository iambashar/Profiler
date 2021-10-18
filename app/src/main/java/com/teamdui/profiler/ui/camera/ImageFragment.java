package com.teamdui.profiler.ui.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.MainActivity;
import com.teamdui.profiler.R;
import com.teamdui.profiler.databinding.FragmentImageBinding;
import com.teamdui.profiler.ui.dailycalorie.DailyMealFragment;
import com.teamdui.profiler.ui.dailycalorie.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static com.teamdui.profiler.MainActivity.calorieDaily;
import static com.teamdui.profiler.MainActivity.date;
import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class ImageFragment extends Fragment {

    ImageView imageView;
    private FragmentImageBinding binding;
    private ImageViewModel imageViewModel;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    ArrayList<String> classes = new ArrayList<String>();
    ArrayList<String> calories = new ArrayList<String>();


    RecyclerView foodRecyclerView;
    LinearLayoutManager foodLayoutManager;
    AdapterCalorie adapterCalorie;
    AppCompatButton addButton;
    public Integer calorieDailyImage = 0;
    public static TextView calorieUpperTextImage;
    public DailyMealFragment dailyMealFragment = new DailyMealFragment();
    public static ArrayList<Food> foodListImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(policy);
        imageViewModel =
                new ViewModelProvider(this).get(ImageViewModel.class);
        binding = FragmentImageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        foodListImage = new ArrayList<>();
        imageView = binding.hgview;
        foodRecyclerView = binding.calorieList;
        addButton = binding.addCalorieButton;
        calorieUpperTextImage = binding.calorieUpperText;
        calorieUpperTextImage.setText(((Integer) calorieDaily).toString());

        byte[] bytes = getArguments().getByteArray("img");

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        imageView.setRotation((float) 90);
        imageView.setImageBitmap(bitmap);

        String attachmentName = "file";
        String attachmentFileName = "file.bmp";
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        HttpURLConnection conn = null;

        URL url;
        try {
            url = new URL(MainActivity.uri);
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        try {
            conn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        conn.setRequestProperty("Accept", "application/json");
        DataOutputStream request = null;
        try {
            request = new DataOutputStream(conn.getOutputStream());
            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    attachmentName + "\";filename=\"" +
                    attachmentFileName + "\"" + crlf);
            request.writeBytes(crlf);
            while (bytes == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            request.write(bytes);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            conn.connect();
            request.flush();
            request.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            JSONObject js = new JSONObject(sb.toString());
            JSONArray jsonArray = js.getJSONArray("calorie");
            int len = jsonArray.length();

            for (int i = 0; i < len; i++) {
                calories.add(jsonArray.get(i).toString());
            }

            jsonArray = js.getJSONArray("class_name");

            for (int i = 0; i < len; i++) {
                classes.add(jsonArray.get(i).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conn.disconnect();

        for (int i = 0; i < classes.size(); i++) {
            calorieDailyImage = calorieDailyImage + Integer.valueOf(calories.get(i));
            initFoodList(classes.get(i), calories.get(i));
            initRecyclerView();
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calorieDaily += calorieDailyImage;
                myRef.child(uid).child("date").child(date).child("progress").child("cal").setValue(calorieDaily);
                calorieUpperTextImage.setText(((Integer) calorieDaily).toString());
                for (int i = 0; i < classes.size(); i++) {
                    dailyMealFragment.initFoodList(classes.get(i), calories.get(i));
                }
            }
        });

        return root;
    }

    public void initFoodList(String food, String calorie) {
        foodListImage.add(new Food(food, calorie, R.drawable.ic_minus, "df"));
    }

    public void initRecyclerView() {
        adapterCalorie = new AdapterCalorie(foodListImage);
        foodLayoutManager = new LinearLayoutManager(this.getContext());
        foodLayoutManager.setOrientation(RecyclerView.VERTICAL);
        foodRecyclerView.setLayoutManager(foodLayoutManager);
        foodRecyclerView.setAdapter(adapterCalorie);
        adapterCalorie.notifyDataSetChanged();
    }

    public void reduceCalorie(String toReduce) {
        toReduce = toReduce.replace(" cal", "");
        calorieDailyImage = calorieDailyImage - Integer.parseInt(toReduce);
    }
}