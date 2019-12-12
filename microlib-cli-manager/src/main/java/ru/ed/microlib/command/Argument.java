package ru.ed.microlib.command;

import java.util.Objects;

public class Argument {
    private String description;
    private String shortKey;
    private String longKey;
    private boolean mandatory;

    public Argument(String description, String shortKey, String longKey, boolean mandatory) {
        this.description = description;
        this.shortKey = shortKey;
        this.longKey = longKey;
        this.mandatory = mandatory;
    }

    public String getDescription() {
        return description;
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
                Objects.equals(description, argument.description) &&
                Objects.equals(shortKey, argument.shortKey) &&
                Objects.equals(longKey, argument.longKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, shortKey, longKey, mandatory);
    }
}
