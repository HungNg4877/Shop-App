package burundi.ilucky.service.Product;

import burundi.ilucky.payload.Response.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductRedisService.class);
    @Value("${spring.data.redis.use-redis-cache}")
    private boolean useRedisCache;

    private String getCacheKey(String keyword, Long categoryId, PageRequest pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        Sort sort = pageRequest.getSort();
        Sort.Order order = sort.getOrderFor("id");
        String sortDirection = (order != null && order.getDirection() == Sort.Direction.ASC) ? "asc" : "desc";

        return String.format("all_products:%s:%d:%d:%d:%s",
                keyword != null ? keyword : "",
                categoryId != null ? categoryId : 0,
                pageNumber, pageSize, sortDirection);

    }
    public List<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        if (!useRedisCache)
            return null;

        String cacheKey = getCacheKey(keyword, categoryId, pageRequest);
        String json = redisTemplate.opsForValue().get(cacheKey);

        if (json == null) {
            logger.info("Cache miss for key: {}", cacheKey);
            return null;
        }

        try {
            return redisObjectMapper.readValue(json, new TypeReference<List<ProductResponse>>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }
    public void saveAllProducts(List<ProductResponse> productResponses, String keyword, Long categoryId, PageRequest pageRequest) throws JsonProcessingException {
        if (!useRedisCache || productResponses == null || productResponses.isEmpty()) {
            return;
        }

        String cacheKey = getCacheKey(keyword, categoryId, pageRequest);
        String json = redisObjectMapper.writeValueAsString(productResponses);
        redisTemplate.opsForValue().set(cacheKey, json, 5, TimeUnit.MINUTES);
        logger.info("Saved products to Redis with key: {}", cacheKey);
    }
    public void clear() {
        logger.info("Flushing all Redis cache...");
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
