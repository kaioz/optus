package com.cocosw.optus.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cocosw.framework.core.Base;
import com.cocosw.optus.R;
import com.cocosw.optus.model.RouteLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Project: Optus
 * Created by LiaoKai(soarcn) on 2016/6/26.
 */
public class Map extends Base<Void> implements OnMapReadyCallback {
    private static final String LOCATION = "_location";
    private RouteLocation location;

    static void launch(Activity activity, RouteLocation location) {
        activity.startActivity(new Intent(activity, Map.class).putExtra(LOCATION, location));
    }

    @Override
    public int layoutId() {
        return R.layout.map;
    }

    @Override
    protected void init(Bundle saveBundle) throws Exception {
        location = (RouteLocation) getIntent().getSerializableExtra(LOCATION);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
        if (location != null) {
            final LatLng latLng = new LatLng(location.latitude, location.longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f));
        }
    }
}
