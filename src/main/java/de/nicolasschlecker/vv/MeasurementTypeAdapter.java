package de.nicolasschlecker.vv;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MeasurementTypeAdapter extends TypeAdapter<MeasurementType> {
    @Override
    public void write(JsonWriter out, MeasurementType value) throws IOException {
        out.value(value.toString().toLowerCase());
    }

    @Override
    public MeasurementType read(JsonReader in) throws IOException {
        return MeasurementType.valueOf(in.nextString().toUpperCase());
    }
}
