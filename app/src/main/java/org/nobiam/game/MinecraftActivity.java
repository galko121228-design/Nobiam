package org.nobiam.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MinecraftActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);
        tv.setText("Isolated Minecraft Environment");
        tv.setTextSize(20);

        setContentView(tv);
    }
}
