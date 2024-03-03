package com.lazarev.flashcards.config.jpa;

import com.google.common.reflect.ClassPath;
import io.hypersistence.utils.hibernate.type.util.ClassImportIntegrator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private static final String DTO_PACKAGE = "com.lazarev.flashcards.dto";
    private static final String ENTITY_PACKAGE = "com.lazarev.flashcards.entity";
    private static final String INTEGRATOR_PROVIDER = "hibernate.integrator_provider";

    private final JpaProperties jpaProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>(){{
            put(INTEGRATOR_PROVIDER, dtoIntegratorProvider());
            putAll(jpaProperties.getProperties());
        }};

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Bean
    @SneakyThrows
    public IntegratorProvider dtoIntegratorProvider() {
        List<Class<?>> dtos = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getTopLevelClassesRecursive(DTO_PACKAGE)
                .stream()
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toList());

        return () -> List.of(new ClassImportIntegrator(dtos));
    }
}
 // in the previous lesson