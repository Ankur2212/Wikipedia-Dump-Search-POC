
Total Time in parsing and indexing the xml file: 2.91 min

File Size of index : 29.1 MB

Sections which I am considering and calculating the frequency (for each section) for every page :
	1.	Category
	2.	Info box
	3.	References
	4.	*** Page Links ****
	5.	External Links
	6.	Page Title
	7.	Body Text
	
Stemming: Used "Paise algorithm" for stemming the tokens

Structure of Index file is:
	token::pageid:section<Frequency>:section2<frequency>::pageid2:section<Frequency>:section2<frequency>
		
Tokens in index file are sorted to retrieve in a fast manner.9May be we can use binary search algo for this)

No External jar file has been used.


//index compression 
//rank functions
//secondary index
//Use B+ tree 