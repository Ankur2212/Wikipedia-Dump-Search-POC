/* 	author:   	Christopher O'Neill
	date:		Sep 2000
   	comments: 	The Paice/Husk Stemmer Translated from Pascal*/

import java.util.*;




/****************************************
 *Class:		Paice			*
 *					*
 *Purpose:	Stemming Algorithm	*
 ****************************************/
public class Stemmer
{

	private Vector<String> ruleTable; 	//array of rules
	private int[] ruleIndex; 	// index to above
	private boolean preStrip = false;
	//private	String filerules;

	/********************************
	 *Method:	Paice		*
	 *				*
	 *Purpose:	init		*
	 ********************************/
	/*public Stemmer (String rules, String pre)
	{
		ruleTable = new Vector();
		ruleIndex = new int[26];
		preStrip = false;
		//filerules = rules;
		if (pre.equals("/p")){
			preStrip=true;
		}
		//ReadRules(filerules);
	}*/
	
	/**
	 * @return the ruleTable
	 */
	public Vector<String> getRuleTable() {
		return ruleTable;
	}

	/**
	 * @param ruleTable the ruleTable to set
	 */
	public void setRuleTable(Vector<String> ruleTable) {
		this.ruleTable = ruleTable;
	}

	/**
	 * @return the ruleIndex
	 */
	public int[] getRuleIndex() {
		return ruleIndex;
	}

	/**
	 * @param ruleIndex the ruleIndex to set
	 */
	public void setRuleIndex(int[] ruleIndex) {
		this.ruleIndex = ruleIndex;
	}

	/****************************************************************
	 *Method:		FirstVowel				*
	 *Returns:		int 					*
	 *Recievs:		String word, int last			*
	 *Purpose:		checks lower-case word for position of  *
	 *			the first vowel				*
	 ****************************************************************/
	private int FirstVowel (String word, int last)
	{
		int i=0;
		if ((i<last) && (!(vowel(word.charAt(i),'a'))))
		{
			i++;
		}
		if (i!=0)
		{
			while ((i<last) && (!(vowel(word.charAt(i),word.charAt(i-1)))))
			{
				i++;
			}
		}
		if (i < last)
		{
			return i;
		}
		return last;
	}

	/************************************************************************
	 *Method:		stripSuffixes					*
	 *Returns:		String 						*
	 *Recievs:		String word					*
	 *Purpose:		strips suffix off word and returns stem using 	*
	 *				paice stemming algorithm		*
	 ************************************************************************/
	private String stripSuffixes (String word)
	{
		//integer variables 1 is positive, 0 undecided, -1 negative equiverlent of pun vars positive undecided negative
		int ruleok = 0;
		int Continue = 0;
		//integer varables
		int pll= 0;	//position of last letter
		int xl;		//counter for number of chars to be replaced and length of stemmed word if rule was aplied
		int pfv;	//position of first vowel
		int prt;	//pointer into rule table
		int ir;		//index of rule
		int iw;		//index of word
		//char variables
		char ll;	// last letter
		//String variables eqivalent of tenchar variables
		String rule ="";	//variable holding the current rule
		String stem = "";   // string holding the word as it is being stemmed this is returned as a stemmed word.
		//boolean variable
		boolean intact = true; //intact if the word has not yet been stemmed to determin a requirement of some stemming rules

		//set stem = to word
		stem=Clean(word.toLowerCase());

		// set the position of pll to the last letter in the string
		pll=0;
		//move through the word to find the position of the last letter before a non letter char
		while ((pll+1 <stem.length()) &&((stem.charAt(pll+1) >= 'a') && (stem.charAt(pll+1)  <='z')))
		{
			pll++;
		}

		if (pll < 1)
		{
			Continue = -1;
		}
		//find the position of the first vowel
		pfv = FirstVowel(stem, pll);

		iw=stem.length()-1;

		//repeat until continue == negative ie. -1
		while (Continue != -1)
		{
			Continue =0;		//SEEK RULE FOR A NEW FINAL LETTER
			ll=stem.charAt(pll);	//last letter

			//Check to see if there are any possible rules for stemming
			if ((ll >= 'a') && (ll <= 'z'))
			{
				prt=ruleIndex[charCode(ll)];	 //pointer into rule-table
			}
			else
			{
				prt=-1;//0 is a vaild rule
			}
			if (prt==-1)
			{
				Continue=-1; //no rule available
			}

			if (Continue == 0)
				//THERE IS A POSSIBLE RULE (OR RULES) : SEE IF ONE WORKS
			{
				rule=(String) ruleTable.elementAt(prt);	// Take first rule
				while(Continue == 0)
				{
					ruleok=0;
					if (rule.charAt(0)!= ll)
					{
						//rule-letter changes
						Continue=-1;
						ruleok=-1;
					}
					ir=1;				//index of rule: 2nd character
					iw=pll-1;			//index of word: next-last letter

					//repeat untill the rule is not undecided find a rule that is acceptable
					while  (ruleok==0)
					{
						if ( (rule.charAt(ir) >='0') && (rule.charAt(ir) <= '9'))  //rule fully matched
						{
							ruleok=1;
						}
						else if (rule.charAt(ir) == '*')
						{
							//match only if word intact
							if (intact)
							{
								ir=ir+1;	       // move forwards along rule
								ruleok = 1;
							}
							else
							{
								ruleok=-1;
							}
						}
						else if (rule.charAt(ir) !=stem.charAt(iw))
						{
							// mismatch of letters
							ruleok=  -1;
						}
						else if (iw<=pfv)
						{
							//insufficient stem remains
							ruleok=-1;
						}
						else
						{
							//  move on to compare next pair of letters
							ir=ir+1;	      // move forwards along rule
							iw=iw-1;	      // move backwards along word
						}
					}

					//if the rule that has just been checked is valid
					if (ruleok==1)
					{
						//  CHECK ACCEPTABILITY CONDITION FOR PROPOSED RULE
						xl=0;		//count any replacement letters
						while (! ((rule.charAt(ir+xl+1) >= '.') && (rule.charAt(ir+xl+1) <= '>')) )
						{
							xl++;
						}
						xl=pll+xl+48-((int)(rule.charAt(ir)));
						// position of last letter if rule used
						if (pfv==0)
						{
							//if word starts with vowel...
							if (xl<1)
							{
								// ...minimal stem is 2 letters
								ruleok=-1;
							}
							else
							{
								//ruleok=1; as ruleok must alread be positive to reach this stage
							}
						}
						//if word start swith consonant...
						else if ((xl<2) | (xl<pfv))
						{
							ruleok=-1;
							// ...minimal stem is 3 letters...
							// ...including one or more vowel
						}
						else
						{
							//ruleok=1; as ruleok must alread be positive to reach this stage
						}
					}
					// if using the rule passes the assertion tests
					if (ruleok==1)
					{
						//  APPLY THE MATCHING RULE
						intact = false;
						// move end of word marker to position...
						// ... given by the numeral.
						pll=pll+48-((int) (rule.charAt(ir)));
						ir++;
						stem=stem.substring(0,(pll+1));


						// append any letters following numeral to the word
						while ((ir < rule.length()) && (('a' <= rule.charAt(ir) ) && ( rule.charAt(ir) <= 'z')))
						{
							stem+=rule.charAt(ir);
							ir++;
							pll++;
						}

						//if rule ends with '.' then terminate
						if ((rule.charAt(ir))=='.')
						{
							Continue=-1;
						}
						else
						{
							//if rule ends with '>' then Continue
							Continue=1;
						}
					}
					else
					{
						//if rule did not match then look for another
						prt=prt+1;		// move to next rule in RULETABLE
						rule=(String) ruleTable.elementAt(prt);
						if (rule.charAt(0)!= ll)
						{
							//rule-letter changes
							Continue=-1;
						}
					}
				}
			}
		}
		return stem;
	}

	/****************************************************************
	 *Method:		vowel					*
	 *Returns:		boolean					*
	 *Recievs:		char ch, char prev			*
	 *Purpose:		determin whether ch is a vowel or not 	*
	 *			uses prev determination when ch == y	*
	 ****************************************************************/
	private boolean vowel( char ch, char prev )
	{
		switch ( ch )
		{
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
			return true;
		case 'y':
		{
			switch ( prev )
			{
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
				return false;
			default:
				return true;
			}
		}
		default :
			return false;
		}
	}

	/****************************************************************
	 *Method:		charCode				*
	 *Returns:		int					*
	 *Recievs:		char ch					*
	 *Purpose:		returns the relavent array index for  	*
	 *			specified char 'a' to 'z'		*
	 ****************************************************************/
	private int charCode(char ch)
	{
		return ((int) ch)-97;
	}

	/********************************************************
	 *Method:		stripPrefixes			*
	 *Returns:		String				*
	 *Recievs:		String str			*
	 *Purpose:		removes prefixes so that suffix	*
	 *			removal can comence		*
	 ********************************************************/
	private String stripPrefixes ( String str)
	{
		String[] prefixes = { 	"kilo"  ,
				"micro" ,
				"milli" ,
				"intra" ,
				"ultra" ,
				"mega"  ,
				"nano"  ,
				"pico"  ,
		"pseudo" };

		int last = prefixes.length;

		for ( int i=0 ; i<last; i++ )
		{
			if ((str.startsWith(prefixes[i]))&&(str.length() > prefixes[i].length()))
			{
				str= str.substring(prefixes[i].length());
				return str;
			}
		}
		return str;
	}

	/********************************************************
	 *Method:		Clean				*
	 *Returns:		String				*
	 *Recievs:		String str			*
	 *Purpose:		remove all non letter or digit  *
	 *			characters from srt and return	*
	 ********************************************************/
	private String Clean( String str )
	{
		int last = str.length();
		String temp = "";
		for ( int i=0; i < last; i++ )
		{
			if ((str.charAt(i)>='a')&(str.charAt(i)<='z') )
			{
				temp += str.charAt(i);
			}
		}
		return temp;
	} //clean


	/*************************** PUBLIC METHODS ****************/
	/****************************************************************
	 *Method:		stripAffixes				*
	 *Returns:		String					*
	 *Recievs:		String str				*
	 *Purpose:		prepares string and calls stripPrefixes *
	 *			and stripSuffixes	 		*
	 ****************************************************************/
	public String stripAffixes( String str)
	{
		//str = str.toLowerCase(); //change all letters in the input to lowercase
		//str = Clean(str); // remove all chars from string that are not a letter or a digit (why digit?)
		if ((str.length() > 3)&&(preStrip)) //if str's length is greater than 2 then remove prefixes
		{
			str = stripPrefixes(str);
		}
		if (str.length()>3 ) // if str is not null remove suffix
		{
			str = stripSuffixes(str);
		}
		return str;
	} //stripAffixes


} //class

