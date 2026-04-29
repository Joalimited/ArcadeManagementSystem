package arcade;

/**
 * Manageable.java
 */
public interface Manageable
{
    void addRecord(Object record);
    boolean updateRecord(int id, Object record);
    boolean deleteRecord(int id);
    Object searchRecord(String keyword);
}
