# Setup Instructions

## JDK Version Used

```
openjdk version "21.0.12.1" 2026-08-18
OpenJDK Runtime Environment (build 21.0.12.1+1-1-24.04.4-Ubuntu)
OpenJDK 64-Bit Server VM (build 21.0.12.1+1-1-24.04.4-Ubuntu, mixed mode, sharing)
```

Any JDK 17+ works. (The project uses `switch` arrow syntax, which needs Java 14 or later.)

## Installing the JDK

1. Download a JDK (for example, Eclipse Temurin or Oracle JDK 21) for your operating system.
2. Install it using the installer.
3. Set the `JAVA_HOME` environment variable to the JDK folder and add `JAVA_HOME/bin` to your `PATH`.
   - **Windows:** System Properties → Environment Variables → New `JAVA_HOME` → edit `Path` → add `%JAVA_HOME%\bin`
   - **macOS / Linux:** add to `~/.bashrc` or `~/.zshrc`:
     ```bash
     export JAVA_HOME=/path/to/jdk-21
     export PATH=$JAVA_HOME/bin:$PATH
     ```
4. Verify the installation in a new terminal:
   ```bash
   java -version
   javac -version
   ```

## Running "Hello World"

The program is in `docs/HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World! Java is set up correctly.");
    }
}
```

Steps:

```bash
cd docs
javac HelloWorld.java      # compiles the source into HelloWorld.class (bytecode)
java HelloWorld            # the JVM loads and runs HelloWorld.class
```

Output:

```
Hello, World! Java is set up correctly.
```

What happened:
1. `javac` (the Java compiler, part of the JDK) read `HelloWorld.java` and produced `HelloWorld.class`, which contains bytecode.
2. `java` started the JVM, which loaded `HelloWorld.class`, found the `main` method and executed it.

> Screenshot: add a screenshot of your own terminal running the commands above here, e.g. `![Hello World run](hello_world.png)`.
