package priv.linjb.entity;

public class User {

	private String name;
	private Integer age;
	private Integer sex;
	
	public User(){
		
	}
	public User(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public User(String name, int age,int sex) {
		super();
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}
	
	
}
