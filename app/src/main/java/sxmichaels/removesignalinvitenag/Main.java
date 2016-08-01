package sxmichaels.removesignalinvitenag;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("org.thoughtcrime.securesms")) return;

        // Force the "seenInvite" argument of updateInviteReminder() to always be true
        findAndHookMethod(
                "org.thoughtcrime.securesms.ConversationActivity", lpparam.classLoader,
                "updateInviteReminder", "boolean", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.args[0] = true;
                    }
                });
    }
}
