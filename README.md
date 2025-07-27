# Twitter Data Storage

Is a lightweight replication of a Twitter-like message storage system built in Java. It features user messaging, timelines, follower relationships, hot/cold storage, and schema evolution capabilities, all using a custom database approach with MessagePack and Jackson for serialization.

---

## Project Overview

This project explores how a simplified Twitter-style system can be implemented with support for:

- **Random-access storage** with MsgPack encoding  
- **Custom timeline generation**  
- **User following relationships**  
- **Schema evolution** (adding, renaming, and removing fields)  
- **Hot vs. cold data segregation** for storage optimization  

The application logic is written in Java and structured for command-line execution, simulating core backend functionality of a social microblogging platform.

---

## Getting Started

Follow the instructions below to set up and run the project locally.

### Prerequisites

Ensure you have the following installed:

- **Java Development Kit (JDK)**  
- **Git**  
- (Optional) **Flutter SDK** — if you're integrating a front-end

---

### Installation & Setup

1. **Clone the Repository**
   ```sh
   git clone https://github.com/darrellathaya/low-twitter-mailbox-randomaccess-encoding-owndb.git
   cd low-twitter-mailbox-randomaccess-encoding-owndb
   ```

2. **Download Required Libraries**  
   Create a `lib/` directory and download the required `.jar` dependencies:
   ```sh
   mkdir lib
   cd lib
   wget https://repo1.maven.org/maven2/org/msgpack/msgpack-core/0.9.8/msgpack-core-0.9.8.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.3/jackson-databind-2.15.3.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.3/jackson-core-2.15.3.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.3/jackson-annotations-2.15.3.jar
   cd ..
   ```

3. **Compile Java Source Files**
   ```sh
   javac -cp ".:lib/*" *.java
   ```

---

## Usage Guide

Use the following commands to interact with the system:

### 1. Post a Message
```sh
java -cp ".:lib/*" Post <user> "<message>"
```

### 2. Post a Message with a Custom Date
```sh
java -cp ".:lib/*" PostWithCustomDate <user> "<message>"
```

### 3. Follow a User
```sh
java -cp ".:lib/*" Follow <follower> <followee>
```

### 4. View Timeline for a User
```sh
java -cp ".:lib/*" Timeline <user>
```

### 5. View All Tweets (Hot + Cold Storage)
```sh
java -cp ".:lib/*" ShowAllTweets
```

### 6. Search Tweets by Date Range
```sh
java -cp ".:lib/*" SearchTweetsByDate <start_date> <end_date>
```

---

## Schema Evolution

Modify the data schema dynamically using the following commands:

### a. Add a New Column
```sh
java -cp ".:lib/*" SchemaEvolver add <new_column_name> <default_value>
```

### b. Rename an Existing Column
```sh
java -cp ".:lib/*" SchemaEvolver rename <old_column_name> <new_column_name>
```

### c. Remove a Column
```sh
java -cp ".:lib/*" SchemaEvolver remove <column_name>
```

---

## Project Structure

```plaintext
low-twitter-mailbox-randomaccess-encoding-owndb/
├── lib/                  # External .jar libraries (MsgPack, Jackson)
├── data/
│   ├── hot/              # Recently posted (hot) tweet data
│   └── cold/             # Archived (cold) tweet data
├── users/                # User data and relationships
├── Post.java             # Post a new message
├── PostWithCustomDate.java
├── Follow.java           # Handle user following
├── Timeline.java         # Display a user's timeline
├── SchemaEvolver.java    # Handles schema evolution commands
```

---

## Technologies Used

- **Java** (Core language)  
- **MessagePack** (Efficient binary serialization)  
- **Jackson** (JSON handling and serialization)  
