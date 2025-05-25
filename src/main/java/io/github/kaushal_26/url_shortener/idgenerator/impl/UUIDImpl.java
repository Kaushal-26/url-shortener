package io.github.kaushal_26.url_shortener.idgenerator.impl;

import io.github.kaushal_26.url_shortener.exception.NotImplementedError;
import io.github.kaushal_26.url_shortener.idgenerator.IdGenerator;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UUIDImpl implements IdGenerator {

    @Override
    public Long nextId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    @Override
    public Map<String, Object> getIdGeneratorProperties(Long id) {
        // No properties to set for UUID
        throw new NotImplementedError("Method getIdGeneratorProperties() is not applicable for UUID");
    }

    @Override
    public void setIdGeneratorProperties(Map<String, Object> idGeneratorProperties) {
        // No properties to set for UUID
        throw new NotImplementedError("Method setIdGeneratorProperties() is not applicable for UUID");
    }

    @Override
    public List<String> getIdsByTimeStamp(Instant timeStamp) {
        throw new NotImplementedError("As UUIDs are not time-based, this method is not applicable");
    }

}
