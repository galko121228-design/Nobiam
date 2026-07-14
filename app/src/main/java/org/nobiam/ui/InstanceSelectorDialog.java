package org.nobiam.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

import java.util.ArrayList;
import java.util.List;

public class InstanceSelectorDialog {

    private static final String PREFS = "nobiam_settings";
    private static final String KEY_VERSION = "selected_version";

    // ВРЕМЕННО: пока нет реальных инстансов — показываем пустой список
    private static List<Instance> getInstances() {
        List<Instance> list = new ArrayList<>();
        // Пока нет реальных версий — пусто
        // TODO: сканировать установленные APK Minecraft
        return list;
    }

    public static void show(Context context, View anchor, TextView targetText) {
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

        EditText searchInput = view.findViewById(R.id.search_input);
        LinearLayout instanceList = view.findViewById(R.id.instance_list);

        List<Instance> instances = getInstances();
        String currentVersion = getSavedVersion(context);

        populateList(instanceList, instances, currentVersion, context, dialog, targetText);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase().trim();
                List<Instance> filtered = new ArrayList<>();
                for (Instance instance : instances) {
                    if (instance.name.toLowerCase().contains(query) ||
                        instance.version.contains(query)) {
                        filtered.add(instance);
                    }
                }
                populateList(instanceList, filtered, currentVersion, context, dialog, targetText);
            }
        });

        dialog.show();
    }

    private static void populateList(LinearLayout container, List<Instance> instances,
                                     String currentVersion, Context context,
                                     Dialog dialog, TextView targetText) {
        container.removeAllViews();

        int accentColor = AccentColorManager.getColor(context);
        float density = context.getResources().getDisplayMetrics().density;

        if (instances.isEmpty()) {
            TextView emptyText = new TextView(context);
            emptyText.setText("No instances found");
            emptyText.setTextColor(ContextCompat.getColor(context, R.color.text_secondary));
            emptyText.setTextSize(14);
            emptyText.setGravity(Gravity.CENTER);
            emptyText.setPadding(0, (int)(16 * density), 0, (int)(16 * density));
            container.addView(emptyText);
            return;
        }

        for (Instance instance : instances) {
            String instanceFullName = instance.name + " " + instance.version;
            boolean isSelected = instanceFullName.equals(currentVersion);

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

            if (isSelected) {
                card.setCardBackgroundColor(accentColor);
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_glass));
            }

            card.setContentPadding(
                    (int)(10 * density), (int)(6 * density),
                    (int)(10 * density), (int)(6 * density)
            );

            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);

            TextView nameText = new TextView(context);
            nameText.setText(instance.name);
            nameText.setTextColor(isSelected ? 0xFFFFFFFF : ContextCompat.getColor(context, R.color.text_primary));
            nameText.setTextSize(14);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);
            row.addView(nameText);

            TextView versionText = new TextView(context);
            versionText.setText(instance.version);
            versionText.setTextColor(isSelected ? 0xCCFFFFFF : ContextCompat.getColor(context, R.color.text_secondary));
            versionText.setTextSize(12);
            row.addView(versionText);

            card.addView(row);

            card.setOnClickListener(v -> {
                saveVersion(context, instanceFullName);
                if (targetText != null) {
                    targetText.setText(instanceFullName);
                }
                dialog.dismiss();
            });

            container.addView(card);
        }
    }

    public static String getSavedVersion(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getString(KEY_VERSION, "No instance selected");
    }

    private static void saveVersion(Context context, String version) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_VERSION, version).apply();
    }

    static class Instance {
        String name;
        String version;

        Instance(String name, String version) {
            this.name = name;
            this.version = version;
        }
    }
}
