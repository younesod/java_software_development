package repository;

import dto.StudentDto;
import file.StudentDao;

import java.io.IOException;
import java.util.List;

public class StudentRepository implements Repository<StudentDto>{
    private final StudentDao stdDao;

    public StudentRepository() {
        this.stdDao = StudentDao.getInstance();
    }

    @Override
    public void add(StudentDto item) throws IOException {
        if(contains(item)){
            stdDao.update(item);
        }
        stdDao.insert(item);
    }

    @Override
    public void remove(StudentDto item) throws IOException {
        if(contains(item)){
            stdDao.delete(item);
        }
    }

    @Override
    public StudentDto get(StudentDto item) throws IOException {
        return stdDao.get(item);
    }

    @Override
    public List<StudentDto> getAll() throws IOException {
        return stdDao.getAll();
    }

    @Override
    public boolean contains(StudentDto item) throws IOException {
        for(int i=0;i<stdDao.getAll().size();i++){
            if(item.equals(stdDao.getAll().get(i))){
                return true;
            }
        }
        return false;
    }
}
