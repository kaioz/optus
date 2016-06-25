package com.cocosw.optus.model;

import com.cocosw.optus.R;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * NBN Atlas Wayfinder
 * <p/>
 * Created by kai on 27/11/2015.
 */
public class WeatherTest {

    @Test
    public void testGetWeatherType() throws Exception {
        assertThat(Weather.getWeatherType("rain")).isNotNull();
        assertThat(Weather.getWeatherType("rain").icon).isEqualTo(R.drawable.rain);
        assertThat(Weather.getWeatherType("rain").bg).isEqualTo(R.color.mandarinColor);

        assertThat(Weather.getWeatherType("wind")).isNotNull();
        assertThat(Weather.getWeatherType("wind").icon).isEqualTo(R.drawable.wind);
        assertThat(Weather.getWeatherType("wind").bg).isEqualTo(R.color.Primary_Pink_700);
    }
}