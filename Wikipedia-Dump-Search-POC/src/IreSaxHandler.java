import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

/**

 */
public class IreSaxHandler extends DefaultHandler {
	
	// Section mapper
	public final static String PAGE_TITLE_SECTION = "t";
	public final static String REVISION_TEXT = "b";
	//public final static String REVISION_COMMENT = "c";
	//public final static String CONTRIBUTOR_USER_NAME = "d";
	public final static String REFERENCES = "r";
	public final static String EXTERNAL_LINKS = "e";
	public final static String INFOBOX = "i";
	public final static String CATEGORIES = "c";
	public static final String PAGE_LINKS = "p";
	
	// Tags those have either one child more children tags
	public final static String PAGE_TAG = "page";
	public final static String REVISION_TAG = "revision";
	//public final static String CONTRIBUTOR_TAG = "contributor";
	// Child Tags 
	public final static String ID_TAG = "id";
	public final static String TITLE_TAG = "title";
	//public final static String USER_NAME_TAG = "username";
	//public final static String COMMENT_TAG = "comment";
	public final static String TEXT_TAG = "text";
	
	
	// Varibales used with in class
	private String pageId= "";
	private long totalPages=0;
	private Stack<String> elementStack = null;
	private Stack<Object> objectStack  = null;
	private Stemmer stemmer = null;
	private IRETokenizer iRETokenizer = null;
	private IREIndexer indexer = null;

	public IreSaxHandler(){
		stemmer =new Stemmer();
		iRETokenizer = new IRETokenizer();
		indexer = new IREIndexer();
		elementStack = new Stack<String>();
		objectStack  = new Stack<Object>();
		stemmer = readRules("data/stemRules.txt");
	}

	/**
	 * @return the iRETokenizer
	 */
	public IRETokenizer getiRETokenizer() {
		return iRETokenizer;
	}


	/**
	 * @param iRETokenizer the iRETokenizer to set
	 */
	public void setiRETokenizer(IRETokenizer iRETokenizer) {
		this.iRETokenizer = iRETokenizer;
	}


	/**
	 * @return the indexer
	 */
	public IREIndexer getIndexer() {
		return indexer;
	}


	/**
	 * @param indexer the indexer to set
	 */
	public void setIndexer(IREIndexer indexer) {
		this.indexer = indexer;
	}
	
	/**
	 * @return the totalPages
	 */
	public long getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}


	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.elementStack.push(qName);
		if(PAGE_TAG.equals(qName)){
			this.objectStack.push(new Page());
		} else if(REVISION_TAG.equals(qName)){
			this.objectStack.push(new Revision());
		}/*else if(CONTRIBUTOR_TAG.equals(qName)){
			this.objectStack.push(new Contributor());
		}*/
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		this.elementStack.pop();
		//if(PAGE_TAG.equals(qName) || REVISION_TAG.equals(qName) || CONTRIBUTOR_TAG.equals(qName)){
		if(PAGE_TAG.equals(qName) || REVISION_TAG.equals(qName)){
			Object object = this.objectStack.pop();
			if(PAGE_TAG.equals(qName)){
				Page page = (Page) object;
				try {
					iRETokenizer.tokenize(page.getTitle(),PAGE_TITLE_SECTION,stemmer);
					indexer.createIndex(pageId,iRETokenizer);
					iRETokenizer = new IRETokenizer();
					totalPages+=1;
				} catch (IOException e) {
					System.out.println("Error while creating token in page title");
					e.printStackTrace();
				}
			}else if(REVISION_TAG.equals(qName)){
				Revision revision = (Revision) object ;
				//long start1 = System.currentTimeMillis();
				try {
					iRETokenizer.tokenize(revision.getText(),REVISION_TEXT,stemmer);
					//iRETokenizer.tokenize(revision.getComment(),REVISION_COMMENT,stemmer);
				} catch (IOException e) {
					System.out.println("Error while creating token in revision text");
					e.printStackTrace();
				}
				//long start2 = System.currentTimeMillis();
				//System.out.println((start2 - start1) + " ms in revision text");
			}/*else if (CONTRIBUTOR_TAG.equals(qName)){
				Contributor contributor = (Contributor) object;
				//long start1 = System.currentTimeMillis();
				try {
					iRETokenizer.tokenize(contributor.getUsername(),CONTRIBUTOR_USER_NAME,stemmer);
				} catch (IOException e) {
					System.out.println("Error while creating token in contributor user name");
					e.printStackTrace();
				}
				//long start2 = System.currentTimeMillis();
				//System.out.println((start2 - start1) / 1000f + " seconds in user name");
			}*/
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	public void characters(char ch[], int start, int length) throws SAXException {
		String value = new String(ch, start, length).trim();
		if(value.length() == 0) return; // ignore white space
		if(ID_TAG.equals(currentElement()) && PAGE_TAG.equals(currentElementParent())){
			Page page = (Page) this.objectStack.peek();
			page.setId((page.getId() != null ? page.getId()  : "") + value);
			pageId = page.getId();
		} else if(TITLE_TAG.equals(currentElement()) && PAGE_TAG.equals(currentElementParent())){
			Page page = (Page) this.objectStack.peek();
			page.setTitle((page.getTitle() != null ? page.getTitle()  : "") + value);
		} else if(ID_TAG.equals(currentElement()) && REVISION_TAG.equals(currentElementParent())){
			Revision revision = (Revision) this.objectStack.peek();
			revision.setId((revision.getId() != null ? revision.getId()  : "") + value);
		}/*else if("timestamp".equals(currentElement()) && "revision".equals(currentElementParent())){
			Revision revision = (Revision) this.objectStack.peek();
			revision.setTimestamp((revision.getTimestamp() != null ? revision.getTimestamp()  : "") + value);
		}else if("minor".equals(currentElement()) && "revision".equals(currentElementParent())){
			Revision revision = (Revision) this.objectStack.peek();
			revision.setMinor((revision.getMinor() != null ? revision.getMinor()  : "") + value);
		}else if(COMMENT_TAG.equals(currentElement()) && REVISION_TAG.equals(currentElementParent())){
			Revision revision = (Revision) this.objectStack.peek();
			revision.setComment((revision.getComment() != null ? revision.getComment()  : "") + value);
		}*/else if(TEXT_TAG.equals(currentElement()) && REVISION_TAG.equals(currentElementParent())){
			Revision revision = (Revision) this.objectStack.peek();
			revision.setText((revision.getText() != null ? revision.getText()  : "") + value);
		}/*else if(USER_NAME_TAG.equals(currentElement()) && CONTRIBUTOR_TAG.equals(currentElementParent())){
			Contributor contributor = (Contributor) this.objectStack.peek();
			contributor.setUsername((contributor.getUsername() != null ? contributor.getUsername()  : "") + value);
		}else if("id".equals(currentElement()) && "contributor".equals(currentElementParent())){
			Contributor contributor = (Contributor) this.objectStack.peek();
			contributor.setId((contributor.getId() != null ? contributor.getId()  : "") + value);
		}else if("ip".equals(currentElement()) && "contributor".equals(currentElementParent())){
			Contributor contributor = (Contributor) this.objectStack.peek();
			contributor.setIp((contributor.getIp() != null ? contributor.getIp()  : "") + value);
		}*/
	}


	private String currentElement() {
		return this.elementStack.peek();
	}

	private String currentElementParent() {
		if(this.elementStack.size() < 2) return null;
		return this.elementStack.get(this.elementStack.size()-2);
	}

	/**
	 * @param stemRules
	 * @return
	 */
	private Stemmer readRules (String stemRules){
		int ruleCount=0;
		int j=0;
		Vector<String> ruleTable = new Vector<>();
		int[] ruleIndex = new int[26];
		Stemmer stemmer = new Stemmer();
		//Acquire each rule in turn. They each take up one line
		try{
			FileReader fr = new FileReader(stemRules);
			BufferedReader br = new BufferedReader(fr);
			String line = " ";

			try{
				while ((line=br.readLine())!= null){
					ruleCount++;
					j=0;
					String rule = new String();
					rule ="";
					while ((j < line.length()) && (line.charAt(j) != ' ')){
						rule+= line.charAt(j);
						j++;
					}
					ruleTable.addElement(rule);
				}
				stemmer.setRuleTable(ruleTable);
			}catch(Exception e){
				System.err.println("File Error Durring Reading Rules"+e);
				System.exit(0);
			}
			// try to close file, file is not needed again so if can't close don't exit
			try
			{
				fr.close();
			}
			catch(Exception e)
			{
				System.err.println("Error Closing File During Reading Rules");
			}
		}
		catch(Exception e)
		{
			System.err.println("Input File" + stemRules +"not found");
			System.exit(1);
		}


		// Now assign the number of the first rule that starts with each letter
		// (if any) to an alphabetic array to facilitate selection of sections

		char ch ='a';
		for(j=0; j <25; j++)
		{
			ruleIndex[j] = 0;
		}

		for (j=0; j<(ruleCount-1); j++)
		{
			while( ((String) ruleTable.elementAt(j)).charAt(0) != ch)
			{
				ch++;
				ruleIndex[((int) ch)-97] = j;
			}
		}
		stemmer.setRuleIndex(ruleIndex);
		return stemmer;
	}

}