package org.nobiam.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

public class AboutFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        applyAccentColor();
        setupListeners();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        applyAccentColor();
    }

    private void applyAccentColor() {
        if (view == null) return;
        int accentColor = AccentColorManager.getColor(requireContext());

        TextView aboutTitle = view.findViewById(R.id.about_title);
        if (aboutTitle != null) {
            aboutTitle.setTextColor(accentColor);
        }
    }

    private void setupListeners() {
        ImageView telegram = view.findViewById(R.id.about_telegram);
        if (telegram != null) {
            telegram.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/NobiamOS"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Telegram not installed", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ImageView tiktok = view.findViewById(R.id.about_tiktok);
        if (tiktok != null) {
            tiktok.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/@NobiamOS"));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Cannot open TikTok", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
