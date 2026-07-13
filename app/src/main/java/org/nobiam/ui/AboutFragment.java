package org.nobiam.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import org.nobiam.R;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Telegram
        LinearLayout telegram = view.findViewById(R.id.about_telegram);
        if (telegram != null) {
            telegram.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/NobiamOS"));
                startActivity(intent);
            });
        }

        // TikTok
        LinearLayout tiktok = view.findViewById(R.id.about_tiktok);
        if (tiktok != null) {
            tiktok.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tiktok.com/@NobiamOS"));
                startActivity(intent);
            });
        }

        return view;
    }
}
