package org.dddml.roochdemocontracts.rooch.bcs;

import java.util.Objects;

public class Day {
    public Month month;
    public Byte number;
    public String timeZone;

    public static Day deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Day value = new Day();
        value.month = Month.deserialize(deserializer);
        value.number = deserializer.deserialize_u8();
        value.timeZone = deserializer.deserialize_str();
        deserializer.decrease_container_depth();
        return value;
    }

    public static Day bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
            throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        Day value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        month.serialize(serializer);
        serializer.serialize_u8(number);
        serializer.serialize_str(timeZone);
        serializer.decrease_container_depth();
    }

    public byte[] bcsSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bcs.BcsSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(month, day.month) && Objects.equals(number, day.number) && Objects.equals(timeZone, day.timeZone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, number, timeZone);
    }

    @Override
    public String toString() {
        return "Day{" +
                "month=" + month +
                ", number=" + number +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
