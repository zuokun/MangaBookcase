MangaBookcase
=============

Android Manga Organiser App

To manage and organise Mangas you own for easy viewing and facilitate buying

Made for Smartphones
Tested on Nexus 5

Working on
----------
- Implement to include images captured as each row's background

To Do List
----------
- Thinking of implementing Light Novels... new Tab maybe?
- Implement special books (e.g. One Piece Red, Blue, Yellow etc. )
- Control what data you want to view
- Finding out what other attributes a Manga should have
- Implement UI to add or remove missing books using touch interface
- Implement associations with pictures to put as background for manga
- Implement more settings like changing theme, font size, etc etc (think along the way)

Recent updates
--------------
- Add a favourites boolean to Manga (Done!)
- Implement tabs to support All, Completed, Ongoing, Favourites (Done!)
- Implement a search-filter (Done!)
- Implement alphabetical sorting (Done!)
- Remove auto-focus to search bar at the beginning (Done!)

Features
--------
Organises Mangas you own in a Expandable List View for easy viewing

- Parent List shows Title and Last Book
- Child List shows
  - Missing books (unable to edit yet)
  - Publisher
  - Ongoing or Completed

Add
- Press the + button on the top right to access a new Activity to add a new Manga
  - Adds a new Manga with Title, Last Book, and Status
  - Optionally, add in Publisher and Missing Books (Missing Books not implemented)

Edit
- To edit a Manga, hold your finger on the title you want to edit
  - Edit an existing Manga with Title, Last Book, and Status
  - Optionally, add or remove Publisher and Missing Books

Delete
- To delete a Manga, access the edit screen by long holding a title, then press the X 
  at the top right of the screen
  - Delete an existing manga with confirmation
  - Delete is permanent, no undo implemented

Quick Add (Increase the last book by 1)
- Just press the + button at the right of the title to 
  - Add the new book you just bought behind
  - Easy to update new books!

Filtering the List
- Using the search bar on top, filter the Mangas you want to look for
