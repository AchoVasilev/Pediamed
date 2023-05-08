package server.application.services.file;

import io.micronaut.http.multipart.CompletedFileUpload;
import server.domain.entities.base.File;

import java.util.List;

public interface FileService<T extends File > {
    void upload(CompletedFileUpload file);

    List<T> getAll();
}
