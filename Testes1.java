package pt.ulusofona.aed.deisiRockstar2021;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class Testes1 {

    @Test
    public void testToString() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "7xPhfUan2yNtyFG0cUWkt8 | Clancy Lowered the Boom | 1921 | 3:28 | 23.0 |";
        String obtained = Main.songs.get("7xPhfUan2yNtyFG0cUWkt8").toString();
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, obtained);
    }

    @Test
    public void testToString2() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "5kZAr97MV7Hxxb38WwNBzK | I'm in Love | 1920 | 1:55 | 12.0 | Creedence Clearwater Revival | (1)";
        String obtained = Main.songs.get("5kZAr97MV7Hxxb38WwNBzK").toString();
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, obtained);
    }


    @Test
    public void testMostFrequentWordsInArtistName() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "Clearwater 1";
        String obtained = Functions.mostFrequentWordsInArtistName(2, 0,5);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Clearwater 1", expected, linha1[0]);
    }

    @Test
    public void testGetUniqueTags() throws IOException {
        //Falta fazer
        LoadFilesTests.loadFilesTest();
        TagsFunctions.addTags("Dylan Scott;a;b;c");
        TagsFunctions.addTags("Post Malone;x;b;d");
        String expected = "A 1";
        String obtained = TagsFunctions.getUniqueTags();
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, linha1[0]);
    }

    @Test
    public void testGetUniqueTagsInBetweenYears() throws IOException {
        //Falta fazer
        LoadFilesTests.loadFilesTest();
        TagsFunctions.addTags("Dylan Scott;a;b;c");
        TagsFunctions.addTags("Post Malone;x;b;d");
        String expected = "A 1";
        String obtained = TagsFunctions.getUniqueTagsInBetweenYears(1920, 2020);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: A 1", expected, linha1[0]);
    }

    @Test
    public void testGetRisingStars() throws IOException {
        //Falta fazer
        LoadFilesTests.loadFilesTest();
        String expected = "Kanye West <=> 0";
        String obtained = Functions1.getRisingStars(2000, 2010,"ASC");
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, linha1[0]);
    }

    @Test
    public void testAddTags() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "Dylan Scott | A,B,C";
        String obtained = TagsFunctions.addTags("Dylan Scott;a;b;c");
        assertEquals("Expected: Dylan Scott", expected, obtained);
    }

    @Test
    public void testRemoveTags() throws IOException {
        LoadFilesTests.loadFilesTest();
        TagsFunctions.addTags("Dylan Scott;a;b;c");
        String expected = "Dylan Scott | C";
        String obtained = TagsFunctions.removeTags("Dylan Scott;a;b");
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, obtained);
    }

    @Test
    public void testCleanup() throws IOException {
        //Falta fazer
        LoadFilesTests.loadFilesTest();
        String expected = "Musicas removidas: 200;\n" +
                "Artistas removidos: 17";
        String obtained = Functions1.cleanup();
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, obtained);
    }
}
