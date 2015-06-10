package com.github.kumaraman21.intellijbehave.utility;

/**
 * Created by DeBritoD on 01.06.2015.
 */
public class ParametrizedToken {
    private int start;
    private int end;
    private String value;
    private boolean isParameter;

    public ParametrizedToken(String value, int start, int end, boolean isParameter) {
        this.value = value;
        this.start = start;
        this.end = end;
        this.isParameter = isParameter;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public void setParameter() {
        isParameter = true;
    }

    public void unsetParameter() {
        isParameter = false;
    }

    public String getValue() {
        return value;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public int getLength() {
        return getEnd() - getStart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParametrizedToken that = (ParametrizedToken) o;

        return getValue().equals(that.getValue());

    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
