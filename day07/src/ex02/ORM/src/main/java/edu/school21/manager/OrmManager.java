package edu.school21.manager;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import edu.school21.exceptions.CreateTableException;
import edu.school21.exceptions.ObjectNotFoundException;
import edu.school21.exceptions.ObjectNotSavedException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;

public class OrmManager implements IOrmManager {

    private DataSource dataSource;
    private Class[] annotatedClasses;

    public OrmManager(DataSource dataSource, Class[] annotatedClasses) {
        this.dataSource = dataSource;
        this.annotatedClasses = annotatedClasses;
    }

    @Override
    public void save(Object object) {
        String query = generateSaveQuery(object);
        this.executeQuery(query);
    }

    private void executeQuery(String query) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            System.out.printf("\nExecute:\n%s\n", query);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new ObjectNotSavedException(e.getMessage());
        }
    }

    private String generateSaveQuery(Object object) {
        Class oClass = object.getClass();
        String headerQuery = new String();
        String insertQuery = new String("VALUES ( ");
        if (oClass.isAnnotationPresent(OrmEntity.class)) {
            headerQuery += String.format(
                "INSERT INTO %s( ",
                ((OrmEntity)oClass.getAnnotation(OrmEntity.class)).table());
            Field fields[] = oClass.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                OrmColumn annotation = fields[i].getAnnotation(OrmColumn.class);
                if (null != annotation) {
                    headerQuery += String.format("%s", annotation.name());
                    insertQuery += getInsertValue(fields[i], object);
                    if (i != fields.length - 1) {
                        headerQuery += ", ";
                        insertQuery += ", ";
                    } else {
                        headerQuery += ")\n";
                        insertQuery += ");";
                    }
                }
            }
            return headerQuery + insertQuery;
        }
        throw new ObjectNotSavedException(String.format(
            "Object %s does not supported.", object.getClass().getName()));
    }

    private String getInsertValue(Field field, Object current) {
        try {
            field.setAccessible(true);
            Object object = field.get(current);
            if (null == object) {
                return "NULL";
            }
            if (object.getClass().equals(String.class)) {
                return String.format("'%s'", object.toString());
            }
            return object.toString();
        } catch (Exception e) {
            throw new ObjectNotSavedException(e.getMessage());
        }
    }

    @Override
    public void update(Object object) {
        String query = generateUpdateQuery(object);
        this.executeQuery(query);
    }

    private String generateUpdateQuery(Object object) {
        Class oClass = object.getClass();
        String headerQuery = new String("UPDATE ");
        String setQuery = new String("SET ");
        String bottomQuery = new String("WHERE id = ");
        if (oClass.isAnnotationPresent(OrmEntity.class)) {
            headerQuery += String.format(
                "%s\n",
                ((OrmEntity)oClass.getAnnotation(OrmEntity.class)).table());
            Field fields[] = oClass.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {
                OrmColumn annotation = fields[i].getAnnotation(OrmColumn.class);
                if (null != annotation) {
                    setQuery += String.format("%s = ", annotation.name());
                    setQuery += getInsertValue(fields[i], object);
                    if (i != fields.length - 1) {
                        setQuery += ", ";
                    } else {
                        setQuery += "\n";
                    }
                }
                OrmColumnId idAnnotation =
                    fields[i].getAnnotation(OrmColumnId.class);
                if (idAnnotation != null) {
                    bottomQuery += getInsertValue(fields[i], object);
                }
            }
            return headerQuery + setQuery + bottomQuery;
        }
        throw new ObjectNotSavedException(String.format(
            "Object %s does not supported.", object.getClass().getName()));
    }

    @Override
    public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            String message =
                String.format("Class %s doesn't exists %s annotation.",
                              aClass.getName(), OrmEntity.class.getName());
            throw new ObjectNotFoundException(message);
        }

        ResultSet data = getResultLine(generateSelectQuery(
            id, ((OrmEntity)aClass.getAnnotation(OrmEntity.class)).table()));
        try {
            data.next();

            Field[] fields = aClass.getDeclaredFields();
            ArrayList<Object> argsObj = new ArrayList();
            for (int i = 0; i < fields.length; ++i) {
                if (fields[i].isAnnotationPresent(OrmColumnId.class)) {
                    argsObj.add(id);
                } else {
                    OrmColumn annotation =
                        fields[i].getAnnotation(OrmColumn.class);
                    if (null == annotation) {
                        argsObj.add(null);
                    } else {
                        argsObj.add(data.getObject(annotation.name()));
                    }
                }
            }
            Constructor constr =
                aClass.getDeclaredConstructor(Arrays.stream(fields)
                                                  .map(f -> f.getType())
                                                  .toArray(Class[] ::new));
            constr.setAccessible(true);
            Object target = constr.newInstance(argsObj.toArray(new Object[0]));
            return (T)target;
        } catch (Exception e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    private String generateSelectQuery(Long id, String tableName) {
        return String.format("SELECT * FROM %s WHERE id = %d", tableName, id);
    }

    private ResultSet getResultLine(String query) {
        try {
            Connection connection = this.dataSource.getConnection();
            Statement statement = connection.createStatement();
            System.out.printf("\nSELECT:\n%s\n", query);
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new ObjectNotFoundException(e.getMessage());
        }
    }

    public static class OrmManagerBuilder {

        private DataSource dataSource;
        private Class[] annotatedClasses;
        private List<Class> annotatedClassList;
        boolean isRemoveTables = false;

        public OrmManagerBuilder() {
            annotatedClassList = new ArrayList<Class>();
        }

        public OrmManagerBuilder setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public OrmManagerBuilder addAnnotationClass(Class annotatedClass) {
            if (annotatedClass.isAnnotationPresent(OrmEntity.class)) {
                this.annotatedClassList.add(annotatedClass);
            } else {
                System.out.printf(
                    "\nWarning:\nClass %s has no annotation OrmEntity.\n",
                    annotatedClass.getName());
            }
            return this;
        }

        public OrmManagerBuilder setRemovingTables() {
            this.isRemoveTables = true;
            return this;
        }

        public OrmManager build() {
            annotatedClasses = annotatedClassList.toArray(new Class[0]);
            if (isRemoveTables) {
                removeTables();
            }
            createTables();
            return new OrmManager(dataSource, annotatedClasses);
        }

        private void removeTables() {
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                String query = getClearQuery();
                System.out.printf("\nRemove tables:\n%s\n", query);
                statement.executeUpdate(query);
            } catch (SQLException e) {
                System.out.printf("Error:\n%s", e.getMessage());
            }
        }

        private String getClearQuery() {
            return "DO $$BEGIN\n"
                + "IF ((SELECT COUNT(*) FROM pg_tables\n"
                + "     WHERE schemaname='public') > 0)\n"
                + "THEN\n"
                + "EXECUTE CONCAT('DROP TABLE IF EXISTS \"',\n"
                + "         (SELECT array_to_string(ARRAY(\n"
                + "             SELECT tablename FROM pg_tables\n"
                + "             WHERE schemaname='public'), '\", \"')),\n"
                + "        '\" CASCADE');\n"
                + "END IF; END;$$;";
        }

        private void createTables() {
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                for (int i = 0; i < annotatedClassList.size(); ++i) {
                    Class currentClass = annotatedClassList.get(i);
                    String query = getCreateTableQuery(currentClass);
                    System.out.printf("\nCreate table:\n%s\n", query);
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                throw new CreateTableException(e.getMessage());
            }
        }

        private String getCreateTableQuery(Class current) {
            String query = new String();
            OrmEntity entityAnnotation =
                (OrmEntity)current.getAnnotation(OrmEntity.class);
            if (entityAnnotation == null) {
                throw new CreateTableException(
                    String.format("OrmEntity annotation not found in class %s",
                                  current.getName()));
            }
            query += String.format("CREATE TABLE IF NOT EXISTS %s (\n",
                                   entityAnnotation.table());
            Field[] fields = current.getDeclaredFields();
            for (int i = 0; i < fields.length; ++i) {

                OrmColumnId idAnnotation =
                    fields[i].getAnnotation(OrmColumnId.class);
                if (null != idAnnotation) {
                    query += String.format("id SERIAL PRIMARY KEY NOT null");
                }
                OrmColumn colAnnotation =
                    fields[i].getAnnotation(OrmColumn.class);
                if (null != colAnnotation) {
                    query += String.format(
                        "%s %s", colAnnotation.name(),
                        getSqlType(fields[i].getAnnotatedType().getType()));
                }
                if (i == (fields.length - 1)) {
                    query += ");";
                } else {
                    query += ",\n";
                }
            }
            return query;
        }

        private String getSqlType(Type type) {
            if (type.equals(String.class)) {
                return "VARCHAR";
            }
            if (type.equals(Integer.class)) {
                return "INTEGER";
            }
            if (type.equals(Double.class)) {
                return "DOUBLE PRECISION";
            }
            if (type.equals(Boolean.class)) {
                return "BOOLEAN";
            }
            if (type.equals(Long.class)) {
                return "BIGINT";
            }
            return null;
        }
    }
}
