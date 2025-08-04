package com.lastcrash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Rect;
import android.app.Activity;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import io.lastcrash.sdk.LastCrash;
import io.lastcrash.sdk.LastCrashReportSenderListener;

import java.util.Locale;
import java.util.UUID;

public class LastCrashModule extends ReactContextBaseJavaModule implements LastCrashReportSenderListener {
    private final ReactApplicationContext reactContext;
    private static final String MODULE_NAME = "LastCrashModule";
    private static boolean didUseDelegate = false;

    /**
     * Helper method to run LastCrash operations on the main thread
     */
    private void runOnMainThread(Runnable runnable) {
        Activity currentActivity = reactContext.getCurrentActivity();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(runnable);
        } else {
            // Fallback to main thread handler if no activity available
            new android.os.Handler(android.os.Looper.getMainLooper()).post(runnable);
        }
    }

    public LastCrashModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void configure(String apiKey) {
        try {
            if (apiKey == null || apiKey.isEmpty()) {
                throw new IllegalArgumentException("API key is required");
            }

            // Get the current activity and pass it to LastCrash.configure
            Activity currentActivity = reactContext.getCurrentActivity();
            if (currentActivity != null) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LastCrash.configure(apiKey, currentActivity);
                    }
                });
            } else {
                System.err.println("Configure error: No current activity available");
            }
        } catch (Exception e) {
            System.err.println("Configure error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void setCrashReportSenderListener() {
        if (!didUseDelegate) {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    LastCrash.setCrashReportSenderListener(LastCrashModule.this);
                }
            });
            didUseDelegate = true;
        }
    }

    @Override
    public void lastCrashReportSenderHandleCrash() {
        if (didUseDelegate) {
            sendEvent("LastCrashDidCrash", null);
        }
    }

    @ReactMethod
    public void enableLogging() {
        // no-op for android
    }

    @ReactMethod
    public void disableLogging() {
        // no-op for android
    }

    @ReactMethod
    public void pause() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                LastCrash.pause();
            }
        });
    }

    @ReactMethod
    public void unpause() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                LastCrash.unpause();
            }
        });
    }

    @ReactMethod
    public void sendCrashes() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                LastCrash.sendCrashes();
            }
        });
    }

    @ReactMethod
    public void event(String name, String value) {
        try {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if (value != null && !value.isEmpty()) {
                        LastCrash.event(name, value);
                    } else {
                        LastCrash.event(name);
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Event tracking error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void applicationInitialized() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                LastCrash.applicationInitialized();
            }
        });
    }

    @ReactMethod
    public void applicationForceTermination() {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                LastCrash.applicationForceTermination();
            }
        });
    }

    @ReactMethod
    public void addNetworkTrackingToDefaultSession() {
        // no-op for android
    }

    @ReactMethod
    public void networkEvent(ReadableMap request, ReadableMap response, double duration,
                           int errorCode, boolean cancelled, int requestBytes, int responseBytes) {
        try {
            // Convert ReadableMap to appropriate network event objects
            // This might need to be adjusted based on the actual LastCrash API
            // LastCrash.networkEvent(request, response, duration, errorCode, cancelled, requestBytes, responseBytes);
        } catch (Exception e) {
            System.err.println("Network event tracking error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void addMaskRect(double x, double y, double width, double height, String maskId) {
        try {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    Rect rect = new Rect((int)x, (int)y, (int)(x + width), (int)(y + height));
                    LastCrash.addMaskRect(maskId, rect);
                }
            });
        } catch (Exception e) {
            System.err.println("Add mask rect error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void removeMaskRect(String maskId) {
        try {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    LastCrash.removeMaskRect(maskId);
                }
            });
        } catch (Exception e) {
            System.err.println("Remove mask rect error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void removeAllMaskRects() {
        try {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    LastCrash.removeAllMaskRects();
                }
            });
        } catch (Exception e) {
            System.err.println("Remove all mask rects error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void addMaskView(Integer viewTag) {
        try {
            View view = reactContext.getCurrentActivity().findViewById(viewTag);
            if (view != null) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LastCrash.addMaskView(view);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            System.err.println("Add mask view error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void removeMaskView(Integer viewTag) {
        try {
            View view = reactContext.getCurrentActivity().findViewById(viewTag);
            if (view != null) {
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        LastCrash.removeMaskView(view);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            System.err.println("Remove mask view error: " + e.getMessage());
        }
    }

    @ReactMethod
    public void removeAllMaskViews() {
        try {
            runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    LastCrash.removeAllMaskViews();
                }
            });
        } catch (Exception e) {
            System.err.println("Remove all mask views error: " + e.getMessage());
        }
    }

    private void sendEvent(String eventName, WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
