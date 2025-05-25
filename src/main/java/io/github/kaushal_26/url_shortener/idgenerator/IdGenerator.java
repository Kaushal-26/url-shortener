package io.github.kaushal_26.url_shortener.idgenerator;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface IdGenerator {

    Long nextId();

    Map<String, Object> getIdGeneratorProperties(Long id);

    void setIdGeneratorProperties(Map<String, Object> idGeneratorProperties);

    List<String> getIdsByTimeStamp(Instant timeStamp);

}
