package second.junit;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListTest {

    @Test
    public void searchingTest() {
        List<String> english = Arrays.asList("window", "plane", "sun", "door", "red");
        List<String> polish = Arrays.asList("okno", "samolot", "słońce", "drzwi", "czerwony");
        String word = "window";
        String word2 = "okno";
        assertThat(english, hasItem(word));
        assertThat(polish, hasItem(word2));
    }

    @Test
    public void searchingTest2() {
        List<String> english = Arrays.asList("window", "plane", "sun", "door", "red", "parasol");
        List<String> polish = Arrays.asList("okno", "samolot", "słońce", "drzwi", "czerwony", "parasol");
        String word = "parasol";
        assertThat(english, hasItem(word));
        assertThat(polish, hasItem(word));
    }

    @Test
    public void searchingTest3() {
        List<String> english = Arrays.asList("window", "plane", "sun", "door", "red");
        List<String> polish = Arrays.asList("okno", "samolot", "słońce", "drzwi", "czerwony");
        String word = "ship";
        assertThat(english, not(hasItem(word)));
        assertThat(polish, not(hasItem(word)));
    }
}
