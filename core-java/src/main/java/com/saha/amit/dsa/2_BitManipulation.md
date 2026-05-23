# Bit masking

 Bit masking is a programming technique that uses bitwise operators (AND, OR, XOR, NOT) to manipulate specific bits—setting, clearing, or reading individual binary digits—within an integer, often used to store multiple boolean flags compactly or for optimized data processing. It acts as a filter to hide or modify data, usually at high speeds, which is beneficial in competitive programming and low-level system operations
 
 **Note** In computer science and digital electronics, you almost always start counting from the right, beginning at 0.So, when ever we say for e.g. 5th bit, it means 6th element as we are starting from 0.

## 1.Find ith bit: Find 3rd bit of 101101

 Solution: Create a **mask** which looks 00100. 3rd Bit(the one we intend to find) is 1 rest zero. Do `AND` operation. We know if one digit is 0 in `AND` it will become zero. So in this case if 3rd bit is zero the result of `AND` will be zero otherwise non-zero

 - STEP1: Take 1 shift it nth(3 times) 001000
 - STEP2: Do `AND` operation
 - STEP3: If result zero then 3rd Bit zero otherise 1

    | AND    |
    |:-------|
    | 101101 |
    | 001000 |
    | 001000 |

 Result Non-zero 3rd bit is non-zero
 Another example find 3rd bit in 10000

   | AND   |
   |:------|
   | 10000 |
   | 01000 |
   | 00000 |
 Result is zero so 3rd bit is zero

 Code challenge find the 5th bit of 439802

 ```java
    //find the 5th bit of 439802
    public static void main(String[] args) {
        int i = 439802;
        System.out.println("i in binary -> " + Integer.toBinaryString(i));
        int mask = 1 << 5;
        System.out.println("Mask -> " + Integer.toBinaryString(mask));
        if ((i & mask) == 0)
            System.out.println("5th element is 0");
        else
            System.out.println("5th element is 1");
    }
```

 
 
## 2.Set ith bit to 1: Set the 5th bit of 10010101 as 1

 Solution: Here we want to keep rest bits unchanged only ith bit will be set as 1. So we create a mask of zero with ith bit as 1 and do or operation. Since in or operation zeros don't change the bit rest bits stays unchanged and ith bit since it is 1 so ir-respective of what ever value is there will  be 1

 - Step 1: Create Mask: Take 1 shift it i(5) times
 - Step 2: Do operation

| OR       |
|:---------|
| 10010101 |
| 00100000 |
| 10110101 |

Code challenge:  change the 5th bit of 54656
```java
    //change the 5th bit of 54656 to 1
    public static void main(String[] args) {
        int i = 54656;
        int mask = 1 << 5;
        int result = i | mask;
        System.out.println("Original Value: " + Integer.toBinaryString(i));
        System.out.println("Masked Value:   " + Integer.toBinaryString(mask));
        System.out.println("Result Value:   " + Integer.toBinaryString(result));
    }
```

## 3.Set the ith bit to 0: Set the 5th bit of 10101011 as 0

Solution: We need to find a mask where all bits are 1 and the ith bit is zero like(1110111) . Now if we do `AND` operation digits with 1 won't change but `AND` operation with zero will always get zero

- Step1:Take 1 shift it n times 100000
- Step2:Do `INVERSE` operation ~ to get mask 11011111
- Step3:Do `AND` operation


| OR       |
|:---------|
| 10101011 |
| 11011111 |
| 10001011 |

## 4. How many bits need to change, to change one number to another for e.g. 10100110 to 1001001

Solution: Do `XOR` and count the number of 1

