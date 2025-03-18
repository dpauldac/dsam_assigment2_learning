package de.uniba.spring.firestoreexample.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
        try {
            // Step 1: Read existing movies
            log.info("Reading existing movies...");
            CollectionReference collectionReference = db.collection("movies");
            ApiFuture<QuerySnapshot> queryFuture = collectionReference.get();
            QuerySnapshot querySnapshot = queryFuture.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String title = document.getString("title");
                Long releaseYear = document.getLong("releaseYear");
                log.info("Movie: Title = {}, Release Year = {}", title, releaseYear);
            }

            // Step 2: Add a new movie
            log.info("Adding a new movie...");
            // Add document data using a hashmap
            Map<String, Object> newMovie = new HashMap<>();
            newMovie.put("title", "28 Years later");
            newMovie.put("releaseYear", 2025);
            //Create a reference to a new document in the Firestore collection. The document will have an auto-generated ID.
            DocumentReference docRef = collectionReference.document();
            /*
            Asynchronously writes the newMovie object to the document referenced by docRef.
            Returns an ApiFuture<WriteResult>, which represents the result of the write operation.
            ApiFuture<WriteResult> Represents a Future Result.
            It does not contain the actual result when it is first returned.
            Retrieve the result once the operation is complete (get()).
            */
            ApiFuture<WriteResult> writeResultApiFuture = docRef.set(newMovie);
            //writeResult.get() Retrieves the Actual Result.
            //The get() method blocks the current thread and waits for the asynchronous operation to complete.
            WriteResult writeResult = writeResultApiFuture.get();
            log.info("New movie added: Into the Wild, 2007");
            log.info(writeResult.toString());

            //Add another
//            Map<String, Object> newDocument = new HashMap<>();
//            newDocument.put("title", "Before sunrise");
//            newDocument.put("releaseYear", 1995);
//            DocumentReference documentReference = db.collection("movies").document();
//            ApiFuture<WriteResult> result = documentReference.set(newDocument);
//            log.info("Update time : " + result.get().getUpdateTime());
//            log.info("Added another movie: Before sunrise, 1995");

            // Step 3: Query movies with releaseYear 2024 or 2025
            log.info("Querying movies with releaseYear 2024 or 2025...");
            Query query = db.collection("movies").whereIn("releaseYear", Arrays.asList(2024, 2025));
//            Query q = db.collection("movies")
//                    .whereGreaterThan("releaseYear", 2024)
//                    .whereLessThan("releaseYear", 2025);
            ApiFuture<QuerySnapshot> future = query.get();
            QuerySnapshot querySnapshot2 = future.get();
            List<QueryDocumentSnapshot> documentSnapshots = querySnapshot2.getDocuments();
            for(QueryDocumentSnapshot document: documentSnapshots) {
                String title = document.getString("title");
                Long relaseYear = document.getLong("releaseYear");
                log.info("Query Result: Title = {}, Release Year = {}", title, relaseYear);
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error performing Firestore operations", e);
            Thread.currentThread().interrupt();
        }
    }
}
