package com.cocosw.optus.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cocosw.optus.ui.MockDelay;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 26/11/2015.
 */
public class MockServiceProvider {

    private final SharedPreferences preferences;

    private final Map<Class<? extends Enum<?>>, Enum<?>> responses = new LinkedHashMap<>();

    public MockServiceProvider(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Initialize mock responses.
        loadResponse(MockWeather.class,MockWeather.NONMOCK);
        loadResponse(MockDelay.class,MockDelay.NONE);
    }

    /**
     * Initializes the current response for {@code responseClass} from {@code SharedPreferences}, or
     * uses {@code defaultValue} if a response was not found.
     */
    private <T extends Enum<T>> void loadResponse(Class<T> responseClass, T defaultValue) {
        responses.put(responseClass, EnumPreferences.getEnumValue(preferences, responseClass, //
                responseClass.getCanonicalName(), defaultValue));
    }


    public <T extends Enum<T>> T getResponse(Class<T> responseClass) {
        return responseClass.cast(responses.get(responseClass));
    }


    public <T extends Enum<T>> void setResponse(Class<T> responseClass, T value) {
        responses.put(responseClass, value);
        EnumPreferences.saveEnumValue(preferences, responseClass.getCanonicalName(), value);
    }

//    public Location getCurrentLocation() {
//             {
//                MockDelay delay = getResponse(MockDelay.class);
//                sleep(delay.ms);
//            }
//    }

}
