package com.fernando.springit;

import com.fernando.springit.domain.Comment;
import com.fernando.springit.domain.Link;
import com.fernando.springit.repository.CommentRepository;
import com.fernando.springit.repository.LinkRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
	}

    // Bean used to start a CommandLineRunner that is used to
    // initialize a line in the database. Since we are using
    // H2 the database will be rebooted every time the server
    // is started
    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
        return args -> {
            log.info("Initializing the database");
            // Creating a new link using the constructor created by Data from lombok
            // It must be noticed that in Coc this always produces an error because
            // it does't recognize the anotations from lombok
            Link link = new Link(
                    "Getting started with spring boot 2", 
                    "https://therealdanvega.com/spring-boot-2");
            linkRepository.save(link);


            Comment comment = new Comment("A new comment", link);
            commentRepository.save(comment);

            link.addComment(comment);
        };
    }


}
