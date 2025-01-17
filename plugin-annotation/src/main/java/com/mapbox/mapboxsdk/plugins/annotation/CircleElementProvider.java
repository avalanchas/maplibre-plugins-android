// This file is generated.

package com.mapbox.mapboxsdk.plugins.annotation;

import androidx.annotation.Nullable;

import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Concrete instance of a core element provider for Circle.
 */
class CircleElementProvider implements CoreElementProvider<CircleLayer> {

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private static final String ID_GEOJSON_LAYER = "mapbox-android-circle-layer-%s";
    private static final String ID_GEOJSON_SOURCE = "mapbox-android-circle-source-%s";

    private final String layerId;
    private final String sourceId;

    CircleElementProvider() {
        long id = ID_GENERATOR.incrementAndGet();
        this.layerId = String.format(ID_GEOJSON_LAYER, id);
        this.sourceId = String.format(ID_GEOJSON_SOURCE, id);
    }

    @Override
    public String getLayerId() {
        return layerId;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public CircleLayer getLayer() {
        return new CircleLayer(layerId, sourceId);
    }

    @Override
    public GeoJsonSource getSource(@Nullable GeoJsonOptions geoJsonOptions) {
        return new GeoJsonSource(sourceId, geoJsonOptions);
    }
}