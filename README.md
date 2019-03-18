# Passman

Java based utility for doing handling basic encryption tasks.

## What is it?

A library that uses reasonably good practices with respect to encryption.
If you see a problem with what I'm doing or how it works, please let me know.

## Why does it exist?

I wanted a tool that would bake in best practices for password-passed AES
encryption and decryption.

## How does it work?

By default passman uses PBKDF2WithHmacSHA512, with 100,000 iterations to
generate a 256-bit AES key. The encryption process uses AES/GCM/NoPadding with
a randomized initialization vector and supports additional authentication data.

## How is it licensed?
Licensed under the Apache License, Version 2.0

## Building

`mvn clean package`

## Decrypting with CLI

`java -jar passman-cli/target/passman-cli-1.0-SNAPSHOT.jar decrypt example.json`

The password for the example is `test`.

## Encrypting with CLI

`java -jar passman-cli/target/passman-cli-1.0-SNAPSHOT.jar encrypt LICENSE > example2.json`

Enter your own password when prompted.
