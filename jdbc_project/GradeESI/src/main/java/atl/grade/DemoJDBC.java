package atl.grade;

import atl.grade.change.DemoDelete;
import atl.grade.change.DemoInsert;
import atl.grade.change.DemoUpdate;
import atl.grade.config.ConfigManager;
import atl.grade.date.DemoDateSelect;
import atl.grade.injection.DemoInjection;
import atl.grade.join.DemoJoin;
import atl.grade.prepare.DemoPrepare;
import atl.grade.selection.DemoSelect;
import atl.grade.selection.DemoSelectAll;
import atl.grade.selection.SelectAllLessons;
import atl.grade.transaction.DemoTransaction;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author jlc
 */
public class DemoJDBC {

    /**
     * Entry points to the <code> Mentoring </code> application.
     *
     * @param args no arguments needed.
     */
    public static void main(String[] args) {
        try {
            ConfigManager.getInstance().load();
            String dbUrl = ConfigManager.getInstance().getProperties("db.url");
            System.out.println("Base de données stockée : " + dbUrl);
//            System.out.println("Id : ");
//            Scanner input = new Scanner(System.in);
//            int id=input.nextInt();
//            input.nextLine();
//            System.out.println("lastName : ");
//            String lastname=input.nextLine();
//
//            Demo demo = new DemoPrepare(dbUrl,id,lastname);
//            demo.printTitle();
//            demo.execute(dbUrl);

//            Demo demo = new DemoSelect();
//            demo.printTitle();
//            demo.execute(dbUrl);
//
//
            Demo demoSelectALl = new DemoSelectAll();
            demoSelectALl.printTitle();
            demoSelectALl.execute(dbUrl);
//
//
//            //ex1
//            Demo lessons = new SelectAllLessons();
//            lessons.printTitle();
//            lessons.execute(dbUrl);
//
//
//            //update
//            Demo update= new DemoUpdate();
//            update.printTitle();
//            update.execute(dbUrl);
//
//            //insert
//            Demo insert = new DemoInsert();
//            insert.printTitle();
//            insert.execute(dbUrl);
//
//            //delete
//            Demo delete= new DemoDelete();
//            delete.printTitle();
//            delete.execute(dbUrl);
//
//            //ex2
//            Demo ex2 = new ex2();
//            ex2.printTitle();
//            ex2.execute(dbUrl);
//
//            //date
//            Demo date= new DemoDateSelect();
//            date.printTitle();
//            date.execute(dbUrl);
//
//            //ex3
//            Demo ex3 = new ex3();
//            ex3.printTitle();
//            ex3.execute(dbUrl);
//
//            //transaction
//            Demo transaction = new DemoTransaction();
//            transaction.printTitle();
//            transaction.execute(dbUrl);
//
//            //Join
//            Demo join = new DemoJoin();
//            join.printTitle();
//            join.execute(dbUrl);
        } catch (IOException ex) {
            System.out.println("Erreur IO " + ex.getMessage());
        }
    }

    private DemoJDBC() {

    }
}
