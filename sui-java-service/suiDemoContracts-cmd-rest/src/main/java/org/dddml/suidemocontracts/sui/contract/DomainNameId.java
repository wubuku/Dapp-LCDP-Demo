// <autogenerated>
//   This file was generated by T4 code generator .
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.suidemocontracts.sui.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.sui.bean.*;

public class DomainNameId extends MoveObject<DomainNameId.DomainNameIdFields> {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DomainNameIdFields {
        private String topLevelDomain;

        private String secondLevelDomain;


        public String getTopLevelDomain() {
            return topLevelDomain;
        }

        public void setTopLevelDomain(String topLevelDomain) {
            this.topLevelDomain = topLevelDomain;
        }

        public String getSecondLevelDomain() {
            return secondLevelDomain;
        }

        public void setSecondLevelDomain(String secondLevelDomain) {
            this.secondLevelDomain = secondLevelDomain;
        }

        @Override
        public String toString() {
            return "DomainNameIdFields{" +
                    "topLevelDomain=" + '\'' + topLevelDomain + '\'' +
                    ", secondLevelDomain=" + '\'' + secondLevelDomain + '\'' +
                    '}';
        }
    }
}

