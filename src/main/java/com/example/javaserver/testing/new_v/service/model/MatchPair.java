package com.example.javaserver.testing.new_v.service.model;

import java.util.Objects;

public class MatchPair {
    private Integer keyId;
    private Integer valueId;

    public MatchPair(Integer keyId, Integer valueId) {
        this.keyId = keyId;
        this.valueId = valueId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchPair matchPair = (MatchPair) o;
        return Objects.equals(keyId, matchPair.keyId) && Objects.equals(valueId, matchPair.valueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keyId, valueId);
    }

    public Integer getValueId() {
        return valueId;
    }

    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }
}
