# Object-Oriented Programming Coursework
### Java Console Systems + JavaFX Desktop Application

This repository contains solutions for the Object-Oriented Programming (OOP) coursework.

The project includes:

• Console-based systems (grading & auction)
• A JavaFX desktop application (student registration form)
• CSV export

# 📁 Project Structure
```
src/
└── oop/
	└── java/
		├── GradingApp.java
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
Main Class: GradingApp.java

Features:

• Accepts student scores (0–100)
• Calculates grade and remark using if–else logic
• Processes multiple students
• Displays grade summary

Run:
```
javac src/oop/java/GradingApp.java
java -cp src/oop/java GradingApp
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

# 📌 Notes
• Runtime files may be generated:

	◦ students.csv
• Only source code is tracked
• Each system runs independently via its own main method

# 📋 Submission Checklist Alignment
✔ Java source files provided via GitHub
✔ GUI screenshots (to be added in report)
✔ Access database template included
✔ Documentation provided (this README)

# Author
Priscilla Akello (apriscilla@vu.sc.ug)
