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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        // Заголовок "About" акцентным цветом
        TextView aboutTitle = view.findViewById(R.id.about_title);
        if (aboutTitle != null) {
            aboutTitle.setTextColor(AccentColorManager.getAccentColor(requireContext()));
        }

        // ... остальное без изменений
    }
}
