package department.dao;

import java.util.List;

public interface IDAOGeneric<T> {

	List<T> findAll();
	List<T> findAll(long limit, long offset);
	int count();

	T find(int id);
	T find(int id, boolean isEager);
	T insert(T entity);
	void update(T entity);
	void remove(T entity);

}