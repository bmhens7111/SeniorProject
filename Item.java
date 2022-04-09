package database;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Item {
	private int itemId;
	private String itemName;
	private int itemQuantity;
	private String[] itemTags;
	private DateTimeFormatter dtf;
	private LocalDate dateAdded;
	private final int TAGSLENGTH = 9;

	public Item() {
		itemId = 0;
		itemName = "";
		itemQuantity = 0;
		itemTags = new String[9];
		dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu");
		dateAdded = LocalDate.now();
	}
	
	public void setId(int id) {
		itemId = id;
	}
	
	public int getId() {
		return itemId;
	}
	
	public void setName(String name) {
		itemName = name;
	}
	
	public String getName() {
		return itemName;
	}
	
	public void setQuantity(Integer quantity) {
		itemQuantity = quantity;
	}
	
	public int getQuantity() {
		return itemQuantity;
	}
	
	public void setTags(int index, String tag) {
		itemTags[index] = tag;
	}
	
	public String getTags() {
		String tagString = "";
		String temp = "";
		for (int i=0; i<itemTags.length; i++) {
			if (itemTags[i] != null && i != TAGSLENGTH) {
				tagString = tagString.concat(itemTags[i] + ", ");
			}
			else if (i == TAGSLENGTH) {
				tagString = tagString.concat(itemTags[i]);
			}
			else {
				//Do nothing
			}
		}
		return tagString;
	}
	
	public String getDateAdded() {
		return dtf.format(dateAdded);
	}
	
	public boolean checkValid(Item item) {
		if (item.getId() > 0 && item.getQuantity() >= 0) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
