package org.dsikkema.orm.orm.entity;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;
import org.dsikkema.orm.orm.entity.property.Type;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import org.w3c.dom.Document;

public class EntityDefinition {
	private Map<String, PropertyDefinition> propertyDefinitions;
	private String entityType;
	
	private EntityDefinition(String entityType) {
		this.propertyDefinitions = new HashMap<String, PropertyDefinition>();
		this.scan(entityType);
		this.entityType = entityType;
	}

	public String getEntityType() {
		return entityType;
	}
	
	public Map<String, PropertyDefinition> getPropertyDefinitions() {
		return Collections.unmodifiableMap(this.propertyDefinitions);
	}
	
	public boolean containsProperty(String property) {
		return this.propertyDefinitions.containsKey(property);
	}
	
	public PropertyDefinition getPropertyDefinition(String property) {
		this.verifyPropertyExists(property);
		return this.propertyDefinitions.get(property);
	}
	
	public boolean isPropertyRequired(String property) {
		this.verifyPropertyExists(property);
		return this.propertyDefinitions.get(property).isRequired();
	}
	
	public static class Factory {
		public EntityDefinition create(String entityType) {
			return new EntityDefinition(entityType);
		}
	}
	
	private void verifyPropertyExists(String property) {
		if (!this.containsProperty(property)) {
			throw new RuntimeException(String.format("Property '%s' is undefined for entity type '%s'", property, this.getEntityType()));
		}
	}
	
	/**
	 * For now, assume valid xml file
	 */
	private void scan(String entityType) {
		Document doc;
		try {
			File inputFile = new File("src/main/resources/orm.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		doc.getDocumentElement().normalize();
		
		// get entity node
		NodeList entityList = doc.getElementsByTagName("entity");
		Node entityNode = null;
		for (int i = 0; i < entityList.getLength(); i++) {
			Node node = entityList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE && ((Element)node).getAttribute("name").equals(entityType)) {
				entityNode = entityList.item(i);
			}
		}
		
		if (entityNode == null) {
			throw new RuntimeException("Entity " + entityType + " not found in orm.xml");
		}
		
		String name;
		boolean isRequired;
		Type type;
		String defaultValue = "";
		
		// populate definition list
		for (int i=0; i<entityNode.getChildNodes().getLength(); i++) {
			Node node = entityNode.getChildNodes().item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element)node;
				name = element.getAttribute("name");
				isRequired = element.getAttribute("required") == "true" ? true : false;
			
				switch (element.getAttribute("type")) {
					case "int" :
						type = Type.INT;
						break;
					case "string" : 
					default:
						type = Type.STRING;
						break;
				}
				if (!isRequired) {
					defaultValue = element.getAttribute("default");
				}
				
				this.propertyDefinitions.put(name, new PropertyDefinition(name, isRequired, type, defaultValue));
			}
		}
	}
}































