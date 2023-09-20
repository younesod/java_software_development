import config.ConfigManager;
import dto.StudentDto;
import repository.StudentRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Main {

    private static void displayStudent(List<StudentDto>dtos){
        System.out.println("_______ STUDENTS _______");
        StringBuilder builder= new StringBuilder();
        for(StudentDto dto: dtos){
            builder.append("|| ");
            builder.append(dto.getMatricule());
            builder.append("\t");
            builder.append(dto.getFirstName());
            builder.append("\t");
            builder.append(dto.getLastName());
            builder.append("\t||\n");
        }
        System.out.println(builder.toString());
    }
    public static void main(String[] args) {
//        try {
//            ConfigManager.getInstance().load();
//        } catch (IOException e) {
//            System.out.println("Chargement de la configuration impossible " + e.getMessage());
//        }
//
//        String author = ConfigManager.getInstance().getProperties("app.author");
//        String name= ConfigManager.getInstance().getProperties("app.name");
//        System.out.println("Auteur : " + author+"\nAppName : "+name);

        try {
            ConfigManager.getInstance().load();
            StudentRepository repository= new StudentRepository();
            List<StudentDto> dtos=repository.getAll();
            displayStudent(dtos);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

