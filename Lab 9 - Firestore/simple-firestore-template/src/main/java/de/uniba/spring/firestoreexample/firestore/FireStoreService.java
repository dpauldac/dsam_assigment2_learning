package de.uniba.spring.firestoreexample.firestore;

import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FireStoreService {

    private final FireStoreConnector fireStoreConnector;

    @Autowired
    FireStoreService(FireStoreConnector fireStoreConnector) {
        this.fireStoreConnector = fireStoreConnector;
    }

    public void fireStoreAction() {
        Firestore db = fireStoreConnector.getFirestoreDatabase();
        //TODO implement here
    }

}
