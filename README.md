# Rising Cities

This is a project which helps a construcction company to decide how to build a complete city.

To help the company, we are using a Min heap and a Red Black tree.

The Min heap helps us to sort the buildings based on the time of work that was done on a building.

The Red-Black tree helps to store the based on the building number.

For this project, the data is taken as input from a text file and output is also written to the text file. 
Input file data has commands according to which actions are performed on the Min Heap and Red - Black. 
For Red - Black tree implemented operations are insert and delete and for minheap implemented operations are insert and extract min. 
Checks are placed to ensure the sanctity of the data structure remains after each and every step.

### Complexity of the Operations:

Print Building takes O(log n) time for a single node where n is the total number of buildings

Printing Building Range takes time O(log n + S) where n is the total number of buildings and S is buildings printed.

All of the other operations work in O(log n) time.
