package burundi.ilucky.model;

import burundi.ilucky.service.Product.ProductRedisService;
import burundi.ilucky.service.Product.ProductService;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductListener {
    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);
    @Autowired
    private ProductRedisService productRedisService;
    @PrePersist
    public void prePersist(Product product) {
        logger.info("prePersist");
    }

    @PostPersist //save = persis
    public void postPersist(Product product) {
        // Update Redis cache
        logger.info("postPersist");
        productRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preUpdate");
    }

    @PostUpdate
    public void postUpdate(Product product) {
        // Update Redis cache
        logger.info("postUpdate");
        productRedisService.clear();
    }

    @PreRemove
    public void preRemove(Product product) {
        //ApplicationEventPublisher.instance().publishEvent(event);
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product) {
        // Update Redis cache
        logger.info("postRemove");
        productRedisService.clear();
    }
}
