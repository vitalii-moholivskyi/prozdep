package department.dao;

import java.util.List;

public interface IDAOGeneric<T> {

	List<T> findAll();

	T find(int id);
	T find(int id, boolean isEager);

	void insert(T entity);

	void update(T entity);

	void remove(T entity);
}