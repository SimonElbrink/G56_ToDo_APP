package se.lexicon.todo_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import se.lexicon.notify.config.NotifyUtilConfig;

@Configuration
@Import(NotifyUtilConfig.class)
public class AppConfig {
}
