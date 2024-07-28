package schedule;

import java.util.*;

public class Students {
	public Students() {
	  BaseInitialization();
	}

	public Students(List<String> names) {
		BaseInitialization();
	  int size = names.size();
		if (size >= max_students_count_) size = max_students_count_;
		Iterator<String> it = names.iterator();
		while (size > 0) {
		  students_list_.put(students_count_, it.next());
			++students_count_;
			--size;
		}
	}

	public Students(Collection<String> names) {
	  BaseInitialization();
		int size = names.size();
		if (size > max_students_count_) size = max_students_count_;
		Iterator<String> it = names.iterator();
		while (size > 0) {
		  students_list_.put(students_count_, it.next());
			++students_count_;
			--size;
		}
	}

	public int Enrollment(String name) {
	  if ((students_count_ < max_students_count_) && (!students_list_.containsValue(name))) {
		  students_list_.put(students_count_++, name);
		}
		return students_count_;
	}

	public void Expulsion(String name) {
		Expulsion(GetID(name));
	}

	public void Expulsion(int id) {
		if (students_list_.containsKey(id)) {
	    students_list_.remove(id);
			Students tmp = new Students(students_list_.values());
			students_list_ = tmp.students_list_;
			students_count_ = tmp.students_count_;
		}
	}

  public int GetID(String name) {
		for(Map.Entry<Integer, String> pair: students_list_.entrySet()) {
		  if (pair.getValue().equals(name)) return pair.getKey();
		}
    return -1;
	}

	public String GetName(int id) {
	  return students_list_.get(id);
	}


	public void Print() {
		if (students_count_ == 0) {
		  System.out.print("Student list is empty\n");
		} else {
      students_list_.forEach((key, value) -> System.out.printf("%s\n", value));
		}
	}


	private Map<Integer, String> students_list_ ;
	private int students_count_;
	private static final int max_students_count_ = 10;

	private void BaseInitialization() {
	  students_list_ = new HashMap<Integer, String>();
		students_count_ = 0;
	}




}
