// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

package org.dddml.roochdemocontracts.rooch.contract;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.wubuku.rooch.bean.*;

import java.math.*;
import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Tag {

    private String name;

    private Long offChainVersion;

    private BigInteger version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOffChainVersion() {
        return offChainVersion;
    }

    public void setOffChainVersion(Long offChainVersion) {
        this.offChainVersion = offChainVersion;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Tag{" +
                ", name=" + '\'' + name + '\'' +
                ", offChainVersion=" + offChainVersion +
                ", version=" + version +
                '}';
    }

    public static class MoveObject extends com.github.wubuku.rooch.bean.MoveOSStdObject<Tag> {
    }

}
