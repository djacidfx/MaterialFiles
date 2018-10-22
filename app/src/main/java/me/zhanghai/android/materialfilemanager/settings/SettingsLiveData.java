/*
 * Copyright (c) 2018 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.materialfilemanager.settings;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import me.zhanghai.android.materialfilemanager.util.SharedPrefsUtils;

public class SettingsLiveData<T> extends LiveData<T>
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @NonNull
    private final SettingsEntry<T> mEntry;

    public SettingsLiveData(@NonNull SettingsEntry<T> entry) {
        mEntry = entry;

        loadValue();
        SharedPrefsUtils.getSharedPrefs().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(@NonNull SharedPreferences sharedPreferences,
                                          @NonNull String key) {
        if (TextUtils.equals(key, mEntry.getKey())) {
            loadValue();
        }
    }

    private void loadValue() {
        setValue(mEntry.getValue());
    }

    public void putValue(@Nullable T value) {
        mEntry.putValue(value);
    }
}
