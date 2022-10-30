package fi.utu.tech.assignment3;

import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App3 {
    public static void main( String[] args )
    {
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
        GradingTask gradingTask = new GradingTask();
        gradingTask.setUngradedSubmissions(ungradedSubmissions);
        Thread t3 = new Thread(gradingTask);
        t3.start();
        
        // odotetaan, että arvoijasäie ovat valmiit
        try {
            t3.join(); 
            System.out.println("Arvioijasäie valmis!");  // ei ole pakko tulostaa
        } catch (InterruptedException e) {
            System.out.println("InterruptedException!");
        }
        // pääsäie jatkaa
        
        // Tulostetaan arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        for (var gs : gradingTask.getGradedSubmissions()) {
            System.out.println(gs);
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);
    }
}
