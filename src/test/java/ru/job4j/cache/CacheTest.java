package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAdd() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        assertThat(cache.add(base1)).isTrue();
        assertThat(cache.add(base2)).isTrue();
    }

    @Test
    void whenUpdate() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        assertThat(cache.update(base1)).isTrue();
        assertThat(cache.update(base2)).isTrue();
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        cache.delete(base1);
        cache.delete(base2);
        assertThat(cache.get(1)).isNull();
        assertThat(cache.get(2)).isNull();
    }

    @Test
    void whenThrowException() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base1);
        cache.add(base2);
        assertThatThrownBy(() -> cache.update(new Base(1, 2)))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
        assertThatThrownBy(() -> cache.update(new Base(2, 2)))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}