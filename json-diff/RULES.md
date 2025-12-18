1. If any node is matching in both file then mention it as Present under category column and keep it in the csv e.g."node0": "efg",
2. When any node is present in both files, and it holds a key-value pair in both files and their type is same i.e. both are {} or [] we will keep category as Present and in column D and E we will keep value as {}  for e.g.
   . node2 present in both files has key-value it will be present
   . but node3 will be different as in one file it is {} and other it is [] and we will show full value
3. In above case i.e. when in node present in both files, and it holds a key-value pair in both files and their type is same. We will do the comparison with child element for e.g. node2.child1 and node2.child2  will be compared with same rules and added as a row
4. This comparison will continue until a node doesn't hold any key value pair e.g. node2.child1 and node2.child2. In this case point no 2 will not be applied and no more child nodes will be evaluated