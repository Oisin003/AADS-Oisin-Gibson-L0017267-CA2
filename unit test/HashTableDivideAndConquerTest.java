
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HashTableDivideAndConquerTest {

    private HashTableDivideAndConquer table;

    @BeforeEach
    public void setUp() {
        table = new HashTableDivideAndConquer(10);
    }

    @Test
    public void testInsert() {
        table.insert("Batman");
        assertTrue(table.search("Batman"), "Batman should be in the table after insertion");

        table.insert("Superman");
        assertTrue(table.search("Superman"), "Superman should be in the table after insertion");
    }

    @Test
    public void testInsertDuplicate() {
        table.insert("Spider-Man");
        table.insert("Spider-Man"); // Duplicate
        assertTrue(table.search("Spider-Man"), "Spider-Man should still be in the table");
    }

    @Test
    public void testSearch() {
        table.insert("Wonder Woman");
        table.insert("Flash");

        assertTrue(table.search("Wonder Woman"), "Wonder Woman should be found");
        assertTrue(table.search("Flash"), "Flash should be found");
        assertFalse(table.search("Aquaman"), "Aquaman should not be found");
    }

    @Test
    public void testSearchEmptyTable() {
        assertFalse(table.search("Batman"), "Search in empty table should return false");
    }

    @Test
    public void testDelete() {
        table.insert("Iron Man");
        table.insert("Captain America");

        assertTrue(table.search("Iron Man"), "Iron Man should exist before deletion");
        table.delete("Iron Man");
        assertFalse(table.search("Iron Man"), "Iron Man should not exist after deletion");

        assertTrue(table.search("Captain America"), "Captain America should still exist");
    }

    @Test
    public void testDeleteNonExistent() {
        table.insert("Thor");
        table.delete("Loki"); // Doesn't exist
        assertTrue(table.search("Thor"), "Thor should still be in the table");
    }

    @Test
    public void testResizeTriggered() {
        // Insert enough elements to trigger resize (load factor > 75%)
        table.insert("Hero1");
        table.insert("Hero2");
        table.insert("Hero3");
        table.insert("Hero4");
        table.insert("Hero5");
        table.insert("Hero6");
        table.insert("Hero7");
        table.insert("Hero8"); // Should trigger resize at 8/10 = 80%

        // Verify all elements are still searchable after resize
        assertTrue(table.search("Hero1"));
        assertTrue(table.search("Hero5"));
        assertTrue(table.search("Hero8"));
    }

    @Test
    public void testPrintTable() {
        table.insert("Hulk");
        table.insert("Black Widow");

        // printTable() prints to console, just verify it doesn't throw an exception
        assertDoesNotThrow(() -> table.printTable(), "printTable should execute without errors");
    }
}
