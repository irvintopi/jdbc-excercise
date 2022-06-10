package repository;

import java.util.List;

public interface Repository<ENTITY, ID> {

    List<ENTITY> findAll();

    ENTITY findById(ID id);

    Boolean exists(ID id);

    Boolean save(ENTITY entity);

    Integer update(ENTITY entity);

}
