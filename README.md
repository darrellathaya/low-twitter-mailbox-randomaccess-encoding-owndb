# Twitter Data Storage

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MessagePack](https://img.shields.io/badge/MessagePack-8B4513?style=for-the-badge)
![Jackson](https://img.shields.io/badge/Jackson-3E7EBF?style=for-the-badge)


## Project Description

**Twitter Data Storage** is a simplified replica of Twitter's message backend architecture, built entirely in Java. It simulates a microblogging system that implements core backend mechanics such as message posting, timeline generation, user relationships, and dynamic data schema management.

The system is powered by a **mailbox-based design**, enabling each user to have a dedicated data stream for efficient message handling. It uses **random-access file storage**, **custom binary encoding (MessagePack)**, and a **fully self-managed database system** with no external relational database dependency. Schema changes and data migration are handled directly within the system, showcasing the flexibility of the architecture.



## Features

- **Mailbox architecture** for scalable per-user data streams  
- **Random-access file storage** for efficient read/write operations  
- **Custom binary encoding** using MessagePack for compact data  
- **Timeline generation** based on user relationships  
- **User follow system** with real-time update support  
- **Schema evolution support** (add/rename/remove fields dynamically)  
- **Hot and cold data storage** separation to optimize performance and archival  



## Technologies Used

- **Java** — Core implementation language  
- **MessagePack** — Efficient binary serialization format  
- **Jackson** — JSON data handling and schema evolution support  



## Getting Started

### Prerequisites

Make sure the following software is installed:

- Java Development Kit (JDK)
- Git
- *(Optional)* Flutter SDK — only needed if planning to add a mobile frontend



### Installation and Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/darrellathaya/low-twitter-mailbox-randomaccess-encoding-owndb.git
   cd low-twitter-mailbox-randomaccess-encoding-owndb
   ```

2. **Download Required Libraries**
   Create a `lib/` directory and download the required dependencies:
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



## Usage Guide

### Post a New Message
```bash
java -cp ".:lib/*" Post <username> "<message>"
```

### Post a Message with a Custom Timestamp
```bash
java -cp ".:lib/*" PostWithCustomDate <username> "<message>"
```

### Follow Another User
```bash
java -cp ".:lib/*" Follow <follower> <followee>
```

### View a User's Timeline
```bash
java -cp ".:lib/*" Timeline <username>
```

### View All Tweets (Hot and Cold Storage)
```bash
java -cp ".:lib/*" ShowAllTweets
```

### Search Tweets by Date Range
```bash
java -cp ".:lib/*" SearchTweetsByDate <start_date> <end_date>
```



## Schema Evolution

Support for modifying the structure of stored data at runtime.

### Add a New Column
```bash
java -cp ".:lib/*" SchemaEvolver add <column_name> <default_value>
```

### Rename a Column
```bash
java -cp ".:lib/*" SchemaEvolver rename <old_column_name> <new_column_name>
```

### Remove a Column
```bash
java -cp ".:lib/*" SchemaEvolver remove <column_name>
```



## Project Structure

```
low-twitter-mailbox-randomaccess-encoding-owndb/
├── data/                            # Hot/cold tweet storage directory
├── lib/                             # External .jar libraries (MessagePack, Jackson)
├── users/
│   └── user1/                       # Example user mailbox directory
│
├── Follow.java                      # Follower relationship logic
├── Follow.class
│
├── Post.java                        # Post a tweet with current timestamp
├── Post.class
│
├── PostWithCustomDate.java          # Post a tweet with custom date
├── PostWithCustomDate.class
│
├── Timeline.java                    # Generate a user's timeline
├── Timeline.class
│
├── ShowAllTweets.java               # Print all tweets (hot and cold)
├── ShowAllTweets.class
│
├── SearchTweetsByDate.java          # Query tweets within a date range
├── SearchTweetsByDate.class
│
├── SchemaEvolver.java               # CLI for evolving schema (add, rename, remove fields)
├── SchemaEvolver.class
├── SchemaEvolver$EvolutionFunction.class
│
├── HotColdManager.java              # Manages tweet movement between hot and cold storage
├── HotColdManager.class
│
├── Tweet.java                       # Tweet object structure and serialization
├── Tweet.class
│
├── README.md                        # Project documentation
```



## License

This project is licensed under the MIT License.
