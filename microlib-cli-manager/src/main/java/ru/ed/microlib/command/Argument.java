package ru.ed.microlib.command;

import java.util.Objects;

public class Argument {
    private String shortKey;
    private String longKey;
    private boolean mandatory;

    public Argument(String shortKey, String longKey, boolean mandatory) {
        this.shortKey = shortKey;
        this.longKey = longKey;
        this.mandatory = mandatory;
    }

    public String getShortKey() {
        return shortKey;
    }

    public String getLongKey() {
        return longKey;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Argument argument = (Argument) o;
        return mandatory == argument.mandatory &&
                Objects.equals(shortKey, argument.shortKey) &&
                Objects.equals(longKey, argument.longKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortKey, longKey, mandatory);
    }
}
