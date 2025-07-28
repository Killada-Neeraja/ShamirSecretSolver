# Shamir Secret Solver

This Java program finds the constant term `c` of a polynomial using Lagrange Interpolation from encoded points.  
It's a part of the Catalog Placements assignment for simplified Shamir’s Secret Sharing.

## 📁 Files Included

- `ShamirSecretSolver.java` – main code
- `testcase1.json` and `testcase2.json` – input JSON test cases
- `json-simple-1.1.1.jar` – external library for JSON parsing

## ▶️ How to Compile and Run

```bash
javac -cp .;json-simple-1.1.1.jar ShamirSecretSolver.java
java -cp .;json-simple-1.1.1.jar ShamirSecretSolver
