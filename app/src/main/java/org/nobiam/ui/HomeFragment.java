package org.nobiam.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        int accentColor = AccentColorManager.getAccentColor(requireContext());

        // Заголовок "Minecraft"
        TextView minecraftTitle = view.findViewById(R.id.minecraft_title_text);
        if (minecraftTitle != null) {
            minecraftTitle.setTextColor(accentColor);
        }

        // Кнопка Launch — tint
        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        }

        // Current Instance selector
        LinearLayout selectVersion = view.findViewById(R.id.select_version_button);
        if (selectVersion != null) {
            selectVersion.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Instance selector (coming soon)", Toast.LENGTH_SHORT).show();
            });
        }

        launchButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
