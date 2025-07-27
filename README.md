# Twitter Data Storage

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MessagePack](https://img.shields.io/badge/MessagePack-8B4513?style=for-the-badge)
![Jackson](https://img.shields.io/badge/Jackson-3E7EBF?style=for-the-badge)

## Project Description

**Twitter Data Storage** is a lightweight simulation of a Twitter-like backend system built in Java. It implements essential features of a microblogging platform, including message posting, user timelines, follower relationships, hot/cold storage separation, and schema evolution — all handled through a custom storage layer using MessagePack and Jackson.

This project is intended for backend architecture experimentation, with a focus on custom binary storage formats and dynamic schema handling.

---

## Features

- **Random-access storage** using MessagePack serialization  
- **Custom timeline generation** per user  
- **User following and follower mapping**  
- **Schema evolution**: add, rename, and remove fields dynamically  
- **Hot and cold storage separation** for performance optimization  

---

## Technologies Used

- **Java** — Core language used to build the CLI application  
- **MessagePack** — Compact binary serialization for tweet storage  
- **Jackson** — Handles object mapping and JSON processing  

---

## Getting Started

### Prerequisites

Make sure the following tools are installed on your machine:

- Java Development Kit (JDK)
- Git
- *(Optional)* Flutter SDK — required only if integrating with a mobile frontend in the future

---

### Installation and Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/darrellathaya/low-twitter-mailbox-randomaccess-encoding-owndb.git
   cd low-twitter-mailbox-randomaccess-encoding-owndb
   ```

2. **Download Dependencies**
   Create a `lib/` folder and fetch the required libraries:
   ```bash
   mkdir lib && cd lib

   wget https://repo1.maven.org/maven2/org/msgpack/msgpack-core/0.9.8/msgpack-core-0.9.8.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.3/jackson-databind-2.15.3.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.3/jackson-core-2.15.3.jar
   wget https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.3/jackson-annotations-2.15.3.jar

   cd ..
   ```

3. **Compile the Source Code**
   ```bash
   javac -cp ".:lib/*" *.java
   ```

---

## Usage Guide

Run the following commands to interact with the system:

### Post a New Message
```bash
java -cp ".:lib/*" Post <username> "<message>"
```

### Post with a Custom Timestamp
```bash
java -cp ".:lib/*" PostWithCustomDate <username> "<message>"
```

### Follow a User
```bash
java -cp ".:lib/*" Follow <follower> <followee>
```

### View Timeline
```bash
java -cp ".:lib/*" Timeline <username>
```

### Show All Tweets (Hot + Cold Storage)
```bash
java -cp ".:lib/*" ShowAllTweets
```

### Search Tweets by Date Range
```bash
java -cp ".:lib/*" SearchTweetsByDate <start_date> <end_date>
```

---

## Schema Evolution Commands

The system supports runtime schema changes without breaking compatibility.

### Add a Column
```bash
java -cp ".:lib/*" SchemaEvolver add <column_name> <default_value>
```

### Rename a Column
```bash
java -cp ".:lib/*" SchemaEvolver rename <old_name> <new_name>
```

### Remove a Column
```bash
java -cp ".:lib/*" SchemaEvolver remove <column_name>
```

---

## Project Structure

```
low-twitter-mailbox-randomaccess-encoding-owndb/
├── lib/                    # External libraries (.jar files)
├── data/
│   ├── hot/                # Recently posted (hot) tweet data
│   └── cold/               # Archived (cold) tweet data
├── users/                  # User information and follower data
├── Post.java               # CLI for posting new messages
├── PostWithCustomDate.java
├── Follow.java             # Handles follow relationships
├── Timeline.java           # Generates a user's timeline
├── ShowAllTweets.java      # Aggregates all stored tweets
├── SearchTweetsByDate.java # Filters tweets by date
├── SchemaEvolver.java      # Handles schema evolution actions
```

---

## License

This project is open-source and available under the MIT License.
