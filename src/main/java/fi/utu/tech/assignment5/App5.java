package fi.utu.tech.assignment5;

import java.util.ArrayList;
import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;


public class App5 {
    public static void main( String[] args )
    {
        // Tässä tehtävässä vaaditaan myös muutoksia TaskAllocator-luokkaan
        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Luodaan uusi GradingTask-olio ja annetaan se allocate:lle
        // jaettavaksi osiin
        List<GradingTask> allocatedList = new ArrayList<GradingTask>();
        int taskCount = 5;
        allocatedList = TaskAllocator.allocate(ungradedSubmissions, taskCount);

        // Luodaan allocate:n palauttaman listan alkioiden määrän
        // mukainen määrä säikeitä. Ilmoitetaan myös, monesko säie on
        // käsittelyssä
        System.out.println("------------ CUT HERE ------------");
        Thread[] threads = new Thread[taskCount];
        for (int x=0; x<(allocatedList.size()); x++) {
            threads[x] = new Thread(allocatedList.get(x));
            threads[x].start();
            System.out.println("Säie " + x + " aloitettu!"); // Näyttää, montako säiettä luodaan
        }
        for (Thread thread : threads) {
            try {
                thread.join(); 
            } catch (InterruptedException e) {
                System.out.println("InterruptedException!");
            }
        }
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
