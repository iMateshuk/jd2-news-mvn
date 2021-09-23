package by.project.news.util;

public enum UserRole {
	
	ROLE_USER("user"),
	ROLE_ADMIN("admin"),
	ROLE_EDITOR("editor"),
	;
	
	private String string;

	UserRole(String string) {
		this.string = string;
	}

	public String getRole() {
		return string;
	}

}
