package org.dddml.roochdemocontracts.rooch.bcs;


import java.util.Optional;

public class BcsDomainBeanUtils {
    private BcsDomainBeanUtils() {
    }

    public static Day toBcsDay(org.dddml.roochdemocontracts.domain.Day day) {
        if (day == null) {
            return null;
        }
        Day bcsDay = new Day();
        bcsDay.month = toBcsMoth(day.getMonth());
        bcsDay.number = (byte) day.getNumber().intValue();
        bcsDay.timeZone = day.getTimeZone();
        return bcsDay;
    }

    private static Month toBcsMoth(org.dddml.roochdemocontracts.domain.Month month) {
        if (month == null) {
            return null;
        }
        Month bcsMonth = new Month();
        bcsMonth.year = toBcsYear(month.getYear());
        bcsMonth.number = (byte) month.getNumber().intValue();
        bcsMonth.isLeap = month.getIsLeap();
        return bcsMonth;
    }

    private static Year toBcsYear(org.dddml.roochdemocontracts.domain.Year year) {
        if (year == null) {
            return null;
        }
        Year bcsYear = new Year();
        bcsYear.number = (short) year.getNumber().intValue();
        bcsYear.calendar = year.getCalendar();
        return bcsYear;
    }

    public static ReferenceVO toReferenceVO(org.dddml.roochdemocontracts.domain.ReferenceVO referenceVO) {
        if (referenceVO == null) {
            return null;
        }
        ReferenceVO bcsReferenceVO = new ReferenceVO();
        bcsReferenceVO.referenceNumber = referenceVO.getReferenceNumber().longValue();
        bcsReferenceVO.title = referenceVO.getTitle();
        bcsReferenceVO.url = Optional.of(referenceVO.getUrl());
        return bcsReferenceVO;
    }


}
