package org.dddml.roochdemocontracts.rooch.bcs;

import java.util.Objects;

public class Month {

    public Year year;
    public Byte number;
    public Boolean isLeap;


    public static Month deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Month value = new Month();
        value.year = Year.deserialize(deserializer);
        value.number = deserializer.deserialize_u8();
        value.isLeap = deserializer.deserialize_bool();
        deserializer.decrease_container_depth();
        return value;
    }

    public static Month bcsDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
            throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bcs.BcsDeserializer(input);
        Month value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
            throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }


    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        year.serialize(serializer);
        serializer.serialize_u8(number);
        serializer.serialize_bool(isLeap);
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
        Month month = (Month) o;
        return Objects.equals(year, month.year) && Objects.equals(number, month.number) && Objects.equals(isLeap, month.isLeap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, number, isLeap);
    }

    @Override
    public String toString() {
        return "Month{" +
                "year=" + year +
                ", number=" + number +
                ", isLeap=" + isLeap +
                '}';
    }
}
