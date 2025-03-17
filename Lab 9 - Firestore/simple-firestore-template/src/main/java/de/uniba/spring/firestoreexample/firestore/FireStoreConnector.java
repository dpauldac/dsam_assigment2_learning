package de.uniba.spring.firestoreexample.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Configuration
@PropertySource("application.properties")
public class FireStoreConnector {

    @Value("${gcp.project.id}")
    private String projectId;

    private Firestore database;

    @PostConstruct
    public void initializeFireStore() {
        GoogleCredentials credentials = null;
        try {
            credentials = GoogleCredentials.getApplicationDefault();
        } catch (IOException e) {
            throw new IllegalStateException("Could not initialize FireStoreConnector", e);
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build();
        FirebaseApp.initializeApp(options);

        database = FirestoreClient.getFirestore();
    }

    public Firestore getFirestoreDatabase() {
        return database;
    }

}
