package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import org.nobiam.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Fade-in анимация для всех карточек
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);

        LinearLayout mainCard = view.findViewById(R.id.main_card);
        LinearLayout modsCard = view.findViewById(R.id.mods_card);
        LinearLayout contentMgmtCard = view.findViewById(R.id.content_mgmt_card);
        LinearLayout miscCard = view.findViewById(R.id.misc_card);

        if (mainCard != null) mainCard.startAnimation(fadeIn);
        if (modsCard != null) modsCard.startAnimation(fadeIn);
        if (contentMgmtCard != null) contentMgmtCard.startAnimation(fadeIn);
        if (miscCard != null) miscCard.startAnimation(fadeIn);

        // Current Instance selector
        LinearLayout selectVersion = view.findViewById(R.id.select_version_button);
        if (selectVersion != null) {
            selectVersion.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Instance selector (coming soon)", Toast.LENGTH_SHORT).show();
            });
        }

        // Launch button
        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show();
            });
        }

        return view;
    }
}
