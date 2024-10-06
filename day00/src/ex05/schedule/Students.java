package schedule;

import java.util.*;

public class Students {

    private static final int MAX_STUDENTS_COUNT = 10;

    private Map<Integer, String> studentsList_;
    private int studentsCount_;

    public Students() { baseInitialization(); }

    public Students(List<String> names) {
        baseInitialization();
        int size = names.size();
        if (size >= MAX_STUDENTS_COUNT) {
            size = MAX_STUDENTS_COUNT;
        }
        Iterator<String> it = names.iterator();
        while (size > 0) {
            studentsList_.put(studentsCount_, it.next());
            ++studentsCount_;
            --size;
        }
    }

    public Students(Collection<String> names) {
        baseInitialization();
        int size = names.size();
        if (size > MAX_STUDENTS_COUNT) {
            size = MAX_STUDENTS_COUNT;
        }
        Iterator<String> it = names.iterator();
        while (size > 0) {
            studentsList_.put(studentsCount_, it.next());
            ++studentsCount_;
            --size;
        }
    }

    public int getStudentsCount() { return studentsCount_; }

    public int enrollment(String name) {
        if ((studentsCount_ < MAX_STUDENTS_COUNT) &&
            (!studentsList_.containsValue(name))) {

            studentsList_.put(studentsCount_++, name);
        }
        return studentsCount_;
    }

    public void expulsion(String name) { expulsion(getID(name)); }

    public void expulsion(int id) {
        if (studentsList_.containsKey(id)) {
            studentsList_.remove(id);
            Students tmp = new Students(studentsList_.values());
            studentsList_ = tmp.studentsList_;
            studentsCount_ = tmp.studentsCount_;
        }
    }

    public int getID(String name) {
        for (Map.Entry<Integer, String> pair : studentsList_.entrySet()) {
            if (pair.getValue().equals(name))
                return pair.getKey();
        }
        return -1;
    }

    public String getName(int id) { return studentsList_.get(id); }

    public void print() {
        if (studentsCount_ == 0) {
            System.out.print("Student list is empty\n");
        } else {
            studentsList_.forEach(
                (key, value) -> System.out.printf("%s\n", value));
        }
    }

    private void baseInitialization() {
        studentsList_ = new HashMap<Integer, String>();
        studentsCount_ = 0;
    }
}
