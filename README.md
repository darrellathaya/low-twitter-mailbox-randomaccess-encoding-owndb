<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a id="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Unlicense License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

This project is a simulation of a simple Java-based tweet storage system, using a multifile approach with `MessagePack` storage format for hot data and `JSON Lines (.jsonl)` for cold data.

The main goals of this project are to demonstrate:
- How to store and read tweets in an efficient format
- Managing hot and cold data based on date
- Implementing schema evolution (add columns, rename, change types, delete columns)
- Simulating basic social media such as timeline and follow

## Built With

* [![Java][Java.io]][Java-url]
* [![MessagePack][MsgPack.io]][MsgPack-url]
* [![Jackson][Jackson.io]][Jackson-url]

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

Clone the repo
   ```sh
   git clone https://github.com/darrellathaya/low-twitter-mailbox-randomaccess-encoding-owndb.git
   ```

## Project Directory
low-twitter-mailbox-randomaccess-encoding-owndb/
├── lib/
│ ├── msgpack-core-0.9.8.jar
│ ├── jackson-databind-2.15.3.jar
│ └── ...
├── data/
│ ├── hot/
│ └── cold/
├── users/
├── Post.java
├── PostWithCustomDate.java
├── Follow.java
├── Timeline.java
├── SchemaEvolver.java


<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

1. Posting a message
   ```sh
   java -cp ".:lib/*" Post <user> "<message>"
   ```
2. Posting yesterday's message
   ```sh
   java -cp ".:lib/*" PostWithCustomDate <user> "<message>"
   ```

3. Follow other users
   ```sh
   java -cp ".:lib/*" Follow user1 user2
   ```
   
4. Viewing user timeline
   ```sh
   java -cp ".:lib/*" Timeline <user>
   ```
   
5. Viewing timeline entirety (Hot + Cold)
   ```sh
   java -cp ".:lib/*" ShowAllTweets
   ```

5. Find tweet based on date range
   ```sh
   java -cp ".:lib/*" SearchTweetsByDate <date_start> <date_end>
   ```

6. Schema Evolution
   a. Adding column
       ```sh
       java -cp ".:lib/*" SchemaEvolver add <new_column_name> <value>
       ```
   b. Renaming Column
       ```sh
       java -cp ".:lib/*" SchemaEvolver rename <old_column_name> <new_column_name>
       ```
    c. Removing Column
       ```sh
       java -cp ".:lib/*" SchemaEvolver remove <column_name>
       ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/darrellathaya/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/darrellathaya/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/darrellathaya/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/darrellathaya/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/darrellathaya/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/darrellathaya/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/darrellathaya/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/darrellathaya/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/darrellathaya/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/darrellathaya/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/darrellathaya
[product-screenshot]: images/screenshot.png
