package org.setana.treenity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class TreenityApplication {

    private static final Logger logger = LoggerFactory.getLogger(TreenityApplication.class);

    @PostConstruct
    public void started() {
        // timezone UTC 셋팅
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        logger.info("Server starts at Timezone: " + TimeZone.getDefault().getDisplayName());
    }

    public static void main(String[] args) {
        SpringApplication.run(TreenityApplication.class, args);
    }

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
