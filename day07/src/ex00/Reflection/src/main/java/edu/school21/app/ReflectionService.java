package edu.school21.app;

import edu.school21.data.DataReader;
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
        try {
            Object current = createObject();
            updateObject(current);
            callMethod(current);
        } catch (Exception e) {
            System.err.printf("Error: %s\n", e.getMessage());
        }
    }

    /**/
    private void printAvailableClasses() {
        System.out.printf("\nClasses:\n");
        for (int i = 0; i < this.availableClasses.length; ++i) {
            System.out.printf("\t- %s\n",
                              this.availableClasses[i].getSimpleName());
        }
        printDelimiterLine();
    }

    /**/
    private void setCurrentClass() {
        while (!chooseClass()) {
            System.out.printf("Invalid class name, try again.\n\t");
        }
        printDelimiterLine();
    }

    private boolean chooseClass() {
        System.out.printf("Enter class name:\n\t");
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
    }

    private void printCurrentClassFields() {
        Field[] fields = getFields(this.currentClass);
        System.out.printf("Fields:\n");
        for (int i = 0; i < fields.length; ++i) {
            System.out.printf("\t\t%s %s\n\n",
                              fields[i].getType().getSimpleName(),
                              fields[i].getName());
        }
    }

    private void printCurrentClassMethods() {
        Method[] methods = getMethods(this.currentClass);
        System.out.printf("Methods:\n");
        for (int i = 0; i < methods.length; ++i) {
            String args = getMethodParameterString(methods[i], ", ");
            System.out.printf("\t\t%s %s(%s)\n\n",
                              methods[i].getReturnType().getSimpleName(),
                              methods[i].getName(), args);
        }
    }

    /**/
    private Object createObject() throws Exception {
        System.out.printf("Let’s create an object.\n");
        Object obj = this.currentClass.getDeclaredConstructor().newInstance();
        Field[] fields = getFields(this.currentClass);
        for (int i = 0; i < fields.length; ++i) {
            setField(fields[i], obj,
                     String.format("%s:\n\t", fields[i].getName()));
        }
        System.out.printf("Object created: %s.\n", obj.toString());
        printDelimiterLine();

        return obj;
    }

    /**/
    private Object updateObject(Object current) throws Exception {
        System.out.printf("Enter name of the field for changing:\n\t");
        Field field = readField(current);
        while (null == field) {
            System.out.printf("Invalid field name - try again\n\t");
            field = readField(current);
        }
        setField(field, current,
                 String.format("Enter %s value:\n\t",
                               field.getType().getSimpleName()));
        System.out.printf("Object updated: %s.\n", current.toString());
        printDelimiterLine();
        return current;
    }

    private Field readField(Object current) {
        try {
            return current.getClass().getDeclaredField(readString());
        } catch (Exception e) {
            System.out.printf("\nError: %s\n", e.getMessage());
            return null;
        }
    }

    private void setField(Field field, Object object, String message)
        throws Exception {
        DataReader reader = new DataReader();
        System.out.print(message);
        field.setAccessible(true);
        field.set(object, reader.readData(field.getType()));
    }

    /**/
    private void callMethod(Object current) throws Exception {
        System.out.printf("Enter name of the method for call:\n\t");
        Method method = readMethod(current);
        while (null == method) {
            System.out.printf("Invalid method name - try again.\n\t");
            method = readMethod(current);
        }
        Object[] args = readMethodArgs(current, method);
        method.setAccessible(true);
        Object ans = method.invoke(current, args);
        if (null != ans) {
            System.out.printf("Method returned:\n\t%s\n", ans);
        }
        printDelimiterLine();
    }

    private Object[] readMethodArgs(Object current, Method method) {
        Class[] args = method.getParameterTypes();
        Object[] result = new Object[args.length];
        for (int i = 0; i < args.length; ++i) {
            System.out.printf("Enter %s value:\n\t", args[i].getSimpleName());
            result[i] = new DataReader().readData(args[i]);
        }
        return result;
    }

    private Method readMethod(Object current) {
        try {
            String name = readString();
            name = name.replaceAll(" ", "");
            String[] nameParts = name.split("[(,)]");
            name = nameParts[0];
            Class[] args = extractArgs(nameParts);
            return current.getClass().getDeclaredMethod(name, args);
        } catch (Exception e) {
            System.out.printf("Error: %s\n", e.getMessage());
            return null;
        }
    }

    private Class[] extractArgs(String[] signature) throws Exception {
        if (signature.length <= 1) {
            return null;
        }
        Class[] types = new Class[signature.length - 1];
        for (int i = 0; i < types.length; ++i) {
            types[i] = convertStringToClass(signature[i + 1]);
        }
        return types;
    }

    private Class convertStringToClass(String typeName) {
        if (typeName.equals("int")) {
            return int.class;
        } else if (typeName.equals("double")) {
            return double.class;
        } else if (typeName.equals("boolean")) {
            return boolean.class;
        } else if (typeName.equals("long")) {
            return long.class;
        } else if (typeName.equals("String")) {
            return String.class;
        }
        return null;
    }

    private Field[] getFields(Class current) {
        return Arrays.stream(current.getDeclaredFields())
            .filter(field -> !Modifier.isStatic(field.getModifiers()))
            .toArray(Field[] ::new);
    }

    private Method[] getMethods(Class current) {
        return current.getDeclaredMethods();
    }

    private String getMethodParameterString(Method method, String delimiter) {
        return Arrays.stream(method.getParameterTypes())
            .map(type -> type.getSimpleName())
            .collect(Collectors.joining(delimiter));
    }

    private void printDelimiterLine() {
        System.out.print("\n");
        for (int i = 0; i < DELIMITER_LENGTH; ++i) {
            System.out.print(DELIMITER_LETTER);
        }
        System.out.print("\n");
    }
}
