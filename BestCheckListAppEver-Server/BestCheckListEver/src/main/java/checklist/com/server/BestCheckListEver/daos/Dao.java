package checklist.com.server.BestCheckListEver.daos;

import java.util.List;

public interface Dao<T>{

	T getById(Integer id);

	List<T> getAll();

	void save(T t);

	void update(T t);

	void delete(T t);

}


