package com.wraith.repository;

import com.wraith.json.ISO8601DateFormatModule;
import com.wraith.repository.entity.Users;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.repository.context.AfterSaveEvent;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.util.ClassUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * User: rowan.massey
 * Date: 24/02/13
 * Time: 20:07
 */
@Configuration
@Import(RepositoryRestMvcConfiguration.class)
public class ApplicationRestConfig {

//    @Bean
//    public ResourceProcessor<Resource<Users>> userProcessor() {
//        return new ResourceProcessor<Resource<Users>>() {
//            @Override
//            public Resource<Users> process(Resource<Users> resource) {
//                resource.add(new Link("http://localhost:8080/testlink", "test.link"));
//                return resource;
//            }
//        };
//    }

    @Bean
    public Validator userValidator() {
        return new Validator() {

            List<String> badWords = new ArrayList<>();

            {
                badWords.add("fuck");
            }

            @Override
            public boolean supports(Class<?> clazz) {
                return ClassUtils.isAssignable(clazz, Users.class);
            }

            @Override
            public void validate(Object target, Errors errors) {
                Users user = (Users) target;

                if (user.getUserName() != null) {
                    return;
                }

                for (String words : badWords) {
                    if (user.getUserName().toLowerCase().contains(words)) {
                        errors.rejectValue("User name", "Invalid user name.");
                    }
                }
            }
        };
    }

//    @Bean
//    public AnnotatedHandlerRepositoryEventListener handlerListener() {
//        return new AnnotatedHandlerRepositoryEventListener("com.wraith.repository.handlers");
//    }

    @Bean
    public ApplicationListener<AfterSaveEvent> afterSaveHandler() {
        return new ApplicationListener<AfterSaveEvent>() {
            @Override
            public void onApplicationEvent(AfterSaveEvent event) {
                if (event.getSource() instanceof Users) {
                    System.out.println("Got a user " + event.getSource());
                }
            }
        };
    }

    @Bean
    public ISO8601DateFormatModule dateFormatModule() {
        return new ISO8601DateFormatModule();
    }
}
