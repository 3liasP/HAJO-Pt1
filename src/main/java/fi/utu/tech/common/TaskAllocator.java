package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to modify this file
 */


public class TaskAllocator {

    /**
     * Allocator that creates list of two (2) GradingTask objects with each having half of the given submissions
     * @param submissions The submissions to be allocated
     * @return The two GradingTask objects in a list, each having half of the submissions
     */
    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        // TODO: Tehtävä 4
        // Finding the size of the list using List.size()
        // and putting in a variable
        GradingTask gradingTask1 = new GradingTask();
        GradingTask gradingTask2 = new GradingTask();
        // Creating two empty lists
        List<Submission> first = new ArrayList<Submission>();
        List<Submission> second = new ArrayList<Submission>();
        // Getting size of the list
        // using size() method
        int size = submissions.size();
        // Step 1
        // (First size)/2 element copy into list
        // first and rest second list
        for (int i = 0; i < size / 2; i++)
            first.add(submissions.get(i));
        // Step 2
        // (Second size)/2 element copy into list first and
        // rest second list
        for (int i = size / 2; i < size; i++)
            second.add(submissions.get(i));
 
        gradingTask1.setUngradedSubmissions(first);
        gradingTask2.setUngradedSubmissions(second);

        List<GradingTask> listOfBoth = new ArrayList<GradingTask>();
        listOfBoth.add(gradingTask1);
        listOfBoth.add(gradingTask2);
        return listOfBoth;
    }


    
    /**
     * Allocate List of ungraded submissions to tasks
     * @param submissions List of submissions to be graded
     * @param taskCount Amount of tasks to be generated out of the given submissions
     * @return List of GradingTasks allocated with some amount of submissions (depends on the implementation)
     */
    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        // TODO: Tehtävä 5
        // Retruns null for now to suppress warnings
        return null;
    }
}
