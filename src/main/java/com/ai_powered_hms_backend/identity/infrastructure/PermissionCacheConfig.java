package com.ai_powered_hms_backend.identity.infrastructure;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * [CACHE] Enterprise-scale performance layer.
 *
 * This entire class exists ONLY to avoid a database round-trip on every
 * authenticated HTTP request to recompute a staff member's effective
 * permissions (see UserAccessService#effectivePermissions).
 *
 * NOT REQUIRED for correctness — a smaller system can delete this class,
 * remove @Cacheable/@CacheEvict from UserAccessService, and everything
 * still works correctly, just with one extra set of DB queries per request.
 *
 * TTL is intentionally short (60s) so that a permission revocation takes
 * effect within, at most, 60 seconds — not instantly, but bounded and
 * predictable. This is the deliberate trade: fewer DB queries per request,
 * in exchange for up to 60s of staleness on authorization decisions.
 *
 * SCALING NOTE: this uses an in-memory (Caffeine) cache, which is CORRECT
 * ONLY for a single application instance. If this system is ever deployed
 * behind a load balancer with multiple instances, this MUST be swapped for
 * a distributed cache (e.g. Redis via spring-boot-starter-data-redis +
 * RedisCacheManager) — otherwise each instance has its own independent
 * stale cache, and a revocation on one instance won't be seen by another.
 */


@Configuration
@EnableCaching
public class PermissionCacheConfig {
	
	 // [CACHE] Logical cache name — referenced by name in @Cacheable/@CacheEvict below.
    public static final String EFFECTIVE_PERMISSIONS_CACHE = "effectivePermissions";

    @Bean
    CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(EFFECTIVE_PERMISSIONS_CACHE);
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(60, TimeUnit.SECONDS) // [CACHE] staleness bound — see class javadoc
                        .maximumSize(10_000) // [CACHE] safety cap — one entry per active staff member
        );
        return cacheManager;
    }	

}
