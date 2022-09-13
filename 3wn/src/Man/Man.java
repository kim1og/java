package Man;


class Man {
	private String name;
	public Man(String name) 
	{
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
};

class BusinessMan extends Man{
	private String company;
	private String role;
	
	BusinessMan(String name, String company, String role)
	{
		super(name);
		this.company = company;
		this.role = role;
	}
	
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void print(BusinessMan man) 
	{
		System.out.println("My name is " + man.getName());
		System.out.println("My company is " + man.getCompany());
		System.out.println("My role is " + man.getRole());
	}
	
	public static void main(String[] args) 
	{
		BusinessMan man = new BusinessMan("Jack","Hannam", "Student");
		man.print(man);
	}
};

