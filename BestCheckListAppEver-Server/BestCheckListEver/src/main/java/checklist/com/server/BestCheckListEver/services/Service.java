package checklist.com.server.BestCheckListEver.services;
import java.util.List;

public interface Service<T> {

	T getById(Integer id);
	
	List<T> getAll();

	void update(T t);

	void add(T t);

	void remove(T t);

}
