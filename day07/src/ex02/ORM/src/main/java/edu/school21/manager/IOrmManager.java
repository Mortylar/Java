package edu.school21.manager;

public interface IOrmManager {

    public void save(Object entity);

    public void update(Object entity);

    public <T> T findById(Long id, Class<T> aClass);
}
