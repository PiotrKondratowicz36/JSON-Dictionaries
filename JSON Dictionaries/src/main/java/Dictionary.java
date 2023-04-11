import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Dictionary {
    public static void main(String[] args) throws IOException, DocumentException {
        int counter = 0;
        HashMap<String, Integer> stats = new HashMap<>();
        while(true){
            System.out.println("1. Search a word");
            System.out.println("2. Add new dictionary");
            System.out.println("3. Create a PDF file and exit");
            Scanner scanner = new Scanner(System.in);
            int x = scanner.nextInt();
            if (x == 1) {
                System.out.println("Searching a word...");
                ObjectMapper mapper = new ObjectMapper();

                try {
                    InputStream dictionaryIS = Dictionary.class.getClassLoader().
                            getResourceAsStream("result.json");
                    List<Słownik> deserializedDictionary = mapper.readValue(dictionaryIS, new TypeReference<List<Słownik>>() { });
                    List<String> wordsNewJava = deserializedDictionary.stream().map(Słownik::getLanguage).collect(Collectors.toList());
                    List<List<String>> wordsNewJava2 = deserializedDictionary.stream().map(Słownik::getWords).collect(Collectors.toList());
                    List<String> wordsNewJava3 = wordsNewJava2.stream().flatMap(List::stream).collect(Collectors.toList());
                    System.out.println(wordsNewJava);
                    System.out.println(wordsNewJava2);
                    System.out.println(wordsNewJava3);
                    System.out.println(wordsNewJava2.get(0));
                    System.out.println("Word to search: ");
                    Scanner scanner1 = new Scanner(System.in);
                    String word = scanner1.nextLine();
                    if (wordsNewJava3.contains(word)) {
                        for (int i = 0; i < deserializedDictionary.size(); i++){
                            List<String> wordsNewJava4 = wordsNewJava2.get(i);
                            if (wordsNewJava4.contains(word)) {
                                System.out.println("Word found! Language: " + wordsNewJava.get(i));
                                counter++;
                                System.out.println(counter);
                                Integer count = stats.get(wordsNewJava.get(i));
                                System.out.println(count);
                                if (count == null){
                                    stats.put(wordsNewJava.get(i), 1);
                                } else {
                                    stats.put(wordsNewJava.get(i), count + 1);
                                }
                                System.out.println(stats);
                            }
                        }
                    } else {
                        System.out.println("Couldn't find a word!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (x == 2) {
                ObjectMapper mapper = new ObjectMapper();
                InputStream dictionaryIS = Dictionary.class.getClassLoader().
                        getResourceAsStream("result.json");
                List<Słownik> deserializedDictionary = mapper.readValue(dictionaryIS, new TypeReference<List<Słownik>>() { });
                System.out.println("Adding new dictionary");
                mapper.enable(SerializationFeature.INDENT_OUTPUT);
                Scanner scanner1 = new Scanner(System.in);
                Słownik dictionary = new Słownik();

                System.out.println("Input a language: ");

                String language = scanner1.nextLine();

                dictionary.setLanguage(language);

                List<String> wrd = new ArrayList<>();

                boolean i = true;

                while (i) {
                    System.out.println("Input a word: ");
                    String new_word = scanner1.nextLine();

                    if (new_word.startsWith("exit") & new_word.endsWith("exit")) {
                        i = false;
                    }
                    wrd.add(new_word);
                    wrd.remove("exit");
                }
                dictionary.setWords(wrd);

                deserializedDictionary.add(dictionary);

                try {
                    mapper.writeValue(new File("ProjectPP\\src\\main\\resources\\result.json"), deserializedDictionary);

                    String jsonInString = mapper.writeValueAsString(dictionary);
                    System.out.println(jsonInString);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (x == 3) {
                String FILE = "ProjectPP\\src\\main\\resources\\stats.pdf";
                System.out.println("Creating a PDF file...");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(FILE));

                document.open();
                Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 18, BaseColor.BLACK);
                Font font2 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Paragraph line = new Paragraph();
                line.add(new Paragraph("Program's statistics:", font));
                line.add(new Paragraph("Total searches: " + counter, font2));
                for(String i : stats.keySet()) {
                    String value = stats.get(i).toString();
                    line.add(new Paragraph(i + ": " + value, font2));
                }
                document.add(line);
                document.close();
                System.out.println("File has been successfully created");
                System.out.println("Exiting a program...");
                System.exit(0);
            }
            if (x < 1 || x > 4) {
                System.out.println("Input a correct number");
            }
        }

    }
}
