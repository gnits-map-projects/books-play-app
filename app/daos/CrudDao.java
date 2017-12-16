package daos;

public interface CrudDao<K, E> {

    E persist(E entity);

    E update(E entity);

    E deleteById(K id);

    E findById(K id);

}
