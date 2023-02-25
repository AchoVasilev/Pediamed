package server.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<TEntity, TKey> extends JpaRepository<TEntity, TKey> {

    @Override
    @Query("UPDATE #{#entityName} e SET e.deleted = false WHERE e.id=?1")
    @Modifying
    void deleteById(TKey id);

    @Override
    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = false")
    List<TEntity> findAll();

    @Query("SELECT e FROM #{#entityName} e WHERE e.deleted = true")
    List<TEntity> findDeleted();

    @Query("SELECT e FROM #{#entityName} e")
    List<TEntity> findAllWithDeleted();
}
