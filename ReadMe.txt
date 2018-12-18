# General
This short Java Selenium WebDriver based project implements simple UI test for www.amazon.com site
Tests run by jUnit, the project managed with Maven.

# Instructions
This project in build and run on Windows based machine.
1) To run this project you first need to download this project files from GitHub https://github.com/neploch/stanley repository.
2) In case you do not have chromedriver.exe file on your computer you can download it from http://chromedriver.chromium.org/downloads
Choose the latest version folder, select and download chromedriver_win32.zip for Windows OS based machine
Unzip it.
3) Create new Java Maven project on your IDE. Use selenium-java and jUnit dependencies from  the POM file.
4) Change the following paths in search_harry_potter.java file:
line 21:
        String projectBasePath = "C:\\Users\\Eliyahu\\Downloads\\Stanley\\";
update to the actual location of the project root path on your machine
line 30:
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Eliyahu\\Downloads\\chromedriver_win32\\chromedriver.exe");
update to the folder actually containing chromedriver.exe on your machine
5) Run the project with jUnit 
6) Find the run results and the screenshot in timestamped folder in location you defined in step 4
