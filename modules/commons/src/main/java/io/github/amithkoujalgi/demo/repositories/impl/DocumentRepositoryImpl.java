package io.github.amithkoujalgi.demo.repositories.impl;

import io.github.amithkoujalgi.demo.entities.Document;
import io.github.amithkoujalgi.demo.repositories.DocumentRepository;

import java.util.ArrayList;
import java.util.List;

public class DocumentRepositoryImpl implements DocumentRepository {
    @Override
    public List<Document> fetchAllDocuments() throws Exception {
        return new ArrayList<>();
    }
}
