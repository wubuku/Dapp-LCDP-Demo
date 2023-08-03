package org.dddml.aptosdemocontracts.specialization;


public class PropertyMetadataDto {

    public PropertyMetadataDto() {
    }

    public PropertyMetadataDto(String propertyName, String propertyType, boolean isFilteringProperty) {
        this.name = propertyName;
        this.typeName = propertyType;
        this.isFilteringProperty = isFilteringProperty;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String value) {
        typeName = value;
    }

    //    private java.lang.Class privateType;
    //    public java.lang.Class getType()
    //    {
    //        return privateType;
    //    }
    //    public void setType(java.lang.Class value)
    //    {
    //        privateType = value;
    //    }

    private boolean isBasicType;

    public boolean getIsBasicType() {
        return isBasicType;
    }

    public void setIsBasicType(boolean value) {
        isBasicType = value;
    }

    private String declaringObjectName;

    public String getDeclaringObjectName() {
        return declaringObjectName;
    }

    public void setDeclaringObjectName(String value) {
        declaringObjectName = value;
    }

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        displayName = value;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int value) {
        length = value;
    }

    private String genericTypeName;

    public String getGenericTypeName() {
        return genericTypeName;
    }

    public void setGenericTypeName(String value) {
        genericTypeName = value;
    }

    private String referenceTypeName;

    public String getReferenceTypeName() {
        return referenceTypeName;
    }

    public void setReferenceTypeName(String value) {
        referenceTypeName = value;
    }

    private String referenceName;

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String value) {
        referenceName = value;
    }

    private String itemTypeName;

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String value) {
        itemTypeName = value;
    }

    private String inverseOf;

    public String getInverseOf() {
        return inverseOf;
    }

    public void setInverseOf(String value) {
        inverseOf = value;
    }

    private boolean isDerived;

    public boolean getIsDerived() {
        return isDerived;
    }

    public void setIsDerived(boolean value) {
        isDerived = value;
    }

    private boolean isTransient;

    public boolean getIsTransient() {
        return isTransient;
    }

    public void setIsTransient(boolean value) {
        isTransient = value;
    }

    private boolean isHoisted;

    public boolean getIsHoisted() {
        return isHoisted;
    }

    public void setIsHoisted(boolean value) {
        isHoisted = value;
    }

    private boolean isReallyFlattened;

    public boolean getIsReallyFlattened() {
        return isReallyFlattened;
    }

    public void setIsReallyFlattened(boolean value) {
        isReallyFlattened = value;
    }

    private String sourceChainingName;

    public String getSourceChainingName() {
        return sourceChainingName;
    }

    public void setSourceChainingName(String value) {
        sourceChainingName = value;
    }

    private String originalPropertyName;

    public String getOriginalPropertyName() {
        return originalPropertyName;
    }

    public void setOriginalPropertyName(String value) {
        originalPropertyName = value;
    }

    private boolean isNullableType;

    public boolean getIsNullableType() {
        return isNullableType;
    }

    public void setIsNullableType(boolean value) {
        isNullableType = value;
    }

    private boolean isDiscriminator;

    public boolean getIsDiscriminator() {
        return isDiscriminator;
    }

    public void setIsDiscriminator(boolean value) {
        isDiscriminator = value;
    }

    private boolean isReservedProperty;

    public boolean getIsReservedProperty() {
        return isReservedProperty;
    }

    public void setIsReservedProperty(boolean value) {
        isReservedProperty = value;
    }

    private boolean isReservedPropertyVersion;

    public boolean getIsReservedPropertyVersion() {
        return isReservedPropertyVersion;
    }

    public void setIsReservedPropertyVersion(boolean value) {
        isReservedPropertyVersion = value;
    }

    private boolean isReservedPropertyDeleted;

    public boolean getIsReservedPropertyDeleted() {
        return isReservedPropertyDeleted;
    }

    public void setIsReservedPropertyDeleted(boolean value) {
        isReservedPropertyDeleted = value;
    }

    private boolean isReservedPropertyActive;

    public boolean getIsReservedPropertyActive() {
        return isReservedPropertyActive;
    }

    public void setIsReservedPropertyActive(boolean value) {
        isReservedPropertyActive = value;
    }

    private boolean isReservedPropertyCreatedBy;

    public boolean getIsReservedPropertyCreatedBy() {
        return isReservedPropertyCreatedBy;
    }

    public void setIsReservedPropertyCreatedBy(boolean value) {
        isReservedPropertyCreatedBy = value;
    }

    private boolean isReservedPropertyCreatedAt;

    public boolean getIsReservedPropertyCreatedAt() {
        return isReservedPropertyCreatedAt;
    }

    public void setIsReservedPropertyCreatedAt(boolean value) {
        isReservedPropertyCreatedAt = value;
    }

    private boolean isReservedPropertyUpdatedBy;

    public boolean getIsReservedPropertyUpdatedBy() {
        return isReservedPropertyUpdatedBy;
    }

    public void setIsReservedPropertyUpdatedBy(boolean value) {
        isReservedPropertyUpdatedBy = value;
    }

    private boolean isReservedPropertyUpdatedAt;

    public boolean getIsReservedPropertyUpdatedAt() {
        return isReservedPropertyUpdatedAt;
    }

    public void setIsReservedPropertyUpdatedAt(boolean value) {
        isReservedPropertyUpdatedAt = value;
    }

    private boolean isId;

    public boolean getIsId() {
        return isId;
    }

    public void setIsId(boolean value) {
        isId = value;
    }

    private boolean isGlobalId;

    public boolean getIsGlobalId() {
        return isGlobalId;
    }

    public void setIsGlobalId(boolean value) {
        isGlobalId = value;
    }

    private boolean isOuterId;

    public boolean getIsOuterId() {
        return isOuterId;
    }

    public void setIsOuterId(boolean value) {
        isOuterId = value;
    }

    private String privateDerivedFrom;

    public String getDerivedFrom() {
        return privateDerivedFrom;
    }

    public void setDerivedFrom(String value) {
        privateDerivedFrom = value;
    }

    public boolean getIsCollectionProperty() {
        return !isStringNullOrEmpty(getItemTypeName());
    }

    public boolean getIsReferenceIdProperty() {
        return !isStringNullOrEmpty(getReferenceTypeName());
    }

    private boolean isFilteringProperty;

    public boolean getIsFilteringProperty() {
        return isFilteringProperty;
    }

    public void setIsFilteringProperty(boolean value) {
        isFilteringProperty = value;
    }

    private static boolean isStringNullOrEmpty(String string) {
        return string == null || string.equals("");
    }

}
