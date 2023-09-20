package file;

import dto.StudentDto;
import repository.Dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentDao implements Dao<StudentDto> {
    private final Path path;
    public StudentDao() {
        this.path=FileManager.getInstance().path();
    }
    StudentDao(String uri){
        this.path= Paths.get(uri);
    }
    public static StudentDao getInstance(){
        return StudentDaoHolder.INSTANCE;
    }
    private static String buildLine(StudentDto student) {
        return student.getMatricule() + ","
                + student.getLastName() + ","
                + student.getFirstName()
                + System.lineSeparator();
    }

    @Override
    public void insert(StudentDto item) throws IOException {
        if(item==null){
            throw new IllegalArgumentException("Pas d'item en param"+item);
        }
        String out= buildLine(item);
        Files.write(path,out.getBytes(), StandardOpenOption.APPEND);

    }

    @Override
    public void delete(StudentDto item) throws IOException {
        if (item == null) {
            throw new IllegalArgumentException("Pas d'item en param" + item);
        }
        List<String> out = Files.lines(path)
                .filter(line -> !line.split(",")[0]
                        .contains(Integer.toString(item.getMatricule())))
                .collect(Collectors.toList());
        if (out.size() == Files.lines(path).count()) {
            throw new IOException("Inexistant du fichier " + item.getMatricule());
        }
        Files.write(path, out,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Override
    public void update(StudentDto item) throws IOException {
        if (item == null) {
            throw new IllegalArgumentException("Pas d'item en param" + item);
        }
        delete(item);
        insert(item);
    }

    private StudentDto buildDto(String line) {
        String[] splited = line.split(",");
        int matricule = Integer.parseInt(splited[0]);
        String lastName = splited[1];
        String fisrtName = splited[2];
        return new StudentDto(matricule, lastName, fisrtName);
    }
    @Override
    public StudentDto get(StudentDto item) throws IOException {
        if (item == null) {
            throw new IllegalArgumentException("Pas d'item en param" + item);
        }
        StudentDto result = null;
        List<String> out = Files.lines(path)
                .filter(line -> line.split(",")[0]
                        .equals(Integer.toString(item.getMatricule())))
                .collect(Collectors.toList());
        if (!out.isEmpty()) {
            result = buildDto(out.get(0));
        }
        return result;
    }

    @Override
    public List<StudentDto> getAll() throws IOException {
        List<StudentDto> result = new ArrayList<>();
        List<String> out = Files.lines(path)
                .collect(Collectors.toList());
        for (String line : out) {
            StudentDto studentDto = buildDto(line);
            result.add(studentDto);
        }
        return result;
    }
    private static class StudentDaoHolder {

        private static final StudentDao INSTANCE = new StudentDao();
    }
}
