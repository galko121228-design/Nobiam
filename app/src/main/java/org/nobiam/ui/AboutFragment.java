package org.nobiam.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import org.nobiam.R;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Telegram
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

        // TikTok
        ImageView tiktok = view.findViewById(R.id.about_tiktok);
        if (tiktok != null) {
            tiktok.setOnClickListener(v -> {
                try {
                    // Пробуем открыть в приложении TikTok
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tiktok://user/@NobiamOS"));
                    startActivity(intent);
                } catch (Exception e) {
                    // Если приложение не установлено — открываем в браузере
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tiktok.com/@NobiamOS"));
                        startActivity(intent);
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), "TikTok not installed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return view;
    }
}
