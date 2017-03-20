package org.dsikkema.orm.orm.entity;

import java.io.File;
import java.util.Collections;
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
			File inputFile = new File("orm.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		NodeList entityList = doc.getDocumentElement().getElementsByTagName("entities").item(0).getChildNodes();
		Node entityNode = null;
		for (int i = 0; i < entityList.getLength(); i++) {
			if (entityList.item(i).getAttributes().getNamedItem("name").equals(entityType)) {
				entityNode = entityList.item(i);
			}
		}
		
		if (entityNode == null) {
			// fail
		}
		
		String name;
		boolean isRequired;
		Type type;
		String defaultValue = "";
		
		for (int i=0; i<entityNode.getChildNodes().getLength(); i++) {
			NamedNodeMap property = entityNode.getChildNodes().item(i).getAttributes();
			name = property.getNamedItem("name").getNodeValue();
			isRequired = property.getNamedItem("required").getNodeValue() == "true" ? true : false;
			switch (property.getNamedItem("type").getNodeValue()) {
				case "int" :
					type = Type.INT;
					break;
				case "string" : 
				default:
					type = Type.STRING;
					break;
			}
			
			if (!isRequired) {
				defaultValue = property.getNamedItem("default").getNodeValue();
			}
			
			this.propertyDefinitions.put(name, new PropertyDefinition(name, isRequired, type, defaultValue));
		}
	}
}































