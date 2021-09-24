package by.project.news.util;

public enum UserRole {
	
	USER("user"),
	ADMIN("admin"),
	EDITOR("editor"),
	;
	
	private String string;

	UserRole(String string) {
		this.string = string;
	}

	public String getRole() {
		return string;
	}

}
