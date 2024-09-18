package edu.school21.app;

import edu.school21.human.Human;
import edu.school21.operation.Operation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReflectionService {

    private static final int DELIMITER_LENGTH = 20;
    private static final char DELIMITER_LETTER = '-';

    private Class[] availableClasses;
    private Class currentClass;

    public ReflectionService() {
        availableClasses = new Class[] {Human.class, Operation.class};
    }

    public void run() {
        printAvailableClasses();
        setCurrentClass();
        printCurrentClassInfo();
    }

    /**/
    private void printAvailableClasses() {
        System.out.printf("Classes:\n");
        for (int i = 0; i < this.availableClasses.length; ++i) {
            System.out.printf("\t- %s\n",
                              this.availableClasses[i].getSimpleName());
        }
        printDelimiterLine();
    }

    /**/
    private void setCurrentClass() {
        while (!chooseClass()) {
            System.out.printf("Invalid class name, try again.\n");
        }
        printDelimiterLine();
    }

    private boolean chooseClass() {
        System.out.printf("Enter class name:\n");
        String className = readString();
        for (int i = 0; i < this.availableClasses.length; ++i) {
            if (className.equals(this.availableClasses[i].getSimpleName())) {
                this.currentClass = availableClasses[i];
                return true;
            }
        }
        return false;
    }

    private String readString() {
        final String INVALID_INPUT = "";
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else if (scanner.hasNext()) {
            return INVALID_INPUT;
        } else {
            System.out.printf("Exiting...\n");
            System.exit(0);
        }
        return INVALID_INPUT;
    }

    /**/
    private void printCurrentClassInfo() {
        printCurrentClassFields();
        printCurrentClassMethods();
        printDelimiterLine();
        Object current = createObject(); // TODO
    }

    private void printCurrentClassFields() {
        Field[] fields =
            Arrays.stream(this.currentClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .toArray(Field[] ::new);
        System.out.printf("Fields:\n");
        for (int i = 0; i < fields.length; ++i) {
            System.out.printf("\t\t%s %s\n\n",
                              fields[i].getType().getSimpleName(),
                              fields[i].getName());
        }
    }

    private void printCurrentClassMethods() {
        Method[] methods = this.currentClass.getDeclaredMethods();
        System.out.printf("Methods:\n");
        for (int i = 0; i < methods.length; ++i) {
            String args = Arrays.stream(methods[i].getParameterTypes())
                              .map(type -> type.getSimpleName())
                              //.mapToObj(String::valueOf)
                              .collect(Collectors.joining(", "));
            System.out.printf("\t\t%s %s(%s)\n\n",
                              methods[i].getReturnType().getSimpleName(),
                              methods[i].getName(), args);
        }
    }

    /**/
    private Object createObject() {
        System.out.printf("Let’s create an object.\n");
    }

    private void printDelimiterLine() {
        System.out.print("\n");
        for (int i = 0; i < DELIMITER_LENGTH; ++i) {
            System.out.print(DELIMITER_LETTER);
        }
        System.out.print("\n");
    }
}
