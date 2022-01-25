package com.example.springes;

import com.example.dto.Component;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

@SpringBootTest
class SpringEsApplicationTests {

    @Qualifier("elasticsearchTemplate")
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @BeforeEach
    void setComponent() {
        Component component = Component.builder()
                .id(1)
                .name("Component1")
                .build();

        elasticsearchOperations.save(component, IndexCoordinates.of("component"));
    }
    @Test
    void elasticsearchOperationsTest() {
        Component component1 = elasticsearchOperations.get("1", Component.class, IndexCoordinates.of("component"));

        assert component1 != null;
        Assertions.assertThat(component1.getName()).isEqualTo("Component1");
    }

    @Test
    void esUpdateTest() {
        Component component = elasticsearchOperations.get("1", Component.class, IndexCoordinates.of("component"));

        assert component != null;
        component.setName("TestComponent1");
        elasticsearchOperations.save(component, IndexCoordinates.of("component"));

        Component component1 = elasticsearchOperations.get("1", Component.class, IndexCoordinates.of("component"));
        assert component1 != null;
        Assertions.assertThat(component1.getName()).isEqualTo(component.getName());
    }

    @Test
    void esDeleteTest() {
        elasticsearchOperations.delete("1", IndexCoordinates.of("component"));
        Component component1 = elasticsearchOperations.get("1", Component.class, IndexCoordinates.of("component"));
        Assertions.assertThat(component1).isNull();
    }


    @Test
    void contextLoads() {
        Assertions.assertThat(true).isTrue();
    }

}
