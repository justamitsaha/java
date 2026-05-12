

### 1.What is decimal?
Base of 10 e.g. 274 = 2x10^2 + 7x10^1 + 4x10^0 = 200+70=4
### What  is Binary?
Base of 2 e.g. 101 = 1x2^2 + 0x2^1+ 1x2^0= 4+0+1 =5

```java  
public static void binaryToDecimal(String s) {  
    int value = Integer.parseInt(s, 2);  
    System.out.println(value);  
  
    int val2 = 0;  
    char[] c = s.toCharArray();  
    for (int i = 0; i < c.length; i++)  
        val2 = val2 + Integer.parseInt(String.valueOf(c[i])) * (int) Math.pow(2, c.length - (i + 1));  
    System.out.println(val2);  
  
    // Optimized manual approach (Horner's Method)  
  val2 = 0;  
    for (int i = 0; i < s.length(); i++) {  
        // Shift left (multiply by 2) and add the new bit  
 // '0' becomes 48 in ASCII, '1' becomes 49. // Subtracting '0' gives you the actual 0 or 1.  val2 = (val2 * 2) + (s.charAt(i) - '0');  
    }  
    System.out.println(val2);  
} 
```  
### Conversion between decimal and binary
- Binary to decimal 101 = 1x2^2 + 0x2^1+ 1x2^0= 4+0+1 =5
- Decimal to binary

  354 /2 -> 0    
  177 /2 -> 1    
  88  /2 -> 0    
  44  /2 -> 0    
  22  /2 -> 0    
  11  /2 -> 1    
  5   /2 -> 1    
  2   /2 -> 0    
  1    
  Answer 10110010

```java  
public static void decimalToBinary(int n) {  
    // 1. Using the standard library (Best practice)  
  System.out.println(Integer.toBinaryString(n));  
  
    // 2. Optimized manual approach  
  if (n == 0) {  
        System.out.println("0");  
        return;  
    }  
  
    // Since we are concatenating StringBuilder  is better than String  
  StringBuilder sb = new StringBuilder();  
    // We use a temp variable to keep 'n' intact if needed later  
  for (int i = n; i > 0; i /= 2) {  
        sb.append(i % 2);  
    }  
  
    // Since we appended to the end, we must reverse it  
  System.out.println(sb.reverse().toString());  
}
```  
### Binary addition

carry       11    Num 1       101     (5)    
Num 2       111     (7) Solution   1100     (12)
### Binary Subtraction
- Let's say we want to subtract 12-7, 12 = 1100, and 7 = 111
- There is no subtraction is binary 12-7 is 12 +(-7).
- So what we have to do is that we have get the -ve value of 2nd operand  7 and add it with 1st operand
- for getting binary of -7 we have to find 2's complement of 7 or 111 by switch all digit and add 1
- When we say 7 is 111, it means  000...000111, zeros are the remaining digits of 32 bit int
- So, for 2's compliment of 7 111...111000 will be added with 1 making it 111...111001
- So, 12 -7 becomes

  000...0001101 (12)

  111...1111001 (-7)

  000...0000110 (5)

### 6. Bitwise operator

- `&`: **AND** operator, when both operand 1, it will be 1 otherwise 0
- `|`: **OR** operator, when either operand is 1, it will be 1 otherwise 0
- `^`: **XOR** operator, when both operand same it will be 0 otherwise 1
- `~`: **INVERSE** operator will reverse the bits e.g. 000...101 will become 111...010 also called 1's compliment
- `>>`: **Right shift** operator 12>>2 means all bits will be shifted 2 times 000...1100 will become 000...0011(3)
- `<<`: **LEFT shift** operator 12<<2 means all bits will be shifted 2 times 000...1100 will become 000...1100100(48)

### 7. Importance of Shift operators
-   **<< (Left Shift):** Shifts bits left.  Effectively multiplies the number by 2
-   **>> (Right Shift):** Shifts bits right. It effectively divides the number by 2