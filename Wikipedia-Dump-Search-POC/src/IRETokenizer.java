
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IRETokenizer{

	private static SortedSet<String> stopKeywordSet = new TreeSet<String>();
	private static String[] stopWordsArray1 = {
		"’tis","’twas","a","able","about","above","abroad","according","accordingly","across","actually","adj","after","afterwards","again","against","ahead","ain’t",
		"all","allow","allows","almost","alone","along","alongside","already","also","although","always","am","amid","amidst","among","amongst","an","and","another",
		"any","anybody","anyhow","anyone","anything","anyway","anyways","anywhere","apart","appear","appreciate",	
		"appropriate",	"are",	"aren’t",	"around",	"as",	"aside",	"ask",	"asking",	"associated",	"at",	"available",	"away",	
		"awfully",	"back",	"backward",	"backwards",	"be",	"became",	"because",	"become",	"becomes",	"becoming",	"been",	"beforehand",	
		"begin",	"behind",	"being",	"believe",	"below",	"beside",	"besides",	"best",	"better",	"between",	"beyond",	"both",	"brief",	
		"but",	"by",	"c’mon",	"c’s",	"came",	"can",	"can’t","cannot","cant","caption","cause","causes","certain","certainly",	
		"changes",	"clearly",	"co",	"co.",	"com",	"come",	"comes",	"concerning",	"consequently",	"consider",	"contain",	"containing",	"contains",	
		"corresponding",	"could",	"could’ve",	"couldn’t",	"course",	"currently",	"dare",	"daren’t",	"de",	"dear",	"definitely",	"described",	
		"despite",	"did",	"didn’t",	"different",	"directly",	"do",	"does",	"doesn’t",	"doing",	"don’t",	"done",	"down",	"downwards",	"during",	
		"each",	"edu",	"eg",	"eight",	"eighty",	"either",	"else",	"elsewhere",	"en",	"end",	"ending",	"entirely",	"especially",	"et",	"etc",	
		"even",	"ever",	"evermore",	"every",	"everybody",	"everyone",	"everything",	"everywhere",	"ex",	"exactly",	"example",	"except",	"fairly",	
		"far",	"few",	"fewer",	"fifth",	"first",	"five",	"followed",	"following",	"follows",	"for",	"forever",	"former",	"formerly",	"forth",	
		"forward",	"found",	"four",	"from",	"further",	"get",	"gets",	"getting",	"given",	"gives",	"go",	"goes",	"going",	"gone",	"got",	"gotten",	
		"greetings",	"had",	"hadn’t",	"half",	"happens",	"hardly",	"has",	"hasn’t",	"have",	"haven’t",	"having",	"he",	"he’d",	"he’ll",	"he’s",	
		"hello",	"help",	"hence",	"her",	"here",	"here’s",	"hereafter",	"hereby",	"herein",	"hereupon",	"hers",	"herself",	"hi",	"him",	"himself",	
		"his",	"hither",	"hopefully",	"how",	"how’d",	"how’ll",	"how’s",	"howbeit",	"however",	"hundred",	"i",	"i’d",	"i’ll",	"i’m",	"i’ve",	
		"ie",	"if",	"ignored",	"immediate",	"in",	"inc",	"inc.",	"indeed",	"indicate",	"indicated",	"indicates",	"inner",	"inside",	"insofar",	
		"instead",	"into",	"inward",	"is",	"isn’t",	"it",	"it’d",	"it’ll",	"it’s",	"its",	"itself",	"just",	"k",	"keep",	"keeps",	"kept",	"know",	
		"known",	"knows",	"la",	"last",	"lately",	"later",	"latter",	"latterly",	"least",	"less",	"let",	"let’s",	"like",	"liked",	"likely",	
		"likewise",	"little",	"look",	"looking",	"looks",	"low",	"lower",	"ltd",	"made",	"mainly",	"make",	"makes",	"many",	"may",	"maybe",	"mayn’t",	
		"me",	"mean",	"meantime",	"meanwhile",	"merely",	"might",	"might’ve",	"mightn’t",	"mine",	"minus",	"miss",	"more",	"moreover",	"most",	"mostly",	
		"mr",	"mrs",	"must",	"must’ve",	"mustn’t",	"my",	"myself",	"name",	"namely",	"nd",	"near",	"nearly",	"necessary",	"need",	"needn’t",	"needs",	
		"neither",	"never",	"neverf",	"neverless",	"nevertheless",	"next",	"nine",	"ninety",	"no",	"nobody",	"non",	"none",	"nonetheless",	"noone",	
		"no-one",	"nor",	"normally",	"not",	"nothing",	"notwithstanding",	"novel",	"now",	"nowhere",	"of",	"off",	"often",	"oh",	"ok",	"okay",	"old",	
		"on",	"once",	"one",	"one’s",	"ones",	"only",	"onto",	"opposite",	"or",	"other",	"others",	"ought",	"oughtn’t",	"our",	"ours",	"ourselves",	
		"out",	"outside",	"over",	"overall",	"own",	"particular",	"particularly",	"past",	"per",	"perhaps",	"placed",	"please",	"plus",	"presumably",	
		"probably",	"provided",	"provides",	"que",	"quite",	"qv",	"rather",	"rd",	"re",	"really",	"reasonably",	"recent",	"recently",	"regarding",	
		"regardless",	"regards",	"relatively",	"right",	"round",	"said",	"same",	"saw",	"say",	"saying",	"says",	"second",	"secondly",	"see",	"seeing",	
		"seem",	"seemed",	"seeming",	"seems",	"seen",	"self",	"sensible",	"sent",	"serious",	"seriously",	"seven",	"several",	"shall",	"shan’t",	
		"she",	"she’d",	"she’ll",	"she’s",	"should",	"should’ve",	"shouldn’t",	"since",	"six",	"so",	"some",	"someday",	"somehow",	"someone",	
		"something",	"sometime",	"sometimes",	"somewhat",	"somewhere",	"soon",	"sorry",	"specified",	"specify",	"specifying",	"still",	
		"sub",	"such",	"sup",	"sure",	"t’s",	"taken",	"taking",	"tell",	"tends",	"th",	"than",	"thank",	"thanks",	"thanx",	"that",	"that’ll",	
		"that’s",	"that’ve",	"thats",	"the",	"their",	"theirs",	"them",	"then",	"thence",	"there",	"there’d",	"there’ll",	"there’re",	"there’s",	
		"there’ve",	"thereafter",	"thereby",	"therefore",	"therein",	"theres",	"thereupon",	"these",	"they",	"they’d",	"they’ll",	"they’re",	
		"they’ve",	"thing",	"things",	"think",	"third",	"thirty",	"this",	"thorough",	"thoroughly",	"those",	"though",	"three",	"through",	
		"throughout",	"thru",	"thus",	"till",	"tis",	"to",	"too",	"took",	"toward",	"towards",	"tried",	"tries",	"truly",	"try",	"trying",	"twas",	"twice",	"two",	"un",	"und",	"under",	"underneath",	"undoing",	"unfortunately",	"unless",	"unlikely",	"until",	"unto",	"up",	"upon",	"upwards",	"us",	"use",	"used",	"useful",	"uses",	"using",	"usually",	"v",	"value",	"various",	"versus",	"very",	"viz",	"vs",	"want",	"wants",	"was",	"wasn’t",	"way",	"we",	"we’d",	"we’ll",	"we’re",	"we’ve",	"welcome",	"well",	"went",	"were",	"weren’t",	"what",	"what’d",	"what’ll",	"what’s",	"what’ve",	"when",	"when’d",	"when’ll",	"when’s",	"whence",	"whenever",	"where",	"where’d",	"where’ll",	"where’s",	"whereafter",	"whereas",	"whereby",	"wherein",	"whereupon",	"wherever",	"whether",	"which",	"whichever",	"while",	"whither",	"who",	"who’d",	"who’ll",	"who’s",	"whoever",	"whole",	"whom",	"whomever",	"whose",	"why",	"why’d",	"why’ll",	"why’s",	"will",	"willing",	"wish",	"with",	"within",	"without",	"won’t",	"wonder",	"would",	"would’ve",	"wouldn’t",	"www",	"yes",	"yet",	"you",	"you’d",
		"you’ll","you’re","you’ve","your","yours","yourself","yourselves",};

	static {
		Collections.addAll(stopKeywordSet, stopWordsArray1);
	}

	private Map<String, Integer> pageTitleTokens;
	private Map<String, Integer> textTokens;
	//private Map<String, Integer> commentTokens;
	private Map<String, Integer> categoriesTokens;
	private Map<String, Integer> infoBoxTokens;
	private Map<String, Integer> referencesTokens;
	private Map<String, Integer> pageLinksTokens;
	private Map<String, Integer> externalLinksTokens;
	//private Map<String, Integer> contributorNamestokens;
	private Set<String> wordList;

	private static String PAGE_TITLE_DELIMITER = " :;[]{}()|,=+-_*&^%$@!<>.?\"\\#`~“‘/";
	private static String CATEGORIES_DELIMITER = " ;,&.:";
	//private static String CONTRIBUTOR_USER_NAME_DELIMITER = " ;,&\"\\#`~“‘.";

	private static String INFOBOX_DELIMITER = " :;[]{}()|',=+-_*&^%$@!<>.?\"\\#`~“‘/";
	private static String REVISION_TEXT_DELIMITER = " :;[]{}()|',=+-_*&^%$@!<>.?\"\\#`~“‘/0123456789";
	//private static String REVISION_COMMENT_DELIMITER = " :;[]{}()|',=+-_*&^%$@!<>.?\"\\#`~“‘/0123456789";
	private static final String EXTERNAL_LINKS_DELIMITER = " :;[]{}()|,=+-'_*&^%$@!<>.?\"\\#`~“‘/01234567890[]{}";
	private static final String REFERENCES_DELIMITER = " ,.':;[]{}()\\*&%$@?#`~“‘\"/";
	private static final String PAGE_LINKS_DELIMITER = " :;[]{}()|',=+-_*&^%$@!<>.?\"\\#`~“‘/";


	public IRETokenizer(){
		this.pageTitleTokens = new HashMap<>();
		this.textTokens = new HashMap<>();
		//this.commentTokens = new HashMap<>();
		//this.contributorNamestokens = new HashMap<>();
		this.categoriesTokens = new HashMap<>();
		this.infoBoxTokens = new HashMap<>();
		this.referencesTokens= new HashMap<>();
		this.pageLinksTokens= new HashMap<>();
		this.externalLinksTokens= new HashMap<>();
		this.wordList = new HashSet<>();
	}

	/**
	 * @return the referencesTokens
	 */
	public Map<String, Integer> getReferencesTokens() {
		return referencesTokens;
	}

	/**
	 * @param referencesTokens the referencesTokens to set
	 */
	public void setReferencesTokens(Map<String, Integer> referencesTokens) {
		this.referencesTokens = referencesTokens;
	}

	/**
	 * @return the pageLinksTokens
	 */
	public Map<String, Integer> getPageLinksTokens() {
		return pageLinksTokens;
	}

	/**
	 * @param pageLinksTokens the pageLinksTokens to set
	 */
	public void setPageLinksTokens(Map<String, Integer> pageLinksTokens) {
		this.pageLinksTokens = pageLinksTokens;
	}

	/**
	 * @return the externalLinksTokens
	 */
	public Map<String, Integer> getExternalLinksTokens() {
		return externalLinksTokens;
	}

	/**
	 * @param externalLinksTokens the externalLinksTokens to set
	 */
	public void setExternalLinksTokens(Map<String, Integer> externalLinksTokens) {
		this.externalLinksTokens = externalLinksTokens;
	}

	/**
	 * @return the infoBoxTokens
	 */
	public Map<String, Integer> getInfoBoxTokens() {
		return infoBoxTokens;
	}

	/**
	 * @param infoBoxTokens the infoBoxTokens to set
	 */
	public void setInfoBoxTokens(Map<String, Integer> infoBoxTokens) {
		this.infoBoxTokens = infoBoxTokens;
	}

	/**
	 * @return the categoriesTokens
	 */
	public Map<String, Integer> getCategoriesTokens() {
		return categoriesTokens;
	}

	/**
	 * @param categoriesTokens the categoriesTokens to set
	 */
	public void setCategoriesTokens(Map<String, Integer> categoriesTokens) {
		this.categoriesTokens = categoriesTokens;
	}

	/**
	 * @return the commentTokens
	 */
	/*public Map<String, Integer> getCommentTokens() {
		return commentTokens;
	}*/

	/**
	 * @param commentTokens the commentTokens to set
	 */
	/*public void setCommentTokens(Map<String, Integer> commentTokens) {
		this.commentTokens = commentTokens;
	}*/

	/**
	 * @return the pageTitleTokens
	 */
	public Map<String, Integer> getPageTitleTokens() {
		return pageTitleTokens;
	}

	/**
	 * @param pageTitleTokens the pageTitleTokens to set
	 */
	public void setPageTitleTokens(Map<String, Integer> pageTitleTokens) {
		this.pageTitleTokens = pageTitleTokens;
	}

	/**
	 * @return the textTokens
	 */
	public Map<String, Integer> getTextTokens() {
		return textTokens;
	}

	/**
	 * @param textTokens the textTokens to set
	 */
	public void setTextTokens(Map<String, Integer> textTokens) {
		this.textTokens = textTokens;
	}

	/**
	 * @return the contributorNamestokens
	 */
	/*public Map<String, Integer> getContributorNamestokens() {
		return contributorNamestokens;
	}*/

	/**
	 * @param contributorNamestokens the contributorNamestokens to set
	 */
	/*public void setContributorNamestokens(
			Map<String, Integer> contributorNamestokens) {
		this.contributorNamestokens = contributorNamestokens;
	}*/

	/**
	 * @return the wordList
	 */
	public Set<String> getWordList() {
		return wordList;
	}

	/**
	 * @param wordList the wordList to set
	 */
	public void setWordList(Set<String> wordList) {
		this.wordList = wordList;
	}

	private String getCategories(String text){
		StringBuilder tempCategories = new StringBuilder();
		Pattern pattern = Pattern.compile("\\[\\[Category:(.*?)\\]\\]", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()){
			String [] temp = matcher.group(1).split("\\|");
			tempCategories.append(temp[0]).append(" ");
		}
		return tempCategories.toString();
	}

	private String getInfoBox(String text){
		StringBuilder tempInfoBox = new StringBuilder();
		Pattern pattern = Pattern.compile("(?s)\\{\\{Infobox(.*?)\n\\}\\}");
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()){
			String [] temp = matcher.group(1).split("\\|");
			for (int i = 0; i< temp.length; i++){
				tempInfoBox.append(temp[i].split("=").length > 1 ? temp[i].split("=")[1].trim():"").append(" ");
			}
		}
		return tempInfoBox.toString();
	}

	private String getExternalLinks(String txt){
		StringBuilder tempExternalLinks = new StringBuilder();
		Pattern pattern = Pattern.compile("==External links==(.*]\n)[^*]",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(txt);
		while (matcher.find()){
			String [] temp = matcher.group(0).split("\\*");
			for (int i = 1; i< temp.length; i++){
				tempExternalLinks.append(temp[i]).append(" ");
			}
		}
		return tempExternalLinks.toString();

	}


	private String getPageLinks(String text){
		StringBuilder tempPageLinks = new StringBuilder();
		Pattern catPattern = Pattern.compile("\\[\\[(.*?)\\]\\]", Pattern.MULTILINE);
		Matcher matcher = catPattern.matcher(text);
		while (matcher.find()){
			String [] temp = matcher.group(1).split("\\|");
			String link = (temp == null || temp.length == 0) ? "" : temp[0];
			if (!link.contains(":\\")){
				tempPageLinks.append(link).append(" ");
			}
		}
		return tempPageLinks.toString();
	}

	private String getReferences(String text){
		StringBuilder tempReferences = new StringBuilder();
		Pattern pattern = Pattern.compile("==References==\n<.*\\>(.*\n)[^*]",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()){
			String [] temp = matcher.group(1).split("*");
			tempReferences.append(temp[1]).append(" ");
		}
		return tempReferences.toString();
	}

	private boolean isNumeric(String string) {
		return string.matches("^[-+]?\\d+(\\.\\d+)?[a-zA-Z]{0,}$");
	}

	//Tokenizing the text and removing the stop words and stemming the words
	public void tokenize(String text, String section, Stemmer stemmer) throws IOException{
		if (null !=text && !"".equals(text)){
			// Sanitizing the text
			text = text.replaceAll("&amp;", "&").replaceAll("&nbsp;", " ").replaceAll("&gt;", ">").replaceAll("&lt;", "<"); 
			if(IreSaxHandler.REVISION_TEXT.equals(section)){

				// Fetching the categories
				String caregoriesText = getCategories(text);
				// Creating the tokens for category
				createTokens(caregoriesText,IreSaxHandler.CATEGORIES,stemmer,CATEGORIES_DELIMITER);

				// Fetching the infobox information
				String infoBoxText = getInfoBox(text);
				// Creating the tokens for category
				createTokens(infoBoxText,IreSaxHandler.INFOBOX,stemmer,INFOBOX_DELIMITER);

				// Fetching the categories
				String pageLinksText = getPageLinks(text);
				// Creating the tokens for category
				createTokens(pageLinksText,IreSaxHandler.PAGE_LINKS,stemmer,PAGE_LINKS_DELIMITER);

				// Fetching the categories
				String referencesText = getReferences(text);
				// Creating the tokens for category
				createTokens(referencesText,IreSaxHandler.REFERENCES,stemmer,REFERENCES_DELIMITER);

				// Fetching the categories
				String externalLinksText = getExternalLinks(text);
				// Creating the tokens for category
				createTokens(externalLinksText,IreSaxHandler.EXTERNAL_LINKS,stemmer,EXTERNAL_LINKS_DELIMITER);

				// Sanitizing the text from text body
				text = text.replaceAll("<ref>.*?</ref>", " ").replaceAll("</?.*?>", " ");
				text = text.replaceAll("\\{\\{.*?\\}\\}", " ").replaceAll("\\[\\[.*?:.*?\\]\\]", " ").replaceAll("\\[\\[(.*?)\\]\\]", "$1");
				text = text.replaceAll("\\s(.*?)\\|(\\w+\\s)", " $2").replaceAll("\\[.*?\\]", " ").replaceAll("\\'+", "");
				text = text.replaceAll("(?s)\\{\\{Infobox(.*?)\n\\}\\}"," ");
				// Creating the tokens for text body
				createTokens(text,section,stemmer,REVISION_TEXT_DELIMITER);
			}/*else if(IreSaxHandler.REVISION_COMMENT.equals(section)){
				// Sanitizing the text
				text = text.replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("<ref>.*?</ref>", " ").replaceAll("</?.*?>", " ");
				text = text.replaceAll("\\{\\{.*?\\}\\}", " ").replaceAll("\\[\\[.*?:.*?\\]\\]", " ").replaceAll("\\[\\[(.*?)\\]\\]", "$1");
				text = text.replaceAll("\\s(.*?)\\|(\\w+\\s)", " $2").replaceAll("\\[.*?\\]", " ").replaceAll("\\'+", "");
				createTokens(text,section,stemmer,REVISION_COMMENT_DELIMITER);
			}*/else if (IreSaxHandler.PAGE_TITLE_SECTION.equals(section)){
				createTokens(text,section,stemmer,PAGE_TITLE_DELIMITER);
			}/*else if (IreSaxHandler.CONTRIBUTOR_USER_NAME.equals(section)){
				createTokens(text,section,stemmer,CONTRIBUTOR_USER_NAME_DELIMITER);
			}*/
		}
	}

	private void createTokens(String text, String section, Stemmer stemmer, String delimiter) throws IOException{
		StringTokenizer st = new StringTokenizer(text, delimiter);
		while (st != null && st.hasMoreTokens()){
			//boolean isTrue = false;
			String token = st.nextToken().toLowerCase().trim();
			/*if (!stopKeywordSet.contains(token) && token.length() > 1){
				isTrue = true;
				if (isNumeric(token) && (IreSaxHandler.REVISION_TEXT.equals(section) || IreSaxHandler.REVISION_COMMENT.equals(section) )){
					isTrue = false;
				}
			}
			if (isTrue){*/
			if (!stopKeywordSet.contains(token) && token.length() > 1){
				// Stemming the token
				//String token1=token;
				token = stemmer.stripAffixes(token);
				
				// Now we have stemmed tokens.....
				if (IreSaxHandler.PAGE_TITLE_SECTION.equals(section)){
					Integer oldFreq = pageTitleTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					pageTitleTokens.put(token, newFreq);
					wordList.add(token);
				}else if(IreSaxHandler.REVISION_TEXT.equals(section)){
					Integer oldFreq = textTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					textTokens.put(token, newFreq);
					wordList.add(token);
				}/*else if(IreSaxHandler.REVISION_COMMENT.equals(section)){
					Integer oldFreq = textTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					commentTokens.put(token, newFreq);
				}else if(IreSaxHandler.CONTRIBUTOR_USER_NAME.equals(section)){
					Integer oldFreq = contributorNamestokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					contributorNamestokens.put(token, newFreq);
				}*/else if(IreSaxHandler.CATEGORIES.equals(section)){
					Integer oldFreq = categoriesTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					categoriesTokens.put(token, newFreq);
					wordList.add(token);
				}else if(IreSaxHandler.INFOBOX.equals(section)){
					Integer oldFreq = infoBoxTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					infoBoxTokens.put(token, newFreq);
					wordList.add(token);
				}else if(IreSaxHandler.PAGE_LINKS.equals(section)){
					Integer oldFreq = pageLinksTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					pageLinksTokens.put(token, newFreq);
					wordList.add(token);
				}else if(IreSaxHandler.EXTERNAL_LINKS.equals(section)){
					Integer oldFreq = externalLinksTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					externalLinksTokens.put(token, newFreq);
					wordList.add(token);
				}else if(IreSaxHandler.REFERENCES.equals(section)){
					Integer oldFreq = referencesTokens.get(token);
					int newFreq = oldFreq == null ? 1 : oldFreq+1;
					referencesTokens.put(token, newFreq);
					wordList.add(token);
				}
							
			}
			//long start2 = System.currentTimeMillis();
			//System.out.println((start2 - start1)  + " ms !!!!");
		}
	}

}
