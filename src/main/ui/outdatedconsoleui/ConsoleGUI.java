//package ui.outdatedConsoleUI;
//
//import model.exceptions.InvalidDateException;
//import model.exceptions.TaskAlreadyExistsException;
//import model.exceptions.TaskDoesntExistException;
//import model.tasks.TaskList;
//import model.filters.*;
//import model.tasks.Priority;
//import model.tasks.Task;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.time.YearMonth;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Scanner;
//
//// to-do list application
//public class ConsoleGUI {
//    private Scanner input;
//    private JsonLoader jsonLoader;
//    private JsonSaver jsonSaver;
//    private boolean autoSave;
//    private TaskList taskList;
//    private MultipleFilters multipleFilters;
//
//    ///////////////////////////////////////////  INITIALIZATION METHODS  /////////////////////////////////////////////
//
//    // MODIFIES: this
//    // EFFECTS: runs the to-do console application
//    public ConsoleGUI() {
//        initialize();
//        askForLoad();
//        welcomeMsg();
//        runMainMenu();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initializes the todolist; the input scanner; and inits lists of months containing certain # of days
//    private void initialize() {
//        input = new Scanner(System.in);
//        jsonLoader = new JsonLoader();
//        jsonSaver = new JsonSaver();
//        taskList = new TaskList();
////        jsonLoader = new JsonLoader("./src/resources/data/guiTestList.json");
////        jsonSaver = new JsonSaver("./src/resources/data/guiTestList.json");
//        autoSave = false;
//        multipleFilters = new MultipleFilters();
//    }
//
//
//
//    ///////////////////////////////////////////////  MENU METHODS  ///////////////////////////////////////////////////
//
//    // MAIN MENU RUNNER
//    // MODIFIES: this
//    // EFFECTS: runs main menu, displays it, and processes user input
//    private void runMainMenu() {
//        boolean keepGoing = true;
//
//        while (keepGoing) {
//            displayMainMenu();
//            String command = getInput();
//            if (command.equals("9")) {
//                if (!autoSave) {
//                    askForSave();
//                }
//                goodbyeMsg();
//                keepGoing = false;
//            } else {
//                processMainMenu(command);
//                save();
//            }
//        }
//    }
//
//    // MAIN MENU DISPLAY
//    // EFFECTS: displays the main menu of options for users to see
//    private void displayMainMenu() {
//        System.out.println("Would You Like To:");
//        System.out.println("\t[0] --> Enter Load and Save Menu");
//        System.out.println("\t[1] --> Add a Task");
//        System.out.println("\t[2] --> Complete a Task");
//        System.out.println("\t[3] --> Delete a Task");
//        System.out.println("\t[4] --> Show All Tasks");
//        System.out.println("\t[5] --> Show All Current Tasks");
//        System.out.println("\t[6] --> Show All Completed Tasks");
//        System.out.println("\t[7] --> View Other Task Filters");
//        System.out.println("\n\t[9] --> Quit");
//    }
//
//    // MAIN MENU PROCESSOR
//    // MODIFIES: this
//    // EFFECTS: processes main menu user commands
//    private void processMainMenu(String command) {
//        if (command.equals("0")) {
//            runLoadAndSaveMenu();
//        } else if (command.equals("1")) {
//            runTaskMenu();
//        } else if (command.equals("2")) {
//            showCurrentTasks();
//            completeTask();
//        } else if (command.equals("3")) {
//            showAllTasks();
//            deleteTask();
//        } else if (command.equals("4")) {
//            showAllTasks();
//        } else if (command.equals("5")) {
//            showCurrentTasks();
//        } else if (command.equals("6")) {
//            showCompletedTasks();
//        } else if (command.equals("7")) {
//            runFilterMenu();
//        } else {
//            invalidMenuInputMsg();
//        }
//    }
//
//
//    // LOAD AND SAVE MENU RUNNER
//    // MODIFIES: this
//    // EFFECTS: runs load and save menu, displays it, and processes user input
//    private void runLoadAndSaveMenu() {
//        boolean keepGoing = true;
//
//        while (keepGoing) {
//            displayLoadAndSaveMenu();
//            String command = getInput();
//            if (command.equals("9")) {
//                returningToMainMsg();
//                keepGoing = false;
//            } else {
//                processSaveAndLoadMenuCommand(command);
//            }
//        }
//    }
//
//    // LOAD AND SAVE MENU DISPLAY
//    // EFFECTS: displays the load and save menu of options for users to see
//    private void displayLoadAndSaveMenu() {
//        System.out.println("\nCurrent AutoSave Status: " + autoSave);
//        System.out.println("\nWould You Like To:");
//        if (autoSave) {
//            System.out.println("\t[1] --> Turn AutoSave Off");
//        } else {
//            System.out.println("\t[1] --> Turn AutoSave On");
//        }
//        System.out.println("\t[2] --> Save Tasks");
//        System.out.println("\t[3] --> Load Tasks");
//        System.out.println("\n\t[9] --> Main Menu");
//    }
//
//    // LOAD AND SAVE MENU PROCESSOR
//    // MODIFIES: this
//    // EFFECTS: processes load and save menu user commands
//    private void processSaveAndLoadMenuCommand(String command) {
//        try {
//            if (command.equals("1")) {
//                autoSave = !autoSave;
//                System.out.println("\nAutoSave Status Changed!");
//            } else if (command.equals("2")) {
//                jsonSaver.save(taskList);
//                System.out.println("\nTasks Saved!");
//            } else if (command.equals("3")) {
//                taskList = jsonLoader.load();
//                System.out.println("\nTasks Loaded!");
//            } else {
//                invalidMenuInputMsg();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    // TASK MENU RUNNER
//    // MODIFIES: this
//    // EFFECTS: runs task menu, displays it, and processes user input
//    private void runTaskMenu() {
//        boolean keepGoing = true;
//
//        while (keepGoing) {
//            displayTaskMenu();
//            String command = getInput();
//            if (command.equals("9")) {
//                returningToMainMsg();
//                keepGoing = false;
//            } else {
//                processTaskMenuCommand(command);
//                save();
//            }
//        }
//    }
//
//    // TASK MENU DISPLAY
//    // EFFECTS: displays the task menu of options for users to see
//    private void displayTaskMenu() {
//        System.out.println("\nWhat Type Of Task Would You Like To Add?");
//        System.out.println("\t[1] --> Add a Regular Task");
//        System.out.println("\t[2] --> Add a Priority Task");
//        System.out.println("\n\t[7] --> What is a Regular Task?");
//        System.out.println("\t[8] --> What is a Priority Task?");
//        System.out.println("\t[9] --> Main Menu");
//    }
//
//    // TASK MENU PROCESSOR
//    // MODIFIES: this
//    // EFFECTS: processes task menu user commands
//    private void processTaskMenuCommand(String command) {
//        if (command.equals("1")) {
//            addRegularTask();
//        } else if (command.equals("2")) {
//            addPriorityTask();
//        } else if (command.equals("7")) {
//            regularTaskExplanationMsg();
//        } else if (command.equals("8")) {
//            priorityTaskExplanationMsg();
//        } else {
//            invalidMenuInputMsg();
//        }
//    }
//
//
//    // FILTER MENU RUNNER
//    // MODIFIES: this
//    // EFFECTS: runs filter menu, displays it, and processes user input
//    private void runFilterMenu() {
//        boolean keepGoing = true;
//        while (keepGoing) {
//            displayFilterMenu();
//            String command = getInput();
//            if (command.equals("9")) {
//                returningToMainMsg();
//                keepGoing = false;
//            } else {
//                processFilterMenuCommand(command);
//            }
//        }
//    }
//
//    // FILTER MENU DISPLAY
//    // EFFECTS: displays the filter menu of options for users to see
//    private void displayFilterMenu() {
//        System.out.println("\nWhat Tasks Would You Like To View?");
//        System.out.println("\t[1] --> Show All Current Tasks");
//        System.out.println("\t[2] --> Show All Completed Tasks");
//        System.out.println("\t[3] --> Show All Regular Tasks");
//        System.out.println("\t[4] --> Show All Priority Tasks");
//        System.out.println("\t[5] --> Show All Tasks Due Within 7 Days");
//        System.out.println("\t[6] --> Show All Tasks Due Within X Days (You Choose X)");
//        System.out.println("\t[7] --> Show All Overdue Tasks");
//        System.out.println("\t[8] --> Add Multiple Filters Together");
//        System.out.println("\n\t[9] --> Main Menu");
//    }
//
//    // FILTER MENU PROCESSOR
//    // MODIFIES: this
//    // EFFECTS: processes filter menu user commands
//    private void processFilterMenuCommand(String command) {
//        if (command.equals("1")) {
//            showCurrentTasks();
//        } else if (command.equals("2")) {
//            showCompletedTasks();
//        } else if (command.equals("3")) {
//            showRegularTasks();
//        } else if (command.equals("4")) {
//            showPriorityTasks();
//        } else if (command.equals("5")) {
//            showTasksWithinRange(7);
//        } else if (command.equals("6")) {
//            showTasksWithinRangeInput();
//        } else if (command.equals("7")) {
//            showOverdueTasks();
//        } else if (command.equals("8")) {
//            multipleFilters.clearFilters(); // empties allFilters, just in case it is not already empty
//            runMultipleFilterMenu();
//        } else {
//            invalidMenuInputMsg();
//        }
//    }
//
//
//    // MULTIPLE FILTER MENU RUNNER
//    // MODIFIES: this
//    // EFFECTS: runs 'multiple filter' menu, displays it, and processes user input
//    private void runMultipleFilterMenu() {
//        boolean keepGoing = true;
//
//        while (keepGoing) {
//            displayMultipleFilterMenu();
//            String command = getInput();
//            if (command.equals("9")) {
//                displayMultipleFilterTasks();
//                keepGoing = false;
//            } else {
//                processMultipleFiltersMenuCommand(command);
//            }
//        }
//    }
//
//    // MULTIPLE FILTER MENU DISPLAY
//    // EFFECTS: displays the 'multiple filter' menu of options for users to see
//    private void displayMultipleFilterMenu() {
//        System.out.println("\nWhat Filter Would You Like To Add?");
//        System.out.println("\t[1] --> Current Tasks Filter");
//        System.out.println("\t[2] --> Completed Tasks Filter");
//        System.out.println("\t[3] --> Regular Tasks Filter");
//        System.out.println("\t[4] --> Priority Tasks Filter");
//        System.out.println("\t[5] --> Tasks Due Within 7 Days Filter");
//        System.out.println("\t[6] --> Tasks Due Within X Days (You Choose X) Filter");
//        System.out.println("\t[7] --> Overdue Tasks Filter");
//        System.out.println("\n\t[8] --> View Currently Applied Filters");
//        System.out.println("\t[9] --> View Tasks That Satisfy Currently Applied Filters");
//    }
//
//    // MULTIPLE FILTER MENU PROCESSOR
//    // MODIFIES: this
//    // EFFECTS: processes 'multiple filter' menu user commands
//    private void processMultipleFiltersMenuCommand(String command) {
//        if (command.equals("1")) {
//            addCurrentFilterToMultipleFilter();
//        } else if (command.equals("2")) {
//            addCompletedFilterToMultipleFilter();
//        } else if (command.equals("3")) {
//            addCategoryFilterToMultipleFilter();
//        } else if (command.equals("4")) {
//            addPriorityFilterToMultipleFilter();
//        } else if (command.equals("5")) {
//            addDueWithin7DaysToMultipleFilter();
//        } else if (command.equals("6")) {
//            addXDaysFilterToMultipleFilter();
//        } else if (command.equals("7")) {
//            addOverdueFilterToMultipleFilter();
//        } else if (command.equals("8")) {
//            System.out.println("\nFilters Will Show Tasks With The Following Attributes:");
//            System.out.println(multipleFilters.toString());
//        } else {
//            invalidMenuInputMsg();
//        }
//    }
//
//
//    // PRIORITY MENU DISPLAY
//    // EFFECTS: displays the priority menu of options for users to see
//    private void displayPriorityMenu() {
//        System.out.println("\nSelect Task Priority");
//        System.out.println("\t[1] -> Low Priority Task");
//        System.out.println("\t[2] -> Medium Priority Task");
//        System.out.println("\t[3] -> High Priority Task");
//    }
//
//    // PRIORITY MENU PROCESSOR
//    // EFFECTS: processes priority menu user commands; returns chosen priorityTask priority
//    //          returns "Low" if user input was invalid
//    private Priority processPriorityMenuCommand(String command) {
//        if (command.equals("1")) {
//            return Priority.LOW;
//        } else if (command.equals("2")) {
//            return Priority.MEDIUM;
//        } else if (command.equals("3")) {
//            return Priority.HIGH;
//        } else {
//            System.out.println("Selection Not Valid! Priority Set To \"Low\".");
//            return Priority.LOW;
//        }
//    }
//
//    //////////////////////////////////  TASK/TASK LIST MANIPULATION METHODS  /////////////////////////////////////////
//
//    // MODIFIES: this
//    // EFFECTS: according to user input for description and due date, adds a regular task to taskList
//    private void addRegularTask() {
//        boolean keepGoing = true;
//        while (keepGoing) {
//            String taskDueDate = null;
//            String taskDescription;
//            try {
//                taskDescription = getTaskDescription();
//                taskDueDate = getTaskDueDate();
//                Task newTask = new Task(taskDueDate, taskDescription);
//                taskList.addTask(newTask);
//                newTaskAddedMsg(newTask);
//                keepGoing = false;
//            } catch (InvalidDateException e) {
//                invalidDateInputMsg(taskDueDate);
//            } catch (TaskAlreadyExistsException e) {
//                invalidDescriptionInputMsg();
//            }
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: according to user input for description, due date, and priority, adds a priority task to taskList
//    private void addPriorityTask() {
//        boolean keepGoing = true;
//        while (keepGoing) {
//            String taskDueDate = null;
//            String taskDescription;
//            try {
//                taskDescription = getTaskDescription();
//                taskDueDate = getTaskDueDate();
//                Priority taskPriority = getTaskPriority();
//                Task newTask = new PriorityTask(taskDueDate, taskDescription, taskPriority);
//                taskList.addTask(newTask);
//                newTaskAddedMsg(newTask);
//                keepGoing = false;
//            } catch (InvalidDateException e) {
//                invalidDateInputMsg(taskDueDate);
//                taskDidntAddMsg();
//            } catch (TaskAlreadyExistsException e) {
//                invalidDescriptionInputMsg();
//                taskDidntAddMsg();
//            }
//        }
//    }
//
//
//    // ADD PRIORITY/REGULAR TASK HELPER
//    // MODIFIES: this
//    // EFFECTS: displays enter description message to user, returns desired String task description
//    private String getTaskDescription() {
//        enterTaskDescriptionMsg();
//        return getInput();
//    }
//
//    // ADD PRIORITY/REGULAR TASK HELPER
//    // MODIFIES: this
//    // EFFECTS: displays enter task date message to user, returns desired String task date
//    private String getTaskDueDate() {
//        enterTaskDateMsg();
//        return getInput();
//    }
//
//    // PRIORITY TASK HELPER
//    // MODIFIES: this
//    // EFFECTS: displays priority selection menu to user, returns desired task priority string
//    private Priority getTaskPriority() {
//        displayPriorityMenu();
//        return processPriorityMenuCommand(getInput());
//    }
//
//
//
//    // MODIFIES: this
//    // EFFECTS: displays message to user asking them to input task description;
//    //          marks task that matches input as complete;
//    //          if task does not exist, does nothing and displays message to user telling them task doesnt exist
//    private void completeTask() {
//        enterExactTaskDescriptionMsg();
//        String taskDescription = getInput();
//        try {
//            taskList.completeTask(taskDescription);
//            System.out.println("\nTask Successfully Completed.\n");
//        } catch (TaskDoesntExistException e) {
//            taskDoesntExistMsg();
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays message to user asking them to input task description;
//    //          deletes task that matches input (from taskList)
//    //          if task does not exist, does nothing and displays message to user telling them task doesnt exist
//    private void deleteTask() {
//        enterExactTaskDescriptionMsg();
//        String taskDescription = getInput();
//        try {
//            taskList.deleteTask(taskDescription);
//            System.out.println("\nTask Successfully Deleted.\n");
//        } catch (TaskDoesntExistException e) {
//            taskDoesntExistMsg();
//        }
//    }
//
//    //////////////////////////////////////////////  FILTER METHODS  //////////////////////////////////////////////////
//
//    // EFFECTS: displays all current tasks for the user to see
//    private void showCurrentTasks() {
//        showingAllXTasksMsg("Current");
//        Filter notCompletedFilter = new StatusFilter(false);
//        printWithFilter(notCompletedFilter);
//    }
//
//    // EFFECTS: displays all completed tasks for the user to see
//    private void showCompletedTasks() {
//        showingAllXTasksMsg("Completed");
//        Filter completedFilter = new StatusFilter(true);
//        printWithFilter(completedFilter);
//    }
//
//    // EFFECTS: displays all regular tasks for the user to see
//    private void showRegularTasks() {
//        showingAllXTasksMsg("Regular");
//        Filter regularFilter = new CategoryFilter(Task.TASK_CATEGORY);
//        printWithFilter(regularFilter);
//    }
//
//    // EFFECTS: displays all priority tasks for the user to see
//    private void showPriorityTasks() {
//        showingAllXTasksMsg("Priority");
//        Filter priorityFilter = new CategoryFilter(PriorityTask.TASK_CATEGORY);
//        printWithFilter(priorityFilter);
//    }
//
//    // EFFECTS: displays all tasks due within days for the user to see
//    private void showTasksWithinRange(int days) {
//        System.out.println("\nShowing All Tasks Due Within " + days + " Days...\n");
//        Filter daysUntilDueFilter = new DueWithinDaysFilter(days);
//        printWithFilter(daysUntilDueFilter);
//    }
//
//    // EFFECTS: displays all tasks for the user to see
//    private void showAllTasks() {
//        System.out.println("\nShowing All Tasks...\n");
//        Filter trueFilter = new TrueFilter();
//        printWithFilter(trueFilter);
//    }
//
//    // EFFECTS: displays all priority tasks for the user to see
//    private void showOverdueTasks() {
//        showingAllXTasksMsg("Overdue");
//        Filter overdueFilter = new OverdueFilter();
//        printWithFilter(overdueFilter);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays all tasks that satisfy all filters in multipleFilter
//    //          removes all filters from allFilters after tasks are displayed
//    private void displayMultipleFilterTasks() {
//        System.out.println("\n Tasks With The Following Attributes:");
//        System.out.println(multipleFilters.toString());
//        printWithFilter(multipleFilters);
//        System.out.println("\nAbove Is All Tasks With The Following Attributes:");
//        System.out.println(multipleFilters.toString());
//        multipleFilters.clearFilters();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays message asking for user input days, displays tasks that are due within X days
//    //          if input invalid, displays message telling user input was invalid
//    private void showTasksWithinRangeInput() {
//        int num = getHowManyDays();
//        if (num != -1) {
//            showTasksWithinRange(num);
//        } else {
//            invalidMenuInputMsg();
//        }
//    }
//
//    ///////////////////////////////////  ADD TO MULTIPLE FILTER METHODS  /////////////////////////////////////////////
//
//    // MODIFIES: this
//    // EFFECTS: adds an overdue filter to the multiple filter and displays message to user telling them so
//    private void addOverdueFilterToMultipleFilter() {
//        multipleFilters.addFilter(new OverdueFilter());
//        System.out.println("Overdue Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a due within 7 days filter to the multiple filter; displays message to user telling them
//    private void addDueWithin7DaysToMultipleFilter() {
//        multipleFilters.addFilter(new DueWithinDaysFilter(7));
//        System.out.println("Due Within 7 Days Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a category (priority) filter to the multiple filter; displays message to user telling them
//    private void addPriorityFilterToMultipleFilter() {
//        multipleFilters.addFilter(new CategoryFilter(PriorityTask.TASK_CATEGORY));
//        System.out.println("Priority Task Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a category (regular)  filter to the multiple filter; displays message to user telling them
//    private void addCategoryFilterToMultipleFilter() {
//        multipleFilters.addFilter(new CategoryFilter(Task.TASK_CATEGORY));
//        System.out.println("Regular Task Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a status (complete/true)  filter to the multiple filter; displays message to user telling them
//    private void addCompletedFilterToMultipleFilter() {
//        multipleFilters.addFilter(new StatusFilter(true));
//        System.out.println("Complete Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: adds a status (current/false)  filter to the multiple filter; displays message to user telling them
//    private void addCurrentFilterToMultipleFilter() {
//        multipleFilters.addFilter(new StatusFilter(false));
//        System.out.println("Not Complete Filter Added\n");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays message asking for user input days
//    //          if valid, adds a due within X days filter to the multiple filter; displays message to user telling th
//    //          if not valid, displays message to user telling them so; does nothing
//    private void addXDaysFilterToMultipleFilter() {
//        int days = getHowManyDays();
//        if (days != -1) {
//            multipleFilters.addFilter(new DueWithinDaysFilter(days));
//            System.out.println("Due Within " + days + " Days Filter Added\n");
//        } else {
//            System.out.println("That Input Was Not Valid! No Filter Added.");
//        }
//    }
//
//
//
//    //////////////////////////////////////////////  MESSAGE METHODS  /////////////////////////////////////////////////
//
//    // EFFECTS: displays welcome message
//    private void welcomeMsg() {
//        System.out.println("\nWelcome To My To-Do List!\n");
//        System.out.println("Try Features Such As Adding Different Types Of Tasks, ");
//        System.out.print("Or Filtering Them Based On Various Attributes! \n \n");
//    }
//
//    // EFFECTS: displays priority task description
//    private void priorityTaskExplanationMsg() {
//        System.out.println("\nA Priority Task Has A:");
//        System.out.println("\t - Due Date");
//        System.out.println("\t - Description");
//        System.out.println("\t - Priority (High, Medium, Or Low)");
//    }
//
//    // EFFECTS: displays regular task description
//    private void regularTaskExplanationMsg() {
//        System.out.println("\nA Regular Task Has A:");
//        System.out.println("\t - Due Date");
//        System.out.println("\t - Description");
//    }
//
//    // EFFECTS: displays a message telling user their menu input was invalid
//    private void invalidMenuInputMsg() {
//        System.out.println("\nYour input was not valid! Please Try Again By Pressing Only Number Keys.\n");
//    }
//
//    // EFFECTS: displays a message asking user to input task description
//    private void enterTaskDescriptionMsg() {
//        System.out.println("\nEnter Task Description:");
//    }
//
//    // EFFECTS: displays a message asking user to input task due date
//    private void enterTaskDateMsg() {
//        System.out.println("\nEnter Task Due Date (yyyy-mm-dd):");
//    }
//
//    // EFFECTS: displays a message telling user their date input was invalid
//    private void invalidDateInputMsg(String taskDueDate) {
//        String reasons = invalidDateInputReasonsMsg(taskDueDate);
//        System.out.println("\nInvalid Date!!");
//        System.out.println("Reason(s):\n" + reasons);
//    }
//
//    // EFFECTS: displays a message telling user their description input was invalid
//    private void invalidDescriptionInputMsg() {
//        System.out.println("\nInvalid Description!! A Task With That Description Has Already Been Created");
//    }
//
//    // EFFECTS: displays a message telling user that the tasks of a given filter are being shown
//    private void showingAllXTasksMsg(String taskFilterTitle) {
//        System.out.println("\nShowing All " + taskFilterTitle + " Tasks...\n");
//    }
//
//    // EFFECTS: displays a message telling user that they are returning to main menu
//    private void returningToMainMsg() {
//        System.out.println("\nReturning To Main Menu...\n");
//    }
//
//    // EFFECTS: displays a message telling user goodbye
//    private void goodbyeMsg() {
//        System.out.println("\nGoodbye!\n");
//    }
//
//    // EFFECTS: displays a message telling user task doesnt exist
//    private void taskDoesntExistMsg() {
//        System.out.println("\nThat Task Does Not Exist!! Please Try Again...\n");
//    }
//
//    // EFFECTS: displays a message telling user task doesnt exist
//    private void taskDidntAddMsg() {
//        System.out.println("\nThat Task Could Not Be Added. Please Try Again...\n");
//    }
//
//    // EFFECTS: displays a message telling user they successfully added a task
//    private void newTaskAddedMsg(Task newTask) {
//        System.out.println("\n" + newTask.getCategory() + " Successfully Added:");
//        System.out.println("\t --> " + newTask.toString());
//    }
//
//    // EFFECTS: displays a message asking user to input how many days they want for filter
//    private void howManyDaysMsg() {
//        System.out.println("\nHow Many Days (X)? Please Enter An Integer.");
//    }
//
//    // REQUIRES: taskDueDate to be in format xxxx-xx-xx where the x's are all integer strings
//    //           ^^ above requires statement is just to cover my ass for project grading.
//    //           I think i dealt with all cases that could throw an exception, but haven't extensively tested
//    // EFFECTS: returns all reasons in string format why user input date was invalid
//    private String invalidDateInputReasonsMsg(String taskDueDate) {
//        String reasons = "";
//        if (!isDateLengthValid(taskDueDate) || !isDateDashesValid(taskDueDate)) {
//            return "\t--> Date Was Not In Correct Format\n";
//        }
//        if (!isValidYearFormat(taskDueDate)) {
//            reasons += " \t--> Year Input Was Not In Correct Format\n";
//        } else if (!isNotBefore2000s(taskDueDate)) {
//            reasons += "\t--> You Input A Year Before The Year 2000...\n";
//        }
//        if (!isValidMonthFormat(taskDueDate)) {
//            reasons += "\t--> Month Input Was Not In Correct Format\n";
//        } else {
//            int month = Integer.parseInt(taskDueDate.substring(5, 7));
//            if (month > 12 || month < 1) {
//                reasons += "\t--> The Month You Entered Does Not Exist\n";
//            }
//        }
//        reasons += buildDayInvalidReasons(taskDueDate);
//        return reasons;
//    }
//
//    // REQUIRES: taskDueDate to be in format xxxx-xx-xx where the x's are all integer strings
//    // EFFECTS: returns all reasons in string format why user input day was invalid
//    private String buildDayInvalidReasons(String taskDueDate) {
//        String reasons = "";
//        if (!isValidDayFormat(taskDueDate)) {
//            reasons += "\t--> Day Input Was Not In Correct Format\n";
//        } else {
//            int days = Integer.parseInt(taskDueDate.substring(8, 10));
//            if (days > 31 || days <= 0) {
//                reasons += "\t--> The Day You Entered Does Not Exist\n";
//            } else if (isValidMonth(taskDueDate) && !isDayInRange(taskDueDate) && days >= 28) {
//                reasons += "\t--> The Day You Entered Does Not Exist Within The Given Month\n";
//            }
//        }
//        return reasons;
//    }
//
//    // EFFECTS: displays a list of tasks to user that meet the given filter condition
//    //          or tells user they have no tasks; or that no tasks satisfy the filter
//    private void printWithFilter(Filter filter) {
//        if (taskList.size() == 0) {
//            System.out.println("\nYou Have No Tasks.");
//            System.out.println("Add Some Tasks And Try Again...\n");
//        } else {
//            ArrayList<Task> filteredTasks = taskList.filterBy(filter);
//            Collections.sort(filteredTasks);
//            if (filteredTasks.isEmpty()) {
//                System.out.println("\nNo Tasks That Satisfied Filter(s) Were Found!\n");
//            } else {
//                printPriorityWithFilter(filter);
//                printRegularWithFilter(filter);
//            }
//        }
//    }
//
//    // EFFECTS: displays a list of priority tasks to user that meet the given filter condition
//    private void printPriorityWithFilter(Filter filter) {
//        MultipleFilters mf = new MultipleFilters();
//        mf.addFilter(filter);
//        mf.addFilter(new CategoryFilter(PriorityTask.TASK_CATEGORY));
//        ArrayList<Task> filteredTasks = taskList.filterBy(mf);
//        if (!filteredTasks.isEmpty()) {
//            System.out.println("Printing Priority Tasks That Satisfied Filter(s)...");
//            Collections.sort(filteredTasks);
//            for (Task task : filteredTasks) {
//                System.out.println(task.toString());
//            }
//        }
//    }
//
//    // EFFECTS: displays a list of regular tasks to user that meet the given filter condition
//    private void printRegularWithFilter(Filter filter) {
//        MultipleFilters mf = new MultipleFilters();
//        mf.addFilter(filter);
//        mf.addFilter(new CategoryFilter(Task.TASK_CATEGORY));
//        ArrayList<Task> filteredTasks = taskList.filterBy(mf);
//        if (!filteredTasks.isEmpty()) {
//            System.out.println("\nPrinting Regular Tasks That Satisfied Filter(s)...");
//            Collections.sort(filteredTasks);
//            for (Task task : filteredTasks) {
//                System.out.println(task.toString());
//            }
//            System.out.print("\n");
//        }
//    }
//
//    // EFFECTS: displays a message asking user to input EXACT task description
//    private void enterExactTaskDescriptionMsg() {
//        System.out.println("\nEnter Task Description (Must Match Exactly, Including Case):");
//    }
//
//    //////////////////////////////////////////////  HELPER METHODS  //////////////////////////////////////////////////
//
//    // EFFECTS: returns true if the string represents an integer
//    private boolean isInteger(String numberStr) {
//        try {
//            Integer.parseInt(numberStr);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    // EFFECTS: returns string input
//    private String getInput() {
//        String inputString = input.nextLine();
//        while (inputString.length() <= 0) {
//            inputString = input.nextLine();
//        }
//        return inputString;
//    }
//
//    private void save() {
//        if (autoSave) {
//            try {
//                jsonSaver.save(taskList);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // EFFECT: asks user if they want to save before they leave the console gui
//    //         if yes, then saves tasks; if no, does nothing
//    private void askForSave() {
//        boolean keepGoing = true;
//        while (keepGoing) {
//            System.out.println("\nWould You Like To:");
//            System.out.println("\t[0] --> Save Tasks");
//            System.out.println("\t[1] --> Quit Anyways");
//            String command = getInput();
//            if (command.equals("0")) {
//                System.out.println("\nSaving Tasks...");
//                try {
//                    jsonSaver.save(taskList);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                keepGoing = false;
//            } else if (command.equals("1")) {
//                keepGoing = false;
//            } else {
//                invalidMenuInputMsg();
//            }
//        }
//    }
//
//    // EFFECT: asks user if they want to load before they open the main menu
//    //         if yes, then loads tasks; if no, goes to main menu
//    private void askForLoad() {
//        boolean keepGoing = true;
//        while (keepGoing) {
//            System.out.println("Would You Like To:");
//            System.out.println("\t[0] --> Load Tasks");
//            System.out.println("\t[1] --> Continue To Main Menu");
//            String command = getInput();
//            if (command.equals("0")) {
//                System.out.println("\nLoading Tasks and Entering Main Menu...\n");
//                try {
//                    taskList = jsonLoader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                keepGoing = false;
//            } else if (command.equals("1")) {
//                System.out.println("\nEntering Main Menu...\n");
//                keepGoing = false;
//            } else {
//                invalidMenuInputMsg();
//            }
//        }
//    }
//
//
//    ////////////////////////////////////////  DATE CHECKER METHODS  //////////////////////////////////////////////////
//
//    // EFFECTS: returns true if given taskDueDate has a dashes in correct places
//    //          else returns false;
//    private boolean isDateDashesValid(String taskDueDate) {
//        return taskDueDate.indexOf("-") == 4 && taskDueDate.indexOf("-",5) == 7;
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate has a year input == XXXX, where X's are integer strings
//    //          else returns false;
//    private boolean isValidYearFormat(String taskDueDate) {
//        return isInteger(taskDueDate.substring(0, 4));
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate is not before the 2000's
//    //          else returns false;
//    private boolean isNotBefore2000s(String taskDueDate) {
//        String firstTwoNumbersOf = taskDueDate.substring(0, 2);
//        int firstTwoNumbersOfInt = Integer.parseInt(firstTwoNumbersOf);
//        return firstTwoNumbersOfInt >= 20;
//    }
//
//    // EFFECTS: returns true if given taskDueDate has a valid length (same length as "yyyy-mm-dd")
//    //          else returns false;
//    private boolean isDateLengthValid(String taskDueDate) {
//        return taskDueDate.length() == "yyyy-mm-dd".length();
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate has a valid month input [1,12];
//    //          else returns false;
//    private boolean isValidMonth(String taskDueDate) {
//        if (!isValidMonthFormat(taskDueDate)) {
//            return false;
//        }
//        String monthNumStr = taskDueDate.substring(5, 7);
//        int monthNumInt = Integer.parseInt(monthNumStr);
//        return monthNumInt <= 12 && monthNumInt > 0;
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate has a month input == XX, where X's are integer strings
//    //          else returns false;
//    private boolean isValidMonthFormat(String taskDueDate) {
//        return isInteger(taskDueDate.substring(5, 7));
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate has a day input == XX, where X's are integer strings
//    //          else returns false;
//    private boolean isValidDayFormat(String taskDueDate) {
//        return isInteger(taskDueDate.substring(8, 10));
//    }
//
//    // REQUIRES: taskDueDate to be in format "xxxx-xx-xx" where the x's are all integer strings
//    // EFFECTS: returns true if given taskDueDate has a day that is within the range of the taskDueDate month;
//    //          else returns false;
//    //          if false, displays message to user telling them that day is out of range
//    private boolean isDayInRange(String taskDueDate) {
//        String dayNumStr = taskDueDate.substring(8, 10);
//        int dayNumInt = Integer.parseInt(dayNumStr);
//        String monthNumStr = taskDueDate.substring(5, 7);
//        int monthNumInt = Integer.parseInt(monthNumStr);
//        String yearNumStr = taskDueDate.substring(0, 4);
//        int yearNumInt = Integer.parseInt(yearNumStr);
//
//        YearMonth yearMonthObject = YearMonth.of(yearNumInt, monthNumInt);
//        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
//        return dayNumInt > 0 && dayNumInt <= daysInMonth;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: displays message asking for user input days, returns days input as integer
//    //          returns -1 if input is not valid
//    private int getHowManyDays() {
//        howManyDaysMsg();
//        String daysStr = getInput();
//        boolean validNum = isInteger(daysStr);
//        if (validNum && Integer.parseInt(daysStr) >= 0) {
//            return Integer.parseInt(daysStr);
//        } else {
//            return -1;
//        }
//    }
//
//}
