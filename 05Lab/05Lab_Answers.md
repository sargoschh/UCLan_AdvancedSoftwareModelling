# Week 6 Lab OCL & UML

### Scenario

You are designing a simplified Online Course Registration System for a university. The system allows Students to register for Courses, each managed by an Instructor. A Student can enrol in multiple courses, but cannot register for a course that is already full.

## Part 1: Structural Modelling

### **Task 1**: Create a UML Class Diagram 

Use Visual Paradigm Online, PlantUML, or Draw.io.

**Include the following classes:**

- Student

- Course

- Instructor

- Registration

**Relationships:**

- A Student registers for one or more Courses.

- A Course is taught by exactly one Instructor.

- A Course has many Registrations.

- Each Registration links one Student to one Course.

**Attributes (suggested):**

- Student: id, name, email

- Course: code, title, capacity

- Instructor: id, name

- Registration: date, status

- Deliverable: Export or screenshot your class diagram and label all multiplicities (1..\*, 0..1, etc.).

![UML_ClassDiagram](../out/05Lab/UML_ClassDiagram/ClassDiagram.png)

## Part 2: Behavioural Modelling

### Task 2: Sequence Diagram 

Model the following use case:

A student registers for a course.

**Include lifelines for:**

- Student

- Registration System

- Course

**Show messages such as:**

- selectCourse()

- checkAvailability()

- createRegistration()

- confirmRegistration()

![SequenceDiagram](../out/05Lab/SequenceDiagram/SequenceDiagram.png)

### Task 3: Activity Diagram 

Create an activity diagram for the "Course Registration" process:

- Student logs in

- Selects a course

- System checks capacity

- If full → show "Course Full" message

- Else → register student

- Confirmation displayed

- Deliverable: Two diagrams (sequence and activity) exported or inserted
in your lab report.

![ActivityDiagram](../out/05Lab/ActivityDiagram/ActivityDiagram.png)

### Part 3: OCL in the Modelling Lifecycle

Now refine your Class Diagram with OCL constraints to enforce business
rules.

### Task 4: Add OCL Constraints 

Write at least three OCL expressions.

**Examples:**

- Invariant:

    - context Course inv: self.capacity \> 0

- Invariant (Registration rule):

    - context Course inv: self.registrations-\>size() \<= self.capacity

- Precondition (Enroll operation):

    - context Student::register(c: Course)

    - pre: c.registrations-\>size() \< c.capacity

    - post: c.registrations-\>includes(self)

- Deliverable: List your OCL rules below your diagram, clearly labelled

### Task 5: Design an ER diagram

Create an ER diagram on slide 9.

![ER_Diagram](../out/05Lab/ERDiagram/ERDiagram.png)
