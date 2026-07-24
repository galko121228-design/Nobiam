package org.nobiam.core.runtime;

import android.content.Context;
import java.io.File;

import org.nobiam.core.session.MinecraftLaunchSession;

public class MinecraftRuntimePreparer {

    public static MinecraftLaunchSession prepare(Context context) {
        File dir = new File(context.getFilesDir(), "isolated_mc");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new MinecraftLaunchSession(dir.getAbsolutePath());
    }
}
