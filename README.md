# 7a_Calculator

This repository contains the solution to the calculator app task assigned in the Software Development course at TGM.

## My Specific Task Assignment

My catalogue number is 13, which results in task B and task X. This means that I need to implement a calculator app where:

- The RadioButtons are initially deactivated and are activated in the `onStart` method (**Task B**).
- The "MS" button stores the current input values persistently, and the "MR" button restores them (**Task X**).

## Task Distribution Based on Catalogue Number

Each student was given a slightly different task, determined by their catalogue number. Here’s how the tasks were assigned:

Take the first (ten's) digit of your catalogue number modulo 3:

- 0 results in A
- 1 results in B
- 2 results in C
  
Then take the last (one's) digit of your catalogue number modulo 3:

- 0 results in X
- 1 results in Y
- 2 results in Z
  
For example, a catalogue number 07 would give:
- First digit 0 modulo 3 = 0, resulting in Task A.
- Last digit 7 modulo 3 = 1, resulting in Task Y.

A catalogue number 18 would give:
- First digit 1 modulo 3 = 1, resulting in Task B.
- Last digit 8 modulo 3 = 2, resulting in Task Z.

## Task Description

Create a calculator app using the following design template. Make sure to implement the design exactly (using either German as in the screenshots, or English) and use a constraint layout. Version your code using Git and commit regularly at sensible points during development.

The distances of the UI elements to the edge are 16dp each and the distances of the UI elements to each other (horizontally and vertically) are 8dp each.

### Functionality

- A click on the "Berechnen"/"Calculate" button shall perform the selected calculation with the two entered values and output the result in the lower output field.
- If the user touches the output field, it is cleared. (Touching is NOT a click!)
- The calculation types are implemented using radio buttons so that only one calculation type can be selected at a time.
- Negative numbers should be shown in red, non-negative numbers in black.

### Extra Functionality 1

Implement one of the options depending on your catalogue number:

- A: The background colour of the "Calculate" button is to be set to a green colour in the onResume method to signal readiness for use.
- B: The RadioButtons are initially deactivated and are activated in the onStart method.
- C: The "Calculate" button is initially deactivated and activated in the onStart method.

### Extra Functionality 2

Implement one of the options depending on your catalogue number:

- The "MS" button (Memory Store) is used for persistent storage (Shared Preferences) of
  - the current input values (X)
  - the current result (Y)
  - the currently selected calculation type (Z)
- If the value has been saved, a short toast or snackbar with the text "Gespeichert"/"Saved" should appear.
- The "MR" button (Memory Recall) restores the respective stored value.

### Extensions

1. Add an options menu to the app (menu in the status bar at the top right). There should be two selection options:

  - Reset: Reset all input and output fields.
  - Info: Output of information about the app (author, version number) as a toast/snackbar.
Also use meaningful icons.

2. Try to solve the selection of calculation type via a spinner element. In the submission interview, you will have to show both versions - ideally, use Git branches to easily switch between the two.

### Notes

- Use at.ac.tgm.hit.sew7.[username].calculator (or ...rechner) as App-Id (Package Name).
- Do not use hard-coded texts in the layout.
- Document your code as usual; comment more involved parts of your code.
- For practice purposes, it is definitely helpful to also look at the other task variants.

### Submission

- Project without build artefacts

### Assessment

Grading is done in a submission interview:

- [x] GKü: Basic functionality with extra functionality 1 OR 2
- [x] GKv: Basic functionality with extra functionality 1 AND 2
- [x] EKü: in addition to GKv also an Options Menu
- [x] EKv: EKü with spinner instead of radio buttons