package org.nobiam.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public static void show(Context context, TextView targetText) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_instance_selector, null);
        dialog.setContentView(view);

        // Настройка размеров
        dialog.getWindow().setLayout(
                (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText searchInput = view.findViewById(R.id.search_input);
        LinearLayout instanceList = view.findViewById(R.id.instance_list);

        String currentVersion = getSavedVersion(context);
        List<Instance> filteredList = new ArrayList<>(INSTANCES);

        // Заполняем список
        populateList(instanceList, filteredList, currentVersion, context, dialog, targetText);

        // Поиск
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

        // Клик вне дропдауна
        view.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private static void populateList(LinearLayout container, List<Instance> instances,
                                     String currentVersion, Context context,
                                     Dialog dialog, TextView targetText) {
        container.removeAllViews();

        int accentColor = AccentColorManager.getColor(context);

        for (Instance instance : instances) {
            CardView card = new CardView(context);
            card.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            card.setRadius(12 * context.getResources().getDisplayMetrics().density);
            card.setCardElevation(4 * context.getResources().getDisplayMetrics().density);
            card.setUseCompatPadding(true);
            card.setPreventCornerOverlap(true);

            boolean isSelected = (instance.name + " " + instance.version).equals(currentVersion);

            if (isSelected) {
                card.setCardBackgroundColor(accentColor);
                card.setContentPadding(
                        (int)(12 * context.getResources().getDisplayMetrics().density),
                        (int)(8 * context.getResources().getDisplayMetrics().density),
                        (int)(12 * context.getResources().getDisplayMetrics().density),
                        (int)(8 * context.getResources().getDisplayMetrics().density)
                );
            } else {
                card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bg_glass));
                card.setContentPadding(
                        (int)(12 * context.getResources().getDisplayMetrics().density),
                        (int)(8 * context.getResources().getDisplayMetrics().density),
                        (int)(12 * context.getResources().getDisplayMetrics().density),
                        (int)(8 * context.getResources().getDisplayMetrics().density)
                );
            }

            // Содержимое карточки
            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);

            // Иконка блока Minecraft
            ImageView icon = new ImageView(context);
            icon.setImageResource(R.drawable.ic_minecraft_block);
            icon.setLayoutParams(new LinearLayout.LayoutParams(32, 32));
            icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            icon.setPadding(
                    (int)(4 * context.getResources().getDisplayMetrics().density),
                    (int)(4 * context.getResources().getDisplayMetrics().density),
                    (int)(4 * context.getResources().getDisplayMetrics().density),
                    (int)(4 * context.getResources().getDisplayMetrics().density)
            );
            row.addView(icon);

            // Текст
            LinearLayout textLayout = new LinearLayout(context);
            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            textLayout.setPadding(
                    (int)(12 * context.getResources().getDisplayMetrics().density), 0, 0, 0
            );

            TextView nameText = new TextView(context);
            nameText.setText(instance.name);
            nameText.setTextColor(isSelected ? 0xFFFFFFFF : ContextCompat.getColor(context, R.color.text_primary));
            nameText.setTextSize(16);
            nameText.setTypeface(null, android.graphics.Typeface.BOLD);
            textLayout.addView(nameText);

            TextView versionText = new TextView(context);
            versionText.setText(instance.version);
            versionText.setTextColor(isSelected ? 0xCCFFFFFF : ContextCompat.getColor(context, R.color.text_secondary));
            versionText.setTextSize(12);
            textLayout.addView(versionText);

            row.addView(textLayout);

            // Галочка
            if (isSelected) {
                ImageView check = new ImageView(context);
                check.setImageResource(R.drawable.ic_check_instance);
                check.setLayoutParams(new LinearLayout.LayoutParams(24, 24));
                check.setColorFilter(0xFFFFFFFF);
                row.addView(check);
            }

            card.addView(row);

            // Клик по карточке
            card.setOnClickListener(v -> {
                String selected = instance.name + " " + instance.version;
                saveVersion(context, selected);
                if (targetText != null) {
                    targetText.setText(selected);
                }
                dialog.dismiss();
            });

            container.addView(card);
        }
    }

    private static String getSavedVersion(Context context) {
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
