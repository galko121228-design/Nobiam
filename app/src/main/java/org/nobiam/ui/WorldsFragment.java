package org.nobiam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.nobiam.R;

public class WorldsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Просто создаём TextView с текстом
        TextView tv = new TextView(getContext());
        tv.setText("Worlds Fragment");
        tv.setTextSize(24);
        tv.setTextColor(0xFFFFFFFF);
        tv.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tv.setPadding(0, 200, 0, 0);
        return tv;
    }
}
