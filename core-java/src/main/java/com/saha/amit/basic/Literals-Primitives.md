1. ### What is difference between `literal` and `primitives`?
    - A primitive is a category of data type Examples    int, double, boolean, char
    - While literals are the actual values you type into your code to represent those types e.g. 100, 3.14, true, 'A', "Hello"

2. ### Types of primitives in Java?
   **Integer type**
    - **byte**: An `8-bit` signed integer. It has a range from `-128 to 127`. It is often used to save memory in large arrays.
    - **short**: A `16-bit` signed integer. Its range is from `-32,768 to 32,767`.
    - **int**: A `32-bit` signed integer. It is the most commonly used numeric type for whole numbers, with a range from roughly `-2.1 billion to 2.1 billion`.
    - **long**: A `64-bit` signed integer used when a wider range than int is needed. For identification, long literals should **end with an L** (e.g., 123L)

   **Decimal type**
    - **float**: A 32-bit single-precision floating-point number. Literals must **end with an f or F** (e.g., 3.14f). It is less precise than double but saves memory in large arrays.
    - **double**: A 64-bit double-precision floating-point number. It is the **default choice for decimal values** in Java and offers higher precision than float

   **Character type**
    - **char**: A single `16-bit Unicode character`. It can store any character from the Unicode set, including letters, symbols, and special characters. The values are enclosed in single quotes (e.g., 'A')

   **Logical Type**
    - **boolean**: Represents only two possible logical values: true or false. It is primarily used for conditional flags and decision-making logic.

3. ### Which literal Java defaults to in arithmetic operations?
