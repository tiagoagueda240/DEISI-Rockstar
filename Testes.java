package pt.ulusofona.aed.deisiRockstar2021;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


public class Testes {

    @Test
    public void testCountSongsYear() throws IOException {
        LoadFilesTests.loadFilesTest();
        int expected = 3;
        int obtained = Functions1.countSongsYear(2000);
        assertEquals("Expected: 1164'", expected, obtained);
    }

    @Test
    public void testCountDuplicateSongsYear() throws IOException {
        LoadFilesTests.loadFilesTest();
        int expected = 9;
        int obtained = Functions1.countDuplicateSongsYear(1920);
        assertEquals("Expected: 9'", expected, obtained);
    }

    @Test
    public void testGetArtistsForTag() throws IOException {
        //falta
        LoadFilesTests.loadFilesTest();
        int expected = 9;
        int obtained = Functions1.countDuplicateSongsYear(1920);
        assertEquals("Expected: 1164'", expected, obtained);
    }

    @Test
    public void testGetMostDanceable() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "\"SideRaq 2 Salinas, Pt. 2 (Outro)\" : 2001 : 0.9159999999999999";
        String obtained = Functions.getMostDanceable(2000, 2010, 1);
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, obtained);
    }

    @Test
    public void testGetArtistsOneSong() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "Empire of the Sun | 96.96.96 | 2001";
        String obtained = Functions.getArtistsOneSong(2000, 2010);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Funky Cold Medina - Re-Recorded : 2010 : 0.985'", expected, linha1[0]);
    }


    @Test
    public void testGetTopArtistsWithSongsBetween() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "Percy Faith & His Orchestra 1";
        String obtained = Functions.getTopArtistsWithSongsBetween(2, 0,5);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Percy Faith & His Orchestra 1", expected, linha1[0]);
    }

    @Test
    public void testCreative() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "\"Kai pios khtipái to mántalo, sirtó\" | 1920 | 0.604 | 50.0 | 0.0883";
        String obtained = CreativeFunctions.creative(1920, 90000);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: \"Kai pios khtipái to mántalo, sirtó\" | 1920 | 0.604 | 50.0 | 0.0883", expected, linha1[0]);
    }

    @Test
    public void testCreative2() throws IOException {
        LoadFilesTests.loadFilesTest();
        String expected = "Hush Little Darlin' | 1920 | 0.6659999999999999 | 0.0 | 0.145";
        String obtained = CreativeFunctions.creative(1920, 90000);
        String[] linha1 = obtained.split("\n");
        assertEquals("Expected: Hush Little Darlin' | 1920 | 0.6659999999999999 | 0.0 | 0.145", expected, linha1[linha1.length-1]);
    }


}
