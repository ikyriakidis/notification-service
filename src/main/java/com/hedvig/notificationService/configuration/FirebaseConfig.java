package com.hedvig.notificationService.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  private final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

  @Bean
  public DatabaseReference firebaseDatabse() {
    DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    return firebase;
  }

  @Value("${hedvig.firebase.database.url}")
  private String databaseUrl;

  @Value("/service-account.json")
  private String configPath;

  @PostConstruct
  public void init() throws IOException {
    logger.info("AAAAAAAA {}",configPath);
    InputStream inputStream = getClass().getResourceAsStream(configPath);

    FirebaseOptions options =
        new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .setDatabaseUrl(databaseUrl)
            .build();

    FirebaseApp.initializeApp(options);
  }
}
