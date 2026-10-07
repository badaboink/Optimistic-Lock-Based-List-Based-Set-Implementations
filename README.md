# Fondements des algorithmes répartis

## Project 1

### Steps to run the benchmark bash file

1. Move ``benchmark`` to ``synchrobench/java``

2. Run it, output it in terminal and send the output to a file. 
    
    a. With errors: ``./benchmark  2>&1 | tee results.txt``
    b. With no errors: ``./benchmark | tee results.txt``

#### Steps to run handmade java class with synchrobench:

1. Move java file to ``synchrobench/java/src/linkedlist/lockbased``

2. Compile it and save it in bin: ``javac -d bin -cp bin src/linkedlists/lockbased/HandOverHandListBasedSet.java``

