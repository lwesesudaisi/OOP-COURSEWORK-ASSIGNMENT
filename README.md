# Object-Oriented Programming Coursework
### Java Console Systems + JavaFX Desktop Application

This repository contains solutions for the Object-Oriented Programming (OOP) coursework.

The project includes:

• Console-based systems (grading & auction)
• Grading system variants (single student, five students, enhanced summary)
• A JavaFX desktop application (student registration form)
• CSV export
• MS Access database template (.accdb)

# 📁 Project Structure
```
src/
└── oop/
	└── java/
		├── GradingApp.java
		├── GradingSystemSingle.java
		├── ModifiedGradingSystemFiveStudents.java
		├── GradingSystemEnhanced.java
		├── AuctionApp.java
		├── StudentRegistrationApp.java
		└── firstName.java

databases/
└── students_template.accdb

nbproject/

build.xml
manifest.mf
.gitignore
test.js
README.md
```

# ✅ Question 1 – Console Applications

## Part A – Grading System
Main Class: GradingSystemSingle.java

Features:

• Accepts student score (0–100)
• Calculates grade and remark using if–else logic
• Displays score, grade, and remark

Run:
```
javac src/oop/java/GradingSystemSingle.java
java -cp src/oop/java GradingSystemSingle
```

### Modified (Five Students)
Main Class: ModifiedGradingSystemFiveStudents.java

Features:

• Accepts and processes scores for five students using a while loop
• Displays a summary count for each grade (1–9)

Run:
```
javac src/oop/java/ModifiedGradingSystemFiveStudents.java
java -cp src/oop/java ModifiedGradingSystemFiveStudents
```

### Enhanced Grading System
Main Class: GradingSystemEnhanced.java

Features:

• Shows grade distribution with percentage summary

Run:
```
javac src/oop/java/GradingSystemEnhanced.java
java -cp src/oop/java GradingSystemEnhanced
```

## Part B – Auction System
Main Class: AuctionApp.java

Features:

• Accepts 3 bidders
• Selects highest bidder
• Tracks deposits and expenses
• Calculates profit or loss

Run:
```
javac src/oop/java/AuctionApp.java
java -cp src/oop/java AuctionApp
```

# ✅ Question 2 – Desktop Application (JavaFX)

## Student Registration Form
Main Class: StudentRegistrationApp.java

Features:

• Form validation
• Email & password checks
• Age calculation
• Gender & department selection
• Generates student ID
• Saves to CSV

Run (IDE recommended for JavaFX):
```
javac src/oop/java/StudentRegistrationApp.java
java -cp src/oop/java StudentRegistrationApp
```

# ⚙ Requirements
• JDK 17 (or any compatible JDK)
• JavaFX SDK configured in your IDE/runtime
• (Optional) MS Access to view the database

# 📌 Notes
• Runtime files may be generated:

	◦ students.csv
• Only source code is tracked
• Each system runs independently via its own main method

# 🗄 Database Setup (Important)
This project provides a template MS Access database:

• databases/students_template.accdb

If needed, copy it to the project root and rename to students.accdb.

The app can connect using a relative path:
```
jdbc:ucanaccess://./students.accdb
```

# 📋 Submission Checklist Alignment
✔ Java source files provided via GitHub
✔ GUI screenshots (to be added in report)
✔ Access database template included
✔ Documentation provided (this README)

# Author
Lwese Sudaisi (lwesesudaisi@vu.sc.ug)
