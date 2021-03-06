package database;

public class LastAction {
	
	enum Type {
		UPDATE,
		DELETE
	}
	
	Type actionType;
	
	Item item;
	
	public LastAction(Item item, Type type) {
		this.item = item;
		this.actionType = type;
	}

	public Item getItem() {
		return item;
	}
	
	public Type getType() {
		return actionType;
	}

}
