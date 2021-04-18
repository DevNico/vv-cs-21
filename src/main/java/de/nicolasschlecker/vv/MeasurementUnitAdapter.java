package de.nicolasschlecker.vv;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MeasurementUnitAdapter extends TypeAdapter<MeasurementUnit> {
    @Override
    public void write(JsonWriter out, MeasurementUnit value) throws IOException {
        out.value(value.toString().toLowerCase());
    }

    @Override
    public MeasurementUnit read(JsonReader in) throws IOException {
        return MeasurementUnit.valueOf(in.nextString().toUpperCase());
    }
}