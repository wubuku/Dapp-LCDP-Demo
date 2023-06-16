// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.bcs;

import com.github.wubuku.rooch.bcs.BcsUtils;

import java.util.Optional;

/**
 * Utils that convert beans in the bcs package to domain beans.
 */
public class BcsDomainBeanUtils {
    private BcsDomainBeanUtils() {
    }

    public static Day toBcsDay(org.dddml.roochdemocontracts.domain.Day day) {
        if (day == null) {
            return null;
        }
        Day bcsDay = new Day();
        bcsDay.month = toBcsMonth(day.getMonth());
        bcsDay.number = day.getNumber().byteValue();
        bcsDay.timeZone = day.getTimeZone();
        return bcsDay;
    }

    public static Month toBcsMonth(org.dddml.roochdemocontracts.domain.Month month) {
        if (month == null) {
            return null;
        }
        Month bcsMonth = new Month();
        bcsMonth.year = toBcsYear(month.getYear());
        bcsMonth.number = month.getNumber().byteValue();
        bcsMonth.isLeap = month.getIsLeap();
        return bcsMonth;
    }

    public static ReferenceVO toBcsReferenceVO(org.dddml.roochdemocontracts.domain.ReferenceVO referenceVO) {
        if (referenceVO == null) {
            return null;
        }
        ReferenceVO bcsReferenceVO = new ReferenceVO();
        bcsReferenceVO.referenceNumber = referenceVO.getReferenceNumber().longValue();
        bcsReferenceVO.title = referenceVO.getTitle();
        bcsReferenceVO.url = referenceVO.getUrl() == null ? Optional.empty() : Optional.of(referenceVO.getUrl());
        return bcsReferenceVO;
    }

    public static Year toBcsYear(org.dddml.roochdemocontracts.domain.Year year) {
        if (year == null) {
            return null;
        }
        Year bcsYear = new Year();
        bcsYear.number = year.getNumber().shortValue();
        bcsYear.calendar = year.getCalendar();
        return bcsYear;
    }


    public static String formatRoochObjectIdHex(String objectId) {
        return com.github.wubuku.rooch.utils.HexUtils.formatHex(objectId);
    }

    public static String shortHex(Integer i) {
        return com.github.wubuku.rooch.utils.HexUtils.byteArrayToHexWithPrefix(com.github.wubuku.rooch.bcs.BcsUtils.serializeU16(i.shortValue()));
    }

    public static String u32Hex(Long i) {
        return com.github.wubuku.rooch.utils.HexUtils.byteArrayToHexWithPrefix(com.github.wubuku.rooch.bcs.BcsUtils.serializeU32(i.intValue()));
    }

    public static String toBcsHex(org.dddml.roochdemocontracts.domain.Day day) {
        try {
            return com.github.wubuku.rooch.utils.HexUtils.byteArrayToHexWithPrefix(BcsDomainBeanUtils.toBcsDay(day).bcsSerialize());
        } catch (com.novi.serde.SerializationError e) {
            throw new RuntimeException(e);
        }
    }


}
