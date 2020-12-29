package application.algorithm;

public class Penalty {
	String userName;
	Long penalty;

	public Penalty() {
		super();
	}

	public Penalty(String userName, Long penalty) {
		super();
		this.userName = userName;
		this.penalty = penalty;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getPenalty() {
		return penalty;
	}

	public void setPenalty(Long penalty) {
		this.penalty = penalty;
	}

	@Override
	public String toString() {
		return this.userName + " | " + this.penalty.toString();
	}

	@Override
	public boolean equals(Object penalty) {
		if (this.userName.equals(((Penalty) penalty).userName) && this.penalty.equals(((Penalty) penalty).penalty))
			return true;
		return false;
	}
}