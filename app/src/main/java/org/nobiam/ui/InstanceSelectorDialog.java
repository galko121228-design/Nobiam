package org.nobiam.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;
import org.nobiam.utils.MinecraftLauncher;

import java.util.ArrayList;
import java.util.List;

public class InstanceSelectorDialog {

    private static final String PREFS = "nobiam_settings";
    private static final String KEY_VERSION = "selected_version";

    public static void show(Context context, View anchor, TextView targetText) {
        // Получаем реальную версию Minecraft
        String currentVersion = MinecraftLauncher.getMinecraftVersionLabel(context);
        
        // Создаём список с одной реальной версией
        List<String> instances = new ArrayList<>();
        if (MinecraftLauncher.isMinecraftInstalled(context)) {
            instances.add(currentVersion);
        } else {
            instances.add("Minecraft not found");
        }

        Dialog dialog = new Dialog(context, R.style.TransparentDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_instance_selector, null);
        dialog.setContentView(view);

        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();

            int[] location = new int[2];
            anchor.getLocationOnScreen(location);

            float density = context.getResources().getDisplayMetrics().density;
            int dialogWidth = (int) (320 * density);
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            int x = location[0];
            if (x + dialogWidth > screenWidth) {
                x = screenWidth - dialogWidth - (int)(16 * density);
            }

            params.width = dialogWidth;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.START;
            params.x = x;
            params.y = location[1] + anchor.getHeight() + (int)(4 * density);

            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        LinearLayout instanceList = view.findViewById(R.id.instance_list);
        instanceList.removeAllViews();

        float density = context.getResources().getDisplayMetrics().density;
        int accentColor = AccentColorManager.getColor(context);

        for (String instance : instances) {
            CardView card = new CardView(context);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, (int)(4 * density), 0, (int)(4 * density));
            card.setLayoutParams(cardParams);
            card.setRadius(12 * density);
            card.setCardElevation(0);
            card.setUseCompatPadding(true);
            card.setPreventCornerOverlap(true);
            card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_glass));
            card.setContentPadding(
                    (int)(12 * density), (int)(10 * density),
                    (int)(12 * density), (int)(10 * density)
            );

            TextView nameText = new TextView(context);
            nameText.setText(instance);
            nameText.setTextColor(ContextCompat.getColor(context, R.color.text_primary));
            nameText.setTextSize(14);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);
            card.addView(nameText);

            card.setOnClickListener(v -> {
                if (targetText != null) {
                    targetText.setText(instance);
                }
                dialog.dismiss();
            });

            instanceList.addView(card);
        }

        dialog.show();
    }
}
