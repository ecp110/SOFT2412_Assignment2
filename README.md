# assignment-2

Welcome to SOFT2412 Thursday 10am Group 1's Cinema Management Software!

In order to run this software, your local machine must have Java (at least Java 8), additionally for compilation, Gradle must be installed properly (installation steps provided below).

### How to build, execute, and test the program:

To run this program, we recommned running the premade executable (Option 1). Alternatively, you can compile it yourself with Gradle (Option 2). 

#### Option 1 - Running the executable jar file

##### Required dependenies:

- Java 11 or later

**A. Installation steps:**

*(1) Installing Java 11:*

i. [Windows](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA)

ii. [Mac](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE)

iii. [Linux](https://www.java.com/en/download/help/linux_install.html)

**B. Downloading our software:**

- Download the .zip file [Direct download (from Jenkins)](http://jenkins.kranz.com.au/job/assignment2/lastSuccessfulBuild/artifact/*zip*/archive.zip)
- Extract the zip file
- Navigate to `archive/executable/`
- Double-click on the *run* file.

If this does not work, i.e. your operating system is not configured to run shell scripts:

- Open the archive/executable/ folder in your shell.
- Type `java -jar app-all.jar` to execute the app.

#### Option 2 - Compiling and running the build with Gradle

##### Required dependencies

- Java 11 or later
- Gradle 7 or later

**A. Installation steps:**

*(1) Installing Java 11:*

i. [Windows](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA)

ii. [Mac](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE)

iii. [Linux](https://docs.oracle.com/en/java/javase/11/install/installation-jdk-linux-platforms.html#GUID-737A84E4-2EFF-4D38-8E60-3E29D1B884B8)

*(2) Installing Gradle:*

Locate your OS in the link below and follow instructions carefully - once both Gradle and Java are installed correctly, the program will be ready for use.

[Gradle install link](https://gradle.org/install/)



**B. Running the Program:**

- Clone the master branch on to your local machine into a directory of your choosing
- In your terminal, locate the directory, and enter the directory `assignment-2`
- Run the command `gradle run --console=plain`
- The program is now running, allowing you to interact with it appropriately

**C. Testing the Program:**

- In your terminal, locate the directory, and enter the directory `assignment-2`
- Run the command `gradle test`
- The program will run its test suite, indicating any failed tests

**D. Contributing to the Codebase:**

- Create a personal fork of the project on Github and clone the fork on your local machine
- Add the original repository as a remote called `upstream`
- Create a new branch from master to work on
- Implement/fix/comment your feature
- Run existing test cases to ensure your feature has been implemented properly
- Add or change the documentation as needed.
- Squash your commits into a single commit with git's interactive rebase.
- Push your branch to your fork on Github, the remote `origin`.
- Open a pull request in the `master` branch
- The pull request will then be inspected for approval and merging

### How to use the program

### Logging in

Upon execution, you will be greeted with a message asking you if you want to login or proceed as a guest. To login, press "l". You will then be prompted for your username and password (which will be masked by asterixs). Provided your login credentials are valid, you will be logged in as a certain type of user (customer or staff). If you enter incorrect login details, you will automatically continue as a guest.

If you do not want to log in, press "g" to continue as a guest.

Here are some sample login and payment details to test the program:

```
Sample Account Logins
      Manager:
        username: Charles
        password first2345

      Staff:
        username: Sergio
        password: it234

      User:
        username: Ruth
        password: then346
        
Sample Payment Cards
			Credit Card:
				name: Charles
				number: 40691
				
			Gift Card
				number: 0000000000000000GC
        
        #PLEASE NOTE THAT GIFT CARDS CAN ONLY BE USED ONCE!
        #STAFF ACCOUNTS CAN ADD NEW GIFT CARS TO THE SYSTEM
	

```

#### Guest Experience

As a guest, you have the following options:

- "r" Register for an account.
- "l" Login to an existing account.
- "f" Filter through movie sessions.
- View a movie's synopsis by typing in its corresponding number to the left of the listing.



#### Customer Experience

As a customer, you have the following options:

- "f" Filter through movie sessions (and booking through this).
- View a movie's synopsis by typing in its corresponding number ot the left of the listing.

#### Staff Experience

As a staff member, you have the following options:

- (1) Display booking log
- (2) Edit movie information
- (3) View cancellations (MANAGER ACCOUNT ONLY)
- (4) Add/remove accounts (MANAGER ACCOUNT ONLY)
- (5) Edit viewings
- (6) Edit gift cards



### Functions

##### Register for an account

Once in the account registration screen, first type your desired username, followed by enter.

If your username is not already taken, you will be prompted for a password. If your username is taken, please use a different username.

Once prompted for a password, enter your desired password, followed by enter. You will then be prompted to re-enter your password, please enter tha same password, followed by enter.



##### Login to an existing account

Once in the login screen, you can login to an account that already exists by typing the username, pressing enter, followed by the password, pressing enter. After this you should be logged in.



##### Filter (and book) through movie sessions

Once you are logged in as either a user or guest, you have the ability to filter by entering "f".

Once you are in the filter screen, enter the number which corresponds to the movie you would like to filter.

Then, enter your preferred movie cinema. If you have no preference, enter anything else.

Then, enter the number corresponding to your preferred cinema class. If you have no preference, enter anything else.

You will then see a list of all sessions matching your specifications.

If you are logged in as a customer, you can then book a session.

##### Booking:

After filtering for sessions, you can enter the number corresponding to the session you would like to book tickets for. 

Once you have done this, you will be prompted for the total number of tickets you require.

Once you have done this, you will be prompted for the types of tickets you require. Enter these accordingly. If you do not require any "student" tickets, for example, simply enter 0.



After this, you will be prompted for where in the cinema you would like to sit. Enter front, middle or back depending on your preference.



After this, you can choose how you would like to pay. To pay with a gift card, enter "g", to pay with eftpos, enter "e", to cancel this transaction and return to the homepage, press "c".

Follow the prompts after this to complete your transaction.

Once your transaction is complete, your booking receipt number will be displayed for 10 seconds, before you are logged out and returned to the home page.











