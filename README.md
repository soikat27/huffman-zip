# HuffmanZip 

HuffmanZip is a Java program that implements Huffman Encoding and Decoding for text files. 
It compresses files to reduce storage space and allows lossless decompression back to the original file. 

The project demonstrates:
* File I/O handling
* Tree data structures
* Bit-level operations
* Priority queue usage in Java

HuffmanZip is perfect for users who want to save storage space or experiment with data compression, while developers can explore **Huffman Tree, BitStream, and file handling in Java**.

## Getting Started

Follow these instructions to run HuffmanZip program on your machine:

### **Prerequisites**

You’ll need:

* Java JDK 11+ (includes both):
    - **Compiler (`javac`)** – to compile Java source files
    - **Runtime (`java`)** – to run compiled programs

* A terminal or command prompt (macOS/Linux or Command Prompt/PowerShell on Windows)

#### Check that Java JDK is installed:

```
java -version   # checks the Java runtime
```
and
```
javac -version  # checks the Java compiler
```

### **Installing**

#### 1. Clone this repository and Navigate to the project directory:

```
git clone https://github.com/soikat27/huffman-zip.git
```

```
cd huffman-zip
```

## Running the program


#### `Option 1`: Compile and Run:

1. **Create a folder for compiled classes:**

```
mkdir bin
```

2. **Compile all .java files:**

```
javac -d bin src/huffman/*.java src/huffmanzip/*.java src/utils/*.java
```

3. **Run the main program**

    (*a sample test file is provided in `./test/resource` dir for program test*):

* #### Encode:
```
java -cp bin huffmanzip.HuffmanZip -encode <your_file>
```

* #### Decode: **For decoding, <your_file> must be a `.hz` compressed file.
```
java -cp bin huffmanzip.HuffmanZip -decode <your_file>
``` 

** Replace `<your_file>` with your own file.


#### `Option 2`: Run with the Prebuilt JAR:
Not in a mood to compile manually? use the JAR located in dist/ !

* #### Encode:
```
java -jar dist/HuffmanZip.jar -encode <your_file>
```

* #### Decode: **must use `.hz` file
```
java -jar dist/HuffmanZip.jar -decode <your_file>
```

** Replace `<your_file>` with your own file.



## Running Tests

JUnit tests are available in the `test/` folder.

#### 2. Compile JUnit tests:

```
javac -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" -d bin test/huffman/*.java
```

#### 1. Run JUnit test classes:

```
java -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore huffman.HNodeTest
```
```
java -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore huffman.HuffmanTreeTest
```

## Deployment

There’s no special deployment needed. Use the ready-to-go Jar or Compile the program with Java and run it locally from the terminal as instructed above.

## Built With

* Java 11+ – core language
* JUnit 4 – for automated testing
* BitInputStream / BitOutputStream – custom utilities for bit-level operations
* `JavaDoc` – documentation generated in doc/ (see doc/index.html)

## Contributing

Contributions are welcome! You can:

* Submit bug reports or feature requests via GitHub Issues

* Fork the repository and submit pull requests for improvements

## Author

* Soikat Saha – initial development


## License

This project is licensed under the **Apache 2.0 License** - see the [LICENSE](LICENSE) file for details

## Acknowledgments

* Inspired by Huffman Coding Algorithm – **David A. Huffman**
* Special thanks to **Professor Ilinkin** for guidance on data structures
* Utility functions provided in **`BitInputStream`** & **`BitOutputStream`**
* Thanks to the Java open-source community for helpful resources 