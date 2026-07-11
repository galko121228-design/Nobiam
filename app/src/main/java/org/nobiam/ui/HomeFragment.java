package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import org.nobiam.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Кнопка Launch теперь называется btnLaunch
        View btnLaunch = view.findViewById(R.id.btnLaunch);
        btnLaunch.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
