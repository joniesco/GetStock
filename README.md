# GetStock
GetStock is a free stock screener.

# Description
The project uses an external API to perform the search query and rest of the API calls.

# Usage
New users must first register using the register screen and submit their details.
<br/>
<img src="https://i.ibb.co/c1nnrQJ/7f2e5b06-73ff-433d-8f69-557469a9a295.jpg" width="200" />

if a user is already registered they must enter their credentials.
<br/>
<img src="https://i.ibb.co/09qg7M2/72db9681-349f-4208-a5bc-ae00fd7b0480.jpg" width="200" />

once logged in you are brought to a search screen , there you can enter your search query.
<br/>
<img src="https://i.ibb.co/jG6xsPv/19a0646f-96aa-4c8d-b676-534643f739fd.jpg" width="200" />
 
The search will display all possible results, the user must simply click the required result :
<br/>
<img src="https://i.ibb.co/Cbf32vp/b3c01849-0ddf-43c0-9ae3-98ddaba54261.jpg" width="200" />

then they will be brought to a screen containing all the necessary details.
<br/>
<img src="https://i.ibb.co/42jxQzZ/051b652f-5658-453a-99c8-58a64d85b464.jpg" width="200" />

# Project structure
| ClassName | Description  | Extends/Implements  |
| :---:   | :-: | :-: |
| Main activity | Entrance screen for login/register | extends AppCompatActivity implements View.OnClickListener |
| Register | Class for register a user with name,age,mail and password | extends AppCompatActivity implements View.OnClickListener |
| User | This Class represents a user (name,age and email) | -------- |
| Profile activity | This Class represents a home page for user where he can search for a stock | extends AppCompatActivity implements View.OnClickListener |
| CustomaAdapter| Making a list of stock results to be dinamic | extends RecyclerView.Adapter<CustomAdapter.ViewHolder> |
| SearchResult| A class to display the results of the user's search | extends AppCompatActivity |
| widgetClick| A class to display some details on a spesific stock from the search results | extends AppCompatActivity implements VolleyCallback |





