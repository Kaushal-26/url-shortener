package io.github.kaushal_26.url_shortener.repository.impl;

import io.github.kaushal_26.url_shortener.model.Url;
import io.github.kaushal_26.url_shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class UrlRepositoryMongoImpl implements UrlRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UrlRepositoryMongoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Url> find(String code) {
        Query query = new Query(Criteria.where("code").is(code).and("deletedAt").is(null));
        return Optional.ofNullable(mongoTemplate.findOne(query, Url.class));
    }

    @Override
    public Url save(Url url) {
        return mongoTemplate.save(url);
    }

    @Override
    public Optional<Url> update(String code, String newOriginalUrl) {
        Query query = new Query(Criteria.where("code").is(code).and("deletedAt").is(null));

        // Document exists, proceed with update
        Update update = new Update()
                .set("originalUrl", newOriginalUrl)
                .set("updatedAt", Instant.now())
                .set("accessCount", 0)
                .set("lastAccessedAt", null);

        return Optional.ofNullable(
                mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Url.class)
        );
    }

    @Override
    public Optional<Url> delete(String code) {
        Query query = new Query(Criteria.where("code").is(code).and("deletedAt").is(null));

        // Document exists, proceed with delete
        Update update = Update.update("deletedAt", Instant.now());

        return Optional.ofNullable(mongoTemplate.findAndModify(query, update, Url.class));
    }

    @Override
    public Url updateAccessedDetails(String code, long accessCount) {
        Query query = new Query(Criteria.where("code").is(code).and("deletedAt").is(null));

        // Document exists, proceed with delete
        Update update = Update.update("accessCount", accessCount + 1)
                .set("lastAccessedAt", Instant.now())
                .set("updatedAt", Instant.now());

        return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Url.class);
    }

}
