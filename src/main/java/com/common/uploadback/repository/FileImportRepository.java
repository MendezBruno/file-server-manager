package com.common.uploadback.repository;

import com.common.uploadback.FileImports;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileImportRepository extends MongoRepository<FileImports, String> {
}
