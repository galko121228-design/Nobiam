package org.nobiam.ui;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
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

        // Кнопка Launch
        Button launchButton = view.findViewById(R.id.launch_button);
        if (launchButton != null) {
            launchButton.setBackgroundTintList(ColorStateList.valueOf(accentColor));
        }

        // Mods
        TextView modsTitle = view.findViewById(R.id.mods_title);
        if (modsTitle != null) {
            modsTitle.setTextColor(accentColor);
            tintDrawableStart(modsTitle, accentColor);
        }

        Button manageMods = view.findViewById(R.id.manage_mods_button);
        if (manageMods != null) {
            manageMods.setTextColor(accentColor);
            GradientDrawable gd = (GradientDrawable) manageMods.getBackground();
            if (gd != null) gd.setStroke(1, accentColor);
        }

        // Content Management
        TextView contentTitle = view.findViewById(R.id.content_title);
        if (contentTitle != null) {
            contentTitle.setTextColor(accentColor);
            tintDrawableStart(contentTitle, accentColor);
        }

        TextView viewAll = view.findViewById(R.id.content_view_all);
        if (viewAll != null) {
            viewAll.setTextColor(accentColor);
        }

        // Miscellaneous
        TextView miscTitle = view.findViewById(R.id.misc_title);
        if (miscTitle != null) {
            miscTitle.setTextColor(accentColor);
            tintDrawableStart(miscTitle, accentColor);
        }

        // Current Instance
        LinearLayout selectVersion = view.findViewById(R.id.select_version_button);
        if (selectVersion != null) {
            selectVersion.setOnClickListener(v ->
                Toast.makeText(getContext(), "Instance selector (coming soon)", Toast.LENGTH_SHORT).show()
            );
        }

        launchButton.setOnClickListener(v ->
            Toast.makeText(getContext(), "Launching Minecraft...", Toast.LENGTH_SHORT).show()
        );

        return view;
    }

    private void tintDrawableStart(TextView textView, int color) {
        Drawable[] drawables = textView.getCompoundDrawables();
        if (drawables[0] != null) {
            Drawable wrapped = DrawableCompat.wrap(drawables[0]);
            DrawableCompat.setTint(wrapped, color);
        }
    }
}
