# JVM Basics

## JDK, JRE and JVM

**JVM (Java Virtual Machine)** – the engine that actually runs Java programs. It reads compiled
bytecode, converts it into instructions your computer's CPU understands, and manages memory for you
(including garbage collection of objects you no longer use). Each operating system has its own JVM.

**JRE (Java Runtime Environment)** – the JVM **plus** the standard class libraries (`java.lang`,
`java.util`, etc.) that programs need at runtime. A JRE is enough to *run* a Java program, but not
to *build* one.

**JDK (Java Development Kit)** – the JRE **plus** development tools such as the compiler (`javac`),
the debugger (`jdb`), `jar` and `javadoc`. Developers install the JDK.

A simple way to remember it:

```
JDK = JRE + development tools (javac, jar, ...)
JRE = JVM + standard libraries
JVM = runs the bytecode
```

> Note: since Java 11, a separate JRE download is generally no longer distributed; you install the JDK,
> which contains everything needed to compile and run.

## What is Bytecode?

When you compile `Main.java` with `javac`, you do not get machine code for Windows or Linux. Instead
you get a `Main.class` file containing **bytecode** – a compact, platform-neutral set of instructions
designed for the JVM. The JVM then interprets this bytecode and, for frequently used code, compiles
it into fast native machine code on the fly using its **JIT (Just-In-Time) compiler**.

```
Main.java  --javac-->  Main.class (bytecode)  --JVM-->  runs on Windows / macOS / Linux
```

## "Write Once, Run Anywhere"

Because Java code is compiled to bytecode instead of to one specific operating system's machine code,
the very same `.class` files can run on any device that has a JVM. You write and compile your program
once on, say, Windows, and the identical bytecode runs on macOS, Linux, or a server in the cloud
without any change.

The platform-specific work is handled by the JVM itself: there is a different JVM for each operating
system, but they all understand the same bytecode. This is what Java's slogan "write once, run
anywhere" means – portability is built into the language's design.
