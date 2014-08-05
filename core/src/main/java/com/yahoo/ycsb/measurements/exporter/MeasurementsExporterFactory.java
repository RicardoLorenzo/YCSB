package com.yahoo.ycsb.measurements.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by ricardolorenzo on 05/08/2014.
 */
public class MeasurementsExporterFactory {
    public static MeasurementsExporter getInstance(Properties props) {
        MeasurementsExporter exporter = null;
        // if no destination file is provided the results will be written to stdout
        OutputStream out;
        String exportFile = props.getProperty("exportfile");
        if (exportFile == null)
        {
            out = System.out;
        } else
        {
            try {
                out = new FileOutputStream(exportFile);
            } catch(FileNotFoundException e) {
                out = System.out;
            }
        }

        String exporterStr = props.getProperty("exporter", "com.yahoo.ycsb.measurements.exporter.TextMeasurementsExporter");
        try
        {
            exporter = (MeasurementsExporter) Class.forName(exporterStr).getConstructor(OutputStream.class).newInstance(out);
        } catch (Exception e)
        {
            System.err.println("Could not find exporter " + exporterStr
                    + ", will use default text reporter.");
            e.printStackTrace();
            exporter = new TextMeasurementsExporter(out);
        }
        return null;
    }
}
