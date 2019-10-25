package com.common.uploadback.repository;
import com.common.uploadback.UploadFileResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UploadFileResponseRepository extends MongoRepository<UploadFileResponse, String> {
}
