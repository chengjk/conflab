package com.jk.conflab;

import com.jk.conflab.model.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by jacky.cheng on 2015/9/25.
 */
@SpringBootApplication
public class Application {
    @Bean
    public User user() {
        return new User("name", 123);
    }
}
