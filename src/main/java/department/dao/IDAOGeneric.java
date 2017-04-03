package department.dao;

import java.util.List;

import department.model.bo.Paper;

public interface IDAOGeneric<T> {

	int count();
	List<T> findAll();
	List<T> findAll(long limit, long offset);

	int count(String name);
	List<T> findAll(String name);
	List<T> findAll(String name, long limit, long offset);
	List<T> findAll(String name, long limit, long offset, boolean isEager);

	T find(int id);
	T find(int id, boolean isEager);
	T insert(T entity);
	void update(T entity);
	void remove(T entity);

}