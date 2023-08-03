package org.dddml.aptosdemocontracts.specialization;

public abstract class AbstractIdFlattenedDtoFormatter<TDto> implements TextFormatter<TDto> {

    public TDto parse(String text) {
        String[] strValues = text.split(getFieldSeparator());
        TDto obj = newDto();
        String[] fieldNames = getFieldNames();
        if (strValues.length != fieldNames.length) {
            throw new IllegalArgumentException(String.format("The fields count in argument \"%1$s\" is NOT right.", "text"));
        }
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            String strVal = strValues[i].trim();
            setFieldStringValue(obj, fieldName, strVal);
        }
        return obj;
    }

    public String toString(TDto value) {
        StringBuilder sb = new StringBuilder();
        for (String f : getFieldNames()) {
            if (!sb.toString().isEmpty()) {
                sb.append(getFieldSeparator());
            }
            sb.append(getFieldStringValue(value, f));
        }
        return sb.toString();
    }

    private String fieldSeparator = ",";

    protected String getFieldSeparator() {
        return fieldSeparator;
    }

    protected void setFieldSeparator(String separator) {
        this.fieldSeparator = separator;
    }

    public AbstractIdFlattenedDtoFormatter() {
    }

    public AbstractIdFlattenedDtoFormatter(String fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    protected abstract TDto newDto();

    protected abstract String[] getFieldNames();

    protected abstract void setFieldStringValue(TDto dto, String fieldName, String fieldValue);

    protected abstract String getFieldStringValue(TDto dto, String fieldName);

}