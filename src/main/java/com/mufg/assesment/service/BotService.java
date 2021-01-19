package com.mufg.assesment.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.mufg.assesment.constants.Constants;
import com.mufg.assesment.dto.Move;
import com.mufg.assesment.dto.Position;
import com.mufg.assesment.payload.Input;
import com.mufg.assesment.payload.Output;

@Service
public class BotService {

	public String updatePosition(Input input) throws Exception {
		String result = Constants.SUCCESS_MESSAGE.getValue();
		try {
			updateXML(input);
		} catch (Exception e) {
			result = Constants.FAILURE_MESSAGE.getValue();
			e.printStackTrace();
		}
		return result;
	}

	public Output getCurrentPosition() throws Exception {
		return getLastPosition();
	}

	private Output getLastPosition() throws Exception {
		Output result = new Output();
		try {
			File xmlFile = new File(Constants.FILE_PATH.getValue());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			if (xmlFile.exists()) {
				Document doc = dBuilder.parse(xmlFile);
				NodeList positions = doc.getElementsByTagName("position");
				Element node = (Element) positions.item(positions.getLength() -  1);
				result.setPosition(new Position(node.getAttribute("direction"), 
						Integer.valueOf(node.getAttribute("x")), Integer.valueOf(node.getAttribute("y"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void updateXML(Input input) throws Exception {
		try {
			File xmlFile = new File(Constants.FILE_PATH.getValue());
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Element mainElement = null;
			Document doc = null;
			if (!xmlFile.exists()) {
				xmlFile.createNewFile();
				doc = dBuilder.newDocument();
				mainElement = doc.createElement("positions");
				doc.appendChild(mainElement);
			} else {
				doc = dBuilder.parse(xmlFile);
				mainElement = (Element) doc.getElementsByTagName("positions").item(0);
			}

			Element rootElement = doc.createElement("position");
			mainElement.appendChild(rootElement);

			Attr attr1 = doc.createAttribute("direction");
			attr1.setValue(input.getPosition().getDirection());
			rootElement.setAttributeNode(attr1);

			Attr attr2 = doc.createAttribute("x");
			attr2.setValue((String.valueOf(input.getPosition().getX())));
			rootElement.setAttributeNode(attr2);

			Attr attr3 = doc.createAttribute("y");
			attr3.setValue(String.valueOf(input.getPosition().getX()));
			rootElement.setAttributeNode(attr3);
			// supercars element
			Element moves = doc.createElement("moves");
			for (Move move : input.getMove()) {
				Element moveTag = doc.createElement("move");

				Attr attro = doc.createAttribute("o");
				attro.setValue(move.getO());
				moveTag.setAttributeNode(attro);

				Attr attrl = doc.createAttribute("l");
				attrl.setValue(move.getL());
				moveTag.setAttributeNode(attrl);

				Attr attrr = doc.createAttribute("r");
				attrr.setValue(move.getR());
				moveTag.setAttributeNode(attrr);

				Attr attrf = doc.createAttribute("f");
				attrf.setValue(move.getF());
				moveTag.setAttributeNode(attrf);

				Attr attrb = doc.createAttribute("b");
				attrb.setValue(move.getB());
				moveTag.setAttributeNode(attrb);

				moves.appendChild(moveTag);

			}
			rootElement.appendChild(moves);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw e;
		}
	}
}
