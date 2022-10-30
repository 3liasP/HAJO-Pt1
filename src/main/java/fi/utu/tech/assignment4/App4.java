package fi.utu.tech.assignment4;

import java.util.ArrayList;
import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;


public class App4 {
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

        // Luodaan uusi arviointitehtävä
        // GradingTask gradingTask = new GradingTask();
        // Annetaan palautukset gradeAll-metodille ja saadaan arvioidut palautukset takaisin
        // List<Submission> gradedSubmissions =  gradingTask.gradeAll(ungradedSubmissions);
        /*
            * TODO: Muokkaa common-pakkauksen GradingTask-luokkaa siten,
            * että alla oleva run()-metodi (ilman argumentteja!) tarkistaa palautukset (ungradedSubmissions).
            * Yllä olevaa gt.gradeAll()-metodia ei tule enää käyttää suoraan
            * tästä main-metodista. Tarkemmat ohjeet tehtävänannossa.
            * Joudut keksimään, miten GradingTaskille voi antaa tehtävät ja miten ne siltä saa noukittua
            */
        List<GradingTask> allocatedList = new ArrayList<GradingTask>();
        allocatedList = TaskAllocator.sloppyAllocator(ungradedSubmissions);
        System.out.println("------------ CUT HERE ------------");
        for (int x=0; x<(allocatedList.size()); x++)
        {
            Thread temp = new Thread(allocatedList.get(x));
            temp.start();
            System.out.println("Säie " + x + " aloitettu!");
            try {
                temp.join(); 
                System.out.println("Säie " + x + " valmis!");  // ei ole pakko tulostaa
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
