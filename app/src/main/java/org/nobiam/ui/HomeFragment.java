package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import org.nobiam.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Кнопка Current Instance (открывает выбор)
        MaterialButton btnCurrentInstance = view.findViewById(R.id.btnCurrentInstance);
        if (btnCurrentInstance != null) {
            btnCurrentInstance.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Instance selector (coming soon)", Toast.LENGTH_SHORT).show();
            });
        }

        // Кнопка Launch
        View btnLaunch = view.findViewById(R.id.btnLaunch);
        if (btnLaunch != null) {
            btnLaunch.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }
}
