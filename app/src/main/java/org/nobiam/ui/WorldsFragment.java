package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.nobiam.R;

public class WorldsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worlds, container, false);

        // Просто показываем текст для теста
        TextView tv = new TextView(getContext());
        tv.setText("WORLDS FRAGMENT WORKS!");
        tv.setTextSize(24);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setPadding(0, 200, 0, 0);

        // Заменяем содержимое на простой текст
        ViewGroup parent = (ViewGroup) view;
        parent.removeAllViews();
        parent.addView(tv);

        return view;
    }
}
