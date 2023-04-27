package server.domain.repositories;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
public interface BaseRepository<TEntity, TKey> extends CrudRepository<TEntity, TKey> {

    @Override
    @Query("UPDATE #{#entityName} e SET e.deleted = false WHERE e.id=?1")
    void deleteById(@NonNull TKey id);

    List<TEntity> findByDeletedFalse();

    List<TEntity> findByDeletedTrue();
}
