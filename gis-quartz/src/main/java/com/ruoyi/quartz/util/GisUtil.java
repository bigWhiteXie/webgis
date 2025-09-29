package com.ruoyi.quartz.util;

import org.ehcache.shadow.org.terracotta.context.query.Query;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTWriter;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * GIS工具类
 */
public class GisUtil {

    /**
     * 读取 Shapefile 文件并返回其中所有几何的 WKT 字符串
     *
     * @param shpFile Shapefile 文件
     * @return WKT 字符串（每个几何一行）
     * @throws Exception 解析异常
     */
    public static String shpToWKT(File shpFile) throws Exception {
        StringBuilder sb = new StringBuilder();

        FileDataStore store = FileDataStoreFinder.getDataStore(shpFile);
        SimpleFeatureSource featureSource = store.getFeatureSource();

        try (SimpleFeatureIterator it = featureSource.getFeatures().features()) {
            WKTWriter wktWriter = new WKTWriter();

            while (it.hasNext()) {
                SimpleFeature feature = it.next();
                Object geometry = feature.getDefaultGeometry();

                if (geometry instanceof Geometry) {
                    String wkt = wktWriter.write((Geometry) geometry);
                    sb.append(wkt).append("\n");
                }
            }
        } finally {
            store.dispose();
        }

        return sb.toString().trim();
    }
}