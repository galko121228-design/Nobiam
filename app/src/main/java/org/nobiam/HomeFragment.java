package org.nobiam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button playButton = view.findViewById(R.id.play_button); // ВАЖНО

        if (playButton != null) {
            playButton.setOnClickListener(v -> {
                Log.d("NOBIAM", "Кнопка нажата");

                Intent intent = new Intent();
                intent.setClassName(
                        "com.mojang.minecraftpe",
                        "com.mojang.minecraftpe.MainActivity"
                );

                startActivity(intent);
            });
        }

        return view;
    }
}
