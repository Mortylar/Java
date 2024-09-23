package edu.school21.manager;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import edu.school21.exceptions.CreateTableException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public void save(Object object) {}

    public void update(Object object) {}

    @Override
    public <T> T findById(Long id, Class<T> aClass) {
        return null;
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

            System.out.printf("\nann = %s\n", query);

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
