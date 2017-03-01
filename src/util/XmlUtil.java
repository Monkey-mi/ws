package util;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.headers.Header;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class XmlUtil {
	private static DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	private static DocumentBuilder builder;
   public static Document init(){
	   Document document;
	   if(builder==null){
		   try {
			builder=factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   document=builder.newDocument();
	   document.setXmlStandalone(true);
	   return document;
   }
   public static Element createElement(Document document,String name,String value){
	   Element el=document.createElement(name);
	   if(value!=null){
		   el.appendChild(document.createTextNode(value));
	   }
	   return el;
   }
   public static String getTextFromDocument(Node doc) {
	   DOMSource source=new DOMSource(doc);
	   StringWriter writer=new StringWriter();
	   Result resultStr =new StreamResult(writer);
	   String xmlStr = null;
	   try {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, resultStr);
		xmlStr = writer.getBuffer().toString();
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated ca tch block
		e.printStackTrace();
	} catch (TransformerFactoryConfigurationError e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return xmlStr;
   }

   public static String getSOAPUserName(Map<String, Object> message){
	   @SuppressWarnings("unchecked")
		List<Header> headerList = (List<Header>) message.get(Header.HEADER_LIST);
		if(headerList!=null && headerList.size()>0){
			Element element = (Element)headerList.get(0).getObject();
			String username = element.getElementsByTagName("wsse:Username").item(0).getTextContent();
			return username;
		}else{
			return null;
		}
   }
}
