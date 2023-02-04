package eg.com.workmotion.employee.helper;

public enum Status {
	ADDED(1), IN_CHECK(2), APPROVED(3), ACTIVE(4);

	private final int index;

	Status(int index) {
		this.index = index;

	}

	int getIndex() {
		return this.index;
	}

	public boolean canChangeTo(Status state) {
		return state.getIndex() - this.getIndex() == 0 || state.getIndex() - this.getIndex() == 1;
	}
}
