package arcade;

/**
 * Manageable.java
 * Interface used by manager classes that support CRUD operations.
 */
public interface Manageable
{
    void addRecord(Object record);
    boolean updateRecord(int id, Object record);
    boolean deleteRecord(int id);
    Object searchRecord(String keyword);
}
