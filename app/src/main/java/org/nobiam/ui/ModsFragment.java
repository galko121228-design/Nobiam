package org.nobiam.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import org.nobiam.R;
import org.nobiam.utils.AccentColorManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModsFragment extends Fragment {

    private LinearLayout modsList;
    private TextView emptyText;
    private EditText searchInput;
    private List<String> allMods = new ArrayList<>();
    private List<String> filteredMods = new ArrayList<>();
    private File modsDir;

    // Launcher для выбора файла
    private final ActivityResultLauncher<Intent> filePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                            Uri uri = result.getData().getData();
                            if (uri != null) {
                                copyFileToMods(uri);
                            }
                        }
                    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mods, container, false);

        modsList = view.findViewById(R.id.mods_list);
        emptyText = view.findViewById(R.id.empty_text);
        searchInput = view.findViewById(R.id.search_input);

        // Создаём папку для модов
        modsDir = new File(Environment.getExternalStorageDirectory(), "Nobiam/mods");
        if (!modsDir.exists()) {
            modsDir.mkdirs();
        }

        // Назад
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Заголовок
        TextView title = view.findViewById(R.id.title);
        if (title != null) {
            title.setTextColor(AccentColorManager.getColor(requireContext()));
        }

        // Добавить мод
        Button btnAddMod = view.findViewById(R.id.btn_add_mod);
        btnAddMod.setOnClickListener(v -> openFilePicker());

        // Поиск
        searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) {
                filterMods(s.toString());
            }
        });

        loadMods();

        return view;
    }

    private void loadMods() {
        allMods.clear();
        if (modsDir.exists() && modsDir.isDirectory()) {
            File[] files = modsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && (file.getName().endsWith(".mcaddon") || file.getName().endsWith(".mcpack"))) {
                        allMods.add(file.getName());
                    }
                }
            }
        }
        filteredMods = new ArrayList<>(allMods);
        renderList();
    }

    private void filterMods(String query) {
        filteredMods.clear();
        String q = query.toLowerCase().trim();
        if (q.isEmpty()) {
            filteredMods.addAll(allMods);
        } else {
            for (String mod : allMods) {
                if (mod.toLowerCase().contains(q)) {
                    filteredMods.add(mod);
                }
            }
        }
        renderList();
    }

    private void renderList() {
        modsList.removeAllViews();

        if (filteredMods.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            return;
        }
        emptyText.setVisibility(View.GONE);

        float density = getResources().getDisplayMetrics().density;
        int accentColor = AccentColorManager.getColor(requireContext());

        for (String modName : filteredMods) {
            View card = createModCard(modName, accentColor, density);
            modsList.addView(card);
        }
    }

    private View createModCard(String modName, int accentColor, float density) {
        LinearLayout card = new LinearLayout(getContext());
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(android.view.Gravity.CENTER_VERTICAL);
        card.setBackgroundResource(R.drawable.card_background);
        card.setPadding(
                (int)(16 * density), (int)(12 * density),
                (int)(16 * density), (int)(12 * density)
        );
        card.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        ((LinearLayout.LayoutParams) card.getLayoutParams()).setMargins(0, (int)(6 * density), 0, (int)(6 * density));

        // Название мода
        TextView nameText = new TextView(getContext());
        nameText.setText(modName);
        nameText.setTextColor(ContextCompat.getColor(getContext(), R.color.text_primary));
        nameText.setTextSize(14);
        nameText.setTypeface(null, android.graphics.Typeface.BOLD);
        nameText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        // Кнопка удаления
        Button deleteBtn = new Button(getContext());
        deleteBtn.setText("🗑️");
        deleteBtn.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        deleteBtn.setTextSize(18);
        deleteBtn.setOnClickListener(v -> {
            deleteMod(modName);
        });

        card.addView(nameText);
        card.addView(deleteBtn);
        return card;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"application/octet-stream", "application/zip"});
        filePickerLauncher.launch(intent);
    }

    private void copyFileToMods(Uri uri) {
        try {
            String fileName = getFileName(uri);
            if (fileName == null) return;

            File destFile = new File(modsDir, fileName);
            if (destFile.exists()) {
                Toast.makeText(getContext(), "Mod already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            FileInputStream inputStream = (FileInputStream) getContext().getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(destFile);

            FileChannel inChannel = inputStream.getChannel();
            FileChannel outChannel = outputStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);

            inputStream.close();
            outputStream.close();

            Toast.makeText(getContext(), "Mod installed: " + fileName, Toast.LENGTH_SHORT).show();
            loadMods();

        } catch (IOException e) {
            Toast.makeText(getContext(), "Failed to install mod", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;
        if (uri.getPath() != null) {
            String[] segments = uri.getPath().split("/");
            fileName = segments[segments.length - 1];
        }
        return fileName;
    }

    private void deleteMod(String modName) {
        File file = new File(modsDir, modName);
        if (file.exists() && file.delete()) {
            Toast.makeText(getContext(), "Mod deleted: " + modName, Toast.LENGTH_SHORT).show();
            loadMods();
        } else {
            Toast.makeText(getContext(), "Failed to delete mod", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMods();
    }
}
