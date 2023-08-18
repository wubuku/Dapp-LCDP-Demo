// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract.daysummary;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import org.dddml.roochdemocontracts.rooch.contract.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DaySummaryCreated {
    private com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> id;

    private Day day;

    private String description;

    private String metaData;

    private String[] arrayData;

    private com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> optionalData;

    private Integer[] u16ArrayData;

    private Long[] u32ArrayData;

    private BigInteger[] u64ArrayData;

    private BigInteger[] u128ArrayData;

    private BigInteger[] u256ArrayData;

    public com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> getId() {
        return id;
    }

    public void setId(com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(String[] arrayData) {
        this.arrayData = arrayData;
    }

    public com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> getOptionalData() {
        return optionalData;
    }

    public void setOptionalData(com.github.wubuku.rooch.bean.AnnotatedMoveOptionView<String> optionalData) {
        this.optionalData = optionalData;
    }

    public Integer[] getU16ArrayData() {
        return u16ArrayData;
    }

    public void setU16ArrayData(Integer[] u16ArrayData) {
        this.u16ArrayData = u16ArrayData;
    }

    public Long[] getU32ArrayData() {
        return u32ArrayData;
    }

    public void setU32ArrayData(Long[] u32ArrayData) {
        this.u32ArrayData = u32ArrayData;
    }

    public BigInteger[] getU64ArrayData() {
        return u64ArrayData;
    }

    public void setU64ArrayData(BigInteger[] u64ArrayData) {
        this.u64ArrayData = u64ArrayData;
    }

    public BigInteger[] getU128ArrayData() {
        return u128ArrayData;
    }

    public void setU128ArrayData(BigInteger[] u128ArrayData) {
        this.u128ArrayData = u128ArrayData;
    }

    public BigInteger[] getU256ArrayData() {
        return u256ArrayData;
    }

    public void setU256ArrayData(BigInteger[] u256ArrayData) {
        this.u256ArrayData = u256ArrayData;
    }

    @Override
    public String toString() {
        return "DaySummaryCreated{" +
                "id=" + id +
                ", day=" + day +
                ", description=" + '\'' + description + '\'' +
                ", metaData=" + '\'' + metaData + '\'' +
                ", arrayData=" + Arrays.toString(arrayData) +
                ", optionalData=" + optionalData +
                ", u16ArrayData=" + Arrays.toString(u16ArrayData) +
                ", u32ArrayData=" + Arrays.toString(u32ArrayData) +
                ", u64ArrayData=" + Arrays.toString(u64ArrayData) +
                ", u128ArrayData=" + Arrays.toString(u128ArrayData) +
                ", u256ArrayData=" + Arrays.toString(u256ArrayData) +
                '}';
    }

}
