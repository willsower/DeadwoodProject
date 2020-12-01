# Deadwood Game

## Overview

Board game made in the Java language.

## Implementors

Daria Miller

Taichen Rose

## Installation

### Dependencies
1. Download javafx-sdk-11.0.2
2. Download JDK 15

### If using Github or Zipped file
1. Clone this repository from Github or open the zipped file
2. Using intellij IDE, add the files from the github or zipped file into a project.
3. Open module settings in intellij and create a module. Go down to Libraries in the Project Structure file and create a library, adding the javafx sdk to it. Going back to the newly made module, attach this library.
4. Create a new application and add javafx sdk to it. Set the JRE to the module you created previously

## Things We Would Have Changed

1. Learned how to combine two .fxml files together so our SystemManager class was not so large. We tried to have two controllers talk to one .fxml file, however this would not work. In the future we would like to have split up the work in the board.fxml View file to two seperate Views.

2. Tried implementing a magnifying glass icon which could be clicked on by the user. When clicked, the user could move their mouse aroudn the board and a tool tip would pop up acting like a magnifier. Our board is smaller, and that is because we couldn't fit the full board onto the screen we were developing on.
