**URL SHORTENER**

The purpose of this system is to take long URLs (often with UTM parameters used for tracking in marketing campaigns) and create shortened versions that can easily be shared. This architecture will maintain URL integrity, click tracking, and ensure privacy.


__**KEY COMPONENTS**__

1) CONTROLLER LAYER

-This api provides endpoints for clients to interact with the system.
-Manages shortening of URLs and retrieval of original URLs.

2) SERVICE LAYER

-Handles the core logic of shortening URLs and decoding the shortened hash.
-Encodes URLs using Sqids to produce a unique hash.
-Tracks click counts and retrieves the original URL from the database.

3) REPOSITORY LAYER

-Manages interaction with the MySQL database using Spring Data JPA.
-Responsible for storing URLs and retrieving them by ID.

4) DATABASE (MYSQL)

-Stores long URLs, click counts, and other related data.
-Each URL is stored with a unique ID, which is encoded into a shortened hash.

5) LIBRARIES

Sqids: This library is used for generating short hashes from numeric IDs. It provides privacy-preserving, short, unique IDs that are hard to guess.



