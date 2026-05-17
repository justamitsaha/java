

###  1.What is decimal?
Base of 10 e.g. 274 = 2x10^2 + 7x10^1 + 4x10^0 = 200+70=4
### 2. What  is Binary?
Base of 2 e.g. 101 = 1x2^2 + 0x2^1+ 1x2^0= 4+0+1 =5

```java

public static void main(String[] args) {
  String s = "1001101";
  int value = Integer.parseInt(s, 2);
  System.out.println(value);

  int val2 = 0;
  char[] c = s.toCharArray();
  for (int i = 0; i < c.length; i++)
    val2 = val2 + Integer.parseInt(String.valueOf(c[i])) * (int) Math.pow(2, c.length - (i + 1));
  System.out.println(val2);

  // Optimized manual approach (Horner's Method)
  val2 = 0;
  // Iterate through each character of the binary string
  for (int i = 0; i < s.length(); i++) {
    // Get the ASCII numeric value of the current bit ('0' or '1') which will be either 48 or 49
    // when we subtract '0' we remove ASCII 40 leaving us with 0 or 1
    int bit = s.charAt(i) - '0';
    // Horner's Method: Multiply current result by base (2) and add next digit
    val2 = (val2 * 2) + bit;
  }
  System.out.println(val2);
}
```  
### 3. Conversion between decimal and binary
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
### 4. Binary addition

carry        11    
Num 1       101     (5)    
Num 2       111     (7) 
Solution   1100     (12)

### 5. Binary Subtraction
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

### 8. Is Bitwise operator faster?

Answer: 
```java
    // Compare benchmark of Left shift vs multiplication  
    public static void main(String[] args) {
        // Note: Start at 1 to avoid the 0-loop trap

        // Loop 1: Bitwise
        long startTime = System.nanoTime();
        int count1 = 0;
        for (long i = 1; i < Integer.MAX_VALUE; i = i << 1) count1++;
        long durationShift = System.nanoTime() - startTime;

        // Loop 2: Multiplication
        startTime = System.nanoTime();
        int count2 = 0;
        for (long i = 1; i < Integer.MAX_VALUE; i = i * 2) count2++;
        long durationMult = System.nanoTime() - startTime;

        System.out.println("Shift Time: " + durationShift + " ns");
        System.out.println("Mult Time:  " + durationMult + " ns");
        System.out.println("Count"+ count2);
    }
```
If you run the corrected benchmark above, you will likely find that the times are almost identical.
Modern compilers automatically optimize jave operations . The compiler looks out for you and rewrites your math to be as fast as possible!
```java
//Compare benchmark of & operation with modulus both used to check even
public static void main(String[] args) {
  int iterations = 500_000_000; // Large enough to trigger JIT optimization

  // --- WARM-UP PHASE ---
  // Run both heavily first so the JVM compiles them equally before we measure
  runBitwise(iterations);
  runModulus(iterations);

  // --- ACTUAL MEASUREMENT ---
  long startTime = System.nanoTime();
  long bitwiseResult = runBitwise(iterations);
  long bitwiseTime = System.nanoTime() - startTime;

  startTime = System.nanoTime();
  long modulusResult = runModulus(iterations);
  long modulusTime = System.nanoTime() - startTime;

  // Print results to prevent Dead Code Elimination
  System.out.println("Bitwise Time: " + bitwiseTime + " ns (Result checksum: " + bitwiseResult + ")");
  System.out.println("Modulus Time: " + modulusTime + " ns (Result checksum: " + modulusResult + ")");
}

private static long runBitwise(int iterations) {
  long count = 0;
  for (int i = 1; i < iterations; i++) {
    if ((i & 1) == 0) { // Check if even
      count++;
    }
  }
  return count;
}

private static long runModulus(int iterations) {
  long count = 0;
  for (int i = 1; i < iterations; i++) {
    if ((i % 2) == 0) { // Check if even
      count++;
    }
  }
  return count;
}
```

**So with modern JIT compiler bitwise operations won't show much effert**

Think of bitwise operations less as a way to speed up your everyday total * 2 calculations, and more as a toolkit for data manipulation, compression, and hardware communication.

uses
- Hardware and Embedded Systems (IoT): If you ever write code that interacts directly with hardware (like an Arduino, Raspberry Pi, or device drivers), you talk to the hardware via "registers." To turn on a physical LED light on a circuit board, you don't call a function like turnOnLight(). Instead, you write a bitwise operation that sets a specific bit on a specific hardware register memory address to 1.
- Efficient Data Structures (Bitsets) : Imagine you need to keep track of a unique list of millions of user IDs to see if they are active today. Storing millions of Integer objects in a HashSet will consume massive amounts of RAM. Instead, you can use a Bitset (or java.util.BitSet). It allocates a giant block of bits. If user #5422 is active, you flip the 5422nd bit to 1.
- Cryptography and Hashing : Almost every major cryptographic algorithm (AES, RSA) and hashing function (SHA-256, MD5) relies heavily on bitwise operations. Algorithms use XOR (^), AND (&), and Bit Rotations to scramble data. The beauty of the XOR operation is that it is perfectly reversible: (A ^ B) ^ B = A. This makes it the foundational building block of encryption.
- Networking Protocols and Binary File Formats :When sending data over a network or saving it to a compact binary file (like a JPEG, ZIP, or MP3), every byte counts. Network packets (like IPv4 headers) compress data down to the bit level to save bandwidth. If a protocol dictates that the first 4 bits of a byte represent the protocol version and the next 4 bits represent the header length, you must use bitwise shifts and masks to slice that byte open and read the data.
- High-Performance Graphics and Game Dev: In game development, colors are usually represented as ARGB (Alpha, Red, Green, Blue). Each channel takes 8 bits (a value from 0 to 255). Instead of creating a complex object for every single pixel on a screen, developers pack all four channels into a single 32-bit integer. Bitwise operations are used to extract individual colors instantly:
- Managing Flags and Permissions (The Bitmask): Instead of storing a bunch of boolean variables (which take up at least 1 byte each in memory), you can pack up to 32 distinct true/false flags into a single int (4 bytes). A classic example is file permissions (Read, Write, Execute): Where you see this: File systems (Linux chmod), Android intent flags, game engine states, and database configuration settings.
```java
public static final int READ    = 1; // 0001
public static final int WRITE   = 2; // 0010
public static final int EXECUTE = 4; // 0100

// Assigning permissions using Bitwise OR (|)
int userPermissions = READ | WRITE;   // 0011 (Can Read and Write)

// Checking permissions using Bitwise AND (&)
boolean canExecute = (userPermissions & EXECUTE) == EXECUTE; // false
```

### 9. Importance of Bitwise operator?

Here are some of the most practical, clever, and meaningful bitwise operations used in real-world programming. You can add these directly to your collection.

----------

- Swapping Two Numbers Without a Temporary Variable

  Using the **XOR (`^`)** operator, you can swap two integers completely in place without allocating a third `temp` variable.



  ```java
  public static void main(String[] args) {
      int a = 5;  // 0101
      int b = 10; // 1010

      a = a ^ b; // a now holds the combined bits
      b = a ^ b; // b becomes the original a (5)
      a = a ^ b; // a becomes the original b (10)

      System.out.println("a: " + a + ", b: " + b); // Prints: a: 10, b: 5
  }
  ```
```java
    public static void main(String[] args) {
        int a = 10, b = 20;
        a = a + b; // a = 30
        b = a - b; // b = 30 - 20 = 10
        a = a - b; // a = 30 - 10 = 20

        System.out.println("a: " + a + ", b: " + b); // Prints: a: 10, b: 5
    }
```

- Checking if a Number is a Power of 2

  Powers of 2 (like 2, 4, 8, 16, 32...) always have exactly **one** bit set to `1` in binary (e.g., $8$ is `1000`). If you subtract 1 from a power of 2, all trailing zeros flip to ones (e.g., $7$ is `0111`).

  Doing a bitwise AND between them results in exactly `0`.



  ```java 
  public static void main(String[] args) {
      int n = 16;
      // If (n & (n - 1)) == 0, it's a power of 2 (Note: n must be greater than 0)
      boolean isPowerOfTwo = (n > 0) && ((n & (n - 1)) == 0);

      System.out.println(n + " is power of 2? " + isPowerOfTwo); // true
  }
  ```

- Slicing/Extracting Specific Bytes (Bit Masking)

  When dealing with network packets, colors, or file headers, you often need to isolate a single byte out of a 4-byte integer. You do this by shifting the desired byte to the rightmost position and using a mask of `0xFF` (which is `11111111` in binary) to clear everything else.


  ```java
  public static void main(String[] args) {
      // Example: An ARGB color packed into one integer
      int color = 0xFF33A2B4;

      int green = (color >> 8) & 0xFF; // Shift right by 8 bits, mask out the rest
      System.out.println("Green channel value: " + green); // 162 (0xA2)
  }
  ```

- Toggling a Value (On / Off Switch)

  Instead of writing an `if-else` statement to flip a value back and forth between two states, you can use XOR to toggle a specific bit.

    ```java
    public static void main(String[] args) {
        int flags = 0b0000; // Binary literal for 0
        int BLUETOOTH_ON = 0b0100;

        // Toggle Bluetooth state (If it's off, turn it on. If it's on, turn it off)
        flags = flags ^ BLUETOOTH_ON;
        System.out.println(Integer.toBinaryString(flags)); // 100 (Now it's on)

        flags = flags ^ BLUETOOTH_ON;
        System.out.println(Integer.toBinaryString(flags)); // 0 (Now it's off)
    }
    ```

- Getting the Absolute Value (Without Math.abs)

  You can find the absolute value of an integer by using the sign bit (the leftmost bit in a 2's complement number). Shifting an `int` right by 31 copies the sign bit to every position, creating a mask of all 0s (for positive) or all 1s/`-1` (for negative).


  ```java
  public static void main(String[] args) {
      int x = -45;
      int mask = x >> 31; // yields -1 if negative, 0 if positive
      int absX = (x + mask) ^ mask;

      System.out.println("Absolute value: " + absX); // 45
  }
  ```

- Clearing the Lowest Set Bit

  This is a variation of the power-of-two trick. `n & (n - 1)` will always find the lowest (rightmost) `1` bit in a number and flip it to `0`. This is highly useful in algorithms that need to count how many total `1` bits are inside a number (Hamming weight).


  ```java
  public static void main(String[] args) {
      int number = 0b10110; // 22 in decimal
      int cleared = number & (number - 1);

      System.out.println(Integer.toBinaryString(cleared)); // 10100 (The lowest 1 bit is gone!)
  }
  ```

### 10. BitSet Operation


#### The Visual Difference: HashSet vs. BitSet

When you store a user ID in a `HashSet<Integer>`, Java doesn't just store the number. It wraps each primitive `int` in an `Integer` object, creates a `HashMap.Node` object, and manages pointers.

In contrast, a `BitSet` is just a long array of primitive `long` values (64 bits each) laid out back-to-back in memory.

#### The Downside of a HashSet

If you stored those same 1 million unique user IDs in a `HashSet<Integer>` on a 64-bit Java Virtual Machine:

1.  Every primitive `int` ($4\text{ bytes}$) is boxed into an `Integer` object ($16\text{ bytes}$).

2.  The `HashSet` wraps this inside a `HashMap$Node` object ($32\text{ bytes}$).

3.  The underlying table tracking the nodes uses object references ($8\text{ bytes}$ per bucket).


Ultimately, a `HashSet` consumes roughly **32 to 40 Megabytes** to track 1 million dense IDs. The `BitSet` accomplishes the exact same task in **122 Kilobytes**—making it roughly **300 times more memory-efficient**.

#### When should you _not_ use a BitSet?

BitSets are perfect when your IDs are dense (e.g., auto-incrementing database IDs from 1 to 10,000,000).

If your IDs are wildly sparse—for example, if you only have 2 active users, but their IDs are `7` and `2,000,000,000`—the `BitSet` will blindly allocate enough bits to reach index 2 billion, consuming around 240 MB of RAM just to hold two flags. In that rare, highly sparse scenario, a `HashSet` or a compressed bitmap (like RoaringBitmap) is a better choice.
```java
    public static void main(String[] args) {
        // Create a BitSet. It dynamically expands, but you can give it an initial size.
        // For 10 million users, it will allocate roughly 1.2 MB of memory up front.
        BitSet activeUsers = new BitSet(10_000_000);

        // 1. Mark users as active (Flipping the bit at that specific index to 1)
        int userA = 5422;
        int userB = 9_999_999;
        int userC = 75;

        activeUsers.set(userA);
        activeUsers.set(userB);
        activeUsers.set(userC);

        // 2. Check if a user is active (Near-instantaneous O(1) bitwise lookup)
        System.out.println("Is user 5422 active? " + activeUsers.get(5422));      // true
        System.out.println("Is user 100000 active? " + activeUsers.get(100000));  // false
        System.out.println("Is user 75 active? " + activeUsers.get(75));          // true

        // 3. Deactivate a user (Flip the bit back to 0)
        activeUsers.clear(5422);
        System.out.println("Is user 5422 still active? " + activeUsers.get(5422)); // false

        // 4. Total count of active users (Counts how many bits are set to 1)
        System.out.println("Total active users today: " + activeUsers.cardinality()); // 2
    }
```
