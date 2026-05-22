### 1.Bit masking

Bit masking is a programming technique that uses bitwise operators (AND, OR, XOR, NOT) to manipulate specific bits—setting, clearing, or reading individual binary digits—within an integer, often used to store multiple boolean flags compactly or for optimized data processing. It acts as a filter to hide or modify data, usually at high speeds, which is beneficial in competitive programming and low-level system operations

### 2.Find ith bit
In computer science and digital electronics, you almost always start counting from the right, beginning at 0.
So 5th bit means starting from 0 meaning 6th element

Solution: Use AND operation. **AND will return 1 only when both bit is , otherwise zero**

Let's find the 3rd bit of 101101
- 101101
- Take 1
- Left shift it i times, then it becomes 1000. **This is called Mask**
- Do AND operations. If result zero then bit was 0 otherwise 1

  | OR     |
  |:-------|
  | 101101 |
  | 001000 |
  | 001000 |

Result Non-zero bit was 1
Another example find 3rd bit in 10000

| OR    |
|:------|
| 10000 |
| 01000 |
| 00000 |
Result is zero so 3rd bit is zero

Code challenge find the 5th bit of 439802