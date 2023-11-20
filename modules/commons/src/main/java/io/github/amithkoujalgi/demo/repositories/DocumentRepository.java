package io.github.amithkoujalgi.demo.repositories;


import io.github.amithkoujalgi.demo.entities.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository {
    List<Document> fetchAllDocuments() throws Exception;
}