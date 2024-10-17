import java.time.LocalDate;
import java.time.LocalDateTime;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        System.out.printf("Hola mundo");
        System.out.println();
        System.out.println();
        System.out.println("-------------------");
        System.out.println("Mi nombre es, Paula");
        System.out.println("Hoy es, "+ LocalDate.now()+" "+LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute());
        }
    }
