# assignment-2

Welcome to SOFT2412 Thursday 10am Group 1's Cinema Booking Software!

In order to run this software, your local machine must have Java (at least Java 8), additionally for compilation, Gradle must be installed properly (installation steps provided below).

### How to build, execute, and test the program:

#### Compiling and running the build with Gradle

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
- Run the command `gradle run`
- The program is now running, allowing you to interact with it appropriately

**C. Testing the Program:**

- In your terminal, locate the directory, and enter the directory `assignment-1`
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

In sprint 1 we implemented the ability to login as any user or proceed as a guest, and then see the movies currently available. 

### Logging in

Upon execution, you will be greeted with a message asking you if you want to login or proceed as a guest. To login, press "l". You will then be prompted for your username and password (which will be masked by asterixs). Provided your login crednetials are valid, you will be logged in as a certain type of user (customer or staff). If you enter incorrect login details, you will automatically continue as a guest.

If you do not want to log in, press "g" to continue as a guest.

