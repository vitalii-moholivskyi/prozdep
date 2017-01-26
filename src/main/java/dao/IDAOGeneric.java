package dao;

import java.util.List;

interface IDAOGeneric<T> {

	List<T> findAll();

	T find(int id);

	T insert(T dep);

	T update(T dep);

	void remove(T dep);
}
