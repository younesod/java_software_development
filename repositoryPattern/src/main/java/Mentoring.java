import java.io.File;
import java.net.URL;

public class Mentoring {
    public Mentoring(){}

    public static void main(String[] args) {
        Mentoring mentoring= new Mentoring();
        mentoring.checkPath();
    }

    public void checkPath(){
        URL path=this.getClass().getClassLoader().getResource("subscribed.txt");
        File file= new File(path.getPath());
        System.out.println(file.getAbsolutePath());
        System.out.println("Chemin pour resources\t"+this.getClass().getClassLoader().getResource("subscribed.txt"));
        System.out.println("Chemin courant\t"+ new File(".").getAbsolutePath());
        System.out.println("Chemin classe\t"+this.getClass().getResource(".").getPath());
        System.out.println("Chemin jar\t"+new File(getClass().getResource(".").getPath()));
    }
}
