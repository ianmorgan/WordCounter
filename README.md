Wordcounter
===========

A simple Java app to stream over a text document counting the words.


Dependencies
------------
* Java 7
* Maven

To Build and Run
----------------
mvn test
mvn compile exec:java -Dexec.args="mobydick.txt"

Bash version
------------
./demo.sh mobydick.txt


Example Data
------------
Original data is at
http://faculty.washington.edu/stepp/courses/2004autumn/tcss143/lectures/files/2004-11-08/mobydick.txt


Possible Optimisations
----------------------
Don't use InputStreamReader and deal with raw bytes. If the raw stream is known to be in particular fixed length
encoding this would be sufficient.

