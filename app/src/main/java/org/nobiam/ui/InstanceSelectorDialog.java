package org.nobiam.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstanceSelectorDialog {

    private static final String PREFS = "nobiam_settings";
    private static final String KEY_VERSION = "selected_version";

    private static final List<Instance> INSTANCES = Arrays.asList(
            new Instance("Minecraft", "1.21.30"),
            new Instance("Minecraft", "1.21.20"),
            new Instance("Minecraft", "1.21.10"),
            new Instance("Minecraft", "1.20.80"),
            new Instance("Minecraft", "1.20.70"),
            new Instance("Minecraft", "1.20.60")
    );

    public static void show(Context context, View anchor, TextView targetText) {
        Dialog dialog = new Dialog(context, R.style.TransparentDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_instance_selector, null);
        dialog.setContentView(view);

        // Закрытие по клику вне диалога
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (380 * context.getResources().getDisplayMetrics().density);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.TOP | Gravity.END;
            params.x = (int) (16 * context.getResources().getDisplayMetrics().density);
            params.y = (int) (80 * context.getResources().getDisplayMetrics().density);
            window.setAttributes(params);
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

        EditText searchInput = view.findViewById(R.id.search_input);
        LinearLayout instanceList = view.findViewById(R.id.instance_list);

        String currentVersion = getSavedVersion(context);
        List<Instance> filteredList = new ArrayList<>(INSTANCES);

        populateList(instanceList, filteredList, currentVersion, context, dialog, targetText);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase().trim();
                filteredList.clear();
                if (query.isEmpty()) {
                    filteredList.addAll(INSTANCES);
                } else {
                    for (Instance instance : INSTANCES) {
                        if (instance.name.toLowerCase().contains(query) ||
                            instance.version.contains(query)) {
                            filteredList.add(instance);
                        }
                    }
                }
                populateList(instanceList, filteredList, currentVersion, context, dialog, targetText);
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

        for (Instance instance : instances) {
            String instanceFullName = instance.name + " " + instance.version;
            boolean isSelected = instanceFullName.equals(currentVersion);

            CardView card = new CardView(context);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, (int)(6 * density), 0, (int)(6 * density));
            card.setLayoutParams(cardParams);
            card.setRadius(14 * density);
            card.setCardElevation(0);
            card.setUseCompatPadding(true);
            card.setPreventCornerOverlap(true);

            if (isSelected) {
                card.setCardBackgroundColor(accentColor);
                card.setContentPadding(
                        (int)(12 * density), (int)(8 * density),
                        (int)(12 * density), (int)(8 * density)
                );
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_glass));
                card.setContentPadding(
                        (int)(12 * density), (int)(8 * density),
                        (int)(12 * density), (int)(8 * density)
                );
            }

            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);

            CardView iconWrapper = new CardView(context);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    (int)(40 * density), (int)(40 * density)
            );
            iconWrapper.setLayoutParams(iconParams);
            iconWrapper.setRadius(8 * density);
            iconWrapper.setCardElevation(0);
            iconWrapper.setUseCompatPadding(true);
            iconWrapper.setPreventCornerOverlap(true);
            iconWrapper.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_surface));

            ImageView icon = new ImageView(context);
            icon.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            icon.setImageResource(R.drawable.ic_minecraft_block);
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            icon.setPadding(
                    (int)(4 * density), (int)(4 * density),
                    (int)(4 * density), (int)(4 * density)
            );
            iconWrapper.addView(icon);
            row.addView(iconWrapper);

            LinearLayout textLayout = new LinearLayout(context);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1
            ));
            textLayout.setPadding((int)(12 * density), 0, 0, 0);

            TextView nameText = new TextView(context);
            nameText.setText(instance.name);
            nameText.setTextColor(isSelected ? 0xFFFFFFFF : ContextCompat.getColor(context, R.color.text_primary));
            nameText.setTextSize(16);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);
            textLayout.addView(nameText);

            TextView versionText = new TextView(context);
            versionText.setText(instance.version);
            versionText.setTextColor(isSelected ? 0xCCFFFFFF : ContextCompat.getColor(context, R.color.text_secondary));
            versionText.setTextSize(13);
            textLayout.addView(versionText);

            row.addView(textLayout);

            if (isSelected) {
                ImageView check = new ImageView(context);
                check.setImageResource(R.drawable.ic_check);
                check.setLayoutParams(new LinearLayout.LayoutParams(
                        (int)(20 * density), (int)(20 * density)
                ));
                check.setColorFilter(0xFFFFFFFF);
                row.addView(check);
            }

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
        return prefs.getString(KEY_VERSION, "Minecraft 1.21.30");
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
