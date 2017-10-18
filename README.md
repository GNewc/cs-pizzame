NOTES:
----------
Though the challenge is "functionally" complete, it is not product quality of course. There are places where I have completely skipped putting literal strings into the Resources instead, or perhaps not optimized some facet of the design.

However, I believe that the gist of the challenge is there, it touches on some of the following facets of Android development that I believe you were looking for:
1. AsyncTask was used to demonstrate a reasonable application of that feature
2. I utilized Volley for the RESTful requests... it's the library I've used in my other applications. Though I have experience with other libraries.
3. Location services access ... and understanding all the asynchronous behavior and Permission foo that goes with using it.
4. Intents, and launching other Activities to accomplish goals
5. MVVM ... and how to keep a good separation of business logic and UI
6. Testing, mocking, espresso, ....

I look forward to your response.
-Gary

RUBRIC:
-----------
Coding Challenge - PizzaMe
User Story
 
As a user I want to find the nearest pizza places to me so that I can select the closest one.
 
Acceptance Criteria
 
*   Using the GPS on my device, it should present a list of nearby pizza places
*   The list should show basic information such as Name, Address, City, State, distance(in miles), phone number
*   Selecting an entry from the list shows a detail page for that location.
*   From the detail page I should be able to open it in maps, or call the number.
 
Technical Requirements
 
Rest query : api<https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D%2778759%27%20and%20query%3D%27pizza%27&format=json&diagnostics=true&callback=>
 
(Note: you will need to modify the API to use your device's geo location instead of a hardcoded zip code ref: 
 
Android Technology to be used
 
*   RecyclerView
*   Should be developed in MVVM style and include automated unit tests
 
Additional technology may be used as the developer sees fit, however be prepared to discuss each items inclusion.
