
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class IREIndexer {

	private Map<String, StringBuilder> tokenList;
	private Map<String, List<Map<String,Map<String, Float>>>> tokenList2;
	NumberFormat nf = NumberFormat.getInstance();

	public IREIndexer() {
		tokenList = new TreeMap<>();
		tokenList2 = new TreeMap<>();
		nf.setMaximumFractionDigits(3);
	}

	/**
	 * @return the tokenList
	 */
	public Map<String, StringBuilder> getTokenList() {
		return tokenList;
	}

	/**
	 * @param tokenList the tokenList to set
	 */
	public void setTokenList(Map<String, StringBuilder> tokenList) {
		this.tokenList = tokenList;
	}

	/**
	 * @return the tokenList2
	 */
	public Map<String, List<Map<String,Map<String, Float>>>> getTokenList2() {
		return tokenList2;
	}

	/**
	 * @param tokenList2 the tokenList2 to set
	 */
	public void setTokenList2(Map<String, List<Map<String,Map<String, Float>>>> tokenList2) {
		this.tokenList2 = tokenList2;
	}

	//token::pageid:section<Frequency>:section2<frequency>::pageid2
	/**
	 * @param pageId
	 * @param tokenizer
	 */

	public void createIndex(String pageId, IRETokenizer tokenizer){
		int totalWordsInPage = tokenizer.getWordList().size();
		for (String token: tokenizer.getWordList()){
			if(token != null && !"".equals(token)){
				int tokenFreq = 0;
				Map<String,Map<String, Float>> postings = new HashMap<>();
				Map<String, Float> sectionFrequency = new HashMap<>();
				List<Map<String,Map<String, Float>>> postingList = new ArrayList<>();
				
				if ( this.tokenList2!= null && this.tokenList2.get(token) != null){
					postingList = this.tokenList2.get(token);
				}
				Integer freq = tokenizer.getTextTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.REVISION_TEXT,(float)freq);
				}
				freq = tokenizer.getPageTitleTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.PAGE_TITLE_SECTION,(float)freq);
				}
				freq = tokenizer.getCategoriesTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.CATEGORIES,(float)freq);
				}
				freq = tokenizer.getInfoBoxTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.INFOBOX,(float)freq);
				}
				freq = tokenizer.getReferencesTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.REFERENCES,(float)freq);
				}
				freq = tokenizer.getPageLinksTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.PAGE_LINKS,(float)freq);
				}
				freq = tokenizer.getExternalLinksTokens().get(token);
				if (freq != null ){
					tokenFreq+=freq;
					sectionFrequency.put(IreSaxHandler.EXTERNAL_LINKS,(float)freq);
				}

				float tf = termFrequency(tokenFreq,totalWordsInPage);
				sectionFrequency.put("tf",tf);
				postings.put(pageId, sectionFrequency);
				postingList.add(postings);
				this.tokenList2.put(token, postingList);
			}
		}
		//long start2 = System.currentTimeMillis();
		//System.out.println((start2 - start1) + " ms @@@");
	}

	// Method to calculate termFrequency (TF)
	public  float termFrequency(int tokenCount,float totalterms) {
		return tokenCount / totalterms;
	}


}
