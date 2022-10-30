package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App6 {
    public static void main(String[] args) {
        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.UNFAIR);

        // Kopioi edellisen tehtävän ratkaisu tähän lähtökohdaksi
        // Voit käyttää yllä olevaa riviä palautusten generointiin. Se ei vaikuta ratkaisuun, mutta
        // "epäreilu" strategia tekee yhdestä palautuksesta paljon muita haastavamman tarkistettavan,
        // demonstroiden dynaamisen työnjaon etuja paremmin.

        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia (HUOM. Nämä on helpompia tarkistettavia, siksi
        // kommetoitu pois!)
        // List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Luodaan uusi GradingTask-olio ja annetaan se allocate:lle
        // jaettavaksi osiin
        List<GradingTask> allocatedList = new ArrayList<GradingTask>();
        int taskCount = 5;
        allocatedList = TaskAllocator.allocate(ungradedSubmissions, taskCount);
        ExecutorService es = Executors.newFixedThreadPool(taskCount);
        System.out.println("------------ CUT HERE ------------");
        Thread[] threads = new Thread[taskCount];
        for (int x=0; x<(allocatedList.size()); x++) {
            threads[x] = new Thread(allocatedList.get(x));
        }
        for (int i=0; i<taskCount; i++) {
            es.execute(threads[i]);
        }
        for (Thread thread : threads) {
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                System.out.println("InterruptedException!");
            }
        }
        es.shutdown();
        // Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gradingTask : allocatedList){
            for (var gs : gradingTask.getGradedSubmissions()) {
                System.out.println(gs);
            }
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
