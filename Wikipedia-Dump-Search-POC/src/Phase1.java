
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

//TODO:
//Time : ~2 mins : done(2.88 mins)
//File Size : ~25 MB : done (24 MB)
//Category : done
//Info box : done
//References : done
//Page Links : done
//External Links : done
//index compression 
//rank functions
//secondary index
//Use B+ tree 


public class Phase1 {
	public static void main (String args []) throws IOException {
		if (args.length  == 0){
			final String inputFilePath="C:\\Users\\agaan07\\Desktop\\evaluation\\sample2.xml";
			final String outputFilePath="C:\\Users\\agaan07\\Desktop\\evaluation\\index2.dat";
			FileWriter writer = null;
			FileInputStream xmlInput = null;
			InputSource input = null;
			Reader reader = null;
			try {
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser=factory.newSAXParser();
				IreSaxHandler handler= new IreSaxHandler();
				xmlInput=new FileInputStream(inputFilePath);

				//setting the encoding to UTF-8
				reader = new InputStreamReader(xmlInput, "UTF-8");
				input = new InputSource(reader);

				//parsing the given xml
				long start = System.currentTimeMillis();
				saxParser.parse(input, handler);
				long start2 = System.currentTimeMillis();
				System.out.println((start2 - start)  + " ms in parsing and indexing the xml");
				long totalPages = handler.getTotalPages();
				//writing the index to file
				writer = new FileWriter(new File(outputFilePath));

				//Map<String,StringBuilder> tokensList =  handler.getIndexer().getTokenList();
				Map<String, List<Map<String,Map<String, Float>>>> tokensList =  handler.getIndexer().getTokenList2();
				StringBuilder postingBuffer =null;
				for (Map.Entry<String,List<Map<String,Map<String, Float>>>> tokenPostingList : tokensList.entrySet()){
					postingBuffer = new StringBuilder();
					int total_doc_term = tokenPostingList.getValue().size();
					double idf = calculateIDF(totalPages,total_doc_term);
					for (Map<String,Map<String, Float>> postings : tokenPostingList.getValue()){
						for (Map.Entry<String, Map<String, Float>> posting : postings.entrySet()){
							Map<String, Float> tmp = posting.getValue();
							
							float tf = tmp.get("tf");
							postingBuffer.append("::").append(posting.getKey()).append(":").append(String.format("%.3f",tf*idf));
							
							Float freq = tmp.get(IreSaxHandler.REVISION_TEXT);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.REVISION_TEXT).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.PAGE_TITLE_SECTION);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.PAGE_TITLE_SECTION).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.CATEGORIES);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.CATEGORIES).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.INFOBOX);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.INFOBOX).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.REFERENCES);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.REFERENCES).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.PAGE_LINKS);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.PAGE_LINKS).append(freq.intValue());
							}
							freq = tmp.get(IreSaxHandler.EXTERNAL_LINKS);
							if (freq != null ){
								postingBuffer.append(":").append(IreSaxHandler.EXTERNAL_LINKS).append(freq.intValue());
							}
						}
					} 
					writer.write(tokenPostingList.getKey()+postingBuffer.toString()+"\n");
				}
				long end = System.currentTimeMillis();
				System.out.println((end - start2)  + " ms in writing");
				tokensList.clear();
			} catch (Throwable err) {
				err.printStackTrace ();
			}finally{
				writer.flush();
				writer.close();
				reader.close();
			}
		}else{
			System.out.println("Program takes one input. directory path of the sample.xml file.");
		}
	}

	

	//Method to calculate inverted document index (IDF)
	public static double calculateIDF(long totalPages, int totalPageOccurence) {
		return 1 + Math.log(totalPages / totalPageOccurence);
	}
}



